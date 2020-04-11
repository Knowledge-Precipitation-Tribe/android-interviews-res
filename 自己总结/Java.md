## 一、Java 基础



#### [Java Exception 结构](https://www.cnblogs.com/wrencai/p/5648442.html)

![](https://images2015.cnblogs.com/blog/641003/201607/641003-20160706232044280-355354790.png)



####  Java 异常处理机制

- 捕获异常：try、catch 和 finally

try 块：用于捕获异常。其后可接零个或多个catch块，如果没有catch块，则必须跟一个finally块。

catch 块：用于处理try捕获到的异常。

finally 块：无论是否捕获或处理异常，finally块里的语句都会被执行。当在try块或catch块中遇到return语句时，finally语句块将在方法返回之前被执行。 
在以下4种特殊情况下，finally块不会被执行： 
1）在finally语句块中发生了异常。 
2）在前面的代码中用了System.exit()退出程序。 
3）程序所在的线程死亡。 
4）关闭CPU。

- try、catch、finally语句块的执行顺序:

1)当try没有捕获到异常时：try语句块中的语句逐一被执行，程序将跳过catch语句块，执行finally语句块和其后的语句；

2)当try捕获到异常，catch语句块里没有处理此异常的情况：此异常将会抛给JVM处理，finally语句块里的语句还是会被执行，但finally语句块后的语句不会被执行；

3)当try捕获到异常，catch语句块里有处理此异常的情况：在try语句块中是按照顺序来执行的，当执行到某一条语句出现异常时，程序将跳到catch语句块，并与catch语句块逐一匹配，找到与之对应的处理程序，其他的catch语句块将不会被执行，而try语句块中，出现异常之后的语句也不会被执行，catch语句块执行完后，执行finally语句块里的语句，最后执行finally语句块后的语句；若catch里面throw了异常，则finally语句块后面的语句不会执行

- 抛出异常 throw 和 throws 
    任何Java代码都可以通过throw抛出异常，从方法中抛出的任何异常都必须使用throws子句。

异常链：Java这种向上传递异常信息的处理机制 
Java方法抛出的可查异常将依据调用栈、沿着方法调用的层次结构一直传递到具备处理能力的调用方法，最高层次到main方法为止。如果异常传递到main方法，而main不具备处理能力，也没有通过throws声明抛出该异常，将可能出现编译错误。

Throwable类中的常用方法 

- getCause()：返回抛出异常的原因。如果 cause 不存在或未知，则返回 null。 

- getMeage()：返回异常的消息信息。 

- printStackTrace()：对象的堆栈跟踪输出至错误输出流，作为字段 System.err 的值。







#### 对象的加载过程？（类的初始化此过程）

- 类加载过程主要包含加载、验证、准备、解析、初始化、使用、卸载七个方

面，下面一一阐述。

1. 加载：获取定义此类的二进制字节流，生成这个类的 java.lang.Class 对象

2. 验证：保证 Class 文件的字节流包含的信息符合 JVM 规范，不会给 JVM 造成

危害

3. 准备：准备阶段为变量分配内存并设置类变量的初始化

4. 解析：解析过程是将常量池内的符号引用替换成直接引用

5. 初始化：不同于准备阶段，本次初始化，是根据程序员通过程序制定的计划

去初始化类的变量和其他资源。这些资源有 static{}块，构造函数，父类的初始

化等

6. 使用：使用过程就是根据程序定义的行为执行

7. 卸载：卸载由 GC 完成。



#### 什么情况下会触发类的初始化

1、 遇到 new，getstatic，putstatic，invokestatic 这 4 条指令；

2、 使用 java.lang.reflect 包的方法对类进行反射调用；

3、 初始化一个类的时候，如果发现其父类没有进行过初始化，则先初始化其父类（注意！如果其父类是接口的话，则不要求初始化父类）；

4、 当虚拟机启动时，用户需要指定一个要执行的主类（包含 main 方法的那个类），虚拟机会先初始化这个主类；

5、 当使用 jdk1.7 的动态语言支持时，如果一个 java.lang.invoke.MethodHandle实例最后的解析结果 REF_getstatic，REF_putstatic,REF_invokeStatic 的方法句柄，并且这个方法句柄所对应的类没有进行过初始化，则先触发其类初始化



#### 双亲委托模式/Java类加载器原理

- 类加载器查找 class 所采用的是双亲委托模式，所谓双亲委托模式就是判断该类

是否已经加载，如果没有则不是自身去查找而是委托给父加载器进行查找，这

样依次进行递归，直到委托到最顶层的 Bootstrap ClassLoader,如果 Bootstrap

ClassLoader 找到了该 Class,就会直接返回，如果没找到，则继续依次向下查找，

如果还没找到则最后交给自身去查找





####  双亲委托模式的好处

1. 避免重复加载，如果已经加载过一次 Class，则不需要再次加载，而是直接读

取已经加载的 Class

2. 更加安全，确保，java 核心 api 中定义类型不会被随意替换，比如，采用双亲

委托模式可以使得系统在 Java 虚拟机启动时旧加载了 String 类，也就无法用自

定义的 String 类来替换系统的 String 类，这样便可以防止核心 API 库被随意篡

改



#### Java IO 结构与区别

- 按照流的方向：输入流（inputStream）和输出流（outputStream）。

- 按照实现功能：节点流（可以从或向一个特定的地方（节点）读写数据。如 FileReader）和处理流（是对一个已存在的流的连接和封装，通过所封装的流的功能调用实现数据读写。如 BufferedReader。处理流的构造方法总是要带一个其他的流对象做参数。一个流对象经过其他流的多次包装，称为流的链接。）

- 按照处理数据的单位：字节流和字符流。字节流继承于 InputStream 和 OutputStream，字符流继承于 InputStreamReader 和 OutputStreamWriter。

![](https://img-blog.csdnimg.cn/20190503233300304.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxNDUyMTk=,size_16,color_FFFFFF,t_70)




#### int、char、long 各占多少字节数

int\float 占用 4 个字节，short\char 占用 2 个字节，long 占用 8 个字节，byte/boolean

占用 1 个字节基本数据类型存放在栈里，包装类栈里存放的是对象的引用，即值的地址，而值存放在堆

里。



#### 谈谈对 java 多态的理解

- 同一个消息可以根据发送对象的不同而采用多种不同的行为方式，在执行期间判断所引用

的对象的实际类型，根据其实际的类型调用其相应的方法。

作用：消除类型之间的耦合关系。实现多态的必要条件：继承、重写（因为必须调用父类

中存在的方法）、父类引用指向子类对象



#### String、StringBuffer、StringBuilder 区别

- 都是字符串类，String 类中使用字符数组保存字符串，因有 final 修饰符，String 对象是不可变的，每次对 String 操作都会生成新的 String 对象，这样效率低，且浪费内存空间。但线程安全。

- StringBuilder 和 StringBuffer 也是使用字符数组保存字符，但这两种对象都是可变的，即对字符串进行 append 操作，不会产生新的对象。它们的区别是：StringBuffer 对方法加了同步锁，是线程安全的，StringBuilder 非线程安全。



## 二、虚拟机


#### Dalvik 和ART是什么，有啥区别？⭐️⭐️

- Dalvik 是 google 的一款虚拟机，它能将以 .dex 格式压缩的程序运行起来，很适合软硬件资源有限的 Android 系统。经过改良后内存中能够支持多个 Dalvik 进程同时运行，避免一个虚拟机奔溃所有程序都崩溃的问题。但由于它存在拖慢 Android 系统而饱受争议，所以 Google 推出了新版的 ART 虚拟机
- ART 与 Dalvik 的区别主要在于预编译，就是说我们以前是用 Dalvik  的时候，每次运行程序都得把字节码编译为机器码在运行，而到了 ART 种，在我们应用程序第一次安装时，就会直接编译为机器码。所以应用程序的启动和执行都会变得更快
- Dalvik 和 ART 区别在于：
  - Dalvik 支持 32 位，而 ART 支持 64 位 
  - 而且Dalvik 时 JIT 即时编译，所以安装快，但运行慢，而 ART 用的是 AOT 预编译，所以安装慢，但运行快
  - 而且ART 还对内存回收做了优化，在堆空间划分上也有所不同



#### Dalvik 和 JVM 区别⭐️⭐️

- 首先是基础架构不同，Dalvik 是基于寄存器的，而 JVM 是基于字节码的
- 然后 Dalvik 会把程序的 .class 文件压缩为 .dex 文件，而 JVM 则是直接执行 .class 文件
- 最后在类相同的情况下 Dalvik 虚拟机可以应用间共享，而 JVM 只能各运行个的类  



#### Java内存分区（结构）

程序计数器：每个线程执行程序指令的行号

虚拟机栈：存放每个方法的栈帧，帧的入栈跟出栈就是方法执行的过程

本地方法栈：Native方法的栈

Java堆：保存Java对象的地方，细分为 Eden区， From Survivor空间， To  Survivor空间（线程共享）

方法区：线程共享，存放已经被虚拟机加载进来的类信息，常量、静态变量，JIT编译后的数据代码。java的class文件首先进入的到方法区里面去。运行时常量池是方法区的一部分。





#### JMM -  Java 内存模型

- Java 内存模型(即 Java Memory Model，简称 JMM)本身是一种抽象的概念，并不

真实存在，它描述的是一组规则或规范，通过这组规范定义了程序中各个变量

（包括实例字段，静态字段和构成数组对象的元素）的访问方式。由于 JVM 运

行程序的实体是线程，而每个线程创建时 JVM 都会为其创建一个工作内存(有些

地方称为栈空间)，用于存储线程私有的数据，而 Java 内存模型中规定所有变量

都存储在主内存，主内存是共享内存区域，所有线程都可以访问，但线程对变

量的操作(读取赋值等)必须在工作内存中进行，首先要将变量从主内存拷贝的自

己的工作内存空间，然后对变量进行操作，操作完成后再将变量写回主内存，

不能直接操作主内存中的变量，工作内存中存储着主内存中的变量副本拷贝，

前面说过，工作内存是每个线程的私有数据区域，因此不同的线程间无法访问

对方的工作内存，线程间的通信(传值)必须通过主内存来完成





#### 原子性 可见性 有序性

- 原子性：对基本数据类型的读取和赋值操作是原子性操作，这些操作不可被中

断，是一步到位的，例如 x=3 是原子性操作，而 y = x 就不是，它包含两步：第

一读取 x，第二将 x 写入工作内存；x++也不是原子性操作，它包含三部，第

一，读取 x，第二，对 x 加 1，第三，写入内存。原子性操作的类如：

AtomicInteger AtomicBoolean AtomicLong AtomicReference

- 可见性：指线程之间的可见性，既一个线程修改的状态对另一个线程是可见

的。volatile 修饰可以保证可见性，它会保证修改的值会立即被更新到主存，所

以对其他线程是可见的，普通的共享变量不能保证可见性，因为被修改后不会

立即写入主存，何时被写入主存是不确定的，所以其他线程去读取的时候可能

读到的还是旧值

- 有序性：Java 中的指令重排序（包括编译器重排序和运行期重排序）可以起到

优化代码的作用，但是在多线程中会影响到并发执行的正确性，使用 volatile 可

以保证有序性，禁止指令重排

- volatile 可以保证可见性 有序性，但是无法保证原子性，在某些情况下可以提

供优于锁的性能和伸缩性，替代 sychronized 关键字简化代码，但是要严格遵循

使用条件。



#### 如何判断对象是否死亡（两种方法）。

- 引用计数法

  - 它是给每个对象中添加一个引用计数器, 然后每当有一个计数器引用它时, 这个计数器值就加1; 当引用失效时, 计数器的值就减1; 然后计数器为 0 的对象（任何时刻）都是不可能再被使用的. 垃圾回收器就可以进行回收.

  - 优点:引用计数法的原理实现简单,	但效率比较低,  不过它大多数情况下都是个不错的算法, 例如微软的COM(component Object Model)技术, 使用ActionScript3的FlashPlayer,Python语言等领域都用了引用计数算法进行内存管理.

  - 缺点:很难解决对象之间的相互循环引用的问题

-  可达性分析算法
  在Java,C#,等语言中,都是通过可达性分析算法来判断对象是否存活.

  - 思想:通过一系"GC Roots"的对象作为起始点,从这些节点开始向下搜索, 搜索走过的路径称为引用链,当一个对象到GC Roots没有任何引用链时,则证明此对象已经死亡(不可用)，可以进行回收

![](https://img-blog.csdnimg.cn/20190814220752116.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xxMTc1OTMzNjk1MA==,size_16,color_FFFFFF,t_70)

Java 中，比如：

1. 虚拟机栈中 (栈帧中的本地变量表) 引用的对象;

2. 方法区中类静态属性引用的对象

3. 方法区中常量引用0的对象

4. 本地方法栈中JNI(即native方法)引用的对象
   引用的分类:强引用,软引用,弱引用,虚引用

都可以可作为GC Roots的对象。




#### 简单的介绍一下强引用、软引用、弱引用、虚引用（虚引用与软引用和弱引用的区别、使用软引用能带来的好处）。

- **1．强引用**

  就是我们大部分使用引用一般都是强引用。如果一个对象使用强引用，那垃圾回收器就一定不会回收它。即使是内存空间不足的时候，Java 虚拟机宁愿抛出 OutOfMemoryError 错误，让我们的程序异常终止，也不会回收具有强引用的对象来解决内存不足问题

- **2．软引用（SoftReference）**

  如果一个对象是软引用，当内存空间足够，垃圾回收器不会回收它，但是一旦内存空间不够了，就会回收这些对象的内存。所以软引用可用来实现内存敏感的高速缓存，够的时候用，不够时释放。

  （ 软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收，JAVA 虚拟机就会把这个软引用加入到与之关联的引用队列中。）

- **3．弱引用（WeakReference）**

  弱引用类似于软引用。**弱引用与软引用的区别在于：弱引用的对象拥有更短暂的生命周期**。**在垃圾回收器线程扫描它 所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存。**不过，由于垃圾回收器是一个优先级很低的线程， 因此不一定会很快发现那些只具有弱引用的对象。

  （弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被垃圾回收，Java 虚拟机就会把这个弱引用加入到与之关联的引用队列中。）

- **4．虚引用（PhantomReference）**

  “虚引用” 顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收。

  虚引用主要用来跟踪对象被垃圾回收的活动。

- **5．虚引用与软引用和弱引用的一个区别：**

  虚引用必须和引用队列（ReferenceQueue）联合使用。当垃 圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。程序可以通过判断引用队列中是 否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。程序如果发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。

  特别注意，在程序设计中一般很少使用弱引用与虚引用，使用软引用的情况较多，这是因为软引用可以加速 JVM 对垃圾内存的回收速度，可以维护系统的运行安全，防止内存溢出（OutOfMemory）等问题的产生。

![](https://img-blog.csdn.net/20180606220747457?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2p1bmp1bmJhMjY4OQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)





#### 如何判断一个常量是废弃常量

- **运行时常量池主要回收的是废弃的常量。**那么，我们如何判断一个常量是废弃常量呢？

- 假如在常量池中存在字符串 “abc”，如果当前没有任何 String 对象引用该字符串常量的话，就说明常量 “abc” 就是废弃常量，如果这时发生内存回收的话而且有必要的话，“abc” 就会被系统清理出常量池。

- JDK1.7 及之后版本的 JVM 已经将运行时常量池从方法区中移了出来，在 Java 堆（Heap）中开辟了一块区域存放运行时常量池。



#### 如何判断一个类是无用的类

- 方法区主要是放类的 Class 文件的

- 如果这个类所有的实例都已经被回收，那么 Java 堆中就不会存在该类的任何实例。

- 然后加载该类的 ClassLoader 也已经被回收。

- 该类的 java.lang.Class 对象没有在任何地方被引用，也无法在任何地方通过反射访问该类的方法。

- 这几种情况都满足的话就可以回收，但不是一定会回收



#### 垃圾收集有哪些算法，各自的特点？

**1．标记-清除算法 Mark-Sweep**

- 它分为标记和清除两个阶段：先把所有活动的对象标记出来，然后把没有被标记的对象统一清除掉。但是它有两个问题，一是效率问题，两个过程的效率都不高。二是空间问题，清除之后会产生大量不连续的内存。

**2．复制算法 Copying （新生代）**

- 复制算法是将原有的内存空间分成两块，每次只使用其中的一块。在GC时，将正在使用的内存块中的存活对象复制到未使用的那一块中，然后清除正在使用的内存块中的所有对象，并交换两块内存的角色，完成一次垃圾回收。它比标记-清除算法要高效，但不适用于存活对象较多的内存，因为复制的时候会有较多的时间消耗。它的致命缺点是会有一半的内存浪费。

**3．标记整理算法 Mark-Compact（老年代）**

- 标记整理算法适用于存活对象较多的场合，它的标记阶段和标记-清除算法中的一样。整理阶段是将所有存活的对象压缩到内存的一端，之后清理边界外所有的空间。它的效率也不高。

-  **区别**

  （1）效率：复制算法>标记/整理算法>标记/清除算法（此处的效率只是简单的对比时间复杂度，实际情况不一定如此）。

  （2）内存整齐度：复制算法=标记/整理算法>标记/清除算法。

  （3）内存利用率：标记/整理算法=标记/清除算法>复制算法。

**4．分代收集算法：（新生代的GC+老年代的GC）**

- 少量对象存活，适合复制算法：在新生代中，每次GC时都发现有大批对象死去，只有少量存活，那就选用复制算法，只需要付出少量存活对象的复制成本就可以完成GC。

- 大量对象存活，适合用标记-清理/标记-整理：在老年代中，因为对象存活率高、没有额外空间对他进行分配担保，就必须使用“标记-清理”/“标记-整理”算法进行GC。



#### 什么对象会进入老年代

1. 大对象直接进入老年代

   - 大对象就是需要大量连续内存空间的对象（比如：字符串、数组）

2. 长期存活的对象将进入老年代

   - 既然虚拟机采用了分代收集的思想来管理内存，那么内存回收时就必须能识别哪些对象应放在新生代，哪些对象应放在老年代中。为了做到这一点，虚拟机给每个对象一个对象年龄（Age）计数器。

   - 如果对象在 Eden 出生并经过第一次 Minor GC 后仍然能够存活，并且能被 幸存者区 容纳的话，将被移动到 幸存者区 空间中，并将对象年龄设为 1. 对象在 Survivor 中每熬过一次 MinorGC, 年龄就增加 1 岁，当它的年龄增加到一定程度（默认为 15 岁），就会被晋升到老年代中。



#### HotSpot 为什么要分为新生代和老年代？

- 其实不分代完全可以完成 GC 的功能，分代的唯一理由就是优化GC性能。你先想想，如果没有分代，那我们所有的对象都在一块，GC的时候我们要找到哪些对象没用，这样就会对堆的所有区域进行扫描。而我们的很多对象都是朝生夕死的，如果分代的话，我们把新创建的对象放到某一地方，当GC的时候先把这块存“朝生夕死”对象的区域进行回收，这样就会腾出很大的空间出来。



#### 常见的垃圾回收器有那些？

> 如果说收集算法是内存回收的方法论，那么垃圾收集器就是内存回收的具体实现。

**1．并行和并发概念补充**

**（1）并行（Parallel）**

指多条垃圾收集线程并行工作，但此时用户线程仍然处于等待状态。

**（2）并发（Concurrent）**

指用户线程与垃圾收集线程同时执行（但不一定是并行，可能会交替执行），用户程序在继续运行，而垃圾收集器运行在另一个 CPU 上。

**2．Serial 收集器**

Serial（串行）收集器收集器是最基本、历史最悠久的垃圾收集器了。大家看名字就知道这个收集器是一个单线程收集器了。它的 “单线程” 的意义不仅仅意味着它只会使用一条垃圾收集线程去完成垃圾收集工作，更重要的是它在进行垃圾收集工作的时候必须暂停其他所有的工作线程（ “Stop The World” ），直到它收集结束。

新生代采用复制算法，老年代采用标记 - 整理算法。

**3．ParNew 收集器**

ParNew 收集器其实就是 Serial 收集器的多线程版本，除了使用多线程进行垃圾收集外，其余行为（控制参数、收集算法、回收策略等等）和 Serial 收集器完全一样。

新生代采用复制算法，老年代采用标记 - 整理算法。

**4．Parallel Scavenge 收集器**

Parallel Scavenge 收集器也是使用复制算法的多线程收集器，它看上去几乎和 ParNew 都一样。

Parallel Scavenge 收集器关注点是吞吐量（高效率的利用 CPU）。CMS 等垃圾收集器的关注点更多的是用户线程的停顿时间（提高用户体验）。所谓吞吐量就是 CPU 中用于运行用户代码的时间与 CPU 总消耗时间的比值。

**5．Serial Old 收集器**

Serial 收集器的老年代版本，它同样是一个单线程收集器。它主要有两大用途：一种用途是在 JDK1.5 以及以前的版本中与 Parallel Scavenge 收集器搭配使用，另一种用途是作为 CMS 收集器的后备方案。

**6．Parallel Old 收集器**

Parallel Scavenge 收集器的老年代版本。使用多线程和 “标记 - 整理” 算法。在注重吞吐量以及 CPU 资源的场合，都可以优先考虑 Parallel Scavenge 收集器和 Parallel Old 收集器。

**7．CMS 收集器**

CMS（Concurrent Mark Sweep）收集器是一种以获取最短回收停顿时间为目标的收集器。它而非常符合在注重用户体验的应用上使用。

CMS（Concurrent Mark Sweep）收集器是 HotSpot 虚拟机第一款真正意义上的并发收集器，它第一次实现了让垃圾收集线程与用户线程（基本上）同时工作。

从名字中的 Mark Sweep 这两个词可以看出，CMS 收集器是一种 “标记 - 清除” 算法实现的，它的运作过程相比于前面几种垃圾收集器来说更加复杂一些。**整个过程分为四个步骤：**

**（1）初始标记：** 暂停所有的其他线程，并记录下直接与 root 相连的对象，速度很快 ；

**（2）并发标记：** 同时开启 GC 和用户线程，用一个闭包结构去记录可达对象。但在这个阶段结束，这个闭包结构并不能保证包含当前所有的可达对象。因为用户线程可能会不断的更新引用域，所以 GC 线程无法保证可达性分析的实时性。所以这个算法里会跟踪记录这些发生引用更新的地方。

**（3）重新标记：** 重新标记阶段就是为了修正并发标记期间因为用户程序继续运行而导致标记产生变动的那一部分对象的标记记录，这个阶段的停顿时间一般会比初始标记阶段的时间稍长，远远比并发标记阶段时间短

**（4）并发清除：** 开启用户线程，同时 GC 线程开始对为标记的区域做清扫。

CMS收集器是一款优秀的垃圾收集器，主要优点：并发收集、低停顿。但是它有下面三个明显的缺点：

- 对 CPU 资源敏感；
- 无法处理浮动垃圾；
- 它使用的回收算法 -“标记 - 清除” 算法会导致收集结束时会有大量空间碎片产生。

**8．G1 收集器**

G1 (Garbage-First) 是一款面向服务器的垃圾收集器，主要针对配备多颗处理器及大容量内存的机器。以极高概率满足 GC 停顿时间要求的同时，还具备高吞吐量性能特征.

被视为 JDK1.7 中 HotSpot 虚拟机的一个重要进化特征。它具备一下特点：

并行与并发：G1 能充分利用 CPU、多核环境下的硬件优势，使用多个 CPU（CPU 或者 CPU 核心）来缩短 Stop-The-World 停顿时间。部分其他收集器原本需要停顿 Java 线程执行的 GC 动作，G1 收集器仍然可以通过并发的方式让 java 程序继续执行。

分代收集：虽然 G1 可以不需要其他收集器配合就能独立管理整个 GC 堆，但是还是保留了分代的概念。

空间整合：与 CMS 的 “标记 – 清理” 算法不同，G1 从整体来看是基于 “标记整理” 算法实现的收集器；从局部上来看是基于 “复制” 算法实现的。

可预测的停顿：这是 G1 相对于 CMS 的另一个大优势，降低停顿时间是 G1 和 CMS 共同的关注点，但 G1 除了追求低停顿外，还能建立可预测的停顿时间模型，能让使用者明确指定在一个长度为 M 毫秒的时间片段内。

G1 收集器的运作大致分为以下几个步骤：

- 初始标记
- 并发标记
- 最终标记
- 筛选回收

G1 收集器在后台维护了一个优先列表，每次根据允许的收集时间，优先选择回收价值最大的 Region (这也就是它的名字 Garbage-First 的由来)。这种使用 Region 划分内存空间以及有优先级的区域回收方式，保证了 G1 收集器在有限时间内可以尽可能高的收集效率（把内存化整为零）



#### Minor Gc 和 Full GC 有什么不同呢？

- 由于目前垃圾收集器都会采用分代回收算法，因此需要把堆内存分为新生代和老年代，方便我们就可以根据各个年代的特点选择合适的垃圾收集算法。

- 新生代 GC（Minor GC）: 指发生新生代的的垃圾收集动作，Minor GC 非常频繁，回收速度一般也比较快。

- 老年代 GC（Major GC/Full GC）: 指发生在老年代的 GC，出现了 Major GC 经常会伴随一般一次的 Minor GC（并非绝对），Major GC 的速度一般会比 Minor GC 的慢 10 倍以上。





## 三、锁



#### [乐观锁和悲观锁的理解及如何实现，有哪些实现方式？](https://blog.csdn.net/qq_34337272/article/details/81072874)

- 悲观锁
  总是假设最坏的情况，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会阻塞直到它拿到锁（共享资源每次只给一个线程使用，其它线程阻塞，用完后再把资源转让给其它线程）。传统的关系型数据库里边就用到了很多这种锁机制，比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁。Java中synchronized和ReentrantLock等独占锁就是悲观锁思想的实现。
- 乐观锁
  总是假设最好的情况，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号机制和CAS算法实现。乐观锁适用于多读的应用类型，这样可以提高吞吐量，像数据库提供的类似于write_condition机制，其实都是提供的乐观锁。在Java中java.util.concurrent.atomic包下面的原子变量类就是使用了乐观锁的一种实现方式CAS实现的。




#### [CAS算法](https://blog.csdn.net/qq_34337272/article/details/81072874)

- 即compare and swap（比较与交换），是一种有名的无锁算法。无锁编程，即不使用锁的情况下实现多线程之间的变量同步，也就是在没有线程被阻塞的情况下实现变量的同步，所以也叫非阻塞同步（Non-blocking Synchronization）。CAS算法涉及到三个操作数
  - 需要读写的内存值 V
  - 进行比较的值 A
  - 拟写入的新值 B

- 当且仅当 V 的值等于 A时，CAS通过原子方式用新值B来更新V的值，否则不会执行任何操作（比较和替换是一个原子操作）。一般情况下是一个自旋操作，即不断的重试。




## 四、hashMap



#### 为什么建议在实例化 Hashmap 的时候传入大小？★★

- 因为他初始化要进行 1<<4 位移运算获得 16 的大小，这个过程要耗时
- 设置合理值提高性能，避免容量太小反复扩容
- 分配太大浪费资源



#### Node 和 Entry ？★★

- 1.7 中 table[] 类型为 Entry implement Map.Entry<,>
- 1.8 中 table[] 类型为 Node implement Map.Entry<,>



#### hashmap 的 put 和 get 方法怎么实现？★★

- put 就是 resize 之前，hash&（length - 1）求 Entry<,>[] 的 index，然后就是能放，替换（跟另一个方法 replace 效果一样，但 replace 不能添加元素）和不能放，不能放就扩容的结果
- get 就更简单了，直接 hash&（length - 1）.找到 index 位置，再用equals 去找链表/树上的位置



#### 为什么 String 和 Integer 适合被作为键值？★★

- 一方面 String 是放运行时常量池的，他的优点也就是他的缺点，就是不可变，就是说它没有 .append 方法，一次创建一个对象不能修改，如果拼接的话，比如 String s = s + "qwe" ，就会 new 出一个新的 String，然后但前的引用 s 指向新 String(s + "qwe") 的地址
- 其他很多对象没有重写 hashcode 和 equals 方法，这就导致本应该相同的对象 .equals 没重写，结果地址比较，直接拉跨，hashcode 没写，结果频繁冲突



#### HashMap 是怎么 hash 的？★★

1. 第一次是将 key 的 hashcode() 的方法得出的 hash 值，由于这个是 int ，int 4字节，也就是 32 位，大小范围在 -21亿多到 +21亿多之间，所以先得跟长度 length 取余，得到这个余数又太短容易冲突，所以把他右移 16 位，真好变成 32 位的一半，也就分成了高低两个区，这时把高半区和低半区异或，就混合了原始的高低位，增加了随机性。
2. 第二次就是将第一次得到的 hash 值和 （length - 1）做 & 运算得到 Entry[] 数组最后的存放位置



#### 除了拉链法还有什么解决冲突的办法？★★

- 开放地址法 和 再哈希法
- 开发地址法包含三方法：线行探查法、平方探查法、双重散列法
  - 线性探查法，就越是一个个往后找，找到为空的 停下、插入
  - 平方探查法也简单，字如其名就是把当前位置加 1^2 放，要还是冲突就找 2^2 ，然后3^2...以此类推
  - 双重散列法,和线性探测法类似，不同的是，线性探测每次都往后走一格，而双重散列法拥有一个附加的散列函数，每次散列结果作为向后走的格数
-  再哈希法更简单，就是设计一堆 hash 函数，这个冲突用下一个，还冲突在下一个，以此类推



#### 为什么要使用 2 的幂作为 hashMap 的大小？★★

1. 因为第一次 hash 后，第二次 hash 本来是要 hash%length 这么算，这时候如果 length 时 2 的 幂，那 hash%length <=等价于=> hash&(length-1)，由于 hash&(length-1) 的效率比取余高，所以 设置为 2 的幂，这样就可以用 & 运算代替 % 运算，提高效率
2. 之所以hash&(length-1) 效率高是因为 length 的值为 2 的幂时，length - 1 二进制全为 1 ，那么跟所传来的 hash 值做与运算就直接是后几位，这样做效率又高又均匀



#### 16是2的幂，8也是，32也是，为啥偏偏选了16？
- 我觉得就是一个经验值，定义16没有很特别的原因，只要是2次幂，其实用 8 和 32 都差不多。
- 用16只是因为作者认为16这个初始容量是能符合常用而已。




#### 头插法和尾插法怎么实现？★★

- 首先是 1.7 头插法，先把链表遍历一遍，看有没有存在，没有的话就把这个链表的头赋值到它的 next，然后把头的位置指向它
- 1.8 尾插法就是直接遍历到最后一个，边遍历，边判断有没有冲突的，有就替换，遍历到最后一个都没有，没有就把最后一个的 next 指向自己完事，贼简单



#### hashmap Entry<> 上节点太多，查询复杂度 O(n) 怎么半？★★

- 在 1.8 中当单个链表上节点大于 64 时，会将单链表转换为红黑树，查找的时间复杂的也就随之变为 O(logn)



#### Hashmap中的链表大小超过八个时会自动转化为红黑树，当删除小于六时重新变为链表，为啥呢？
- 根据泊松分布，在负载因子默认为0.75的时候，单个hash槽内元素个数为8的概率小于百万分之一，所以将7作为一个分水岭，等于7的时候不转换，大于等于8的时候才进行转换，小于等于6的时候就化为链表。


#### hashmap 什么时候扩容？★★

- 随着 hash 碰撞次数增加，单个 Entry 上的链表或者红黑树的节点越来越多，当数量 > capacity(table[]数组长度) * loadfactor(默认阈值0.75) 时，进行扩容




#### hashmap 是这么扩容的？★★

- 首先调用 resize 方法把当前 table[] 大小 *2 ，得到新数组，然后调用 transfer 方法
- 在 transfer 方法里面，把节点键值 key ，进行 rehash 后转移过去，再把值传到表中




#### hashmap 是怎么 resize 的？★★

- resize 就是扩容，resize 时扩容入口方法，在里面 new 新的 Node 数组



#### 为什么 hashMap 不是线程安全的？★★

- hashmap 线程不安全主要发生在 put 和 resize 方法
- 两个线程同时往 hashmap 里 put 东西，A 线程本来要往最后一个节点 node 后添加新元素，这是由于时间片轮到 B 线程，B 线程也往 node 后添加元素，并且添加成功，这是时间片轮转会 A 线程，A 线程再把自己的东西添加到 node 后，这是，就造成了 B 线程所添加数据的丢失
- 还有一种是发生在 resize 方法里
    - 1.7 由于时头插法，原来的尾巴移过去可能变成头，原来的头变成尾，但之前上个 Entry 里，又有头尾关系，结构就变成了环状
    - 由于 1.8 变为尾插，之前啥顺序，之后还是啥循序，顶多所在 Entry 的位置变了，所以不影响



#### 为什么会形成头尾相接的环状结构？★★

- 首先我们 1.7 用的是头插法，所以新 Map 是逆序的，原来 A -> B，现在会变成 B -> A，这就可能有问题
- 假设我们有 A->B->C  ,线程1 首先执行 Node next = e.next， 保存 next（B） 和 e（A） 的状态，准备移动 e 和 next.
- 这时线程2来了，所以线程1的状态暂时保持在本地内存里，然后线程2完成 resize 全部操作，得到两个新链表：B->A->null  ; C -> null.
- 这时轮转到线程1执行，线程1，先把 当前的 e（A） 移过去，变成 A->null， 再把 next（B）执行 Node next = e.next，但这时由于线程2 ，B现在 -> A, 所以next 为A，e为 B
- 然后 e 移过去变成 B->A->null，再去移 next（A）；首先还是 Node next = e.next， 保存 next（nul） 和 e（A） 再移过去加变成了 A->B->A->B（由于头插，所以A由 A->null 变为 A->B）
- 这时由于 next == null 所以停止，此时 A->B->A->B 循环



#### HashMap在多线程环境下存在线程安全问题，那你一般都是怎么处理这种情况的？
- 美丽迷人的面试官您好，一般在多线程的场景，我都会使用好几种不同的方式去代替：
- 使用Collections.synchronizedMap(Map)创建线程安全的map集合；
	- Hashtable
	- ConcurrentHashMap
- 不过出于线程并发度的原因，我都会舍弃前两者使用最后的ConcurrentHashMap，他的性能和效率明显高于前两者。


#### Collections.synchronizedMap是怎么实现线程安全的你有了解过么？
- 在SynchronizedMap内部维护了一个普通对象Map，还有排斥锁mutex
![](https://user-gold-cdn.xitu.io/2019/12/17/16f14087ded252dc?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)
- ` Collections.synchronizedMap(new HashMap<>(16));`
- 我们在调用这个方法的时候就需要传入一个Map，可以看到有两个构造器，如果你传入了mutex参数，则将对象排斥锁赋值为传入的对象。
- 如果没有，则将对象排斥锁赋值为this，即调用synchronizedMap的对象，就是上面的Map。
- 创建出synchronizedMap之后，再操作map的时候，就会对方法上锁，如图全是锁
![](https://user-gold-cdn.xitu.io/2019/12/17/16f14087dffc8e69?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)


#### HashMap与HashTable区别是啥？★★

1. hashmap 的父类是 abstractmap, 而 hashtable 父类是 Dictionary（字典） 类
2. hashmap 是线程不安全的，hashtable 加了 synchorized 同步锁线程安全
3. hashmap 没加锁，所以比 hashtable 效率高
4. hashmap 的 key 和 value 可以为空，但 hashtable 不行

	- 原因是Hashtable使用的是安全失败机制（fail-safe），这种机制会使你此次读到的数据不一定是最新的数据。
	- 如果你使用null值，就会使得其无法判断对应的key是不存在还是为空，因为你无法再调用一次contain(key）来对key是否存在进行判断，ConcurrentHashMap同理。
5. hashmap 是用的自己定义的 hash算法，hashtable 是用 key 对象自己的 hashcode() 值
6. hashmap 扩容是 *2 而 hashtable 扩容是 *2+1
7. 迭代器 hashmap 用的是Iterator（其原理是fail-fast，就说，一个线程在遍历元素，另一个线程在修改结构，就会抛 ConcurrentModificationException 并发修改异常） hashtable用的是 enumerator 原理是fail-safe（它加锁，线程安全，所以不会抛异常）



#### 为什么不用 HashTable？★★

- Directry 类已被废弃，所以子类 Hashtable 自然也被废弃，它内部有很多没有优化的冗余代码
- 所有方法加synchronized 效率低，有更好的 ConcurrentHashMap 类替代
![](https://user-gold-cdn.xitu.io/2019/12/17/16f14087e8fcfc64?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)





## 六、ConcurrentHashMap



#### 的数据结构吧，以及为啥他并发度这么高？

- ConcurrentHashMap 底层是基于 数组 + 链表 组成的，不过在 jdk1.7 和 1.8 中具体实现稍有不同。
- 我先说一下他在1.7中的数据结构吧：
  ![](https://user-gold-cdn.xitu.io/2019/12/17/16f140880441eab3?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)
- 如图所示，是由 Segment 数组、HashEntry 组成，和 HashMap 一样，仍然是数组加链表。
  Segment 是 ConcurrentHashMap 的一个内部类，主要的组成如下：

![](https://user-gold-cdn.xitu.io/2020/4/2/17138e7a4c094c22?w=804&h=363&f=png&s=14077)

HashEntry跟HashMap差不多的，但是不同点是，他使用volatile去修饰了他的数据Value还有下一个节点next。


#### volatile的特性是啥？
- 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。（实现可见性）
- 禁止进行指令重排序。（实现有序性）
- volatile 只能保证对单次读/写的原子性。i++ 这种操作不能保证原子性。


#### 能说说他并发度高的原因么？
- 原理上来说，ConcurrentHashMap 采用了分段锁技术，其中 Segment 继承于 ReentrantLock。
不会像 HashTable 那样不管是 put 还是 get 操作都需要做同步处理，理论上 ConcurrentHashMap 支持 CurrencyLevel (Segment 数组数量)的线程并发。
- 每当一个线程占用锁访问一个 Segment 时，不会影响到其他的 Segment。
- 就是说如果容量大小是16他的并发度就是16，可以同时允许16个线程操作16个Segment而且还是线程安全的。

![](https://user-gold-cdn.xitu.io/2020/4/2/17138ea570d48ea1?w=805&h=257&f=png&s=19339)

- 他先定位到Segment，然后再进行put操作。

- 我们看看他的put源代码，你就知道他是怎么做到线程安全的了，关键句子我注释了。

![](https://user-gold-cdn.xitu.io/2020/4/2/17138ebb4895d48c?w=646&h=861&f=png&s=44594)

- 首先第一步的时候会尝试获取锁，如果获取失败肯定就有其他线程存在竞争，则利用 `scanAndLockForPut()` 自旋获取锁。

1. 尝试自旋获取锁。
2. 如果重试的次数达到了 `MAX_SCAN_RETRIES` 则改为阻塞锁获取，保证能获取成功。



#### 那他get的逻辑呢？

- get 逻辑比较简单，只需要将 Key 通过 Hash 之后定位到具体的 Segment ，再通过一次 Hash 定位到具体的元素上。

- 由于 HashEntry 中的 value 属性是用 volatile 关键词修饰的，保证了内存可见性，所以每次获取时都是最新值。

- ConcurrentHashMap 的 get 方法是非常高效的，**因为整个过程都不需要加锁**。



#### 你有没有发现1.7虽然可以支持每个Segment并发访问，但是还是存在一些问题？

- 是的，因为基本上还是数组加链表的方式，我们去查询的时候，还得遍历链表，会导致效率很低，这个跟jdk1.7的HashMap是存在的一样问题，所以他在jdk1.8完全优化了。



#### 那你再跟我聊聊jdk1.8他的数据结构是怎么样子的呢？

- 其中抛弃了原有的 Segment 分段锁，而采用了 `CAS + synchronized` 来保证并发安全性。

- 跟HashMap很像，也把之前的HashEntry改成了Node，但是作用不变，把值和next采用了volatile去修饰，保证了可见性，并且也引入了红黑树，在链表大于一定值的时候会转换（默认是8）。



#### 同样的，你能跟我聊一下他值的存取操作么？以及是怎么保证线程安全的？

- ConcurrentHashMap在进行put操作的还是比较复杂的，大致可以分为以下步骤：

1. 根据 key 计算出 hashcode 。
2. 判断是否需要进行初始化。
3. 即为当前 key 定位出的 Node，如果为空表示当前位置可以写入数据，利用 CAS 尝试写入，失败则自旋保证成功。
4. 如果当前位置的 `hashcode == MOVED == -1`,则需要进行扩容。
5. 如果都不满足，则利用 synchronized 锁写入数据。
6. 如果数量大于 `TREEIFY_THRESHOLD` 则要转换为红黑树。


![](https://user-gold-cdn.xitu.io/2019/12/17/16f140880b600d11?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)




#### CAS是什么？自旋又是什么？

- CAS 是乐观锁的一种实现方式，是一种轻量级锁，JUC 中很多工具类的实现就是基于 CAS 的。

- CAS 操作的流程如下图所示，线程在读取数据时不进行加锁，在准备写回数据时，比较原值是否修改，若未被其他线程修改则写回，若已被修改，则重新执行读取流程。

- 这是一种乐观策略，认为并发操作并不总会发生。



![](https://user-gold-cdn.xitu.io/2019/12/17/16f1408812043a58?imageView2/0/w/1280/h/960/format/webp/ignore-error/1 )



- 还是不明白？那我再说明下，乐观锁在实际开发场景中非常常见，大家还是要去理解。

- 就比如我现在要修改数据库的一条数据，修改之前我先拿到他原来的值，然后在SQL里面还会加个判断，原来的值和我手上拿到的他的原来的值是否一样，一样我们就可以去修改了，不一样就证明被别的线程修改了你就return错误就好了。

- SQL伪代码大概如下：


`update a set value = newValue where value = #{oldValue}//oldValue就是我们执行前查询出来的值 `



#### CAS就一定能保证数据没被别的线程修改过么？

- 并不是的，比如很经典的ABA问题，CAS就无法判断了。

#### 什么是ABA？

- 就是说来了一个线程把值改回了B，又来了一个线程把值又改回了A，对于这个时候判断的线程，就发现他的值还是A，所以他就不知道这个值到底有没有被人改过，其实很多场景如果只追求最后结果正确，这是没关系的。

- 但是实际过程中还是需要记录修改过程的，比如资金修改什么的，你每次修改的都应该有记录，方便回溯。

#### 那怎么解决ABA问题？

- 用版本号去保证就好了，就比如说，我在修改前去查询他原来的值的时候再带一个版本号，每次判断就连值和版本号一起判断，判断成功就给版本号加1。

`update a set value = newValue ，vision = vision + 1 where value = #{oldValue} and vision = #{vision} // 判断原来的值和版本号是否匹配，中间有别的线程修改，值可能相等，但是版本号100%不一样`



#### 除了版本号还有别的方法保证么？

- 其实有很多方式，比如时间戳也可以，查询的时候把时间戳一起查出来，对的上才修改并且更新值的时候一起修改更新时间，这样也能保证，方法很多但是跟版本号都是异曲同工之妙，看场景大家想怎么设计吧。



#### CAS性能很高，但是我知道synchronized性能可不咋地，为啥jdk1.8升级之后反而多了synchronized？

- synchronized之前一直都是重量级的锁，但是后来java官方是对他进行过升级的，他现在采用的是锁升级的方式去做的。

- 针对 synchronized 获取锁的方式，JVM 使用了锁升级的优化方式，就是先使用**偏向锁**优先同一线程然后再次获取锁，如果失败，就升级为 **CAS 轻量级锁**，如果失败就会短暂**自旋**，防止线程被系统挂起。最后如果以上都失败就升级为**重量级锁**。

- 所以是一步步升级上去的，最初也是通过很多轻量级的方式锁定的。

#### ConcurrentHashMap的get操作又是怎么样子的呢？

- 根据计算出来的 hashcode 寻址，如果就在桶上那么直接返回值。
- 如果是红黑树那就按照树的方式获取值。
- 就不满足那就按照链表的方式遍历获取值。


![](https://user-gold-cdn.xitu.io/2019/12/17/16f14088244abb23?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



- 小结：1.8 在 1.7 的数据结构上做了大的改动，采用红黑树之后可以保证查询效率（`O(logn)`），甚至取消了 ReentrantLock 改为了 synchronized，这样可以看出在新版的 JDK 中对 synchronized 优化是很到位的。



#### CopyOnWrite

> ArrayList 可能出现问题

- 线程A执行`getLast()`方法，线程B执行`deleteLast()`方法

- 线程A执行`int lastIndex = list.size() - 1;`得到lastIndex的值是3。**同时**，线程B执行`int lastIndex = list.size() - 1;`得到的lastIndex的值**也**是3

- 此时线程B先得到CPU执行权，执行`list.remove(lastIndex)`将下标为3的元素删除了

- 接着线程A得到CPU执行权，执行`list.get(lastIndex);`，发现已经没有下标为3的元素，抛出异常了.

> 

- ArrayList底层是一个数组来存放你的列表数据，那么这时比如你要修改这个数组里的数据，你就必须先拷贝这个数组的一个副本。

- 然后你可以在这个数组的副本里写入你要修改的数据，但是在这个过程中实际上你都是在操作一个副本而已。

- 这样的话，读操作是不是可以同时正常的执行？这个写操作对读操作是没有任何的影响的吧！但那那个写线程现在把副本数组给修改完了，现在怎么才能让读线程感知到这个变化呢？

- 所以一旦写线程搞定了副本数组的修改之后，那么就可以用**volatile写的方式**，把这个副本数组赋值给volatile修饰的那个数组的引用变量了。

- 只要一赋值给那个volatile修饰的变量，立马就会对读线程可见，大家都能看到最新的数组了。

- 下面是JDK里的 CopyOnWriteArrayList 的源码。

- 大家看看写数据的时候，他是怎么拷贝一个数组副本，然后修改副本，接着通过volatile变量赋值的方式，把修改好的数组副本给更新回去，立马让其他线程可见的。







## 七、[线程池](https://juejin.im/post/5e435ac3f265da57537ea7ba#heading-1)


#### [CAS算法](https://blog.csdn.net/qq_34337272/article/details/81072874)

- 即compare and swap（比较与交换），是一种有名的无锁算法。无锁编程，即不使用锁的情况下实现多线程之间的变量同步，也就是在没有线程被阻塞的情况下实现变量的同步，所以也叫非阻塞同步（Non-blocking Synchronization）。CAS算法涉及到三个操作数
  - 需要读写的内存值 V
  - 进行比较的值 A
  - 拟写入的新值 B


#### 使用线程池有哪些好处？

- 提高系统的响应速度
- 然后线程池可以减少线程创建和销毁的消耗，如果每次多线程操作都创建一个线程，会浪费时间和消耗系统资源
- 还有的话线程池可以对多个线程进行统一管理，统一调度，使得线程的管理更加可控



#### 几种常见的线程池 ★

- FixedThreadPool：有固定数量的核心线程，没有非核心线程，且核心线程不会被回收。当有对象来时，先检查核心线程有没有空闲，有空闲直接加入，没有的话加入排队队列
- SingleThreadPool：有且只有一个核心线程，当有新线程来时，如何没有核心线程则进行创建，如果核心线程正在运行则加入等待队列，保证了线程的有序进行
- CachedThreadPool：没有核心线程，所有的线程都是非核心线程，线程来时即加入运行，用完释放，且最大线程数为 Integer.MAX_VALUE ，适合大量耗时短的任务
- ScheduledThreadPool：有固定数量的核心线程，且核心线程数与非核形线程总数最大 Integer.MAX_VALUE





#### 创建线程池的参数有哪些？

使用线程池我们可以使用现有的 Executors，或者就是手动创建线程池，如果是手动创建就需要知道各个参数的设置。Executors创建线程池的话，要不就是对线程的数量没有控制，如CachedThreadPool，要不就是是无界队列，如FixedThreadPool。**对线程池数量和队列大小没有限制的话，容易导致OOM异常。**所以我们要自己手动创建线程池：

- corePoolSize：核心线程数量，默认情况下每提交一个任务就会创建一个核心线程，直到核心线程的数量等于corePoolSize就不再创建。**线程池提供了两个方法可以提前创建核心线程，`prestartAllCoreThreads()`提前创建所有的核心线程，`prestartCoreThread`，提前创建一个核心线程**
<<<<<<< HEAD
- maximumPoolSize：线程池允许创建的最大线程数。只有当线程池队列满的时候才会创建
- maximumPoolSize：线程池允许创建的最大线程数。
- keepAliveTime：线程池空闲状态可以等待的时间，默认对非核心线程生效，但是设置`allowCoreThreadTimeOut`的话对核心线程也生效
- unit: 保活时间的单位，创建线程池的时候，`keepAliveTime = unit.toNanos(keepAliveTime)`
- workQueue: 任务队列，用于保持或等待执行的任务阻塞队列。BlockingQueue的实现类即可，有无界队列和有界队列

  - ArrayBlockingQueue: 基于数组结构的有界队列，此队列按FIFO原则（先入先出）对元素进行排序
  - LinkedBlockingQueue: 基于链表的阻塞队列，FIFO原则，吞吐量通常高于ArrayBlockingQueue.
  - SynchronousQueue: 不存储元素的阻塞队列。每个插入必须要等到另一个线程调用移除操作。
  - PriorityBlockingQueue: 具有优先级的无阻塞队列
- threadFactory： 用于设置创建线程的工厂。
- handler：拒绝策略，当队列线程池都满了，必须采用一种策略来处理还要提交的任务。在实际应用中，我们可以将信息记录到日志，来分析系统的负载和任务丢失情况，JDK中提供了4中策略：
- AbortPolicy: 直接抛出异常
  - CallerRunsPolicy: 只用调用者所在的线程来运行任务
- DiscardOldestPolicy： 丢弃队列中最老的一个人任务，并执行当前任务。
  - DiscardPolicy: 直接丢弃新进来的任务



#### 线程池数量如何配置？

- 任务性质：CPU密集，IO密集，和混合密集
- 任务执行时间：长，中，低
- 任务优先级：高，中，低
- 任务的依赖性：是否依赖其它资源，如数据库连接

在代码中可以通过：`Runtime.getRuntime().availableProcessors();`获取CPU数量。线程数计算公式：

```
N = CPU数量
U = 目标CPU使用率，  0 <= U <= 1
W/C = 等待(wait)时间与计算(compute)时间的比率

线程池数量 =  N * U * (1 + W/C)
复制代码
```

不过最简单的线程数指定方式，不需要公式的话：

- CPU密集型，创建线程数为`CPU核数 + 1`
- IO密集型，线程数最好为`CPU核数 * n`，耗时越久，分配线程数多一些





#### 线程池的状态有哪些？

线程池的状态主要通过ctl属性来控制，通过ctl可以计算出：

- 当前线程池状态
- 当前线程的数量

计算规则主要是利用了按位操作：

```
11100000000000000000000000000000   RUNNING
00000000000000000000000000000000   SHUTDOWN
00100000000000000000000000000000   STOP
01000000000000000000000000000000   TYDYING
01100000000000000000000000000000   TERMINATED


11100000000000000000000000000000   ctl初始值
11100000000000000000000000000000  ~CAPACITY  
private static int runStateOf(int c)     { return c & ~CAPACITY; }

11100000000000000000000000000000   ctl初始值
00011111111111111111

111111111111  CAPACITY
private static int workerCountOf(int c)  { return c & CAPACITY; }
    
private static int ctlOf(int rs, int wc) { return rs | wc; }  
复制代码
```

- RUNNING：运行状态，接受新任务，持续处理任务队列里的任务。
- SHUTDOWN：调用shutdown()方法会进入此状态，不再接受新任务，但要处理任务队列里的任务
- STOP：调用shutdownNow()方法，不再接受新任务，不再处理任务队列里的任务，中断正在进行中的任务
- TIDYING：表示线程池正在停止运作，中止所有任务，销毁所有工作线程。
- TERMINATED：表示线程池已停止运作，所有工作线程已被销毁，所有任务已被清空或执行完毕

```
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
复制代码
```

关于TIDYING和TERMINATED主要有一块代码区，可以看出来TIDYING状态紧接着就是TERMINATED。

```
						if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try {
                    		// 默认是空方法
                        terminated();
                    } finally {
                        ctl.set(ctlOf(TERMINATED, 0));
                        termination.signalAll();
                    }
                    return;
                }
复制代码
```







#### 线程池提供的扩展方法有哪些？

默认有三个扩展方法，可以用来做一些线程池运行状态统计，监控：

```
 protected void beforeExecute(Thread t, Runnable r) { }  // task.run方法之前执行
 protected void afterExecute(Runnable r, Throwable t) { }  // task执行完之后，不管有没有异常都会执行
 protected void terminated() { }  
复制代码
```

默认线程池也提供了几个相关的可监控属性：

- taskCount: 线程池需要执行的任务数量
- completedTaskCount: 已经完成的任务数量
- largestPoolSize: 线程池中曾经创建的最大的线程数量
- getPoolSize: 线程池的线程数量
- getActiveCount: 活动的线程数







#### 线程池中的Worker线程执行的过程？

Worker类实现了Runnable方法，**在成功创建Worker线程后就会调用其start方法。**

```java
w = new Worker(firstTask);
final Thread t = w.thread;   //理解为 w.thread = new Thread(w)
if (workerAdded) {
	t.start();
	workerStarted = true;
}


Worker(Runnable firstTask) {
	setState(-1); // inhibit interrupts until runWorker
	this.firstTask = firstTask;
	this.thread = getThreadFactory().newThread(this);
}
复制代码
```

Worker线程运行时执行runWorker方法，里面主要事情：

- 如果构造Worker的时候，指定了firstTask，那么首先执行firstTask，否则从队列中获取任务
- Worker线程会循环的getTask()，然后去执行任务
- 如果getTask()为空，那么worker线程就会退出
- 在任务执行前后，可以自定义扩展beforeExecute与afterExecute方法
- 如果检测到线程池为STOP状态，并且线程还没有被中断过的话，进行中断处理

简单来说就是不断的从任务队列中取任务，如果取不到，那么就退出当前的线程，取到任务就执行任务。

```java
    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock(); // allow interrupts
        // 代表着Worker是否因为用户的程序有问题导致的死亡
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                w.lock();
                // If pool is stopping, ensure thread is interrupted;
                // if not, ensure thread is not interrupted.  This
                // requires a recheck in second case to deal with
                // shutdownNow race while clearing interrupt
                if ((runStateAtLeast(ctl.get(), STOP) ||
                     (Thread.interrupted() &&
                      runStateAtLeast(ctl.get(), STOP))) &&
                    !wt.isInterrupted())
                    wt.interrupt();
                try {
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        task.run();
                    } catch (Exception x) {
												  //... 不同的异常处理
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }
```



#### 线程池如何区分核心线程与非核心线程？

实际上内部在创建线程时，并没有给线程做标记，因此无法区分核心线程与非核心线程。可以看出`addWorker()方法`。

但是为什么可以保持核心线程一直不被销毁呢？

其内部主要根据当前线程的数量来处理。(也可以理解为，只要当前的worker线程数小于配置的corePoolSize，那么这些线程都是核心线程。线程池根据当前线程池的数量来判断要不要退出线程，而不是根据是否核心线程)



#### 核心线程能否被退出？

上面一个问题我们说到了内部其实不区分核心线程与非核心线程的，只是根据数量来判断是否退出线程，但是线程是如何退出的，又是如何一直处于保活状态呢？

如果配置了allowCoreThreadTimeOut，代表核心线程在配置的keepAliveTime时间内没获取到任务，会执行退出操作。也就是尽管当前线程数量小于corePoolSize也会执行退出线程操作。

workQueue.take()方法会一直阻塞当前的队列直到有任务的出现，因此如果执行的是take方法，那么当前的线程就不会退出。想要退出当前的线程，有几个条件：

- 1 当前的worker数量大于maximumPoolSize的worker数量。

- 2 线程池当前处于STOP状态，即shutdownNow

- 3 线程池处于SHUTDOWN状态，并且当前的队列为空

- 4 worker线程等待task超时了，并且当前的worker线程配置为可以被退出。`timed=true`
- allowCoreThreadTimeOut配置为true
  - 线程数量大于核心线程数



#### 如何提前创建核心线程数？

上面提到了，有两个方法：

- `prestartAllCoreThreads()`提前创建所有的核心线程
- `prestartCoreThread`，提前创建一个核心线程，如果当前线程数量大于corePoolSize，则不创建



#### 线程池异常退出与自动退出的区别？

如果线程是由于程序异常导致的退出，那么completedAbruptly为true，如下代码会再新建一个Worker线程。

如果线程是系统自动退出，即completedAbruptly为false的话，会根据配置判断当前可以允许的最小核心线程数量

- 配置allowCoreThreadTimeOut为true的话，最小核心线程数可以为0。
- 默认情况下最小线程数为corePoolSize

```
int c = ctl.get();
        if (runStateLessThan(c, STOP)) {
            if (!completedAbruptly) {
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
                if (min == 0 && ! workQueue.isEmpty())
                    min = 1;
                if (workerCountOf(c) >= min)
                    return; // replacement not needed
            }
            addWorker(null, false);
        }
复制代码
```



#### 线程池 shutdown 与 shutdownNow 有什么区别？

看代码主要三个区别：

- shutdown会把线程池的状态改为SHUTDOWN，而shutdownNow把当前线程池状态改为STOP
- shutdown只会中断所有空闲的线程，而shutdownNow会中断所有的线程。
- shutdown返回方法为空，会将当前任务队列中的所有任务执行完毕；而shutdownNow把任务队列中的所有任务都取出来返回。

















----



- CountDownLatch原理?
- [private protected public 关键字的用法区别](https://github.com/FishInWater-1999/android_interviews/blob/master/Java/private%20protected%20public%20%E5%85%B3%E9%94%AE%E5%AD%97%E7%9A%84%E7%94%A8%E6%B3%95%E5%8C%BA%E5%88%AB.md)
- [接口，抽象类区别？抽象类要不要实现接口的方法](https://github.com/FishInWater-1999/android_interviews/blob/master/Java/%E6%8E%A5%E5%8F%A3%EF%BC%8C%E6%8A%BD%E8%B1%A1%E7%B1%BB%E5%8C%BA%E5%88%AB%EF%BC%9F%E6%8A%BD%E8%B1%A1%E7%B1%BB%E8%A6%81%E4%B8%8D%E8%A6%81%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%96%B9%E6%B3%95.md)
- 进程 线程区别c
- 子线程间通讯
- 解决死锁的办法，怎么判断发生死锁
- 用过哪些设计模式
- 观察者举例
- 图片压缩算法？ [聊聊关于Android图片压缩那点事儿](https://juejin.im/entry/59c07c936fb9a00a636a4471)  
- Bitmap JPG区别 [图像处理：JPG、PNG及BMP的区别？](https://blog.csdn.net/u013841196/article/details/80517018)  
- 断点重传？**怎么动态确定范围？** [Android 实现断点上传文件](https://technicalsearch.iteye.com/blog/2158915) RandomAccessFile 
- 断点下载？**CRC原理** [文件校验MD5、SHA1、CRC32、sha256、cksum](https://blog.csdn.net/gsls200808/article/details/48844921)
- **MVC优点缺点**
- **MVP中的MVC的C实现在哪**
- **单例模式的饿汉与懒汉的选择，使用场景**[Java单例模式的不同写法（懒汉式、饿汉式、双检锁、静态内部类、枚举）](https://blog.csdn.net/fly910905/article/details/79286680)
- B线程怎么实现等待A线程完成工作 
- 线程怎么结束工作 [Java结束线程的三种方法](https://blog.csdn.net/xu__cg/article/details/52831127)  
- **Java哪些方法支持中断**  
- **设计一个有限资源的请求**  
- **怎么设计对象池（对象的存与放回）**  
- 线程池的线程什么时候创建 
- Java中的锁 
- 可重入锁性能
- 单例模式
- volatile关键字原子性
- **MVP MVVM使用场景**
- 数组 链表区别
- 进程/线程的通讯方式
- ThreadLocal原理
- 弱/软引用区别
- **线程的状态**
- **自旋和阻塞区别**
- int型长度？与机器有关么？
- 32位/64位系统指的是什么？
- 用过哪些设计模式
- 抽象 接口区别 
- 哪几种集合，List Map区别 

​		哪几种集合，List Map区别 

-		线程同步方式 

- 重载 重写区别 

- **怎么实现多态**  

-		运行时异常有哪些？(只答了空指针，数组越界，IO，还有ClassNotFound) 

-		内存泄漏举例？怎么定义内存泄漏？ 

- GC机制？

- 进程线程区别

- 线程上下文切换切换了啥

- 死锁 死锁避免 银行家算法

- 虚拟内存解决什么问题 页面置换算法

- 链表 顺序表的区别 应用场景

- 网络各层作用 对称加密 非对称加密 路由表咋来的 mac表咋来的

- volatile的语义 怎么实现的 gc算法 强软弱虚

- 双亲委派作用 要是到最后所有类加载器都没找到这个要加载的类 会发生什么

- hashmap hashtable

- a启动b 再返回生命周期

- 线程进程协程 区别

- 上下文切换的区别

- java内存分区

- jvm和dvm有什么区别

- final关键字

- 重载重写 返回值如果不一样方法签名和列表一样会出现什么 为什么

- 抽象类和接口 抽象类能不能继承普通类

- jvm分区 栈溢出是什么 栈里面存了什么

- 子线程如果出现异常会怎么样

- 说一下sychronize的实现的机制 说一下volatile有什么用

- wait notify notify之后之前wait的代码是从哪里开始执行的

- wait不在同步块里面执行会怎么样

- Reentrantlock用过吗 和sychronized有什么区别

- 内存泄露有哪些场景

- 长连接发心跳包的作用是什么

- 设计模式 责任链讲一下

- java内存布局

- 堆和栈的区别

- c++智能指针

- volatile关键字（突然不知道怎么回答，然后记起来了跟java内存模型相关，就说了一下jmm）  

-  多进程（莫名认为是多线程，说歪了）  

- 什么场景需要用到多进程（回答中说了推送）  

- 为什么推送需要多进程，有什么优势（不知道有什么优势）  

- hashmap的数据结构（回答了维护一个entry数组，hash冲突，1.8中链表会变成红黑树，然后面试官没继续深究）

- mvp（回答不够发散，可以从mvc发散到mvp）

- 拆箱装箱具体是在哪一步实现的

1. final作用
2. static作用
3. final和static区别
4. final方法可以调用局部变量吗？那该如何调用
5. voltatile作用以及在单例中发挥的作用（防止代码重排）

1. Java的四种引用以及对应的什么时候回收的解释
2. 服务分成哪几种，获取服务返回什么（binder），进程间如何获取服务

1. 面向对象思想
2. 封装、继承和多态
3. 重载怎么用
4. 进程和线程

- hashmap 的实现，解决哈希冲突的方法
- ArrayList 和 LinkedList 的区别
- 队，栈区别,生活中的例子
- hashmap
- try cash finally中finally是否一定会被执行。
- java中的线程类
- mvc mvp
- 死锁
- int 和 float存储区别
- 内核态和用户态的沟通(中断)
- ArrayList 和 LinkedInList 区别，是否线程安全
- 如果保证上面的线程安全
- volatile
- 原子性和可见性的区别
- synchronized 如何保证原子性
- 四种引用
- GC
- final 关键字修饰方法，变量，类有什么作用
- final 修饰变量的时候，真正的含义
- 内部类为什么要用final修饰变量
- hashmap hasTable ConcurrentHashMap 的区别
- 包装类和基本数据类型的区别
- 包装类的两个相同数值的对象是否想等
- Android View 有哪些标签，都有什么作用(除了 include 其他全忘了)
- 泛型知道多少
- 泛型的继承
- 泛型的通配符
- 泛型的 T
- 两个线程去访问同一个对象的两个 synchronized 方法是否互斥(互斥)
- 在什么情况下不互斥
- 如何访问两个非静态方法不互斥
- 如何访问两个非静态方法里的代码端互斥
- volatile的原理
- 如何保证int的原子性
- 问了java里的乐观锁和悲观锁
- 数据库索引，说b和b+区别
- String可以被继承吗？ 给了个式子问创建了几个对象？
- 事务的隔离级别，默认的是哪种？可重复读会造成什么问题？
- 集合常用的是哪种？我说的List和set 有啥区别 ？（我回答反了/猪脑子吗？） set原理？list原理？ 
- ThreadLocal 实现原理 作用
- synchronize 以及锁的优化 轻量级锁 偏向锁 重量级锁
- 面向对象三大特性 
- 父类子类的继承以及实例化的对象访问问题 
- 简单地问了arraylist原理，以及是不是线程安全的。
- 垃圾回收gc 怎么判断垃圾 
- 垃圾回收基本方法.
- jvm如何发起gc等。
- 内存区域的就是基础的方法区虚拟机栈堆之类的基础问题。
- java多线程关键字
- cas原理以及问题
- 进程间的通信方式等
- java初始化时的流程；
- jvm内存分布；
- Object中有什么方法；
- sleep与wait的区别；
- java引用类型，哪一种可以用于内存泄漏
- Java深拷贝与浅拷贝
- 手写DCL单例模式
- java基本类型，以及基本类型所占的字节，以及boolean在jvm中怎么存储；
- HashMap的工作原理，hash函数的原理，resize函数的原理，与HashTable的区别，key是否可以为null；
- syncronized修饰类和修饰代码块有什么区别
- java异常捕捉，finally和return的关系
- .sleep与wait的关系，线程状态，以及先start一个线程后又start一个线程会出现什么错误。
- java中保证线程安全的方法有哪些；
- java多线程了解吗？生产者消费者怎么设计？
- int型数字转Byte?
- 设计一下一个发送快递、运送快递和接收快递的功能，用面向对象的思维。
- java封装继承多态
- 1、java锁的分类，什么情况下用什么锁
- 2、接口抽象类什么区别，什么情况下使用
- 3、单例多例是什么，应用场景
- 4、静态变量什么时候用
- java引用传递，值传递（搞不懂，java不是只有值传递？？）
- java常用的中间件
- 内存分配
- 谈谈线程的基本状态，其中的wait() sleep() yield()方法的区别。