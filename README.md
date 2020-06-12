# component 👏👏👏👏👏👏👏👏
[![](https://jitpack.io/v/codyer/component.svg)](https://jitpack.io/#codyer/component)

## 简介

+ 中小型公司或个人项目快速开发的良好脚手架，经过实际项目检验，目前持续维护更新中

## 优点

+ 模版模式，自动生成大部风重复代码，提供大部分业务情景Base类
+ 简单明了的 M-V-VM 模式
+ 底层逻辑（出错、loading显示、初始化页面、刷新、加载更多等）统一处理，只需要处理业务逻辑和画子布局
+ 不依赖后段开发进度可以直接测试前段代码显示逻辑
+ 模块化编程，需要的模块直接引用就可以
+ 创建本地仓库和远端仓库，提高编译效率
+ 清晰明了的架构和底层依赖图，方便合理组合选用
+ 良好的UI反馈机制，尽量做到每一个点击都能给用户很好的反馈
+ 其他


## 使用技术

|        技术             |               说明                   |
| ---------------------  |  ----------------------------------  |
|       M-V-VM             |   Model和View通过VM进行绑定的设计模式 |
|       dataBinding      |   数据绑定实现M-V-VM设计模式 |
|       APT              |   Android注解处理工具进行代码生成 |
|       LiveData         |   生命周期绑定的数据对象 |
|       Room             |   数据库 |
|       PageList & Paging|   数据分页 |
|       Retrofit         |   网络请求 |
|       channel          |   多渠道打包 |
|       multi module     |   多模块依赖、模块化 |
|       version ext      |   统一变量进行版本管理 |
|       glide            |   图片加载通过bindingAdapter统一处理 |
|      easy permissions  |   权限问题统一处理 |
|      lazy loading      |   Fragment懒加载 |
|      Swipe Back        |   侧滑返回 |
|       ...              |   其他第三方依赖处理常规问题 |


## 包含模块

| 模块 | 说明 | 详细说明 |
| ----| --- | ---- |
| app-demo      | 模块使用示例 |   [详细说明](https://github.com/codyer/component/blob/master/app-demo/README.md)   |
| app-core      | 利用dataBinding实现M-V-VM底层BASE模块 沉浸式体验布局 侧滑返回 懒加载等| [详细说明](https://github.com/codyer/component/blob/master/app-core/README.md) |
| marquee-core  | 滚动广告布局 | [详细说明](https://github.com/codyer/component/blob/master/marquee-core/README.md) |
| update-core   | 应用升级更新套件 | [详细说明](https://github.com/codyer/component/blob/master/update-core/README.md) |
| blues-core    | crash捕获，内部集成bugly | [详细说明](https://github.com/codyer/component/blob/master/blues-core/README.md) |
| banner-core   | 向上滚动广告组建 | [详细说明](https://github.com/codyer/component/blob/master/banner-core/README.md) |
| cat-core      | 网络数据监听，快速定位bug原因 | [详细说明](https://github.com/codyer/component/blob/master/cat-core/README.md)  |
| hybrid-core   | 利用拦截onJsPrompt实现的混合交互方案 | [详细说明](https://github.com/codyer/component/blob/master/hybrid-core/README.md) |
| image-core    | 图片浏览，扫码，身份证，营业执照拍摄等照片相关  | [详细说明](https://github.com/codyer/component/blob/master/image-core/README.md) |
| http-core     | 对retrofit封装  | [详细说明](https://github.com/codyer/component/blob/master/http-core/README.md) |
| http-compiler | 通过APT实现模版代码自动生成，减少重复工作量  | [详细说明](https://github.com/codyer/component/blob/master/http-compiler/README.md) |
| bus-core      | 利用LiveData实现的event-bus | [详细说明](https://github.com/codyer/LiveEventBus/blob/master/README.md) |
| bus-compiler  | 定义事件后，通过APT实现模版代码自动生成，减少重复工作量  | [详细说明](https://github.com/codyer/LiveEventBus/blob/master/README.md) |
| ...           | 其他底层工具类和UI组件 | [详细说明](https://github.com/codyer/component/blob/master/cat-core/README.md) |


## 项目结构图

[项目结构](https://github.com/codyer/component/blob/master/structure/APP-structure.xmind)


<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejsmvqtj311c0oitbv.jpg" width="50%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtdeohj30ue0jy75a.jpg" width="50%">

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtl5upj30uv0i4q3u.jpg" width="48.2%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejt0qm1j31210kr0u8.jpg" width="51.8%">


## 仓库说明

- 本地仓库
  自己开发时，可以使用本地仓库离线开发，提高编译效率
  
- 远程仓库
  团队合作时，可以使用远程仓库


### 本地和远程仓库通过分支或者tag进行自动区分
  
  通过分支名来实现不同的依赖方式，免去了总是切换参数的麻烦，直接使用不同分支实现

- 通过包含指定名字（local）的tag或者branch 实现推送到本地仓库

- 通过包含指定名字（remote）的tag或者branch 实现依赖远程仓库

- 通过包含指定名字（component）的tag或者branch 实现排除 compiler 打出一个总组件的依赖包

- 通过包含指定名字（compiler）的tag或者branch 实现排除 component 打出一个总注解处理器的包


## 调试APT代码

APT会用到调试，调试步骤如下

1. 打开gradle.properties 里面的两行代码注释
org.gradle.jvmargs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5033
-Dorg.gradle.debug=true
2. 在Android Studio建立通过 edit configurations 创建 Remote Debugger。
3. 在Android Studio Terminal控制台中输入gradlew --daemon来启动守护线程。 切换到新建的debugger
4. 在Android Studio Terminal控制台中输入gradlew clean assembleDebug


## 接入说明

+ 单模块依赖请查看各个模块说明文件
+ 整体component依赖说明如下

* 1） 在项目根目录的build.gradle中添加jitpack依赖

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }  // <===添加这行
        google()
        jcenter()
    }
}
```

* 2） 在应用的build.gradle中开启dataBinding并且添加依赖
```groovy
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

```groovy
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
```java
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

## 问题说明

- 如果运行错误可以看看是不是没有开启multiDexEnabled

## 下载地址

1）[蒲公英地址](https://www.pgyer.com/iZqn)

#### 二维码：  
   ![](https://tva1.sinaimg.cn/large/007S8ZIlgy1ge16zvhh3bj30760763yd.jpg)
  
#### 密码: 123456

2）[apk]可以下载代码自己编译

## 截图演示

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1fekznefj30u01poqk2.jpg" width="40%"><img width="5%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1fejx4srj30u01po4l4.jpg" width="40%">
----------
<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1feiy9v2j30u01poata.jpg" width="40%"><img width="5%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge1fei5ad7j30u01poar6.jpg" width="40%">
----------
<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0catu64cj30u01poah1.jpg" width="40%"><img width="5%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0cavec5nj30u01po44y.jpg" width="40%">
----------
<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0caufrzqj30u01podiz.jpg" width="40%"><img width="5%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0cawtqk4j30u01poajc.jpg" width="40%">


## 原则

1. Simple is Fast ：简单保障快速迭代，目前为了方便，所有的模块使用了统一的版本号管理，后期稳定了也许会考虑单独的版本依赖方式
2. Less is More ：少即是多，用更少的技术实现更高效的开发效率，一点都不花里胡哨，每一个技术引入都是为了更高的开发效率和更低的维护成本。
3. Fit is Better ：最适合的才是最好的，中小型团队和大型团队开发方式会有所不同，但初衷都是为了提高效率。

###  👏欢迎使用并提意见，感兴趣可以star，大家一同进步！

# tks
