# 模块化编程

+ 模块化编程的“最佳”实践事例，需要的模块直接引用就可以，
+ 创建本地仓库和远端仓库，提高编译效率

# 使用技术

- MVVM
- databinding
- APT
- LiveData
- Room
- PageList
- Retrofit

# 包含模块
+ marquee-core --> 滚动广告布局
+ update-core --> 升级更新 
+ blues-core --> crash捕获
+ banner-core --> 滚动广告组建 
+ cat-core --> 数据监听 监控猫
+ hybrid-core --> hybrid方案
+ image-core --> 图片浏览，扫码，身份证，营业执照拍摄等照片相关 
+ app-core --> 利用databinding实现MVVM底层BASE模块
+ http-core --> 对retrofit封装
+ http-compiler --> 通过APT实现模版代码自动生成，减少重复工作量
+ bus-core --> 利用livedata实现的event-bus
+ bus-compiler --> 定义事件后，通过APT实现模版代码自动生成，减少重复工作量
+ 其他底层工具类和UI组件

[项目结构](https://github.com/codyer/component/blob/master/structure/APP-structure.xmind)


<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejsmvqtj311c0oitbv.jpg" width="50%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtdeohj30ue0jy75a.jpg" width="50%">

<img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejtl5upj30uv0i4q3u.jpg" width="50%"><img src="https://tva1.sinaimg.cn/large/007S8ZIlgy1ge0ejt0qm1j31210kr0u8.jpg" width="50%">


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

# 单模块接入说明

+ cat-core [监控猫 详细说明](https://github.com/codyer/component/blob/master/cat-core/README.md)
