## 一、Java 基础



#### 面向对象的三个基本特征
- 封装、继承、多态。

- 封装（把事物抽象成一个类，其次才是封装，将事物拥有的属性和动作隐藏起来，只保留特定的方法与外界联系）好处：

  > 1、良好的封装能够减少耦合。
  >
  > 2、类内部的结构可以自由修改。
  >
  > 3、可以对成员进行更精确的控制。
  >
  > 4、隐藏信息，实现细节。

- 继承的缺点

  1、父类变，子类就必须变。

​	2、继承破坏了封装，对于父类而言，它的实现细节对与子类来说都是透明的。

​	3、继承是一种强耦合关系。

- 多态
  - 多态可以说是“一个接口，多种实现”或者说是父类的引用变量可以指向子类的实例，被引用对象的类型决定调用谁的方法，但这个方法必须在父类中定义
  - 多态可以分为两种类型：编译时多态（方法的重载）和运行时多态（继承时方法的重写），编译时多态很好理解，后述内容针对运行时多态
  - 运行时多态依赖于继承、重写和向上转型



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




#### volatile的特性是啥？

- 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。（实现可见性）
- 禁止进行指令重排序。（实现有序性）
- volatile 只能保证对单次读/写的原子性。i++ 这种操作不能保证原子性。



#### Java IO 结构与区别

- 按照流的方向：输入流（inputStream）和输出流（outputStream）。

- 按照实现功能：节点流（可以从或向一个特定的地方（节点）读写数据。如 FileReader）和处理流（是对一个已存在的流的连接和封装，通过所封装的流的功能调用实现数据读写。如 BufferedReader。处理流的构造方法总是要带一个其他的流对象做参数。一个流对象经过其他流的多次包装，称为流的链接。）

- 按照处理数据的单位：字节流和字符流。字节流继承于 InputStream 和 OutputStream，字符流继承于 InputStreamReader 和 OutputStreamWriter。

![](https://img-blog.csdnimg.cn/20190503233300304.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxNDUyMTk=,size_16,color_FFFFFF,t_70)




#### int、char、long 多少字节数

int\float 占用 4 个字节，short\char 占用 2 个字节，long 占用 8 个字节，byte/boolean

占用 1 个字节基本数据类型存放在栈里，包装类栈里存放的是对象的引用，即值的地址，而值存放在堆

里。



#### 多态

- 同一个消息可以根据发送对象的不同而采用多种不同的行为方式，在执行期间判断所引用

的对象的实际类型，根据其实际的类型调用其相应的方法。

作用：消除类型之间的耦合关系。实现多态的必要条件：继承、重写（因为必须调用父类

中存在的方法）、父类引用指向子类对象



#### String、StringBuffer、StringBuilder 区别

- 都是字符串类，String 类中使用字符数组保存字符串，因有 final 修饰符，String 对象是不可变的，每次对 String 操作都会生成新的 String 对象，这样效率低，且浪费内存空间。但线程安全。

- StringBuilder 和 StringBuffer 也是使用字符数组保存字符，但这两种对象都是可变的，即对字符串进行 append 操作，不会产生新的对象。它们的区别是：StringBuffer 对方法加了同步锁，是线程安全的，StringBuilder 非线程安全。



## 二、类 / 对象



### 2.1 类加载器




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



#### 双亲委托模式 / Java 类加载器原理

- 类加载器查找 class 所采用的是双亲委托模式，所谓双亲委托模式就是判断该类是否已经加载，如果没有则不是自身去查找而是委托给父加载器进行查找，这样依次进行递归，直到委托到最顶层的 Bootstrap ClassLoader ,如果 BootstrapClassLoader 找到了该 Class,就会直接返回，如果没找到，则继续依次向下查找，如果还没找到则最后交给自身去查找





####  双亲委托模式的好处

1. 避免重复加载，如果已经加载过一次 Class，则不需要再次加载，而是直接读取已经加载的 Class

2. 更加安全，确保，java 核心 api 中定义类型不会被随意替换，比如，采用双亲委托模式可以使得系统在 Java 虚拟机启动时旧加载了 String 类，也就无法用自定义的 String 类来替换系统的 String 类，这样便可以防止核心 API 库被随意篡改





#### 种类 - 四大类加载器

- 引导类加载器（bootstrap class loader）：它用来加载 Java 的核心库，是用原生代码来实现的，并不继承自 `java.lang.ClassLoader`。
- 扩展类加载器（extensions class loader）：它用来加载 Java 的扩展库。Java 虚拟机的实现会提供一个扩展库目录。该类加载器在此目录里面查找并加载 Java 类。
- 系统类加载器（system class loader）：它根据 Java 应用的类路径（CLASSPATH）来加载 Java 类。一般来说，Java 应用的类都是由它来完成加载的。可以通过 `ClassLoader.getSystemClassLoader()`来获取它。

- 除了系统提供的类加载器以外，开发人员可以通过继承  `java.lang.ClassLoader` 类的方式实现自己的类加载器，以满足一些特殊的需求。



### 2.2 ART 加载器



#### 四大类加载器

- PathClassLoader: 主要用于系统和app的类加载器,其中optimizedDirectory为null, 采用默认目录/data/dalvik-cache/
- DexClassLoader: 可以从包含classes.dex的jar或者apk中，加载类的类加载器, 可用于执行动态加载,但必须是app私有可写目录来缓存odex文件. 能够加载系统没有安装的apk或者jar文件， 因此很多插件化方案都是采用DexClassLoader;
- BaseDexClassLoader: 比较基础的类加载器, PathClassLoader和DexClassLoader都只是在构造函数上对其简单封装而已.
- BootClassLoader: 作为父类的类构造器。



####  [PathClassLoader](http://gityuan.com/2017/03/19/android-classloader/#%E4%B8%89-pathclassloader%E5%8A%A0%E8%BD%BD%E7%B1%BB%E7%9A%84%E8%BF%87%E7%A8%8B)

  - PathClassLoader 比较简单, 继承于 BaseDexClassLoader . 封装了一下构造函数, 默认 optimizedDirectory = null

  - ```java
    public class PathClassLoader extends BaseDexClassLoader {
    
        public PathClassLoader(String dexPath, ClassLoader parent) {
            super(dexPath, null, null, parent);
        }
    
        public PathClassLoader(String dexPath, String libraryPath,
                ClassLoader parent) {
            super(dexPath, null, libraryPath, parent);
        }
    }
    ```




#### DexClassLoader

  - DexClassLoader也同样,只是简单地封装了BaseDexClassLoader对象,并没有覆写父类的任何方法.

  - ```java
    public class DexClassLoader extends BaseDexClassLoader {
    
        public DexClassLoader(String dexPath, String optimizedDirectory,
                String libraryPath, ClassLoader parent) {
            super(dexPath, new File(optimizedDirectory), libraryPath, parent);
        }
    }
    ```



#### BaseDexClassLoader

  - BaseDexClassLoader构造函数, 有一个非常重要的过程, 那就是初始化DexPathList对象.

    另外该构造函数的参数说明:

    - dexPath: 包含目标类或资源的apk/jar列表;当有多个路径则采用:分割;
    - optimizedDirectory: 优化后dex文件存在的目录, 可以为null;
    - libraryPath: native库所在路径列表;当有多个路径则采用:分割;
    - ClassLoader:父类的类加载器.

  - ```java
    public class BaseDexClassLoader extends ClassLoader {
        private final DexPathList pathList;  //记录dex文件路径信息
    
        public BaseDexClassLoader(String dexPath, File optimizedDirectory, String libraryPath, ClassLoader parent) {
            super(parent);
            this.pathList = new DexPathList(this, dexPath, libraryPath, optimizedDirectory);
        }
    }
    ```



#### BootClassLoader

- ```java
  class BootClassLoader extends ClassLoader {
      private static BootClassLoader instance;
  
      public static synchronized BootClassLoader getInstance() {
          if (instance == null) {
              instance = new BootClassLoader();
          }
  
          return instance;
      }
  
      public BootClassLoader() {
          super(null, true);
      } }
  ```



#### ClassLoader

- ```java
  public abstract class ClassLoader {
      private ClassLoader parent;  //记录父类加载器
  
      protected ClassLoader() {
          this(getSystemClassLoader(), false); //见下文
      }
  
      protected ClassLoader(ClassLoader parentLoader) {
          this(parentLoader, false);
      }
  
      ClassLoader(ClassLoader parentLoader, boolean nullAllowed) {
          if (parentLoader == null && !nullAllowed) {
              //父类的类加载器为空,则抛出异常
              throw new NullPointerException("parentLoader == null && !nullAllowed");
          }
          parent = parentLoader;
      }
  }
  ```

- 再来看看SystemClassLoader，这里的getSystemClassLoader()返回的是PathClassLoader类。

- ```java
  public abstract class ClassLoader {
  
      static private class SystemClassLoader {
          public static ClassLoader loader = ClassLoader.createSystemClassLoader();
      }
  
      public static ClassLoader getSystemClassLoader() {
          return SystemClassLoader.loader;
      }
  
      private static ClassLoader createSystemClassLoader() {
          //此处classPath默认值为"."
          String classPath = System.getProperty("java.class.path", ".");
          // BootClassLoader见小节2.5
          return new PathClassLoader(classPath, BootClassLoader.getInstance());
      }
  }
  ```

- ![](http://gityuan.com/images/classloader/classloader.jpg)



#### ClassNotFound 原因

- ABI异常：常见在系统APP，为了减小system分区大小会将apk源文件中的classes.dex文件移除，对于既然可运行在64位又可运行在32位模式的应用，当被强制设置32位时，openDexFileNative在查找不到oat文件时会运行在解释模式，而classes.dex文件不再则出现ClassNotFound异常。
- MultiDex处理不当，由于每个Dex文件中方法个数不能超过65536，引入MultiDex机制。dex2oat会自动查找Apk文件中的classes.dex，classes2.dex，…classesN.dex等文件，编译到/data/dalvik-cache下生成oat文件。这里需要文件名跟classesN.dex格式，并且一定要与classes.dex一起放置在第一级目录，有些APP不按照要求来，导致ClassNotFound异常。





### 2.3 引用和回收



#### 如何判断对象是否x死亡（两种方法）
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



#### 如何判断一个常量是废弃常量

- **运行时常量池主要回收的是废弃的常量。**那么，我们如何判断一个常量是废弃常量呢？

- 假如在常量池中存在字符串 “abc”，如果当前没有任何 String 对象引用该字符串常量的话，就说明常量 “abc” 就是废弃常量，如果这时发生内存回收的话而且有必要的话，“ abc ” 就会被系统清理出常量池。

- JDK1.7 及之后版本的 JVM 已经将运行时常量池从方法区中移了出来，在 Java 堆（Heap）中开辟了一块区域存放运行时常量池。



#### 如何判断一个类是无用的类

- 方法区主要是放类的 Class 文件的
- 如果这个类所有的实例都已经被回收，那么 Java 堆中就不会存在该类的任何实例。
- 然后加载该类的 ClassLoader 也已经被回收。
- 该类的 java.lang.Class 对象没有在任何地方被引用，也无法在任何地方通过反射访问该类的方法。
- 这几种情况都满足的话就可以回收，但不是一定会回收




#### 强引用、软引用、弱引用、虚引用

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





## 三、虚拟机



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



#### Java 内存分区（结构）

- 程序计数器：每个线程执行程序指令的行号

- 虚拟机栈：存放每个方法的栈帧，帧的入栈跟出栈就是方法执行的过程

- 本地方法栈：Native方法的栈

- Java堆：保存Java对象的地方，细分为 Eden区， From Survivor空间， To  Survivor空间（线程共享）

- 方法区：线程共享，存放已经被虚拟机加载进来的类信息，常量、静态变量，JIT编译后的数据代码。java的class文件首先进入的到方法区里面去。运行时常量池是方法区的一部分。



#### 堆和栈区别

- 各司其职

  最主要的区别就是栈内存用来存储局部变量和方法调用。
  而堆内存用来存储Java中的对象。无论是成员变量，局部变量，还是类变量，它们指向的对象都存储在堆内存中。

- 独有还是共享

  栈内存归属于单个线程，每个线程都会有一个栈内存，其存储的变量只能在其所属线程中可见，即栈内存可以理解成线程的私有内存。
  而堆内存中的对象对所有线程可见。堆内存中的对象可以被所有线程访问。

- 异常错误

  如果栈内存没有可用的空间存储方法调用和局部变量，JVM会抛出java.lang.StackOverFlowError。
  而如果是堆内存没有可用的空间存储生成的对象，JVM会抛出java.lang.OutOfMemoryError。

- 空间大小

  栈的内存要远远小于堆内存，如果你使用递归的话，那么你的栈很快就会充满。如果递归没有及时跳出，很可能发生StackOverFlowError问题。
  你可以通过-Xss选项设置栈内存的大小。-Xms选项可以设置堆的开始时的大小，-Xmx选项可以设置堆的最大值。



#### JMM -  Java 内存模型

- Java 内存模型(即 Java Memory Model，简称 JMM)本身是一种抽象的概念，并不真实存在，它描述的是一组规则或规范，通过这组规范定义了程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式。
- 由于 JVM 运行程序的实体是线程，而每个线程创建时 JVM 都会为其创建一个工作内存(有些地方称为栈空间)，用于存储线程私有的数据，而 Java 内存模型中规定所有变量都存储在主内存，主内存是共享内存区域，所有线程都可以访问，但线程对变量的操作(读取赋值等)必须在工作内存中进行，首先要将变量从主内存拷贝的自己的工作内存空间，然后对变量进行操作，操作完成后再将变量写回主内存，不能直接操作主内存中的变量，工作内存中存储着主内存中的变量副本拷贝，前面说过，工作内存是每个线程的私有数据区域，因此不同的线程间无法访问对方的工作内存，线程间的通信(传值)必须通过主内存来完成





#### 原子性 可见性 有序性

- 原子性：对基本数据类型的读取和赋值操作是原子性操作，这些操作不可被中断，是一步到位的，例如 x=3 是原子性操作，而 y = x 就不是，它包含两步：第一读取 x，第二将 x 写入工作内存；x++也不是原子性操作，它包含三部，第一，读取 x，第二，对 x 加 1，第三，写入内存。原子性操作的类如：

  AtomicInteger AtomicBoolean AtomicLong AtomicReference

- 可见性：指线程之间的可见性，既一个线程修改的状态对另一个线程是可见的。volatile 修饰可以保证可见性，它会保证修改的值会立即被更新到主存，所以对其他线程是可见的，普通的共享变量不能保证可见性，因为被修改后不会立即写入主存，何时被写入主存是不确定的，所以其他线程去读取的时候可能读到的还是旧值

- 有序性：Java 中的指令重排序（包括编译器重排序和运行期重排序）可以起到优化代码的作用，但是在多线程中会影响到并发执行的正确性，使用 volatile 可以保证有序性，禁止指令重排

- volatile 可以保证可见性 有序性，但是无法保证原子性，在某些情况下可以提供优于锁的性能和伸缩性，替代 sychronized 关键字简化代码，但是要严格遵循使用条件。



#### GC 算法，各自的特点？

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



#### 垃圾回收器有那些？

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





## 四、锁



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





## 五、[线程池](https://juejin.im/post/5e435ac3f265da57537ea7ba#heading-1)




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



#### 数量如何配置？

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





#### 状态有哪些？

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







#### 扩展方法有哪些？

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







#### Worker线程执行的过程？

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



#### 区分核心线程与非核心线程？

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



#### 提前创建核心线程数？

上面提到了，有两个方法：

- `prestartAllCoreThreads()`提前创建所有的核心线程
- `prestartCoreThread`，提前创建一个核心线程，如果当前线程数量大于corePoolSize，则不创建



#### 异常退出与自动退出的区别？

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



#### shutdown 与 shutdownNow 有什么区别？

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