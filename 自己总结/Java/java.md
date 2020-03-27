- Java内存布局
  GC机制
  集合框架
  进程线程区别，进程消耗资源多原因
  同步
  死锁
  IPC
- ART Dalvik区别？
- GC机制？
- CountDownLatch原理?
- [private protected public 关键字的用法区别](https://github.com/FishInWater-1999/android_interviews/blob/master/Java/private%20protected%20public%20%E5%85%B3%E9%94%AE%E5%AD%97%E7%9A%84%E7%94%A8%E6%B3%95%E5%8C%BA%E5%88%AB.md)
- [接口，抽象类区别？抽象类要不要实现接口的方法](https://github.com/FishInWater-1999/android_interviews/blob/master/Java/%E6%8E%A5%E5%8F%A3%EF%BC%8C%E6%8A%BD%E8%B1%A1%E7%B1%BB%E5%8C%BA%E5%88%AB%EF%BC%9F%E6%8A%BD%E8%B1%A1%E7%B1%BB%E8%A6%81%E4%B8%8D%E8%A6%81%E5%AE%9E%E7%8E%B0%E6%8E%A5%E5%8F%A3%E7%9A%84%E6%96%B9%E6%B3%95.md)
- Map的线程安全？读多写少选哪个集合？(**CopyOnWrite，不懂原理没敢说**)
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