/*
 * ************************************************************
 * 文件：RemoteDataSourceProcessor.java  模块：http-compiler  项目：component
 * 当前修改时间：2019年04月06日 16:38:35
 * 上次修改时间：2019年04月03日 18:59:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.live.event.bus.compiler;

import com.cody.http.lib.annotation.Domain;
import com.cody.http.lib.exception.GenerateDataSourceException;
import com.cody.http.lib.exception.InvalidDefineException;
import com.cody.live.event.bus.compiler.bean.MethodBean;
import com.cody.live.event.bus.compiler.bean.DomainBean;
import com.cody.live.event.bus.compiler.bean.DataSourceInfoBean;
import com.cody.live.event.bus.compiler.bean.ParameterBean;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * Created by xu.yi. on 2019/4/2.
 * 根据注解自动生成 remoteDataSource 实现类
 */
@AutoService(Processor.class)
public class RemoteDataSourceProcessor extends AbstractProcessor {
    private static final String TAG = "[RemoteDataSourceProcessor]";

    private static final String VIEW_MODEL_CLASS = "com.cody.component.handler.BaseViewModel";
    private static final String BASE_DATA_SOURCE_CLASS = "com.cody.http.core.BaseRemoteDataSource";
    private static final String CALL_BACK_CLASS = "com.cody.http.core.callback.RequestCallback";
    private static final String CALL_BACK = "callback";

    private static final String GEN_PKG = ".remote";
    private static final String INTERFACE_NAME_PREFIX = "I";
    private static final String CLASS_NAME_SUFFIX = "$DataSource";

    private static final String FILE_DESCRIPTION = "\nRemoteDataSource class auto generate. don't modify !!!\n";
    private Elements mElementUtils;
    private Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //初始化我们需要的基础工具
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Domain.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver()) {
            processAnnotations(roundEnvironment);
        }
        return true;
    }

    private void processAnnotations(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Domain.class)) {
            if (element.getKind() == ElementKind.INTERFACE) {//Domain 注解在接口上
                DataSourceInfoBean info = new DataSourceInfoBean();
                TypeElement typeElement = (TypeElement) element;
                PackageElement packageElement = mElementUtils.getPackageOf(element);
                info.setPackageName(packageElement.getQualifiedName().toString());
                info.setClassName(typeElement.getSimpleName().toString());
                info.setDomainBean(getDomainBean(element));
                List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
                for (Element e : enclosedElements) {
                    if (e.getKind() == ElementKind.METHOD) {//接口定义的所有方法
                        ExecutableElement methodElement = (ExecutableElement) e;
                        MethodBean methodBean = getMethod(methodElement);
                        info.addMethodBean(methodBean);
                    }
                }
                generateDataSourceInterfaceClass(info);
                generateDataSourceClass(info);
            } else {
                throw new InvalidDefineException();
            }
        }
    }

    /**
     * 获取注解域名
     */
    private DomainBean getDomainBean(Element domainElement) {
        DomainBean domainBean = new DomainBean();
        List<? extends AnnotationMirror> annotationMirrors = mElementUtils.getAllAnnotationMirrors(domainElement);
        if (annotationMirrors != null && annotationMirrors.size() > 0) {
            for (AnnotationMirror mirror : annotationMirrors) {
                if (Domain.class.getName().equals(mirror.getAnnotationType().toString())) {
                    if (mirror.getElementValues() != null) {
                        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : mirror.getElementValues().entrySet()) {
                            String key = entry.getKey().getSimpleName().toString();
                            if (("value").equals(key)) {
                                domainBean.setHost(entry.getValue().getValue().toString());
                                break;
                            }
                        }
                    }
                }
            }
        }
        return domainBean;
    }

    private MethodBean getMethod(ExecutableElement methodElement) {
        MethodBean methodBean = new MethodBean();
        String methodName = methodElement.getSimpleName().toString();
        TypeMirror returnTypeMirror = methodElement.getReturnType();
        methodBean.setName(methodName);
        String type = Util.typeToString(returnTypeMirror);
        if (type.startsWith("io.reactivex.Observable<")) {//去掉最外层的 Observable
            type = Util.innerTypeToString(type);
        } else {
            throw new GenerateDataSourceException("接口定义方法返回值错误，请用 io.reactivex.Observable 作为返回值");
        }
        if (type.startsWith("com.cody.http.lib.bean.Result<")) {//去掉最外层的 Result
            type = Util.innerTypeToString(type);
            methodBean.setOriginal(false);
        } else {
            methodBean.setOriginal(true);
        }
        methodBean.setType(type);
        List<? extends VariableElement> parameters = methodElement.getParameters();
        if (parameters != null && parameters.size() > 0) {
            for (VariableElement parameter : parameters) {
                ParameterBean parameterBean = new ParameterBean();
                parameterBean.setName(parameter.getSimpleName().toString());
                if (parameter.asType().getKind().isPrimitive()) {
                    parameterBean.setType(Util.box((PrimitiveType) parameter.asType()));
                } else {
                    parameterBean.setType(ClassName.bestGuess(parameter.asType().toString()));
                }
                methodBean.addParameter(parameterBean);
            }
        }
        return methodBean;
    }

    /**
     * 创建数据源接口类文件
     */
    private void generateDataSourceInterfaceClass(DataSourceInfoBean infoBean) {
        String interfaceName = INTERFACE_NAME_PREFIX + infoBean.getClassName() + CLASS_NAME_SUFFIX;
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(interfaceName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc(infoBean.getDomainBean().getHost())
                .addJavadoc(FILE_DESCRIPTION);
        for (MethodBean method : infoBean.getMethodBeans()) {
            TypeName parameterType = getCallBackTypeName(method);
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(method.getName())
                    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
            for (ParameterBean parameter : method.getParameters()) {
                methodBuilder = methodBuilder.addParameter(parameter.getType(), parameter.getName());
            }
            methodBuilder.addParameter(parameterType, CALL_BACK).returns(TypeName.VOID);
            builder.addMethod(methodBuilder.build());
        }

        TypeSpec typeSpec = builder.build();
        String packageName = infoBean.getPackageName() + GEN_PKG;
        infoBean.setInterfaceTypeName(ClassName.get(packageName, interfaceName));
        try {
            JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据源实现类文件
     */
    private void generateDataSourceClass(DataSourceInfoBean infoBean) {
        String className = infoBean.getClassName() + CLASS_NAME_SUFFIX;
        TypeName superTypeName = ClassName.bestGuess(BASE_DATA_SOURCE_CLASS);
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(superTypeName)
                .addSuperinterface(infoBean.getInterfaceTypeName())
                .addJavadoc(infoBean.getDomainBean().getHost())
                .addJavadoc(FILE_DESCRIPTION);
        TypeName viewModelTypeName = ClassName.bestGuess(VIEW_MODEL_CLASS);
        if (viewModelTypeName != null) {
            builder.addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(viewModelTypeName, "baseViewModel")
                    .addStatement("super($N);", "baseViewModel")
                    .build());
        }
        for (MethodBean method : infoBean.getMethodBeans()) {
            TypeName callBackTypeName = getCallBackTypeName(method);
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(method.getName())
                    .addModifiers(Modifier.PUBLIC);
            for (ParameterBean parameter : method.getParameters()) {
                methodBuilder = methodBuilder.addParameter(parameter.getType(), parameter.getName());
            }
            String methodExecute = method.isOriginal() ? "executeOriginal" : "execute";
            TypeName serviceTypeName = ClassName.get(infoBean.getPackageName(), infoBean.getClassName());
            methodBuilder.addParameter(callBackTypeName, CALL_BACK)
                    .addAnnotation(Override.class)
                    .addCode("$N(getService($T.class)."/*execute,service*/, methodExecute, serviceTypeName)
                    .addCode("$L("/*invoke*/, method.getName());
            int i = 0;
            if (method.getParameters() != null && method.getParameters().size() > 0) {
                for (; i < method.getParameters().size() - 1; i++) {
                    methodBuilder.addCode("$N,"/**/, method.getParameters().get(i).getName());
                }
                methodBuilder.addCode("$N),$N);", method.getParameters().get(i).getName(), CALL_BACK);
            } else {
                methodBuilder.addCode("),$N);", CALL_BACK);
            }
            methodBuilder.returns(TypeName.VOID);
            builder.addMethod(methodBuilder.build());
        }

        TypeSpec typeSpec = builder.build();
        String packageName = infoBean.getPackageName() + GEN_PKG;
        try {
            JavaFile.builder(packageName, typeSpec).build().writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TypeName getCallBackTypeName(MethodBean method) {
        ClassName callbackClassName = ClassName.bestGuess(CALL_BACK_CLASS);
        TypeName parameterType;//添加的callback参数类型
        String resultBeanType = method.getType();
        if (resultBeanType == null || resultBeanType.length() == 0) {
            parameterType = ParameterizedTypeName.get(callbackClassName, ClassName.get(Object.class));
        } else {
            TypeName callbackType = ClassName.bestGuess(resultBeanType);
            if (callbackType != null) {
                parameterType = ParameterizedTypeName.get(callbackClassName, callbackType);
            } else {
                parameterType = ParameterizedTypeName.get(callbackClassName, TypeVariableName.get(resultBeanType));
            }
        }
        return parameterType;
    }
}
