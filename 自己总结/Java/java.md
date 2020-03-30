## Java 基础

- Java内存布局
- GC机制、



####  Java 异常处理机制





#### 对象的加载过程？（类的初始化此过程）






- 进程线程区别，进程消耗资源多原因
- 同步
- 死锁
- IPC
- cas 等



## 虚拟机



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




## hashMap



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




#### 头插法和尾插法怎么实现？★★

- 首先是 1.7 头插法，先把链表遍历一遍，看有没有存在，没有的话就把这个链表的头赋值到它的 next，然后把头的位置指向它
- 1.8 尾插法就是直接遍历到最后一个，边遍历，边判断有没有冲突的，有就替换，遍历到最后一个都没有，没有就把最后一个的 next 指向自己完事，贼简单



#### hashmap Entry<> 上节点太多，查询复杂度 O(n) 怎么半？★★

- 在 1.8 中当单个链表上节点大于 64 时，会将单链表转换为红黑树，查找的时间复杂的也就随之变为 O(logn)




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




#### 如何让 Hashmap 变成线程安全？★★

- Map m = Collections.synchronizeMap(hashMap);



#### HashMap与HashTable区别是啥？★★

1. hashmap 的父类是 abstractmap, 而 hashtable 父类是 Dictionary（字典） 类
2. hashmap 是线程不安全的，hashtable 加了 synchorized 同步锁线程安全
3. hashmap 没加锁，所以比 hashtable 效率高
4. hashmap 的 key 和 value 可以为空，但 hashtable 不行
5. hashmap 是用的自己定义的 hash算法，hashtable 是用 key 对象自己的 hashcode() 值
6. hashmap 扩容是 *2 而 hashtable 扩容是 *2+1
7. 迭代器 hashmap 用的是Iterator（其原理是fail-fast，就说，一个线程在遍历元素，另一个线程在修改结构，就会抛 ConcurrentModificationException 并发修改异常） hashtable用的是 enumerator 原理是不 fail-fast（它加锁，线程安全，所以不会抛异常）



#### 为什么不用 HashTable？★★

- Directry 类已被废弃，所以子类 Hashtable 自然也被废弃，它内部有很多没有优化的冗余代码
- 所有方法加synchronized 效率低，有更好的 ConcurrentHashMap 类替代



#### ConcurrentHashMap 如何保证线程安全



#### Map的线程安全？读多写少选哪个集合？(**CopyOnWrite，不懂原理没敢说**)



#### GC机制？



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