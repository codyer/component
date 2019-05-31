# component
android-component
## 模块化编程

## 抽取各个通用模块

## 通过包含指定名字（local）的tag或者branch 实现推送到本地仓库

## 通过包含指定名字（remote）的tag或者branch 实现依赖远程仓库

## 通过包含指定名字（component）的tag或者branch 实现排除 compiler 打出一个总组件的依赖包

## 通过包含指定名字（compiler）的tag或者branch 实现排除 component 打出一个总注解处理器的包

## 调试远端代码
#1、打开gradle.properties 里面的两行代码注释
org.gradle.jvmargs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5033
-Dorg.gradle.debug=true
#2、在Android Studio建立通过 edit configurations 创建 Remote Debugger。
#3、在Android Studio Terminal控制台中输入gradlew --daemon来启动守护线程。 切换到新建的debugger
#4、在Android Studio Terminal控制台中输入gradlew clean assembleDebug