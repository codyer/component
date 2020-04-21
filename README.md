# 模块化编程

+ 模块化编程的“最佳”实践事例，需要的模块直接引用就可以，
+ 创建本地仓库和远端仓库，提高编译效率


# 使用技术

|        技术             |               说明                   |
|------------------------|--------------------------------------|
|       MVVM             |   Model和View通过VM进行绑定的设计模式    |
|       databinding      |   数据绑定实现MVVM设计模式              |
|       APT              |   Android注解处理工具进行代码生成        |
|       LiveData         |   生命周期绑定的数据对象                |
|       Room             |   数据库                              |
|       PageList & Paging|   数据分页                            |
|       Retrofit         |   网络请求                            |
|       channel          |   多渠道打包                          |
|       multi module     |   多模块依赖、模块化                    |
|       version ext      |   统一变量进行版本管理                  |
|       glide            |   图片加载通过bindingAdapter统一处理    |
|       other            |   其他第三方依赖处理常规问题             |


# 包含模块
+ app-demo --> 模块使用事例
+ marquee-core --> 滚动广告布局
+ update-core --> 升级更新 
+ blues-core --> crash捕获
+ banner-core --> 滚动广告组建 
+ [cat-core](https://github.com/codyer/component/blob/master/cat-core/README.md) --> 数据监听 监控猫
+ hybrid-core --> hybrid方案
+ image-core --> 图片浏览，扫码，身份证，营业执照拍摄等照片相关 
+ app-core --> 利用databinding实现MVVM底层BASE模块
+ http-core --> 对retrofit封装
+ http-compiler --> 通过APT实现模版代码自动生成，减少重复工作量
+ bus-core --> 利用livedata实现的event-bus
+ bus-compiler --> 定义事件后，通过APT实现模版代码自动生成，减少重复工作量
+ 其他底层工具类和UI组件


# 项目结构图

[项目结构](https://github.com/codyer/component/blob/master/structure/APP-structure.xmind)


<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejsmvqtj311c0oitbv.jpg" width="50%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtdeohj30ue0jy75a.jpg" width="50%">

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtl5upj30uv0i4q3u.jpg" width="48.2%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejt0qm1j31210kr0u8.jpg" width="51.8%">


# 仓库说明

- 本地仓库
  自己开发时，可以使用本地仓库离线开发，提高编译效率
  
- 远程仓库
  团队合作时，可以使用远程仓库


## 本地和远程仓库通过分支或者tag进行自动区分
  
  通过分支名来实现不同的依赖方式，免去了总是切换参数的麻烦，直接使用不同分支实现

- 通过包含指定名字（local）的tag或者branch 实现推送到本地仓库

- 通过包含指定名字（remote）的tag或者branch 实现依赖远程仓库

- 通过包含指定名字（component）的tag或者branch 实现排除 compiler 打出一个总组件的依赖包

- 通过包含指定名字（compiler）的tag或者branch 实现排除 component 打出一个总注解处理器的包


# 调试APT代码

APT会用到调试，调试步骤如下
1. 打开gradle.properties 里面的两行代码注释
org.gradle.jvmargs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5033
-Dorg.gradle.debug=true
2. 在Android Studio建立通过 edit configurations 创建 Remote Debugger。
3. 在Android Studio Terminal控制台中输入gradlew --daemon来启动守护线程。 切换到新建的debugger
4. 在Android Studio Terminal控制台中输入gradlew clean assembleDebug


# 接入说明
+ 单模块依赖请查看各个模块说明文件
+ 整体component依赖说明如下

* 1） 在项目根目录的build.gradle中添加jitpack依赖
```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }  // <===添加这行
        google()
        jcenter()
    }
}
```

* 2） 在应用的build.gradle中开启dataBinding并且添加依赖
```
android {
  .
  .
  .
  dataBinding.enabled = true
  .
  .
  .
}
```

```
dependencies {
  .
  .
  .
  implementation 'com.github.codyer.component:core:1.0.66'
  annotationProcessor 'com.github.codyer.component:compiler:1.0.66'
  .
  .
  .
}
```

* 3） Application继承BaseApplication并在Application中初始化
```
                .
                .
                .
 ImagePicker.init();
        HttpCore.init(this)
                .withLog(true)
                .withHttpCat(HttpCat.create(this))
                .withHttpHeader(new HeaderParameterInterceptor())
                .done();
                .
                .
                .
```


* 4）具体初始化方式可以参考demo-app

# 问题说明

- 如果运行错误可以看看是不是没有开启multiDexEnabled

####  👏欢迎使用并提意见，大家一同进步！

# 下载地址

1）[蒲公英](https://www.pgyer.com/app/qrcode/iZqn?sign=hIW%252FnmwCphNQbGMK8g0i3m4sHsZA85Yx9FxLHd7%252F5L5t6NaNQyRa%252F6znmpf6Xljh&auSign=&code=)

2）[apk]可以下载代码自己编译

# 截图演示

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0catu64cj30u01poah1.jpg" width="40%"><img width="5%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0cavec5nj30u01po44y.jpg" width="40%">
----------
<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0caufrzqj30u01podiz.jpg" width="40%"><img width="5%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0cawtqk4j30u01poajc.jpg" width="40%">


