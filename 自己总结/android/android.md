## 一、存储



#### 项目什么地方用到数据的持久化，说一下 ⭐️⭐️⭐️

- 首先最轻量的是 SharePreferences
- 稍微大点的存文件
- 比较多了的存 SQLite 数据库
- 存很多东西还可以用 ContentProvider 对外共享
- 如果多到手机不够放，还可以存网络



## 二、四大组件



#### Activity的生命周期，弹出 dialog 和一个 activitydialog 生命周期有什么区别？⭐️⭐️⭐️

- 正常情况下生命周期：onCreate - onStart - onResume - 界面显示出来 - onPause - onStop(界面消失) - onDestroy
- 其实不要去背各种情况下的生命周期，因为同一情况，如果主题/背景不同，那么生命周期回调又会发生变化，所以应该了解每个方法的调用前提，遇到时进行判断
- 首先是 onCreate 方法。它的作用是创建活动，他由主线程通过反射机制调用的，我们一般在里面进行一些布局和数据的初始化工作，所以他表示的是活动正在被创建
- 然后是 onStart 方法，他调用时，Activity 已经创建了，但还没启动，，所以它的作用是去启动活动。之后还需要调用下一个 onResume 方法才能将其拿到前台，和用户进行交互
- 接着是 onResume 方法，他让 Activity 变得可见，而且可以和用户进行交互
- 然后是 onPause 方法，他表示活动被暂停，他一般会和 onStop 方法连用，除非是弹出对话框这种，不会遮住全部界面的情况，甚至普通的对话框，onPause 都不会执行，只有主题对话框也就是 ActivityDialog 这种，才会 onPause
- onStop 方法表示，活动退居后台，也就是说，他在活动不可见时调用，释放当前屏幕给下一个占用者
- onDestroy 表示活动被销毁，也就是资源全部释放，一般要在这里，接触服务的绑定，以及停止线程等等
- onRestart 是onStart 前的准备动作，可以理解为之前有调用过 onStop ，从新回来就会调用 onRestart
- onSaveInstance - 可能在 onPause 前也可能之后，随机，onRestoreInstance - onstart 后调用
- 根据上面这几点，可以把生命周期分为三种：分别是由 onCreate - onDestroy 的完整生存期，和 onStart - onStop 的可见生存期，以及 onResume - onPause 的前台生存期




#### Activity的启动模式，应用场景，然后举了很多微信的场景，让我去选择用那种启动模式，说下理由。⭐️⭐️⭐️

- standard 普通
- singleTop 栈顶复用，新闻
- singleTask 栈内唯一，微信主页
- singleInstance 操作系统全局唯一，闹钟提示
- 两个栈 AC 一个栈， B 一个栈， C 切换到 B时，会先把栈切换成 AC 的栈，结果变成了 C 切换 A 的效果（Android 四大组件，类似微信效果）





#### service 两种启动模式，区别⭐️⭐️⭐️

- 第一种是采用 startService 的方式，先写个类继承 Service ，然后再 Manifest 里申明这个 Service，使用时调用 startServive(intent) 开启，不用时调用 stopService(intent) 停止。
  - 所以其生命周期是 onCreate - onStartCommand - onDestroy，由于 onStart 已经过时，所以现在都是调用 onStartCommand。
  - 注意已启动的 Service 不会再调用 onCreate 而是直接 onStartCommand，也从侧面说明，这种服务一旦启动酒喝调用者没有关系了，调用者也无法获取它的方法，除非调用 stopService 他会在后台一直跑
- 第二种是通过 BindService 绑定服务，其过程还是先写个类继承 Service ，然后再 Manifest 里申明这个 Service，使用时调用 bindService(intent, ServieConnection ,int) 开启，不用时调用 unBindService(ServiceConnection) 停止。
  - 它的生命周期是 onCreate - onBind - onUnBind - onDestroy，与 startService 启动不同，如果绑定者销毁了，它会自动 unBind 并销毁
  - 并且 onBind 会返回一个 Binder 对象，而这个 Binder 对象我们可以在 Service 里自定义，然后在 onBind 里放回，由于调用者绑定时需要传入实现 ServieConnection 接口的类或内部类，所以会在这个内部类的 onServiceConnected 回调里，获得 Service 对象，强转下即可使用
- 注意：一个调用了 startServive 服务，如果被任意调用者绑定，那么 stopService 就无法关闭，必须先解绑才能成功 stopService



#### Fragment 生命周期⭐️⭐️

- 中间和 Activity 一样，就是前后多了绑定和解绑，也就是 **onAttach** - onCreate - **onCreateView - onActivityCreated** - onStart - onPause - onStop - **onViewDestroyed** - onDeatroy - **onDetach**
- onAttach：这个方法执行时说明 Activity 和 fargment 已完成绑定，他有一个参数 context 便是被绑定的Activity，同时还我们可以通过 getArguments() 方法获得传递来的参数，要注意的是，如果要传递参数，必须使用 Fagment.newInstance(...) 的方式创建 Fragment 而不能直接 new
- onCreate 的作用是创建 Fragment，这里可以通过 onSaveInstanceState 参数获得非正常销毁时保存的数据
- onCreateView ：在这里进行静态 View 的实例化操作，但不要进行自定义 View 的初始化工作，也不不要进行进行耗时操作（自定义 View 或非静态 View  是例化需要传入 Activity 的 context，也就是 getActivity ，但和 Activity 的绑定需要等 onActivityCreated 调用时才完成，这里找不到会空指针）
- onActivityCreated ：该方法表明 Fagment 与 Activity 进行绑定的 onCreate 方法已经完成，所以可以在这里进行与 Activity UI 的交互操作，也从侧面说明，非静态 View 比如自定义 View 等，需要传入 Activity 的 context 实例的 View 需要在这里进行
- onStart ：把 Fragment 变为可见状态，但还看不到内容
- onResume：View 的绘制结束，这是用户可以与 Fragment 进行交互
- onPause ：由于切屏等原因进入暂停状态
- onSaveInstanceState：如果是 Fragment 背意外回收，调用来保存当前数据，并在重启时，从 onSaveInstanceState 参数获得所保存的内容
- onStop ：释放当前占有的屏幕，变得不可见
- onDestroyView：销毁 Fragment 的所有视图，通常是在 Viewpager 和 Fragment 联用时调用
- onDestroy：销毁碎片，被回收
- onDetach：与 Activity 解除绑定



#### [Fragment add replace 区别](https://www.cnblogs.com/genggeng/p/6780014.html)⭐️⭐️

- add() 是添加但不销毁，它他以不断地往容器里添加 fragment 先加的会被放在最上面，由于容器内 Fragment 的引用可能被回收，且重复添加会抛异常，所以在 add Fragment 方法里要加上一个 Tab 标记，然后结合 hide 或者 remove 使用时通过 FargmentManager 的 findFragmentByTag 获得，这样一来可以节约绘制时间，而来节约反复创建的资源消耗

- replace 调用时，会把容器里所有的 Fragment 都销毁掉，这种做法可以减少 Fragment 的层级，但是每次又得创建新的 Fragment 资源开销比较大
- 两者的共同点是都会执行一遍 Fragment 的生命周期，但 replace 开销大所以只在一些特殊场景上用。而使用 add 时为了避免重复添加，建议使用单例模式



## 三、启动流程



#### App 启动流程⭐️⭐️⭐️

- 首先我们用户点击应用图标的时候，AMS 先会去检查系统中是否有支持该应用程序的进程，如果没有的话，择取通知 Zygote
- Zygote 的意思是受精卵，说明它是用来复制增殖用的，比如我们的系统管理进程 system_server 就是他孵化出来的，所以在收到 AMS 发来的消息后，恢复 fork 自身，这样我们就获得到了一个虚拟机实例，紧接着为其创建 Binder 线程池和 Loop 消息循环，这样我们的 App 进程就出现了
- 然后我们的进程会通过 Binder IPC 想我们的 system_server 发送一个 ATTACH_APPLICATION 消息，我们的 system_server 收到后，会先做一系列的准备，再向 ApplicationThread 发送 SHCHEDULE_LAUNCH_ACTIVITY 消息
- 我们的 ApplicationThread 收到消息后，通过 Handler 向主线程发送 LAUNCH_ACTIVITY 消息，主线程在收到消息后，会通过反射调用我们 Activity 的 onCreate 方法，至此就进入了我们 Activity 的生命周期



#### [Activity 启动流程](https://blog.csdn.net/u010648159/article/details/81103092)⭐️⭐️⭐️

- 首先，除了我们 App 启动流程中启动 Activity 的情况外，我们所谓的 Activity 启动流程，一般是指 Context 调用 startActivity 方法开始，到 Activity 最终显示在屏幕上的过程。
- 然而我们 Context 调用 Activity 实际上是 ContextImpl 调用 startActivity ，它的内部会通过 Instrumentation 调用 execStartActivity 的过程，而这个 Instrumentation  则是一个在程序运行前初始化的，用来检测程序和操作系统之间交互的类
- 其内部会调用  AMS 的 startActivity 方法，所以这实际上是一个跨进程通行的过程（注意 Instrumentation  不是 Binder，不是做）
- 在我们 AMS 调用其 startActivity 方法之后，AMS 会去检查下目标 Activity 的合法性，在通过 ApplicationThred 回到我们的进程，而我们的 ApplicationThred 实际上就是一个 Binder，所以起本质上也是一个跨通信的过程
- 最后由于 Android 的单线程模型，我们的所有 UI 操作都必须在主线程中执行，所以我们的 ActivityThread 需要向 Handler 发送一个 LAUNCH_ACTIVUTY 消息，调用其 handleResumeActivity 方法，这个方法中会调用我们 Activity 的 onResume 方法，把 DecorView 交给 ViewRootImpl ，也就进入了 View 的绘制流程





## 四、线程



#### 线程池的好处? ★

- 提高反应速度
- 减少频繁创建线程带来的资源开销
- 对多线程进行统一管理、调度



#### 几种常见的线程池 ★

- FixedThreadPool：有固定数量的核心线程，没有非核心线程，且核心线程不会被回收。当有对象来时，先检查核心线程有没有空闲，有空闲直接加入，没有的话加入排队队列
- SingleThreadPool：有且只有一个核心线程，当有新线程来时，如何没有核心线程则进行创建，如果核心线程正在运行则加入等待队列，保证了线程的有序进行
- CachedThreadPool：没有核心线程，所有的线程都是非核心线程，线程来时即加入运行，用完释放，且最大线程数为 Integer.MAX_VALUE ，适合大量耗时短的任务
- ScheduledThreadPool：有固定数量的核心线程，且核心线程数与非核形线程总数最大 Integer.MAX_VALUE



## 五、View



#### View 加载过程★

- View 是随着 Activity 加载而加载的，当我们调用 context 的 startActivity 启动 activity 的时候，我们的 ActivityThread 会再 handleLaunchActivity 里方法，调用 Activity 的 onCreate 方法。在 onCreate 方法中，我们会首先将布局的 layout 文件传入 setContentView 方法中，而在这个方法里，DecorView 会被 PhoneWindow 创建，也就是说这使得 DecorView 持有了我们的布局。紧接着当执行到 handleResumeActivity 时，会调用我们 Activity 的 onResume 方法，在这个方法里 DecorView 被 ViewRootImpl 所持有，因此我们的布局就被传到 Window 上，但还需要执行 View 的绘制流程才能够显示出来。
- 而我们知道 View 的绘制是由 ViewRoot 负责的，需要注意的是每一个 DecorView 都对应这一个 ViewRoot 对象与之关联，而这种关联关系则是由 WindowManager 来维持的。当 DocorView 和 ViewRoot 关联完毕后，我们的 ViewRootImpl 的 requestLayout 会被调用来完成初步布局。然后就会去调用 scheduleTraversales 向主线程请求遍历，最终调用到 ViewRotImpl 的 performTravels 方法去执行 onLayout、onMeasure、onDraw 方法完成绘制



#### View 绘制过程 onMeasure★



- 我们知道 View 绘制流程的入口是 CiewRootImpl 的 performTraversals 方法，它会调用 performMeasure() 方法，而这个方法里会传入两个参数，分别是 childHeightMeasureSpec 和 childWodthMeasureSpec ，代表着 DecorView 的 MeasureSpec 值，而这个 MeasureSpec 值是由窗口大小和 DecorView 的 layoutParams 决定的，最终会调用到 View 的 onMeasure 方法内开始测量
- view 的 measure 方法是由 ViewGroup 传来的，而 View 在调用 measure 方法之前首先会根据自己的 layoutParams 和父布局大小，确定自己的 MeasureSpec，在传递到 measure 方法中，所以先说说 measureSpec 的确定方法
  - 父布局是  EXACTLY 情况：
    - 若子 View 是确定值，则 mode 是 EXACTLY
    - 若子 View 是 macth_parent ，则 mode 是 EXCATLY
    - 若子 View 是 wrap_content ，则 mode 是 AT_MOST
  - 若父布局是 AT_MOST
    - 若子 View 是确定值，则 mode 是 EXCATLY
    - 若子 View 是 match_parent ，则 mode 是 AT_MOST
    - 若子 View 是 wrap_content ， 则 mode 是 AT_MOST
  - 若父布局是 UNSPECIFIED
    - 若子 View 是 确定值，则 mode 是 EXCATLY
    - 若子 View 是 match_parent，则 mode 是 UNSPECIFIED
    - 若子 View 是 wrap_content ，则mode 是 UNSPECIFIED
- 确定完 MeasureSpec 后，传入 measure 来确定宽高还要分情况
  - 如果 MeasureSpec 的 mode 是 UNSPECIFIED ，那么需要看 View 是否有设置背景，如果有背景，就用 minHeight 和 minWeight 作为 View 的宽高（minHeight 和 minWeight 默认值为零），如果有背景就用 minHeight 和 minWeight 和背景宽高的最大值，作为宽高
  - 如果 MeasureSpaec 是 EXCATLY 或者 AT_MOST ，宽高都是用 MeasureSpec 中的 size 值，所以自定View 时一定要把 wrap_content 和 match_content 分开处理，否则两者效果一样。



#### 绘制过程 onLayout
layout 方法的作用是用来确定 view 本身的位置，onLayout 方法用来确定所有子
元素的位置，当 ViewGroup 的位置确定之后，它在 onLayout 中会遍历所有的子
元素并调用其 layout 方法，在子元素的 layout 方法中 onLayout 方法又会被调
用。layout 方法的流程是，首先通过 setFrame 方法确定 view 四个顶点的位置，
然后 view 在父容器中的位置也就确定了，接着会调用 onLayout 方法，确定子
元素的位置，onLayout 是个空方法，需要继承者去实现。
getMeasuredHeight 和 getHeight 方法有什么区别？getMeasuredHeight（测量高
度）形成于 view 的 measure 过程，getHeight（最终高度）形成于 layout 过程，
在有些情况下，view 需要 measure 多次才能确定测量宽高，在前几次的测量过
程中，得出的测量宽高有可能和最终宽高不一致，但是最终来说，还是会相
同，有一种情况会导致两者值不一样，如下，此代码会导致 view 的最终宽高比
测量宽高大 100px
```java
public void layout(int l,int t,int r, int b){
 super.layout(l,t,r+100,b+100);}
```



#### 绘制过程 onDraw
- View 的绘制过程遵循如下几步：
a. 绘制背景 background.draw(canvas)
b. 绘制自己（onDraw）
c. 绘制 children（dispatchDraw）
d. 绘制装饰（onDrawScrollBars）
- View 绘制过程的传递是通过 dispatchDraw 来实现的，它会遍历所有的子元素的draw 方法，如此 draw 事件就一层一层的传递下去了
- ps：view 有一个特殊的方法 setWillNotDraw，如果一个 view 不需要绘制内容，即不需要重写 onDraw 方法绘制，可以开启这个标记，系统会进行相应的优化。默认情况下，View 没有开启这个标记，默认认为需要实现 onDraw 方法绘制，当我们继承 ViewGroup 实现自定义控件，并且明确知道不需要具备绘制功能时，可以开启这个标记，如果我们重写了 onDraw,那么要显示的关闭这个标记




#### 点击事件分发机制，[onTouchEvent返回false](https://blog.csdn.net/jianesrq0724/article/details/54908119)?[ dispatchTouchEvent 返回 false](https://blog.csdn.net/xyz_lmn/article/details/12517911)? 
- 点击事件产生后，首先传递给 Activity 的 dispatchTouchEvent 方法，通过PhoneWindow 传递给 DecorView,然后再传递给根 ViewGroup,进入 ViewGroup 的dispatchTouchEvent 方法，执行 onInterceptTouchEvent 方法判断是否拦截，再不拦截的情况下，此时会遍历 ViewGroup 的子元素，进入子 View 的dispatchToucnEvent 方法，如果子 view 设置了 onTouchListener,就执行 onTouch方法，并根据 onTouch 的返回值为 true 还是 false 来决定是否执行onTouchEvent 方法，如果是 false 则继续执行 onTouchEvent，在 onTouchEvent的 Action Up 事件中判断，如果设置了 onClickListener ,就执行 onClick 方法。


#### Android NestedScrolling解决滑动冲突
- NestedScrollingChild侧
	NestedScrollingChild(后面简称NC)处理MotionEvent(一般在onTouchEvent中，如果是ViewGroup还要注意onInterceptTouchEvent的处理，拦截滑动相关的MotionEvent事件)，分析用户滑动操作。
	在滑动开始时，调用startNestedScroll找到联动此次滑动的NestedScrollingParent(后面简称NP)。
	对于每次用户交互产生的滑动距离，先调用dispatchNestedPreScroll，询问联动NP是否预先处理此滑动，如果NP预先处理了，会给出消耗掉的滑动距离。
	对于NP预处理剩下的滑动距离，NC决定自己是否处理部分或者全部距离(自己的滑动)。
	如果NC自己滚动之后，还剩下部分滑动距离，则调用dispatchNestedScroll让NP自行选择是否处理最后剩下的这些滑动距离。
	用户交互停止滑动，调用stopNestedScroll通知NC停止滑动联动。
- NestedScrollingParent侧
	在onStartNestedScroll中，决定是否与此次NC发起的滑动请求联动，如果决定联动，返回true，否则返回false。返回true之后，会收到onNestedScrollAccepted回调，表示NC同意与其联动，可以开始做初始化操作了；返回false之后，后面的NC联动操作不会通知此NestedScrollingParent(不会收到后续的onNestedPreScroll、onNestedScroll、onStopNestedScroll等)。
	在onNestedPreScroll中，决定是否预处理滑动单步，并给出消耗掉的滑动距离(不处理则为0)。
	在onNestedScroll中，决定是否消耗NC处理剩下的滑动距离。
	在onStopNestedScroll做联动滑动收尾工作。
	通过NC与NP的配合，可以做到很多复杂的滑动操作。只要分析了界面上外层视图与内层视图在滑动时的交互逻辑，就可以利用这两个接口实现。
- fling的处理
	相对于滑动操作，还有一个fling操作，也叫猛划，指用户拖住UI元素快速滑动之后抬手，这时会有一个fling事件，一般的操作逻辑是UI元素在抬手之后按照初始速度做减速运动。
	NestedScroll 接口也提供了API处理fling事件，在NestedScrollingChild中dispatchNestedPreFling通知NP预处理fling事件，dispatchNestedFling通知NP后处理fling事件。
```java
boolean dispatchNestedPreFling(float velocityX, float velocityY);
boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed);
```
NestedScrollingParent中对应的接口为onNestedPreFling、onNestedFling。
```java
boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY);
boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed);
```
	通过这几个接口，可以让NC和NP各自对fling事件做出反应，但是不能像滑动事件一样联动。即不能先让NP预处理 部分 fling 速度，然后NC处理剩下的 部分 fling速度，再将最后剩下的交给NP继续处理。这种情况下，上层UI元素与下层UI元素缺乏交互，很难做到像滑动操作一样的UI效果(例如fling时先收起上层视图部分内容，再滑动下层视图)。
- NestedScroll++
	为了解决此问题，在support包 26.0.0-beta2 版本中引入了 NestedScroll 接口的升级版本(后面称为 NestedScroll++ ): NestedScrollingParent2、NestedScrollingChild2 。
	在 NestedScroll++ 接口中，引入了touch type 的概念：对于用户手指触摸拖拽产生的滑动事件type为 ViewCompat.TYPE_TOUCH， fling产生的滑动事件 type 为 ViewCompat.TYPE_NON_TOUCH。滑动接口的相应API中加入了此 type 参数，如onNestedScroll接口改为：
```java
void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @NestedScrollType int type);
```
	NestedScroll++ 接口中，对于滑动事件的处理，与 NestedScroll 接口一样，只是API中加入了 type 参数。
	而对于fling事件的处理，不再依赖于 dispatchNestedPreFling、dispatchNestedFling、onNestedPreFling、onNestedFling等接口，而是选择使用与滑动事件相同的处理方式，只是 type 不同（为ViewCompat.TYPE_NON_TOUCH）。
- 相应的交互逻辑改为：
- NestedScrollingChild侧
	在fling开始时，调用startNestedScroll找到联动此次滑动的NestedScrollingParent(后面简称NP)。
每次刷新视图时，计算当前时间片由fling产生的滑动距离，先调用dispatchNestedPreScroll，询问联动NP是否预先处理此滑动距离，如果NP预先处理了，会给出消耗掉的滑动距离。
对于NP预处理剩下的滑动距离，NC决定自己是否处理部分或者全部距离(自己的滑动)。
如果NC自己滚动之后，还剩下部分滑动距离，则调用dispatchNestedScroll让NP自行选择是否处理最后剩下的这些滑动距离。
	用户交互停止滑动，调用stopNestedScroll通知NC停止滑动联动。
- NestedScrollingParent侧
	在onStartNestedScroll中，决定是否与此次NC发起的fling联动请求，如果决定联动，返回 true ，否则返回 false 。返回true之后，会收到onNestedScrollAccepted回掉，表示NC同意与其联动，可以开始做初始化操作了；返回false之后，后面的NC联动操作不会通知此NestedScrollingParent(不会收到后续的onNestedPreScroll、onNestedScroll、onStopNestedScroll等)。
	在onNestedPreScroll中，决定是否预处理fling产生的滑动距离，并给出消耗掉的滑动距离(不处理则为0)。
	在onNestedScroll中，决定是否消耗NC处理剩下的滑动距离。
	在onStopNestedScroll做联动滑动收尾工作。


## 六、handler



#### [用一句话概括Handler，并简述其原理](https://www.cnblogs.com/angrycode/p/6576905.html)★

- handler 主要是用来做线程与线程之间的异步通信，就比如说在子线程中进行下载等耗时长的任务容易导致 ANR，这时就需要在子线程中下载，并在主线程中更新 UI
- handler 的原理主要是结合 Message Looper MessageQueue 来使用，而 Handler 的机制主要是一个生产者消费者模式。一开始调用 Handler 的构造方法时，其内部会执行 mLooper = Looper.myLooper() 来和所处的线程的 looper 绑定，所以如果所处线程没有 looper 会抛出异常，通知程序员在线程中调用 Looper.prepare() 创建 looper（调用其构造方法，创建 MessageQueue ，持有当前线程），这也就是为什么主线程可以直接使用 Looper 而子线程必须先调用 Looper.prepare() 再创建 Handler 的原因。
- 当 Handler 调用 sendMessage 等发送方法时，最终都会调用到 MessageQueue 的 enqueueMessage 方法，向链表尾插一个所发的 Message 。与此同时在 Looper 被创建出来之后便会调用 loop 方法，这个方法里面是个 for(;;) 死循环，这个循环里会反复调用 queue 的 next 方法，而这个方法又回不断的从 MessageQueue 链表头取出元素
- 调用 msg.target.dispatchMessage(msg); 方法，而这个 target 是 Message 的成员变量，是目标的 Hadler 对象，然后 Hadler 收到消息之后，先判断 Meaasge 的成员变量 Ruuable 是不是空，因为之前消息可能是 Handler 的 post(Runnable) 发送的，注意这个 handler 的 Runnable 叫做 callbak ，我们 handle 的dispatchMessage(msg)回西安判断是否为空， 如果不为空的话就调用直接调用 Handler 的 handleMessage 方法。如果为空，就调用 Handler 内部 Callback 接口的 handleMessage 方法发送消息，这就是 Handler 机制的内部原理



#### 如何停止 loop 的轮循？★

- 主要有两种方法，一种是 quit() 一种是 quitSafely()
- quit 内部调用了 removeAllMessagesLocked 方法，这个方法会把 queue 中所有消息都清空，这样由于没消息，loop() 就会走到 else 里面，然后又因为他把标志位 设为 true 了，他会调用 diapose() ，这个方法里会调用 native 方法去更新底层消息队列
- 相比之下 quitSafely 调用的 removeAllFutureMessagesLocked 方法，只会移除queue 中的延时消息，并将所有 非延时消息消息都派发去给 handler 处理，之后在结束
- 但是有一点，不管调用 quit 和 quitSafely ，这时候 queue 就不会在接受任何消息了



#### Android 为什么要使用 Handler 机制更新 UI

- Android 的 UI 访问是不加锁的，而且每个控件也都不是线程安全的 ，如果多线程并发去修改 UI ，涉及到 JMM 可见行和并发安全问题，很容易导致界面错乱
- 而如果对所有 UI 操作都加锁，又会导致性能的下降
- 因此Android 设计出了单线程模型，根据这套模型，我们需要在主线程中修改 UI，而子线程和主线程间的通信就要使用 Handler 机制了



#### [Android中子线程真的不能更新UI吗？](https://blog.csdn.net/xyh269/article/details/52728861)

- 其实子线程在极端情况下是可以更新 UI 的，如果我们在 onCreate 的 setContentView 下瞬间创建一个线程并修改 UI 就能够通过
- 这主要是因为我们在修改 UI 时，会对修改 UI 线程和 ViewRootImpl 所在线程进行检测，判断是否是同一个线程，也就是他的 checkThread 方法
- 但我们从源码发现 ViewRootImpl 是在 onResume 方法里创建的，也就是说刚执行 onCreate 时还没有实例化 ViewRootImpl 也就更别谈检测，所以可以修改 UI，但是 200ms 后，随着 onResume ViewRootImpl 创建就不行了
- 子线程的 runOnUIThread 虽然写在子线程里，但实际上还是在主线程中更新



#### 一个Thread可以有几个Looper？几个Handler？
- 一个线程只能有一个 Looper 但可以有无限个 Handler，由于每个 Message 持有名为 target 的目标 Handler 对象，所以 loop 轮循时可以把消息准确分发给各个 looper

#### Message可以如何创建？哪种效果更好，为什么？
- 只要有三种方法 new 、obtain()、obtainMessage()
- 我们Android 中有一个全局的 Message Pool 消息池，所有用完待回收的 Message 都会现将字段置空（这里置空为了防止内存泄漏），然后存入 Message Pool 中，这个 Message Pool 是一个上限为 50 的链表，所以不用当心内存溢出
- 而前一个方法就是直接 new新的 Message ，而后两个都是先判断当前 Message 的池 spool 是否有 Message ，有的话直接拿出来复用，没有的话才创建新的，更节约内存


#### 主线程中Looper的轮询死循环为何没有阻塞主线程导致 ANR ？
- 因为Android 是以事件驱动的，我们 ActivityYhread 的main 方法其实主要就是一个 while(true) 消息循环，而我们的 Lopper 在不断的接受事件、处理事件，可以说所有的事件事件都是在他的监控之下进行的
- 所以如果它退出了，那整个 应用程序也就退出了，相反在主线程中做了耗时操作，导致下一次用户点击事件等消息无法处理，时间长了才是导致 ANR 的罪魁祸首。因此只能说是某个消息的处理，阻塞了 Looper.loop() 而不是。loop() 阻塞了消息的处理
- 主线程只是负责从消息队列里读取、处理消息，当没有消息时就会被阻塞，而子线程往消息队列里增加消息，并且往管道里写文件时，主线程就会被唤醒。并从管道里读取数据，处理消息。也就是说主线程的存在只是为了读取消息，读取完毕再次随眠，因此 loop 循环并不会降低性能

##### [Handler的post/send()的原理]()


#### 使用 postDealy() 后消息队列会发生什么变化？
- 在 MessageQueue 的 next 方法中，如果轮循道的消息的 msg.when 大于当前时间，会先计算出延时时间，再回到方法头部，调用 native 方法将其阻塞，在 msg.when - now 时间后自动唤醒，其过程相当于计时的 object.wait()


#### [点击页面上的按钮后更新TextView的内容，谈谈你的理解？](https://blog.csdn.net/songzi1228/article/details/88713640)
- 主线程更新 UI ，子线程不能（极端能），单线程模型
- 子线程使用 handler 需要准备 Looper
- Handler 使用不当导致内存泄漏 OOM （Handler 一般做内部类用，持有 Activity 引用，而耗时的子线程又持有 Handler 引用，导致子线程不结束 Activity 无法回收，采用事件监听Actvity 生命周期，其要停止时，关闭子线程，或者将 Handler 改为静态内部类，不持有 Activity 引用两种方案，但是第二种不能访问 Activity 的UI 控件，需要以弱引用 WeakReference 的形式持有 Activity 对象）
- handler 刚要处理，页面已经退出了，报空指针异常
- Message 使用优化 obtain()/obtainMessage()



#### 关于ThreadLocal，谈谈你的理解？

- ThreadLocal 不是 thread 而是 Thread 的局部变量，用于存储资源类，使得资源在各个线程都有拷贝，避免使用时被其他线程修改，其本质是 ThreadLocalMap
- ThreadLocalMap 是以 数组的形式存储数据，其中的 key 值为 ThreadLocal，value 为所传入的值（value 是 obj 对象，啥值都有可能）
- ThreadLocalMap 采用了内存泄漏的预防措施，每次调用。add、set、remove 的时候，就会移除所有 key == null 的 Entry
- 避免使用 static 的 ThreadLocal，而且线程运行结束时，记得去清理一下 ThreadLocalMap 中 key == null 的 Entry



#### ThreadLocal 跟HashMap类似，为什么不直接用HashMap呢？

- ThreadLocal 的本质在于其内部类 ThreadLocalMap ，其 key 值只有 ThreadLocal 不需要考虑 hashMap 那么多情况
- 结构简单，hash 冲突直接用 +1 和 -1




## 七、Binder



#### [简单说一下Android的Binder机制?](https://blog.csdn.net/linmiansheng/article/details/37918333)

- 在我们 Android 系统启动时，系统会孵化出第一个子进程 Zygote 而我们息息相关的 AMS PMS 等服务都运行在这个进程的各个线程中
- 出于安全考虑，Android 系统会为每一个应用程序分配一个 UID ，让每个应用程序运行在一个独立的进程中，由于 Linux 的安全机制，这些进程是无法访问到上述服务的
- 但我们的 App 和上述服务又有着千丝万缕的联系，所以就急需一种进程间通信的方式，而我们的 Binder 就是android 系统实现的进程间通信 IPC 方式



#### Binder机制是怎么实现的呢？

- 要弄清楚 Binder 的原理首先要搞清楚 Server 、 Client 、 ServerManager 、Binder 驱动的概念，其中 Server 和 Client 自不必说，即获取服务的客户端和提供服务的服务端，而 ServiceManager  和我们能的 Zygote 一样，是Android 的守护进程之一，从他的名字就可以看出，他的所用是维护众多的Service
- 它就像个电话本，你要打电话给某个人问点事情（想要获取某种Service），但是你没有这个人的电话（不知道Service的访问点，只知道名字），于是你先找到电话本（ServiceManager），在电话本上找到这个人的电话（通过名字找到电话），然后你才能打电话给他（开始获取Service），所以ServiceManager 作为一个独立的 Server 进程，和其他 Client 之间进行着进程间的通信
- Android 在启动 ServiceManager 的时候，首先会打开“/dev/binder”文件，映射内存。在用 BINDER_SET_CONTEXT_MGR 命令，使自己成为上下文管理者，也就是 ServiceManager。最后就进入了无限循环，等待 Client 的到来。总结一下就是系统通过 Binder 驱动和 BINDER_SET_CONTEXT_MGR 命令，让某一个进程 变成了 Servermanager



#### Binder驱动是用来干什么的？

- Binder驱动时运行在 内核空间的一段代码，可以说是Binder 机制的核心，通过一个 “/dev/binder”文件，在内核孔吉纳和用户空间来回搬数据
- 我们知道 Server ， ServerManager 、 Client 运行在不同的进程中，而不同进程间的通信需要通过内核空间来传数据，然而只有 Binder 驱动是在内核空间的
- 它负责 Binder 节点的建立、引用的计数管理、数据包以及 Binder 在进程间传输。整个进程间的通信过程，大都是调用open(), mmap() ioctl()进行文件操作，在内核空间进进出出来完成的



#### 那"BINDER_SET_CONTEXT_MGR"又是怎么回事？

- ServerManager 刚启动时还只是一个进程，当它打开 “/dev/binder”文件并将 BINDER_SET_CONTEXT_MGR命令传递给 binder驱动时
- Binder 驱动就会为其在内核空间，创建一个名为 binder_context_mgr_node 的节点，这就是 Binder 的实体 ，而其所在进程就是 ServiceManager



#### 那么，你怎么知道ServiceManager在哪的？

- 一个系统中只会有一个 binder_context_mgr_node 节点，也就是只有一个 ServiceManager ，系统会为其分配一个记为0 的句柄
- 所以要想访问 ServiceManager 只要告诉 Binder 驱动访问句柄是 0 的进程即可，Binder 驱动就会为其 唤醒 ServiceManager 让它接受访问



#### 什么是句柄？

- 句柄是 Android 系统中区分不同进程中的服务才会用到句柄，作为标识，如果服务在同一个进程中就用引用来描述





#### Binder的机制是怎么实现的呢，先简单地说一下？

- 我们可以这样理解，每一个 Service 都会通过 Binder 驱动将自己注册到 Servicemanager中，方便 Client 在访问时去 ServiceManager 中找到自己
- 首先 Server 会将自己封装在数据包中，然后复制到内核空间，供 Binder 驱动访问。当 Binder 驱动读取数据包时，发现其中有  Binder 实体，就会在这些 Server 所属的进程内，为其创建 Binder 节点（即 BinderNode）
- Binder 驱动回味起分配大于 0 的句柄，并把句柄记录在 Binder 驱动中，然后再把 句柄和名字发送给 Servicemanager 并让其负责维护



#### 那么Client们是怎么和Binder驱动通信的呢？

- Client 会把想要获得的服务的名字，加上一个值为 0 的句柄，通过 Binder 设备文件，将数据包发给 Binder 驱动，Binder 接收到句柄为 0 就会把数据包转发给 ServiceManager
- Servicemanager 通过解析数据包，发现了某个句柄的名字，就会把该服务对应的句柄发回来，驱动再将这个服务的句柄发送 Client
- Client 获得句柄之后，会将句柄连同服务再发给Binder ，Binder驱动会找到对应的句柄，返回 Server（这块都是 c++，返回的是个结构体）



#### [AIDL是什么？你有使用过它吗，它支持哪些数据类型？](https://www.jianshu.com/p/467016b4487c)

- AIDL 是 Android 的接口定义语言，由于 Android 的应用进程相互独立，无法直接通信。所以 Android 使用 AIDL 这种方式进行进程间的通信
- 除了 AIDL，Android还提供了Messenger来进程跨进程通信，但其底层还是 AIDL，而且它不支持并发访问



## 八、优化



 #### [OOM](https://www.jianshu.com/p/2fdee831ed03) ★

- 程序由于加载图片等原因，进程申请的内存超过了系统为 App 分配的最大内存限制，即使手机剩余内存再多也不会给 app 分配，从而抛出的 outOfMemeryError 错误

- 为什么要有内存限制？一方面是逼程序员更合理的使用内存，同时手机屏幕大小有限只要给每个进程分配足够即可，同时为了避免系统崩溃，每开启一个 app 都会为其打开至少独立的一个虚拟机这本身就很耗费内存，不能再耗费了

- 什么造成 OOM？

  - 图片等耗内存资源一次性加载过多

  - Cusor、InputStream/outputStream、Bitmap 等对象使用后没有及时释放
  - 长周期引用短周期、匿名内部类持有外部类等导致无法 GC

- 如何解决 OOM？

  - 使用 LruCache DiskLruCache 等缓存技术
  - 使用软引用和弱引用来代替强引用
  - 资源对象及时回收
  - 使用 hashmap 等数据结构统一管理内存



#### ANR ★

- 系统中的 AMS 和 WMS 检测到应用程序在特定时间内无法响应屏幕、键盘，或者在特定事件未处理完毕
  - 比如 5 s 内无法响应屏幕、键盘
  - 或者前台广播 10s 未完成，以及后台广播 60s 未完成
  - 对于前台服务 20s 没完成，或者后台服务 200s 内未完成
  - 以及 contentProvider 的 publish 在 10s 内未执行完
- 解决方法：使用线程池或开辟新进程，避免在 UI 线程里做耗时操作





- Activity进程的优先级。
- 如何防止微信不被系统杀死？
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
- **LinearLayout (wrap_content) & TextView (match_parent) 最终结果**??? 
- 为什么选择OKHttp？ 
- OKHttp 性能了解不？
- OKHttp内部有哪些设计模式 
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
- okhttp 的源码分析
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