### iOS

#### 二、多线程 GCD 绕不开的**任务**与**队列**

- 想必大家都有过类似的经历，在对 `GCD` 没有深入理解之前，使用 `GCD` 做同步/异步操作，很容易造成死锁，所以想使用好 `GCD`，就必须首先理解两个非常重要的概念：任务 和 队列
- 很好理解任务和队列直接的关系：任务被创建出来，加入到队列里，由队列去管理执行
- 那么我就直接带大家看看任务和队列的具体实现

##### 1.1.1 任务

- 首先我们需要明确一个问题：什么是任务？
- 简单的理解，一个任务其实就是你的一段代码，在 `GCD` 中称为一个 `Block`
- 在我们的使用过程中，任务的使用场景主要有两种：同步执行 和 异步执行
- 而所谓的同步和异步，主要区别在于会不会阻塞当前线程，直到 `Block` 中的任务执行完毕？
- **同步`（sync）` ：阻塞当前线程并等待 `Block` 中的任务执行完毕，然后当前线程才会继续往下运行**

```swift
  dispatch_sync(<#queue#>, { () -> Void in
      //TODO
      println(NSThread.currentThread())
  })
```

- **异步（async）：当前线程会直接往下执行，它不会阻塞当前线程**

```swift
  dispatch_async(<#queue#>, { () -> Void in
      //TODO
      println(NSThread.currentThread())
  })
```

> `<#queue#>` 为队列，比如常用的 `dispatch_get_main_queue()`，这个我们将在后面队列里展开

##### 1.1.2 队列（Dispatch Queue）

- 首先我们知道： 队列`（queue）`是先进先出一个 `FIFO`, `First-In-First-Out` 的线性表
- 所以我们不难想到任务都是需要加入到队列里，再由队列进行管理派发的
- 那么在使用队列前，我们同样需要明确两个概念：串行队列 和 并发队列

![img](https://user-gold-cdn.xitu.io/2018/5/4/1632a7add940e2a8?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

- **串行队列：说明这个队列中的任务要串行执行，也就是一个一个的执行，必须等上一个任务执行完成之后才能开始下一个，而且一定是按照先进先出的顺序执行**

```swift
  let serialQueue = DispatchQueue(label: "vip.mybadge")
```

- **并发队列：说明这个队列中的任务可以并发执行，也就任务可以同时执行**

```swift
    let concurQueue = DispatchQueue(label: "vip.mybadge", attributes: .concurrent)
```

- 除了上面两种自定义队列之外，这里也向大家介绍两种常用的系统内置队列
- **主队列 (串行队列)**

```swift
  let mainQueue = DispatchQueue.main
```

- **全局并行队列**

```swift
  let globalQueue = DispatchQueue.global(qos: .default)
```

#### 三、多线程场景下 GCD 的具体打法

##### 3.1 串行队列异步任务

- 首先我们创建一个串行队列，并在其中执行 5 个异步任务
- 让我们看下示例代码：

```swift
dispatch_queue_t serialQueue = dispatch_queue_create("Dan-serial", DISPATCH_QUEUE_SERIAL);
    for(int i = 0; i < 5; i++){
        dispatch_async(serialQueue, ^{
            NSLog(@"第 %@ 线程开始：-->| %@",@(i),[NSThread currentThread]);
            [NSThread sleepForTimeInterval: i % 3];
        });
    }
```

- 输出的日志打印如下：

```html
2020-07-31 19:11:18.582379+0800 oc-test[43084:424495] 第 0 线程开始：-->| <NSThread: 0x600000450400>{number = 4, name = (null)}
2020-07-31 19:11:18.582541+0800 oc-test[43084:424495] 第 1 线程开始：-->| <NSThread: 0x600000450400>{number = 4, name = (null)}
2020-07-31 19:11:19.587345+0800 oc-test[43084:424495] 第 2 线程开始：-->| <NSThread: 0x600000450400>{number = 4, name = (null)}
2020-07-31 19:11:21.589643+0800 oc-test[43084:424495] 第 3 线程开始：-->| <NSThread: 0x600000450400>{number = 4, name = (null)}
2020-07-31 19:11:21.589859+0800 oc-test[43084:424495] 第 4 线程开始：-->| <NSThread: 0x600000450400>{number = 4, name = (null)}
```

- 首先我们从日志的线程数量可以看出，他们都是在同一个线程中执行`（ number = 4 ）`，而且都开启了新线程`（ name = (null) ）`
- 并且日志的输出是按照顺序执行的`1 - 2 - 3 - 4 - 5`，也就说明线程的串行性：前一个执行完，在执行下一个

> swift 同版实现

```swift
//创建串行队列
let serialQueue = DispatchQueue(label: "SCGCD", attributes: .init(rawValue: 0))
//异步步执行
 for i in 0..<10 {
     serialQueue.async {
         print(i)
         print(Thread.current)
     }
}
```

##### 3.2 串行队列同步任务

- 首先我们创建一个串行队列，并在其中执行 `5` 个同步任务
- 示例代码如下：

```swift
    dispatch_queue_t serialQueue = dispatch_queue_create("Dan-serial", DISPATCH_QUEUE_SERIAL);
        for(int i = 0; i < 5; i++){
                dispatch_sync(serialQueue, ^{
                    NSLog(@"第 %@ 线程开始：-->| %@",@(i),[NSThread currentThread]);
                    [NSThread sleepForTimeInterval: i % 3];
                });
            }
```

- 输出的日志打印如下：

```html
2020-07-31 19:27:42.947037+0800 oc-test[43942:442924] 第 0 线程开始：-->| <NSThread: 0x600002b140c0>{number = 1, name = main}
2020-07-31 19:27:42.947170+0800 oc-test[43942:442924] 第 1 线程开始：-->| <NSThread: 0x600002b140c0>{number = 1, name = main}
2020-07-31 19:27:43.948245+0800 oc-test[43942:442924] 第 2 线程开始：-->| <NSThread: 0x600002b140c0>{number = 1, name = main}
2020-07-31 19:27:45.949159+0800 oc-test[43942:442924] 第 3 线程开始：-->| <NSThread: 0x600002b140c0>{number = 1, name = main}
2020-07-31 19:27:45.949283+0800 oc-test[43942:442924] 第 4 线程开始：-->| <NSThread: 0x600002b140c0>{number = 1, name = main}
```

- 从输出的日志我们可以看出：执行所有任务的都是同一个线程`（<NSThread: 0x600002b140c0>）`
- 并且没有开启新线程`（ number = 1 ）`
- 都是在主线程执行`（ name = main ）`

> swift 同版实现

```swift
//创建串行队列
let serialQueue = DispatchQueue(label: "SCGCD", attributes: .init(rawValue: 0))
//同步执行
for i in 0..<10 {
   serialQueue.sync {
      print(i)
      print(Thread.current)
   }
}
```

##### 3.3 并发队列异步任务

- 首先我们创建一个并发队列，并在其中执行 5 个异步任务
- 示例代码如下：

```swift
        dispatch_queue_t concurrent_queue = dispatch_queue_create("DanCONCURRENT", DISPATCH_QUEUE_CONCURRENT);
        for(int i = 0; i < 5; i++){
            dispatch_async(concurrent_queue, ^{
                NSLog(@"第 %@ 线程开始：-->| %@",@(i),[NSThread currentThread]);
                [NSThread sleepForTimeInterval: i % 3];
                NSLog(@"第 %@ 线程开始：-->| %@",@(i),[NSThread currentThread]);
            });
        }
```

- 输出的日志打印如下：

```html
2020-07-31 19:23:23.575834+0800 oc-test[43626:437789] 第 0 线程开始：-->| <NSThread: 0x600001f3c440>{number = 7, name = (null)}
2020-07-31 19:23:23.575835+0800 oc-test[43626:437795] 第 4 线程开始：-->| <NSThread: 0x600001f33280>{number = 3, name = (null)}
2020-07-31 19:23:23.575840+0800 oc-test[43626:437786] 第 1 线程开始：-->| <NSThread: 0x600001f78a40>{number = 4, name = (null)}
2020-07-31 19:23:23.575855+0800 oc-test[43626:437784] 第 2 线程开始：-->| <NSThread: 0x600001f70280>{number = 5, name = (null)}
2020-07-31 19:23:23.575861+0800 oc-test[43626:437785] 第 3 线程开始：-->| <NSThread: 0x600001f35800>{number = 6, name = (null)}
2020-07-31 19:23:23.575990+0800 oc-test[43626:437789] 第 0 线程开始：-->| <NSThread: 0x600001f3c440>{number = 7, name = (null)}
2020-07-31 19:23:23.576015+0800 oc-test[43626:437785] 第 3 线程开始：-->| <NSThread: 0x600001f35800>{number = 6, name = (null)}
2020-07-31 19:23:24.580830+0800 oc-test[43626:437795] 第 4 线程开始：-->| <NSThread: 0x600001f33280>{number = 3, name = (null)}
2020-07-31 19:23:24.580833+0800 oc-test[43626:437786] 第 1 线程开始：-->| <NSThread: 0x600001f78a40>{number = 4, name = (null)}
2020-07-31 19:23:25.579376+0800 oc-test[43626:437784] 第 2 线程开始：-->| <NSThread: 0x600001f70280>{number = 5, name = (null)}
```

- 分析日志我们不难发现：执行所有线程的都不是同一个线程`（ <NSThread: ？> ）`，而且他们都开启了各自的线程执行`（ number = ？）`
- `PS:` 这里会发现个子线程开启的时间不同，我估计这可能是方法执行延时导致的，希望有大佬能解答下

> swift 同版实现

```swift
 //创建并发队列
let conQueue = DispatchQueue(label: "Mazy", attributes: .concurrent)
//异步执行
 for i in 0..<10 {
    conQueue.async {
       print(i)
       print(Thread.current)
    }
}
```

##### 3.4 并发队列同步任务

- 我们创建一个并发队列，并在其中执行 `5` 个同步任务

```swift
    dispatch_queue_t concurrent_queue = dispatch_queue_create("DanCONCURRENT", DISPATCH_QUEUE_CONCURRENT);
         for(int i = 0; i < 5; i++){
                dispatch_sync(concurrent_queue, ^{
                    NSLog(@"第 %@ 线程开始：-->| %@",@(i),[NSThread currentThread]);
                    [NSThread sleepForTimeInterval: i % 3];
                    NSLog(@"第 %@ 线程开始：-->| %@",@(i),[NSThread currentThread]);
                });
            }
```

- 输出的日志打印如下：

```html
2020-07-31 19:55:11.617313+0800 oc-test[46482:471125] 第 0 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:11.617447+0800 oc-test[46482:471125] 第 0 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:11.617554+0800 oc-test[46482:471125] 第 1 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:12.618101+0800 oc-test[46482:471125] 第 1 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:12.618235+0800 oc-test[46482:471125] 第 2 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:14.619512+0800 oc-test[46482:471125] 第 2 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:14.619717+0800 oc-test[46482:471125] 第 3 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:14.619789+0800 oc-test[46482:471125] 第 3 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:14.619873+0800 oc-test[46482:471125] 第 4 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
2020-07-31 19:55:15.620270+0800 oc-test[46482:471125] 第 4 线程开始：-->| <NSThread: 0x6000004840c0>{number = 1, name = main}
```

- 我们预测，想并发队列添加的任务应该是并发执行，那么我们来看看日志
- 通过输出的日志我们可以看出：我们的程序即没有并发执行，而且也没有开启新的线程。他们都在祝线程执行。
- 这主要是因为，使用`dispatch_sync` 添加同步任务，必须等添加的 `block` 执行完成之后才返回。那么既然要执行 `block`，肯定需要线程，要么新开线程执行，要么再已存在的线程（包括当前线程）执行
- 官方对这种情况的解释如下：

> As an optimization, dispatch_sync() invokes the block on the current thread when possible.
> 翻译过来就是：作为优化，如果可能，直接在当前线程调用这个block。

- 所以，所以，一般，在大多数情况下，通过`dispatch_sync`添加的任务，在哪个线程添加就会在哪个线程执行。上面我们添加的任务的代码是在主线程，所以就直接在主线程执行了。

> swift 同版实现

```swift
//创建并发队列
let conQueue = DispatchQueue(label: "KaitoGCD", attributes: .concurrent)
//同步执行
for i in 0..<10 {
   conQueue.sync {
       print(i)
       print(Thread.current)
   }
}
```

##### 3.5 延时处理

- `dispatch_after：`所谓延时处理是指，在指定时间后追加到队列中。
- 为了方便理解，这里我给大家举两个个例子，首先是 `swift` 的：

```swift
DispatchQueue.main.asyncAfter(deadline: .now() + 4.5) {
	// ...
}
```

- 可以看到 `swift` 使用非常方便，但如果是在 `oc` 下，则可以这样使用

```objective-c
dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(<#delayInSeconds#> * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
    <#code to be executed after a specified delay#>
});
```

> 注：这里 `DISPATCH_TIME_NOW` 是指当前时间，`NSEC_PER_SEC` 指秒，`NSEC_PER_MSEC` 指毫秒

##### 3.6 队列组

- 所谓队列组，就是可以将很多队列添加到一个组里
- 这样做的好处是：当这个组里所有的任务都执行完了，队列组会通过一个方法通知我们。
- 下面是我们来看一个简单的 `Demo`， 注释内容已经很清晰了，这里就不做过多的解释
- **swift demo 实现如下**

```swift
//1.创建队列组
let group = dispatch_group_create()
//2.创建队列
let queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0)

//3.多次使用队列组的方法执行任务, 只有异步方法
//3.1.执行3次循环
dispatch_group_async(group, queue) { () -> Void in
    for _ in 0..<3 {
        NSLog("group-01 - %@", NSThread.currentThread())
    }
}

//3.2.主队列执行8次循环
dispatch_group_async(group, dispatch_get_main_queue()) { () -> Void in
    for _ in 0..<8 {
        NSLog("group-02 - %@", NSThread.currentThread())
    }
}

//3.3.执行5次循环
dispatch_group_async(group, queue) { () -> Void in
    for _ in 0..<5 {
        NSLog("group-03 - %@", NSThread.currentThread())
    }
}

//4.都完成后会自动通知
dispatch_group_notify(group, dispatch_get_main_queue()) { () -> Void in
    NSLog("完成 - %@", NSThread.currentThread())
}
```

- **objective-c 实现如下**

```objective-c
//1.创建队列组
dispatch_group_t group = dispatch_group_create();

//2.创建队列
dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);

//3.多次使用队列组的方法执行任务, 只有异步方法
//3.1.执行3次循环
dispatch_group_async(group, queue, ^{
   for (NSInteger i = 0; i < 3; i++) {
       NSLog(@"group-01 - %@", [NSThread currentThread]);
   }
});

//3.2.主队列执行8次循环
dispatch_group_async(group, dispatch_get_main_queue(), ^{
   for (NSInteger i = 0; i < 8; i++) {
       NSLog(@"group-02 - %@", [NSThread currentThread]);
   }
});

//3.3.执行5次循环
dispatch_group_async(group, queue, ^{
   for (NSInteger i = 0; i < 5; i++) {
       NSLog(@"group-03 - %@", [NSThread currentThread]);
   }
});

//4.都完成后会自动通知
dispatch_group_notify(group, dispatch_get_main_queue(), ^{
   NSLog(@"完成 - %@", [NSThread currentThread]);
});
```

#### 四、一生宿敌·避坑指南越指越难

- 上面的部分中我已经详尽的介绍了 `GCD` 中任务/队列的使用，那么下面我再向大家分享下实际场景经常遇到坑

##### 4.1 同步任务会阻塞当前线程导致死锁

- 举个例子，下面这段代码：

```swift
        NSLog("开始 - %@", Thread.current)
        DispatchQueue.main.sync(execute: { () -> Void in
                NSLog("执行：sync - %@", Thread.current)
        })
        NSLog("结束 - %@", Thread.current)
```

- 运行一下：

```swift
        NSLog("开始 - %@", Thread.current)
        DispatchQueue.main.sync(execute: { () -> Void in
                NSLog("执行：sync - %@", Thread.current)
        })
        NSLog("结束 - %@", Thread.current)
```

- 我们发现：只会打印第一句：`开始 - <NSThread: 0x60000246c240>{number = 1, name = main}`，然后主线程就卡死了
- 我们的步骤就是：打印完第一句后，`dispatch_sync` 立即阻塞当前的主线程，然后把 Block 中的任务放到 `main_queue` 中，可是 `main_queue` 中的任务会被取出来放到主线程中执行，但主线程这个时候已经被阻塞了，所以 `Block` 中的任务就不能完成，它不完成，`dispatch_sync` 就会一直阻塞主线程，这就是死锁现象。导致主线程一直卡死。
- 总结一下就是同步任务会阻塞当前线程，然后把 `Block` 中的任务放到指定的队列中执行，只有等到 `Block` 中的任务完成后才会让当前线程继续往下运行。

##### 4.2 同步和异步串行

- 首先我们先看下面的 `demo` 代码：

```swift
        let serialQueue = DispatchQueue(label: "hornhuang")
        serialQueue.async {
            NSLog("同步队列开始前 - %@", Thread.current)
            serialQueue.sync {
                NSLog("同步队列运行中 - %@", Thread.current)
            }
            NSLog("同步队列结束后 - %@", Thread.current)
        }
```

- 运行一下

```html
2020-07-31 20:42:47.405313+0800 swift-test[50886:526759] 同步队列开始前 - <NSThread: 0x6000021992c0>{number = 6, name = (null)}
(lldb) 
```

- 很明显 `同步队列运行中 - %@` 和 `同步队列结束后 - %@` 没有被打印出来
- 按执行步骤来分析，首先我们先创建了一个串行队列：serialQueue
- 接着我们在异步任务中打印出了 同步队列开始前 - %@ 这句
- 然后`serialQueue.async` 异步执行，所以当前线程不会被阻塞，于是有了两条线程，一条当前线程继续往下打印出 `同步队列结束后 - %@`这句, 另一台执行 Block 中的内容打印 `同步队列开始前 - %@` 这句。因为这两条是并行的，所以打印的先后顺序无所谓。
- 但此时的情况和上一个例子一样了。由于 `serialQueue.sync` 是同步执行，于是它所在的线程会被阻塞，一直等到 `同步队列运行中 - %@` 里的任务执行完才会继续往下。这时 `同步队列运行中 - %@` 就高兴的把自己 Block 中的任务放到 `queue` 中，可谁想 `queue` 是一个串行队列，一次执行一个任务，所以 `同步队列运行中 - %@` 的 Block 必须等到前一个任务执行完毕，可万万没想到的是 `queue` 正在执行的任务就是被 `同步队列运行中 - %@` 阻塞了的那个。于是又发生了死锁。所以 `同步队列运行中 - %@` 所在的线程被卡死了。剩下的两句代码自然不会打印。



#### - 
#### - 
#### - 



### Flutter

- 首先，Flutter 自建了一个绘制引擎，底层是由 C++ 编写的引擎，负责渲染，文本处理，Dart VM 等；上层的 Dart Framework 直接调用引擎。避免了以往 JS 解决方案的 JS Bridge、线程跳跃等问题。

- 第二，引擎基于 Skia 绘制，操作 OpenGL、GPU，不需要依赖原生的组件渲染框架。

- 第三，Dart 的引入，是 Flutter 团队做了很多思考后的决定，Dart 有 AOT 和 JIT 两种模式，线上使用时以 AOT 的方式编译成机器代码，保证了线上运行时的效率；而在开发期，Dart 代码以 JIT 的方式运行，支持代码的即时生效（HotReload)，提高开发效率。

- 第四，Flutter 的页面和布局是基于 Widget 树的方式，看似不习惯，但这种树状结构解析简单，布局、绘制都可以单次遍历完成计算

简单说就是：用GPU直接绘制UI实现的跨平台高性能且不受Native UI拖累



#### State生命周期

一个StatefulWidget类会对应一个State类，State表示与其对应的StatefulWidget要维护的状态，

- initState()
  界面初始化状态时调用
- didChangeDependencies()
  当state状态对象发生变化时调用（典型的场景是当系统语言Locale或应用主题改变时，Flutter framework会通知widget调用此回调。）
- build()
  主要是用于构建Widget子树,调用时机如下：
  在调用initState()之后。
  在调用didUpdateWidget()之后。
  在调用setState()之后。
  在调用didChangeDependencies()之后。
  在State对象从树中一个位置移除后（会调用deactivate）又重新插入到树的其它位置之后。
- reassemble()
  主要用于调试，热重载时调用，release环境下不会调用
- didUpdateWidget()
  用于更新widget ，Widget.canUpdate返回true则会调用此回调
- deactivate()
  从widget树中移除State对象t时调用（位置交换）
- dispose()
  从widget树中移除State对象，并不再插入此State对象时调用（一般用于释放资源）



#### 2. main()和runApp()函数在flutter的作用分别是什么？有什么关系吗？

  main函数是类似于java语言的程序运行入口函数

  runApp函数是渲染根widget树的函数

  一般情况下runApp函数会在main函数里执行

#### 3. 什么是widget? 在flutter里有几种类型的widget？分别有什么区别？能分别说一下生命周期吗？    

  widget在flutter里基本是一些UI组件

  有两种类型的widget，分别是statefulWidget 和 statelessWidget两种

  statelessWidget不会自己重新构建自己，但是statefulWidget会  

#### 4. Hot Restart 和 Hot Reload 有什么区别吗？

  Hot Reload比Hot Restart快，Hot Reload会编译我们文件里新加的代码并发送给dart虚拟机，dart会更新widgets来改变UI，而Hot Restart会让dart 虚拟机重新编译应用。另一方面也是因为这样， Hot Reload会保留之前的state，而Hot Restart回你重置所有的state回到初始值。

#### 5. 在flutter里streams是什么？有几种streams？有什么场景用到它？

  Stream 用来处理连续的异步操作，Stream 是一个抽象类，用于表示一序列异步数据的源。它是一种产生连续事件的方式，可以生成数据事件或者错误事件，以及流结束时的完成事件

  Stream 分单订阅流和广播流。

  网络状态的监控

#### 6. 简单说一下在flutter里async和await？

  await的出现会把await之前和之后的代码分为两部分，await并不像字面意思所表示的程序运行到这里就阻塞了，而是立刻结束当前函数的执行并返回一个Future，函数内剩余代码通过调度异步执行。

  async是和await搭配使用的，await只在async函数中出现。在async 函数里可以没有await或者有多个await。

#### 7. future 和steam有什么不一样？ 

  在 Flutter 中有两种处理异步操作的方式 Future 和 Stream，Future 用于处理单个异步操作，Stream 用来处理连续的异步操作。

#### 8. 什么是flutter里的key? 有什么用？

  key是Widgets，Elements和SemanticsNodes的标识符。

  key有LocalKey 和 GlobalKey两种。

  LocalKey 如果要修改集合中的控件的顺序或数量。GlobalKey允许 Widget 在应用中的任何位置更改父级而不会丢失 State。

#### 9. 在什么场景下使用profile mode？

  profile model 是用来评估app性能的，profile model 和release mode是相似的，只有保留了一些需要评估app性能的debug功能。在模拟器上profile model是不可用的。  

#### 10. 怎么做到只在debug mode运行代码？

  foundation有一个静态的变量kReleaseMode来表示是否是release mode

#### 11. 怎么理解Isolate？  

   isolate是Dart对actor并发模式的实现。 isolate是有自己的内存和单线程控制的运行实体。isolate本身的意思是“隔离”，因为isolate之间的内存在逻辑上是隔离的。isolate中的代码是按顺序执行的，任何Dart程序的并发都是运行多个isolate的结果。因为Dart没有共享内存的并发，没有竞争的可能性所以不需要锁，也就不用担心死锁的问题







#### 旋转数组



#### 买卖股票



#### 找到数组中，只出现一次的数



#### 量表是否有环



#### 打印出最长没有重复字符的子串







- 奇数偶数数组，重新排序保证顺序不变，奇数在前偶数在后
- 2数/3数之和

- 不使用for查找数组最大值？

- 手写二分法
- 电梯状态机设计

最长公共子串

两个数组交换一个值，让和相等，找到这两个值

- 快排原理，最好&最坏时间复杂度
- 怎么判断单向链表是否有环
- 写一个单例
- 求Sqrt(n) 二分法

[回文序列](https://interview.nowcoder.com/interview/273916/interviewee?code=EshuwsFt#userCode)

一上来写归并排序 写镜像二叉树

两个升序链表合并

求树高度

kmp算法

写个算法题，比如大学里面那种c语言程序，return0 就退出了，为什么你写的andriod app 一行一行代码执行了不会退出？

两个线程交替打印

哲学家进餐问题 写出pv操作伪代码 这个问题比较经典。。但是没准备充分，只给出来两种比较简单的解决方法。。

两个数组找中位数

算法题：链表有无环

二叉树前序遍历递归版本和非递归版本

1. 手撕代码：寻找出现次数的中位数

2. 手撕代码：最少去掉几个字符，字符串可以变成回文字符串（动态规划）

3. 算法：鸡下蛋卖一半的鸡蛋，剩下的鸡蛋第二天未成年鸡、第三天成年鸡，成年鸡可下蛋

4. 算法：n个数，找出连续k个数字之和最小

5. 手撕快排

6. 手撕线程互斥

7. 算法: 一堆数，有一个数出现奇数次，其余的数都出现偶数次。找出这个数

8. 手写字符转数字4

9. 算法：N个数找第m大个数。

10. 哈希算法

11. 抛硬币(两个人抛硬币，谁先抛到正面，谁胜，反面给另一个人，求先抛的人胜的概率)

12. 快排(思想，找中枢(优化，三数取中)，时间复杂度)

13. 快排(思想，中枢数的选取，缺点)

14. 算法：一堆数，1个数出现一次，其余数出现两次，求出现一次的数

    - 一堆数，2个数出现1此，其余数出现两次，求这两个出现1次的数
    - 算法，1万个数找前100大，快排的时间复杂度
    - 算法：统计int数，二进制中1的个数
    - 算法: 删除链表中重复节点
    - 算法：树的深度

    算法，判断两个字符串是否元素相同(顺法序不同)

智力题：一个长方形，掏取一个大小未知的圆，圆心知道，画一条线，让这个长方形等分。

算法：两个链表是否存在公共节点，找到第一个公共节点

算法:翻转数字

撕简单算法。AAAA VBBB DDD给这样一个字符数组。每个单词之间保留一个空格，首字母大写，其他小写

解数独

介绍一种快排算法(快排，快排优化https://blog.csdn.net/qq_38289815/article/details/82718428)

---------------------------------








![](https://img-blog.csdnimg.cn/20190916171535126.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4NDk5ODU5,size_16,color_FFFFFF,t_70)