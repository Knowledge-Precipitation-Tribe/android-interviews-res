## 一、存储



#### 项目什么地方用到数据的持久化，说一下 ⭐️⭐️

- 首先最轻量的是 SharePreferences
- 稍微大点的存文件
- 比较多了的存 SQLite 数据库
- 存很多东西还可以用 ContentProvider 对外共享
- 如果多到手机不够放，还可以存网络



## 二、四大组件



#### Activity的生命周期，弹出 dialog 和一个 activitydialog 生命周期有什么区别？⭐️⭐️

- 正常情况下生命周期：onCreate - onStart - onResume - 界面显示出来 - onPause - onStop(界面消失) - onDestroy
- 其实不要去背各种情况下的生命周期，因为同一情况，如果主题/背景不同，那么生命周期回调又会发生变化，所以应该了解每个方法的调用前提，遇到时进行判断
- 首先是 onCreate 方法。它的作用是创建活动，他由主线程通过反射机制调用的，我们一般在里面进行一些布局和数据的初始化工作，所以他表示的是活动正在被创建
- 然后是 onStart 方法，他调用时，Activity 已经创建了，但还没启动，，所以它的作用是去启动活动。之后还需要调用下一个 onResume 方法才能将其拿到前台，和用户进行交互
- 接着是 onResume 方法，他让 Activity 变得可见，而且可以和用户进行交互
- 然后是 onPause 方法，他表示活动被暂停，他一般会和 onStop 方法连用，除非是弹出对话框这种，不会遮住全部界面的情况，甚至普通的对话框，onPause 都不会执行，只有主题对话框也就是 ActivityDialog 这种，才会 onPause
- onStop 方法表示，活动退居后台，也就是说，他在活动不可见时调用，释放当前屏幕给下一个占用者
- onDestroy 表示活动被销毁，也就是资源全部释放，一般要在这里，接触服务的绑定，以及停止线程等等
- onRestart 是onStart 前的准备动作，可以理解为之前有调用过 onStop ，从新回来就会调用 onRestart
- 根据上面这几点，可以把生命周期分为三种：分别是由 onCreate - onDestroy 的完整生存期，和 onStart - onStop 的可见生存期，以及 onResume - onPause 的前台生存期




#### Activity的启动模式，应用场景，然后举了很多微信的场景，让我去选择用那种启动模式，说下理由。⭐️⭐️

- standard 普通
- singleTop 栈顶复用，新闻
- singleTask 栈内唯一，微信主页
- singleInstance 操作系统全局唯一，闹钟提示
- 两个栈 AC 一个栈， B 一个栈， C 切换到 B时，会先把栈切换成 AC 的栈，结果变成了 C 切换 A 的效果（Android 四大组件，类似微信效果）





#### service 两种启动模式，区别⭐️⭐️

- 第一种是采用 startService 的方式，先写个类继承 Service ，然后再 Manifest 里申明这个 Service，使用时调用 startServive(intent) 开启，不用时调用 stopService(intent) 停止。
  - 所以其生命周期是 onCreate - onStartCommand - onDestroy，由于 onStart 已经过时，所以现在都是调用 onStartCommand。
  - 注意已启动的 Service 不会再调用 onCreate 而是直接 onStartCommand，也从侧面说明，这种服务一旦启动酒喝调用者没有关系了，调用者也无法获取它的方法，除非调用 stopService 他会在后台一直跑
- 第二种是通过 BindService 绑定服务，其过程还是先写个类继承 Service ，然后再 Manifest 里申明这个 Service，使用时调用 bindService(intent, ServieConnection ,int) 开启，不用时调用 unBindService(ServiceConnection) 停止。
  - 它的生命周期是 onCreate - onBind - onUnBind - onDestroy，与 startService 启动不同，如果绑定者销毁了，它会自动 unBind 并销毁
  - 并且 onBind 会返回一个 Binder 对象，而这个 Binder 对象我们可以在 Service 里自定义，然后在 onBind 里放回，由于调用者绑定时需要传入实现 ServieConnection 接口的类或内部类，所以会在这个内部类的 onServiceConnected 回调里，获得 Service 对象，强转下即可使用
- 注意：一个调用了 startServive 服务，如果被任意调用者绑定，那么 stopService 就无法关闭，必须先解绑才能成功 stopService



#### Fragment 生命周期⭐️

- 中间和 Activity 一样，就是前后多了绑定和解绑，也就是 **onAttach** - onCreate - **onCreateView - onActivityCreated** - onStart - onPause - onStop - **onViewDestroyed** - onDeatroy - **onDetach**
- onAttach：这个方法执行时说明 Activity 和 fargment 已完成绑定，他有一个参数 context 便是被绑定的Activity，同时还我们可以通过 getArguments() 方法获得传递来的参数，要注意的是，如果要传递参数，必须使用 Fagment.newInstance(...) 的方式创建 Fragment 而不能直接 new
- onCreate 的作用是创建 Fragment，这里可以通过 onSaveInstanceState 参数获得非正常销毁时保存的数据
- onCreateView ：在这里进行 View 的实例化操作，但不要进行耗时操作
- onActivityCreated ：该方法表明 Fagment 与 Activity 进行绑定的 onCreate 方法已经完成，可以在这里进行 UI 的交互操作，也从侧面说明，在回调该方法前 onCreate 并没有执行完，所以在他之前访问 Activity 的 UI 会报空指针
- onStart ：把 Fragment 变为可见状态，但还看不到内容
- onResume：View 的绘制结束，这是用户可以与 Fragment 进行交互
- onPause ：由于切屏等原因进入暂停状态
- onSaveInstanceState：如果是 Fragment 背意外回收，调用来保存当前数据，并在重启时，从 onSaveInstanceState 参数获得所保存的内容
- onStop ：释放当前占有的屏幕，变得不可见
- onDestroyView：销毁 Fragment 的所有视图，通常是在 Viewpager 和 Fragment 联用时调用
- onDestroy：销毁碎片，被回收
- onDetach：与 Activity 解除绑定



#### [Fragment add replace 区别](https://www.cnblogs.com/genggeng/p/6780014.html)⭐️

- add() 是添加但不销毁，它他以不断地往容器里添加 fragment 先加的会被放在最上面，但重复添加会抛异常，所以添加时要加上一个 Tab 标记，然后结合 hide 或者 remove 使用时通过 FargmentManager 的 findFragmentByTag 获得，这样一来可以节约绘制时间，而来节约反复创建的资源消耗

- replace 调用时，会把容器里所有的 Fragment 都销毁掉，这种做法可以减少 Fragment 的层级，但是每次又得创建新的 Fragment 资源开销比较大
- 两者的共同点是都会执行一遍 Fragment 的生命周期，但 replace 开销大所以只在一些特殊场景上用。而使用 add 时为了避免重复添加，建议使用单例模式



## 三、启动流程



#### App 启动流程⭐️⭐️

- 首先我们用户点击应用图标的时候，AMS 先会去检查系统中是否有支持该应用程序的进程，如果没有的话，择取通知 Zygote
- Zygote 的意思是受精卵，说明它是用来复制增殖用的，比如我们的系统管理进程 system_server 就是他孵化出来的，所以在收到 AMS 发来的消息后，恢复 fork 自身，这样我们就获得到了一个虚拟机实例，紧接着为其创建 Binder 线程池和 Loop 消息循环，这样我们的 App 进程就出现了
- 然后我们的进程会通过 Binder IPC 想我们的 system_server 发送一个 ATTACH_APPLICATION 消息，我们的 system_server 收到后，会先做一系列的准备，再向 ApplicationThread 发送 SHCHEDULE_LAUNCH_ACTIVITY 消息
- 我们的 ApplicationThread 收到消息后，通过 Handler 向主线程发送 LAUNCH_ACTIVITY 消息，主线程在收到消息后，会通过反射调用我们 Activity 的 onCreate 方法，至此就进入了我们 Activity 的生命周期



#### [Activity 启动流程](https://blog.csdn.net/u010648159/article/details/81103092)⭐️⭐️

- 首先，除了我们 App 启动流程中启动 Activity 的情况外，我们所谓的 Activity 启动流程，一般是指 Context 调用 startActivity 方法开始，到 Activity 最终显示在屏幕上的过程。
- 然而我们 Context 调用 Activity 实际上是 ContextImpl 调用 startActivity ，它的内部会通过 Instrumentation 调用 execStartActivity 的过程，而这个 Instrumentation  则是一个在程序运行前初始化的，用来检测程序和操作系统之间交互的类
- 其内部会调用  AMS 的 startActivity 方法，所以这实际上是一个跨进程通行的过程（注意 Instrumentation  不是 Binder，不是做）
- 在我们 AMS 调用其 startActivity 方法之后，AMS 会去检查下目标 Activity 的合法性，在通过 ApplicationThred 回到我们的进程，而我们的 ApplicationThred 实际上就是一个 Binder，所以起本质上也是一个跨通信的过程
- 最后由于 Android 的单线程模型，我们的所有 UI 操作都必须在主线程中执行，所以我们的 ActivityThread 需要向 Handler 发送一个 LAUNCH_ACTIVUTY 消息，调用其 handleResumeActivity 方法，这个方法中会回调我们 Activity 的 onResume 方法，把 DecorView 交给 ViewRootImpl ，也就进入了 View 的绘制流程





#### ThreadLocal 



## 四、线程



#### Handler机制 







## 五、View



#### View 绘制过程





#### 点击事件分发机制，onTouchEvent返回false? dispatchTouchEvent 返回 false? 





- Activity启动到加载View过程 
- Activity进程的优先级。
- 如何防止微信不被系统杀死？
- service两种启动模式，区别
- 两种启动模式，如果我在退出Activity的时候没有退出service会怎么样。
- 设计一个图片浏览框架，（线程池，lru缓存，brabra的说了一堆）。
- 有一个很大很大的图片加载到内存上，不能降低清晰度和压缩图片你怎么解决？（提示我局部显示？我没懂）
- 如何适配不同厂商的手机，然后设计模式，brara又说了一大堆，最后还说到jetkins自动部署上面去了
- AsyncTask源码分析，每个方法在哪个线程执行的？
- 6.fragment的懒加载。
- 3.接下来就EventBus的东西了，还是老问题，优缺点，有没有什么问题，列举了很多场景，我看源码看的比较细，根据自己看过的东西做回答和分析，然后还是，接口回调和观察者模式之间的选择。
- 4.问我你看过这么多源码，你觉得什么东西最重要?
- 5.答了源码中看到了大量的反射使用，多线程方面，Collections，数据结构这些。
- 6.问我多线程，引申出handler，我从handler的源码去解释
- 7.handler引申出的内存泄漏，为什么静态内部类不会持有外部对象
- 8.接下来还是场景题，图片框架的实现，涉及到的Lru缓存，线程池，线程池该如何分配线程数量。
- 9.APP从打开到显示之间发生的事情。
- 10.为什么java可以调用c/c++的函数，调用jni发生的事情说一下。
- 11.动画种类，使用动画的步骤，有没有看过动画框架的源码。
- 有没有遇到OOM问题(有遇到内存泄漏问题) 
- Handler机制 
- **LinearLayout (wrap_content) & TextView (match_parent) 最终结果**??? 
- OKHttp(1. 为什么选择它？ 
- **2. 性能了解不？**3. 内部有哪些设计模式) 
- **了解EventBus嘛？**
- 为什么选择OKHTTP框架
- 加载图片框架？(**学一下Glide**)
- JSON解析框架？（**学一下Gson，FastJson**）
- **Activity生命周期？启动透明Activity生命周期？按Home键生命周期？**  
- **后台杀死APP后怎么恢复数据？**  
- 一个APP可以多进程嘛？ 
- ListView和RecyclerView区别？ 
- **RecyclerView卡顿怎么排查？**  
- RecyclerView怎么实现多Type？ 
- RecyclerView的ItemView层级过深怎么优化？ 
- Android多进程？ 
- 怎么设计Android线程间通信？ 
- Handler机制？子线程可以用Handler吗？ 
- ANR？
- 实现的功能，基于OKHTTP实现网络请求
- 图片，语音大内存数据的性能排查，定位？
- Handler内存泄漏问题
- Android四大组件安全性 
- Activity启动模式 
- **IntentFilter匹配规则**，action和category区别？ 
- Handler 阻塞为什么不卡死? 
- Looper 
- 对象池，手写对象池实现 
- ContentProvider原理 
- sp支持跨进程么？怎么解决跨进程，怎么实现进程同步 
- 帧动画实现: 100张图，200ms显示一张，读取一张图要400ms，怎么解决避免卡顿(多线程读) 
- Bitmap内存复用限制条件 
- 线程时间片分配原理
- 类加载机制
- okhttp原理
- 热修复原理
- MVP&MVC
  OKhttp源码
  Retrofit源码
  Service的两种状态&通信&生命周期
  Activity的生命周期&启动模式
  View的事件分发&滑动冲突
  View的绘制流程&自定义View
  Rxjava优点&源码&应用
  广播
  内容提供器
  Handler机制&源码
  Fragment生命周期
  Lru缓存算法(LruCache&DiskLruCache)
  进程间通信[Binder源码]
- **TouchEvent传递过程**？ **onTouchEvent返回flase怎么办**  
- **怎么设计缓存**  
- Android数据持久化 
- 数据库怎么批处理（原理） 
- SP支不支持多线程？SP怎么实现多线程 
- **View绘制过程**  
- Handler消息机制
- Lint工具？
- 进程间通信方式（与linux进程间通信区别） 
- Socket怎么验证安全性 
- 广播（全局 本地区别） 
- 怎么实现文件的多进程通讯（A进程改了文件怎么通知B进程读取） 
- 二级缓存怎么设计（网络 数据库 view间关系）
- Activivty 生命周期 
- onSaveInstanceState onRestoreInstanceState区别，调用时机 
- 广播注册应该在Activity哪个生命周期里 
- 怎么统计onCreate的次数 
- Fragment与Activity区别 
- Fragment生命周期管理 
- Fragment与ViewPager怎么做到重复加载 
- View绘制过程 MeasureSpec的三种模式 
- Framelayout LinearLayout ReativeLayout怎么做到View在右下 
- margin padding区别 
- gone invisible的区别 
- requestLayout、invalidate与postInvalidate区别 
- Android动画 怎么取消循环动画 repeat模式 
- drawable与view区别 有哪些drawable
- 图片传输过程中URL加上默认大小如果是wrap_content怎么办 
- 图片相关缓寸，编码，内存复用 
- svg (其他图片格式) 
- drawable mutate了解不 
- okhttp 桥接拦截器和缓存拦截器 
- 设计自定义DNS解析器 
- 打点系统设计:写文件过程中会有buffer，此时进程被杀怎么办，怎样设计日志系统 打点日志被用户篡改怎么办，保证日志安全性 
- 磁盘内存映射原理 
- 有没有看过开源打点框架 
- 平时开发有没有遇到过资源复用 
- 最近了解啥Android新动向不
- OSS STS凭证设计
- **Lint工具是编译期的嘛？原理？**
- 美团首页设计？
- **RecyclerView多Item的难点**？
- Queue.next到底处于什么状态（睡眠？阻塞？）** 应该是阻塞状态，底层 
- **epoll到底怎么实现的**（还是没能说清楚？机制？native层呢还是系统层） epoll(Linux系统)监听文件描述符 
- 应用程序的main方法在哪？怎么实现不退出？ 
- 广播的机制？ 
- 应用程序的退出？**进程优先级**
- Activity生命周期？onRestart什么时候执行？别的生命周期？ 
- 四大组件？ 
- Service两种启动方式？区别？生命周期流程？**能不能在Application中启动Service(可以，有context了)**  
- 局部广播 
- ListView RecyclerView区别？**ListView定量更新(根据位置取出来直接更新)**  
- 图片大怎么加载？图片加载框架设计？ 
- Handler机制？ 
- AsyncTask？ 
- 线程池参数？ 
- ANR机制？ 
- ANR，Crash怎么上传到服务器？ **CrashHandler UncaughtExceptionHandler？？？**  
- 网络加载框架，怎么设计网络请求接口
- 四大组件都用过么（回答没用过contentprovider，再问那知道他是干啥的不……背了一些概念）
- activity启动模式
- 多进程的几个activity依次启动 一个application只会被初始化一次吗
- handler
- 怎么样算是一次请求成功了
- 项目中写的bitmap优化是指？
- 线程池用过没，有什么优点
- 问怎么恢复acticity状态 哪些方法 oncreate里面能恢复吗 和重写那俩方法恢复有什么区别
- view的 measure onmeasure什么区别 on draw draw dispatchdraw什么区别
- framelayout relativelayout有什么区别
- 对android什么地方最熟悉
- bitmap存储的位置 安卓几个版本有什么不同？
- 对android什么地方最熟悉
- bitmap存储的位置 安卓几个版本有什么不同？
- framelayout relativelayout有什么区别
- recyclerview机制 怎么区分不同类型的item的
- 内存泄露有哪些场景
- activity生命周期
- oncreate和onstart区别
- oncreate执行一个耗时操作会怎么样
- 什么情况会anr
- handler
- looper prepare做了什么事情
- dialog弹出会不会影响生命周期（我说这个试过，不能，他说确定吗。。我说确定…他说会，下来之后再看看……）
- 项目的图片太大怎么处理的
- 什么是采样率 什么是分辨率
- 跨进程通信
- bitmap的优化 怎么压缩
- 提到分辨率和质量 压缩什么区别inbitmap什么用 bitmapRegiondecoder
- a启动b流程 为什么是先pause 等b展示完了再stop
- 怎么监控卡顿
- 性能优化做过图片是吧 讲一下
- 了解android push的机制吗
- 怎么保证客户端的安全
- anr分类有哪些，原因（具体不了解，就知道执行网络或者数据存储等耗时操作）
- anr定位（不会）
- activity生命周期
- activity从A打开B的生命周期（答错）
- 事件分发
- Android的权限机制了解过吗？(只说了6.0后加入了动态权限并举了几个简单例子，应该更深入点展开) 
- view的绘制流程？是谁发起的绘制流程(谁触发了decoerview的绘制)？viewgroup的ontouch方法一定会执行吗(什么情况下会执行)？  
- viewGroup的onTouchEvent一定会执行吗(如何判断其是否会执行)？ 
- 有没有使用过codeLint 
- 什么是anr？handler中进行耗时***作有可能导致anr吗？
- handler机制以及其内存泄露、多个handler如何识别 	
- Broadcast Receiver有哪几种区别以及在哪个进程中，为什么本地Receiver不可以用在线程间通信，onReceiver在哪个线程中， 	
- service在哪个进程中，service具体
- target_SDK_version是干什么的
- activity启动模式 		
- ANR是什么以及产生原因 		
- handler机制以及怎么调用handler，looper和线程的关系 		
- 多线程通信有哪些方式？（handler，线程池） 		
- 进程间通信的方式 		
- 线程池的分类以及具体是什么，以及这些线程池的参数都是什么 		
- handler内存泄露问题如何解决
- Android的APP启动流程
- 进程间通信方式
- 进程间通信
- 四大组件是什么
- activity生命周期
- 死锁条件以及如何解锁
- 线程池的种类及作用
- 设计模式有哪些？最了解哪个？这些设计模式的使用场景 				
- 内存泄露以及handler内存泄露原理 				
- 垃圾回收机制（垃圾回收算法，怎么就老年代了，如何判断是不是可以回收，GC root是什么有哪些） 			
- 写过哪些应用？ 				
- 前端项目问，关于前端和android结合H5的了解
- View事件分发+ 滑动冲突
- View的绘制流程
- Activity的生命周期
- MVC MVP
- 线程安全
- 弱引用，软引用
- Rxjava(使用，好处)
- 内存泄漏
- ListView和RecycleView的区别
- Service的生命周期
- Fragment的生命周期
- Handler内部实现机制
- 如果是你，你怎么实现Handler
- OOM
- listView在什么情况会发生OOM
- surfaceView
- Binder(手写AIDL)
- 绘制流程
- view事件分发
- GC
- Rxjava优点，应用
- Retrofit
- RecyclerView不同子项
- 事件分发
- MVP 和MVC的区别
- 四大组件的使用场景
- Service两种状态，两种使用场景，组件间通信
- 如何实现不同机型的适配
- 持久化存储
- 不同Fragment之间传递数据
- 组件化的使用
- 组件化通信
- 编译时注解 运行时注解(没答上来)
- 进程线程区别
- ARoute
- 多进程读取SharedPreferences
- 进程间通信
- sp存在哪
- sp提供了那些接口[不知道]
- 数据库(15的按照学号逆序)[没写出来]
- okhttp
- retrofit
- recycler view优点，使用时注意什么
- 滑动冲突
- 自定义view
- 进程通信
- 进程间调度算法
- binder
- 广播
- 滑动冲突
- 自定义view
- 事件分发
- okhttp
- Retrofit
- rxjava
- 项目中组件化
- rxjava 1.0和2.0区别
- okhttp的源码优点
- url点击之后发生了什么
- langchar点击到第一个应用的启动(zygoto创建应用进程)
- onCreate的view加载
- asm如何跨进程通信
- binder机制
- 为什么用binder
- ims获取事件
- android6.0到9.0都有什么变化(不知道…)
- okhttp亮点
- 百度实习经历cash的解决
- 文本压缩的实现(哈夫曼编码)
- 视频压缩，音频压缩
- 自己如何实现图片加载库
- lru缓存
- Handler(loop死循环，如何唤醒，定时任务的实现)
- 事件分发
- binder(如果实现数据传输，服务端在哪个线程接收数据)
- activity的四种启动方式
- activity启动方式singleInstance在什么情况下被使用
- 启动另一个app的activity发生了什么
- activity中包含一个ViewGrop，ViewGrop里面包含一个button，手指在Button中心放着，慢慢移动到button外- 这个过程中发生了什么?
- 上面那个是否会调用button的onClick时间
- android q的适配
- 语音SDK的实现
- jetpack是什么
- livedata是什么
- viewmodel是什么
- kotlin语言语法(网络)
- 如何学习android
- 组件化相关
- gradle的作用，构建过程
- 项目遇到的难点
- 滑动冲突的解决
- rxjava的基本原理
- Retrofit的基本原理
- Retrofit对于反射注解的有什么优化
- Okhttp的拦截器链的设计模式
- 责任链模式在哪里还有使用
- retrofit的实现
- 注解的原理
- 如何自己实现注解
- rxjava的原理(背压的实现，操作符的实现)
- android q的适配
- audio
- surface(大概讲解，surfaceView和普通view的区别)
- okhttp的源码分析
- 一个app存在两个进程，app的application会初始化几次
- 两个进程访问同一个单例是否有问题
- 讲讲单例模式
- 懒汉饿汉
- 锁膨胀
- android q的适配
- 沙盒模式
- 说百度实习经历
- view渲染 surfase
- ipc binder机制
- android权限的分类
- android唯一标识符

- 自我介绍时说过自己看过EventBus源码，然后让我谈谈事件总线的理解。

- EventBus会有什么问题吗？
- EventBus、接口回调、观察者模式的使用场景说一下。