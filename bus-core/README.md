# Bus-core 👏👏👏👏👏👏👏👏

#### 发送消息
- **setValue（Object o）**
### 在主线程发送消息

```java
LiveEventBus.begin()
            .inScope(Scope$demo.class)
            .withEvent$testBean()
            .setValue(value);
```

- **postValue(Obeject o)**
### 在后台线程发送消息，订阅者会在主线程收到消息

```java
LiveEventBus.begin()
            .inScope(Scope$demo.class)
            .withEvent$testBean()
            .postValue(value);
```


## 混淆规则
暂无

## core代码配置

``` java
compileSdkVersion 28

defaultConfig {
    minSdkVersion 19
    targetSdkVersion 28
    versionCode 1
    versionName 1.0.0
}
```

## 版本

版本 | 功能
---|---
1.0.0 | 初版，通过APT技术，自动生成代码，统一管理事件，防止事件名称冲突或者写错
1.2.0 | 事件支持分组，通过控制Scope的active值，可以全局关闭或者打开事件组
2.0.0 | 放开限制，用户可以选择不使用提供的事件管理，可以通过自己的方式进行事件管理。


## 主要功能提交记录
1. 主要功能完成（Apr. 3, 2019）


## 其他
- 欢迎提Issue与作者交流
- 欢迎提Pull request，帮助 fix bug，增加新的feature，让LiveEventBus变得更强大、更好用


## 感谢如下作者提供新的思路，因为不想用反射，所以基于原作者做了重构，具体实现原理以及原文链接参见如下


#### 实现原理
- LiveEventBus的实现原理可参见作者在美团技术博客上的博文：
[Android消息总线的演进之路：用LiveDataBus替代RxBus、EventBus](https://tech.meituan.com/Android_LiveDataBus.html)
- 该博文是初版LiveEventBus的实现原理，与当前版本的实现可能不一致，仅供参考
1. [invoking-message](https://github.com/JeremyLiao/invoking-message) 消息总线框架，基于LiveEventBus实现。它颠覆了传统消息总线定义和使用的方式，通过链式的方法调用发送和接收消息，使用更简单


## 原则

1. Simple is Fast ：简单保障快速迭代，目前为了方便，所有的模块使用了统一的版本号管理，后期稳定了也许会考虑单独的版本依赖方式
2. Less is More ：少即是多，用更少的技术实现更高效的开发效率，一点都不花里胡哨，每一个技术引入都是为了更高的开发效率和更低的维护成本。
3. Fit is Better ：最适合的才是最好的，中小型团队和大型团队开发方式会有所不同，但初衷都是为了提高效率。

###  👏欢迎使用并提意见，感兴趣可以star，大家一同进步！

# tks