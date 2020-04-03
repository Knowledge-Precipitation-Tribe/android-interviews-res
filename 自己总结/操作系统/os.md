## 进程 - 线程



#### 进程和线程的区别？★★

- 导致门牌地址带资源找老板和老板娘而崩溃
- 线程之间共享进程的地址空间，而进程之间的地址空间是相互独立的
- 线程之间共享进程的 cpu、内存、IO 等资源，而进程间的资源是相互独立的
- 一个线程崩溃，整个进程都会崩溃，而一个进程崩溃，其它进程不受影响，除非是操作系统进程
- 线程切换的资源消耗小，进程切换的资源消耗大
  - 进程切换要切换页目录使用的地址空间，和内核硬件的上下文
  - 而线程只需要切换硬件的上下文，而且线程切换使用的上下文虚拟内存空间是相同的，但进程不同
  - 而当虚拟内存空间改变时，页表缓冲需要被全部刷新，从而导致一段时间内访问内存相当低效
- 线程执行需要依赖进程来执行，而进程执行则有程序入口和顺序执行序列
- 线程之调度的基本单位，而进程不是
- 但是现场和进程都能并发执行



####  线程同步机制与原理，举例说明

为什么需要线程同步？当多个线程操作同一个变量的时候，存在这个变量何时

对另一个线程可见的问题，也就是可见性。每一个线程都持有主存中变量的一

个副本，当他更新这个变量时，首先更新的是自己线程中副本的变量值，然后

会将这个值更新到主存中，但是是否立即更新以及更新到主存的时机是不确定

的，这就导致当另一个线程操作这个变量的时候，他从主存中读取的这个变量

还是旧的值，导致两个线程不同步的问题。线程同步就是为了保证多线程操作

的可见性和原子性，比如我们用 synchronized 关键字包裹一端代码，我们希望

这段代码执行完成后，对另一个线程立即可见，另一个线程再次操作的时候得到的是上一个线程更新之后的内容，还有就是保证这段代码的原子性，这段代

码可能涉及到了好几部操作，我们希望这好几步的操作一次完成不会被中间打

断，锁的同步机制就可以实现这一点。一般说的 synchronized 用来做多线程同

步功能，其实 synchronized 只是提供多线程互斥，而对象的 wait()和 notify()方

法才提供线程的同步功能。JVM 通过 Monitor 对象实现线程同步，当多个线程

同时请求 synchronized 方法或块时，monitor 会设置几个虚拟逻辑数据结构来管

理这些多线程。新请求的线程会首先被加入到线程排队队列中，线程阻塞，当

某个拥有锁的线程 unlock 之后，则排队队列里的线程竞争上岗（synchronized

是不公平竞争锁，下面还会讲到）。如果运行的线程调用对象的 wait()后就释

放锁并进入 wait 线程集合那边，当调用对象的 notify()或 notifyall()后，wait 线

程就到排队那边。这是大致的逻辑



#### 线程同步机制与原理，举例说明

- 为什么需要线程同步？当多个线程操作同一个变量的时候，存在这个变量何时

对另一个线程可见的问题，也就是可见性。每一个线程都持有主存中变量的一

个副本，当他更新这个变量时，首先更新的是自己线程中副本的变量值，然后

会将这个值更新到主存中，但是是否立即更新以及更新到主存的时机是不确定

的，这就导致当另一个线程操作这个变量的时候，他从主存中读取的这个变量

还是旧的值，导致两个线程不同步的问题。线程同步就是为了保证多线程操作

的可见性和原子性，比如我们用 synchronized 关键字包裹一端代码，我们希望

这段代码执行完成后，对另一个线程立即可见，另一个线程再次操作的时候得到的是上一个线程更新之后的内容，还有就是保证这段代码的原子性，这段代

码可能涉及到了好几部操作，我们希望这好几步的操作一次完成不会被中间打

断，锁的同步机制就可以实现这一点。一般说的 synchronized 用来做多线程同

步功能，其实 synchronized 只是提供多线程互斥，而对象的 wait()和 notify()方

法才提供线程的同步功能。JVM 通过 Monitor 对象实现线程同步，当多个线程

同时请求 synchronized 方法或块时，monitor 会设置几个虚拟逻辑数据结构来管

理这些多线程。新请求的线程会首先被加入到线程排队队列中，线程阻塞，当

某个拥有锁的线程 unlock 之后，则排队队列里的线程竞争上岗（synchronized

是不公平竞争锁，下面还会讲到）。如果运行的线程调用对象的 wait()后就释

放锁并进入 wait 线程集合那边，当调用对象的 notify()或 notifyall()后，wait 线

程就到排队那边。这是大致的逻辑





#### 死锁的产生条件，如何避免死锁

- 死锁的四个必要条件

1. 互斥条件：一个资源每次只能被一个进程使用2.请求与保持条件：进程已经保持了至少一个资源，但又提出了新的资源请求，而该资源 已被其他进程占有，此时请求进程被阻塞，但对自己已获得的资源保持不放。

3. 不可剥夺条件:进程所获得的资源在未使用完毕之前，不能被其他进程强行夺走，即只能 由获得该资源的进程自己来释放（只能是主动释放)。

4. 循环等待条件: 若干进程间形成首尾相接循环等待资源的关系

- 这四个条件是死锁的必要条件，只要系统发生死锁，这些条件必然成立，而只

要上述条件之一不满足，就不会发生死锁。

- 避免死锁的方法：系统对进程发出每一个系统能够满足的资源申请进行动态检

查,并根据检查结果决定是否分配资源,如果分配后系统可能发生死锁,则不予分

配,否则予以分配，这是一种保证系统不进入死锁状态的动态策略。

- 在资源的动态分配过程中，用某种方法去防止系统进入不安全状态，从而避免

发生死锁。 一般来说互斥条件是无法破坏的，所以在预防死锁时主要从其他三

个方面入手 ：

	(1)破坏请求和保持条件：在系统中不允许进程在已获得某种资源的情况下，申

请其他资源，即要想出一个办法，阻止进程在持有资源的同时申请其它资源。

	- 方法一：在所有进程开始运行之前，必须一次性的申请其在整个运行过程中所

需的全部资源，

	- 方法二：要求每个进程提出新的资源申请前，释放它所占有的资源
	
	(2)破坏不可抢占条件：允许对资源实行抢夺。
	
	- 方式一：如果占有某些资源的一个进程进行进一步资源请求被拒绝，则该进程

必须释放它最初占有的资源，如果有必要，可再次请求这些资源和另外的资

源。

	- 方式二：如果一个进程请求当前被另一个进程占有的资源，则操作系统可以抢

占另一个进程，要求它释放资源，只有在任意两个进程的优先级都不相同的条

件下，该方法才能预防死锁。
	(3)破坏循环等待条件

	- 对系统所有资源进行线性排序并赋予不同的序号，这样我们便可以规定进程在

申请资源时必须按照序号递增的顺序进行资源的申请，当以后要申请时需检查

要申请的资源的编号大于当前编号时，才能进行申请。

	- 利用银行家算法避免死锁：

所谓银行家算法，是指在分配资源之前先看清楚，资源分配后是否会导致系统死锁。如果会死锁，则不分配，否则就分配。按照银行家算法的思想，当进程请求资源时，系统将按如下原则分配系统资源





#### 传统 IPC 机制的通信原理（2 次内存拷贝）

1. 发送方进程通过系统调用（copy_from_user）将要发送的数据存拷贝到内核缓存区中。

2. 接收方开辟一段内存空间，内核通过系统调用（copy_to_user）将内核缓存区中的数据拷贝到接收方的内存缓存区。种传统 IPC 机制存在 2 个问题：

1. 需要进行 2 次数据拷贝，第 1 次是从发送方用户空间拷贝到内核缓存区，第2  次是从内核缓存区拷贝到接收方用户空间。

2. 接收方进程不知道事先要分配多大的空间来接收数据，可能存在空间上的浪费









## 设计模式



#### 观察者模式

- 被观察者修改后，通知所有观察者更新数据

- 被观察实现接口

  ```java
  interface Subject{
  	// 注册观察者
  	void registerObserver(Observer o);
  	// 移除观察者
  	void removeObserver(Observer o);
  	// 通知观察者更新
  	void notifyObservers();
  }
  ```

- 观察者实现接口

  ```java
  interface Observer {
  	// 更新数据
  	void update(int temperature, int pressure, int humidity);
  }
  ```

- 天气类（资源类）实现

  ```java
  class CurrentConditions implements Observer{
  	// 温度，气压，湿度
  	private float temperature;
  	private float pressure;
  	private float humidity;
  	
  	public void update(float temperature, float pressure, float humidity) {
  		this.temperature = temperature;
  		this.pressure = pressure;
  		this.humidity = humidity;
  		display();
  	}
  	// 显示
  	public void display() {
  		System.out.println("temperature\t ---- \t" + temperature);
  		System.out.println("pressure\t ---- \t"    + pressure);
  		System.out.println("humidity\t ---- \t"    + humidity);
  	}
  }
  ```

- 被观察者，含有：当前天气，观察者集合，通知所有观察者更新消息

  ```java
  class WeatherData implements Subject {
  	// 温度，气压，湿度
  	private float temperature;
  	private float pressure;
  	private float humidity;
  	private ArrayList<Observer> observers;
  	
  	public WeatherData(CurrentConditions currentConditions) {
  		observers = new ArrayList<Observer>();
  	}
  	
  	public float getTemperature() {
  		return temperature;
  	}
  
  	public float getPressure() {
  		return pressure;
  	}
  
  	public float getHumidity() {
  		return humidity;
  	}
  	// 更新时调用 dataChange 
  	public void update(float temperature, float pressure, float humidity) {
  		this.temperature = temperature;
  		this.pressure = pressure;
  		this.humidity = humidity;
  		dataChange();
  	}
  	// 调用 notifyObservers 循环遍历所有观察者，通知更新
  	public void dataChange() {
  		notifyObservers();
  	}
  	
  
  	@Override
  	public void registerObserver(Observer o) {
  		observers.add(o);
  	}
  
  	@Override
  	public void removeObserver(Observer o) {
  		if (observers.contains(o)) {
  			observers.remove(o);
  		}
  	}
  
  	@Override
  	public void notifyObservers() {
  		for (int i = 0; i < observers.size(); i++) {
  			observers.get(i).update(this.temperature, this.pressure, this.humidity);
  		}
  	}
  	
  }
  ```

  



#### [建造者模式](https://www.bilibili.com/video/BV1G4411c7N4?p=57)

- 通过层层加工，构建出产品

- 最后要建出来的 House

  ```java
  class House {
  	private String baise;
  	private String wall;
  	private String roofed;
  	public String getBaise() {
  		return baise;
  	}
  	public void setBaise(String baise) {
  		this.baise = baise;
  	}
  	public String getWall() {
  		return wall;
  	}
  	public void setWall(String wall) {
  		this.wall = wall;
  	}
  	public String getRoofed() {
  		return roofed;
  	}
  	public void setRoofed(String roofed) {
  		this.roofed = roofed;
  	}
  }
  ```

- 抽象的建造者

  ```java
  abstract class HouseBuilder {
  	protected House house = new House();
  	// 将建造者的流程写好，抽象的方法
  	abstract void buildBaise();
  	abstract void buildWall();
  	abstract void buildRoofed();
  	// 建筑房子后，将房子返回
  	public House buildHouse() {
  		return house;
  	}
  }
  ```

- 具体建造者

  ```java
  // 具体的建造者 1 
  class CommonHouse extends HouseBuilder {
  	// 产品属性在 House 里，而制作流程在 Builder 里。把流程和属性分开了
  	@Override
  	void buildBaise() {
  		System.out.println("地基 5 米");
  	}
  
  	@Override
  	void buildWall() {
  		System.out.println("砌墙 10 米");
  	}
  
  	@Override
  	void buildRoofed() {
  		System.out.println("普通 屋顶");
  	}
  	
  }
  // 具体建造者 2
  class HighBuilding extends HouseBuilder {
  
  	@Override
  	void buildBaise() {
  		System.out.println("高楼地基 100 m");
  	}
  
  	@Override
  	void buildWall() {
  		System.out.println("高楼砌墙 20m");
  	}
  
  	@Override
  	void buildRoofed() {
  		System.out.println("高楼 透明屋顶");
  	}
  	
  }
  ```

- 建房指挥者,指定制作流程

  ```java
  class HouseDirector {
  	HouseBuilder houseBuilder = null;
  	// 构造器传入 建造者
  	public HouseDirector(HouseBuilder houseBuilder) {
  		this.houseBuilder = houseBuilder;
  	}
  	// 通过 setter 传入 houseBuilder
  	void setHouseBuilder(HouseBuilder houseBuilder) {
  		this.houseBuilder = houseBuilder;
  	}
  	// 交给指挥者处理整个建房的流程
  	public House constructHouse() {
  		houseBuilder.buildBaise();
  		houseBuilder.buildWall();
  		houseBuilder.buildRoofed();
  		return houseBuilder.buildHouse();
  	}
  }
  ```

- 用户调用，建房

  ![](https://user-gold-cdn.xitu.io/2020/4/2/1713b75edfaa22f0?w=597&h=447&f=png&s=231233)

- [结合链式调用](https://blog.csdn.net/junehappylove/article/details/85236946)



#### [享元模式](https://blog.csdn.net/weixin_38810239/article/details/79441247)

- 享元即共享对象，场景：数据库连接池，String 常量池，缓冲池（说白了就是多个用户共享一套数据）

- 定义外部

  ```java
  class User {
  	private String name;
  
  	public User(String name) {
  		this.name = name;
  	}
  	
  	public String getName() {
  		return name;
  	}
  
  	public void setName(String name) {
  		this.name = name;
  	}
  }
  ```

- 定义一个抽象类：WebSite

  ```java
  abstract class WebSite {
  	// 使用该网站
  	abstract void use(User user);
  }
  ```

- 定义 WebSite 的实体类

  ```java
  class ConcreteWebSite extends WebSite{
  	// 网站类型,共享部分
  	private String type;
  	
  	public ConcreteWebSite(String type) {
  		this.type = type;
  	}
  	
  	@Override
  	void use(User user) {
  		System.out.println("网站：《'" + type + "'》 的使用者是：-" + user.getName());
  	}
  	
  }
  ```

- 网站工厂类

  ```java
  class WebSiteFactory {
  	// 集合 - 池
  	HashMap< String, ConcreteWebSite> pool = new HashMap<>();
  	// 根据类型返回网站，如果没有就新建一个，放入池中
  	public WebSite getWebSiteCategory(String type) {
  		if (!pool.containsKey(type)) {
  			pool.put(type, new ConcreteWebSite(type));
  		}
  		return pool.get(type);
  	}
  	// 获得当前网站数目
  	public int getWebSiteCount() {
  		return pool.size();
  	}
  }
  ```

- 使用：发现多个人使用，但网站数量不增加

  ![](https://user-gold-cdn.xitu.io/2020/4/2/1713b451aaa970ce?w=533&h=398&f=png&s=204804)



#### 单例

- 保证整个软件系统中，某个类只存在一个实例对象

- 饿汉

  - 静态常量式

    - 私有构造器，对外暴露 getInstance

    ```java
    class Singleton {
    	private Singleton() {}
    	
    	private final static Singleton instance = new Singleton();
    	
    	public static Singleton getInstance() {
    		return instance;
    	}
    }
    ```

    - 优点：类加载时就实例化，避免的线程同步问题
    - 缺点：没有达到懒加载效果，如果不用这个类，内存就浪费了

  - 静态代码块式

    - 把初始化放在静态代码块中

    ```java 
    class Singleton {
    	private Singleton() {}
    	
    	static {
    		instance = new Singleton();
    	}
    	
    	private static Singleton instance;
    	
    	public static Singleton getInstance() {
    		return instance;
    	}
    }
    ```

    - 优缺点和上面一样

- 懒汉

  - 线程不安全

    ```java
    class Singleton {
    	private Singleton() {}
    	
    	private static Singleton instance;
    	
    	public static Singleton getInstance() {
    		if (instance == null) {
    			instance = new Singleton();
    		}
    		return instance;
    	}
    }
    ```

    - 优点：实现了懒加载
    - 缺点：多线程并发时，很多走到 if 语句那里时间片轮转走，再回来时全部 new Instance 会长生很多实例，变成非单例

  - 线程安全同步写法

    ```java
    class Singleton {
    	private Singleton() {}
    	
    	private static Singleton instance;
    	
    	public static synchronized Singleton getInstance() {
    		if (instance == null) {
    			instance = new Singleton();
    		}
    		return instance;
    	}
    }
    ```

    - 优点：加入同步锁线程安全
    - 缺点：本来问题只出现在第一次调用时，现在每次都加锁，导致效率太低

  - 线程安全，同步代码块

    ```java
    class Singleton {
    	private Singleton() {}
    	
    	private static Singleton instance;
    	
    	public static Singleton getInstance() {
    		if (instance == null) {
    			synchronized(Singleton.class) {
    				instance = new Singleton();
    			}
    		}
    		return instance;
    	}
    }
    ```

    - 缺点：跟第一种情况一样，判断是时间片轮转走，回来时会创建多个对象

  - 双重检查

    ```java
    class Singleton {
    	private Singleton() {}
    	
    	private static volatile Singleton instance;
    	
    	public static Singleton getInstance() {
    		if (instance == null) {
    			synchronized(Singleton.class) {
    				if (instance == null) {
    					instance = new Singleton();
    				}
    			}
    		}
    		return instance;
    	}
    }
    ```

    - 优点：解决线程安全问题；解决效率问题