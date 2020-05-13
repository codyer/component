# 👏👏👏👏👏👏👏👏

## 简介

## 优点

## 项目结构图

[项目结构](https://github.com/codyer/component/blob/master/structure/APP-structure.xmind)


<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejsmvqtj311c0oitbv.jpg" width="50%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtdeohj30ue0jy75a.jpg" width="50%">

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtl5upj30uv0i4q3u.jpg" width="48.2%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejt0qm1j31210kr0u8.jpg" width="51.8%">


## 接入说明

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