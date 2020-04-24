# 介绍
httpCat简称监控猫，为了提高测试人员和开发人员的沟通定位bug的效率而产生的一个工具模块

# 原理
整个模块的原理比较简单，就是通过一个拦截器，拦截经过App的网络数据，并把请求过程产出的数据存储在数据库，
当发生问题时，测试人员或者开发人员可以快速定位是后端数据问题还是前段显示问题，同时，为了方便bug定位，
模块还提供了系统级的分享功能。

# 使用
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
  implementation 'com.github.codyer.component:cat-core:1.0.66'
  .
  .
  .
}
```

* 3） 在Application中初始化
```
 Interceptor interceptor = HttpCat.create(this);
```
将创建的拦截器加入你自己的请求框架

* 4）默认是关闭监控猫的，可以通过下面代码进行打开监控猫，打开会在桌面产生一个图标
```
HttpCat.getInstance().showWithNotification();
```

# 其他功能

+ 可以通过HttpCat.getInstance()提供的函数进行关闭/打开提醒
+ 隐藏/显示监控猫
+ 设置猫的名字等操作。

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
