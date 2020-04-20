## component
android-component

### 这个工程可以给你提供什么？
MVVM 、databinding、APT、升级更新、模块化、crash捕获、recycleView实现banner效果、滚动广告组建、Retrofit封装、日志监听、hybrid方案、LiveData、Room 、PageList、Jetpack等
快速开发App和提升开发效率、提升开发技术的很好的参考事例。

### 模块化编程
模块化编程的“最佳”实践事例，抽取自己的模块，不再重复做一样的工作，需要的时候直接引用就可以，创建自己的本地仓库和远端仓库
这个工程也是很好的事例。

[项目结构](https://github.com/codyer/component/blob/master/structure/APP-structure.xmind)
![](https://github.com/codyer/component/blob/master/structure/module_structure.png)

### 说明
- 本项目使用了本地仓库和远程仓库，当是自己开发时，可以使用本地仓库离线开发，当多团队合作的时候可以使用远程仓库
本地仓库的使用可以更好的进行模块的管理

#### 使用：通过分支名来实现不同的依赖方式，免去了总是切换参数的麻烦，直接使用不同分支实现

- 通过包含指定名字（local）的tag或者branch 实现推送到本地仓库

- 通过包含指定名字（remote）的tag或者branch 实现依赖远程仓库

- 通过包含指定名字（component）的tag或者branch 实现排除 compiler 打出一个总组件的依赖包

- 通过包含指定名字（compiler）的tag或者branch 实现排除 component 打出一个总注解处理器的包

### 调试远端代码
APT会用到调试，调试步骤如下
1. 打开gradle.properties 里面的两行代码注释
org.gradle.jvmargs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5033
-Dorg.gradle.debug=true
2. 在Android Studio建立通过 edit configurations 创建 Remote Debugger。
3. 在Android Studio Terminal控制台中输入gradlew --daemon来启动守护线程。 切换到新建的debugger
4. 在Android Studio Terminal控制台中输入gradlew clean assembleDebug

# 单模块接入说明

+ cat-core [监控猫 详细说明](https://github.com/codyer/component/blob/master/cat-core/README.md)
