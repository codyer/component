# app-core 👏👏👏👏👏👏👏👏

## 简介

+ 涵盖大部分业务场景的Base类封装

    1. 侧滑返回
    2. Fragment懒加载
    3. DataBinding封装
    4. 加载loading
    5. FriendlyLayout 布局实现初始页面、出错页面、空页面、正常页面、下拉刷新等功能的封装
    5. 列表加载、普通数据加载
    6. 键盘弹出关闭问题处理
    7. Fragment Container 多情况封装
    8. 本地数据存储封装
    9. 沉浸式布局
    10. 点击水波纹效果
    11. 列表快速且柔和滚动到顶部等工具类
    12. bugly 集成并做了异常拦截，可以在关闭应用前进行提示，或者重启应用 [详情请见blues-core](https://github.com/codyer/component/blob/master/blues-core/README.md)

## 项目结构图

[项目结构](https://github.com/codyer/component/blob/master/structure/APP-structure.xmind)


<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejsmvqtj311c0oitbv.jpg" width="50%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtdeohj30ue0jy75a.jpg" width="50%">

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtl5upj30uv0i4q3u.jpg" width="48.2%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejt0qm1j31210kr0u8.jpg" width="51.8%">

## M-V-VM 的思考

+ MVC、MVP、MVVM的区别和优缺点相信很多人都从各种文章中了解过，我就不介绍了，不了解的可以去百度搜搜这几个关键词。我这里说说我是怎么理解MVVM，以及怎么实现的吧。
+ M: Model :模型:数据库、缓存、网络请求Bean
+ V: View :视图:Activity、Fragment、XML
+ VM: ViewModel:视图模型:存储视图相关的数据

+ MVVM(Model-View-ViewModel)是一种软件架构设计模式，由微软WPF和Silverlight的架构师Ken Cooper和Ted Peters开发，是一种简化用户界面的事件驱动编程方式，由John Gossman于2005年博客发表。
MVVM是一种架构模式，并非一种框架，是一种思想，一种组织和管理代码的艺术。它利用数据绑定、属性依赖、路由事件、命令等特性实现高效灵活的架构。
而这种设计模式在运用到Android中，出现了各种实现方式，三年前作为刚接触MVVM的我查了多方资料，看了N种实现方式，都没有找到真正意义上的界面和数据分离的完美实现。
于是决定自己写一个自己理解的MVVM。期间Google发布了ViewModel后，为了更好的理解，我在原来基础上进行了重构。

首先，明确目标，视图独立于业务逻辑存在，视图可以单独测试，业务逻辑不需要关心视图展示。
依赖关系为View层依赖VM提供数据，ViewModel层通过Model层获取数据。具体如下图所示：

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1geijukbm3sj309f0bndfw.jpg)

需要考虑的问题：（因为Activity和Fragment场景类似，下面只讨论一个场景）
0. DataBinding 是什么？
    DataBinding 顾名思义，数据绑定，出来很长一段时间了，但是业界反响却不怎么突出，有的人会抱怨，项目使用 DataBinding 之后简直是一场灾难，原来逻辑写在Activity或者Presenter，出了问题可以调试，
    可以很快定位问题所在，自从用了 DataBinding ，很多逻辑跑到XML中去了，无形中增加了调试难度。在众多人诟病后，Google估计也发现了不妥，于是又进行了改造，目前已经将视图绑定单独抽取出来了，成了一个新的
    库，ViewBinding 。
    其实 DataBinding 出来的时候我就比较担心会被滥用，因为它真的太灵活了，提供了太多使用的可能，太强大了，却又没有管理和限制，最终导致滥用也无可厚非。但东西确实是个好东西。
    因此，我在设计团队的开发框架的时候，为了避免成员踩坑，尽量使用最少最合适的功能，实现最高效的开发，灵活导致开放、有时候适当限制反而会高效，这也是框架的存在意义吧。
    只有提供规则，设定条条框框，才能称之为框架。最后经过团队的验证，我的决策确实避免了团队踩坑，提高了整体的开发效率。

1. Activity、XML、ViewModel职责有哪些？
    XML的职责很简单，就是描绘页面，通过变量控制页面显示逻辑，最理想的情况是不需要任何代码，就可以直接测试页面效果是不是符合设计稿（预览效果）
    要达到这个要求，其实大部分时候只是控制一些显示隐藏逻辑就可以实现；
    Activity的职责更像是一个粘合剂，通过DataBinding把ViewModel数据和XML页面进行绑定，DataBinding 每次执行一次绑定就会进行页面重绘，上面提到我的限制，
    有一条就是，只给XML提供数据和简单的点击事件绑定，其他的一概不能使用，简单来说就是一个页面只进行一次数据绑定和一次事件绑定。
    这样下来，数据绑定的时候我的XML布局就模版化了，出现问题也比较好查。无论是 Activity 布局还是列表的 Item 布局都可以使用一样的模版，如下
    ```xml
    <layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:bind="http://schemas.android.com/apk/res-auto">

        <data>

            <import type="android.view.View" />

            <variable
                name="onClickListener"
                type="android.view.View.OnClickListener" />

            <variable
                name="viewData"
                type="。。。。ViewData" />
        </data>
        <-- 这里增加布局主体 -->
    </layout>
    ```

2. 一个Activity需要和几个ViewModel关联？
    在考虑这个问题的时候，因为大部分页面是先通过接口获取数据然后统一设置页面显示，可以看到我这里绑定的并不是ViewModel，
    而是一个叫ViewData的数据，其实本意和 ViewModel 是如出一辙，ViewData 只是将页面（XML）所需要的数据统一包装在一起，
    这样业务逻辑（获取数据）和实际数据就可以使用不同的类进行管理，更清晰。

ViewModel生命周期如下：
![](https://tva1.sinaimg.cn/large/007S8ZIlgy1geiq9oyvlaj30ei0f3q3l.jpg)

因为ViewData只是ViewModel的一个数据字段，因此和ViewModel拥有一样的生命周期。
考虑如果是将ViewModel设置进去XML，在列表的时候，表现逻辑就会有点诡异，** ViewModel更多是页面（Page）级别的，
而ViewData是布局（View）级别的，一个布局一个数据，数据和布局绑定。**

3. 如何进行数据刷新？
    数据刷新这块简单情况下，其实有两种方式，一种是通过重新绑定，这样整个View会重新绘制，还有一种是使用LiveData或者Observable变量，
    当数据变化时重新设置数据，这样可以做到每个变量单独控制。
    具体使用那种方式可以根据实际情况考虑。比如说有时候一个界面只有部分数据会变化，这个时候推荐第二种方式。
    如果说每次页面都是所有数据都会变化，用第一种方式看来会更节约资源，毕竟LiveData或者Observable还是会有些性能损耗的。
    因为第一种方式会看得到数据闪烁，目前个人在不考虑性能的情况下使用第二种方式会多一些。

4. 列表数据和详情数据绑定是否区分？
    如果要做到概念一致，最好还是不要区分列表和详情，所以我抽象出ViewData这一个模型，用来实现概念一致性，通过这种设定，列表的适配器也可以做到统一，
    传入子布局，剩余的绑定全交给框架处理，刷新、空页面、默认页面、出错页面等逻辑也可以统一封装。

## 经过以上考虑，最终版本的 MVVM 出炉，一个新的业务出来，画页面和写业务逻辑完全独立，再也不用担心接口数据格式不满足要求，或者要等接口数据出来才能调UI的困扰了。

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
  implementation 'com.github.codyer.component:app-core:1.0.66
  .
  .
  .
}
```

* 3） Application继承BaseApplication并在Application中初始化 bugly 参数，如果不想用也可以不启动blues
```java
                .
                .
                .
  private void initBlues() {
         BluesConfig.setAppChannel("CHANNEL_ID");
         BluesConfig.setAppPackageName(BuildConfig.APPLICATION_ID);
         BluesConfig.setAppVersion(BuildConfig.VERSION_NAME);
         BluesConfig.setCrashDebugKey("66666666");
         BluesConfig.setCrashReleaseKey("8888888");
         BluesConfig.setDebug(BuildConfig.DEBUG);
         BluesConfig.setTestMode(true);
         BluesConfig.setUserId("test1");
         Blues.install(this, new BluesCallBack() {
             @Override
             public void showException(final String s) {
                 Activity activity = ActivityUtil.getCurrentActivity();
                 if (activity instanceof BaseActivity) {
                     ((BaseActivity) activity).showToast(s);
                 }
             }

             @Override
             public void sameException(final Thread thread, final Throwable throwable) {
 //                reStart();
             }

             @Override
             public void fillCrashData(final Map<String, String> map) {
             }
         });
     }
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