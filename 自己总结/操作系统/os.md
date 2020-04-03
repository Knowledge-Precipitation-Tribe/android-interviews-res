## 进程 - 线程



#### 进程和线程的区别？★★★

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

> 门牌地址



####  线程同步机制与原理，举例说明 ★

- 之所有要有线程同步机制，主要是因为多线程访问资源类，涉及到线程间对资源修改的可见性问题。根据我们的JMM 可以知道，每一个线程在被创建的时候，都会有一个本地的工作内存，也就是所谓的栈空间，然后线程再将其所需要操作的资源对象，从主内存中拷贝到这个本地内存里来。每个线程更新完资源之后，都是将更新后的资源，先保存到本地内存中去，再将本地内存中的新资源，刷新到主内存中，由于资源对象从工作内存刷新到主内存的时间是不一定的，如果在刷新之前，有新的线程创建，那么他就会得到旧的资源变量，这导致了线程间不同步的问题。
- 而我们的线程同步机制，正是为了保证多线程操作时的可见性鱼原子性。就比如我们使用 synchronized 包裹住一段代码，使得当前线程操作完之后，其他线程才能进行访问，这样一来，后到达的线程所读取到的。就是前一线程已经更新之后的值，从而达到了可见性的目的。同时为了保证原子性，我们多线程的操作需要分若干步来进行，我们希望这几部的操作能够一次性完成，而不被打断，所以使用同步锁的机制也是为了保证多线程操作的原子性。
- 我们一般认为 synchronized 实现了多线程同步的效果，而实际上 synchronized 只实现了线程间的互斥，而结合对象的 wait() 和 notify() 方法，才真正实现了多线程同步的效果。此外 JVM 还通过 Monitor （即对象锁：Lock）对象来实现线程间的同步：当多个线程同时访问 synchronized 包裹的方法块时，我们的 monitor 会设置几个虚拟的数据结构，来管理这些线程。新线程到来时，如果资源对象已被其他线程所占用，它会先被加入到线程排队队列中阻塞并等待，直到拥有锁的线程 unlock 之后，排队队列里的线程再去竞争刚释放的锁（ 注意 synchronized是不公平竞争锁 ）。如果当前占用锁的运行线程调用了自己的 wait() ，他就会就释放占用的锁并进入 wait 线程集合中，当其他线程调用了 notify() 或 notifyall() 方法后，wait 线程池中的线程就重新回到排队队列中去，等待下一次锁的竞争。

> 由 JMM - 可见/原子 - Monitor 



#### 死锁的产生条件，如何避免死锁★

- 死锁的四个必要条件

  1.  互斥条件：一个资源每次只能被一个进程使用
  2.  请求与保持条件：进程已经保持了至少一个资源，但又提出了新的资源请求，而该资源 已被其他进程占有，此时请求进程被阻塞，但对自己已获得的资源保持不放。
  3.  不可剥夺条件:进程所获得的资源在未使用完毕之前，不能被其他进程强行夺走，即只能 由获得该资源的进程自己来释放（只能是主动释放)。
  4.  循环等待条件: 若干进程间形成首尾相接循环等待资源的关系

  > 两人户次，群众插话

- 这四个条件是死锁的必要条件，只要系统发生死锁，这些条件必然成立，而只要上述条件之一不满足，就不会发生死锁。

- 避免死锁的方法：

  采用银行家算法，系统对进程发出每一个系统能够满足的资源申请进行动态检查, 并根据检查结果决定是否分配资源, 如果分配后系统可能发生死锁, 则不予分配, 否则予以分配，这是一种保证系统不进入死锁状态的动态策略。

- 在资源的动态分配过程中，用某种方法去防止系统进入不安全状态，从而避免发生死锁。 一般来说互斥条件是无法破坏的，所以在预防死锁时主要从其他三个方面入手 ：

  - (1) 破坏请求和保持条件：在系统中不允许进程在已获得某种资源的情况下，申请其他资源，即要想出一个办法，阻止进程在持有资源的同时申请其它资源。
    - 方法一：在所有进程运行前，必须一次性的申求完整个运行过程中所需的全部资源
    - 方法二：每个进程在提出新的资源申请前，释放它所占有的资源

  - (2) 破坏不可抢占条件：允许对资源实行抢夺。

    	- 方式一：如果占有某些资源的一个进程，进行进一步资源请求并被被拒绝时，则该进程必须释放它当前占有的资源，如果有必要则可再去请求这些资源和新的的资源。

    - 方式二：如果一个进程请求一个已经备其他进程占有的资源，则操作系统可以迫使当前占用资源的进程，要求其释放该资源。但只有在任意两个进程的优先级都不同的情况下，该方法才能预防死锁。

  - (3) 破坏循环等待条件
    - 对系统所有资源都进行线性排序，并赋予不同的序号。这样我们便可以规定进程在申请资源时，必须按照序号递增的顺序，进行资源申请。当要申请新的资源时，需要检查新申请的资源的编号，当新资源的编号大于当前资源的编号时，才能进行申请





#### 传统 IPC 机制的通信原理（2 次内存拷贝）★

1. 发送方进程通过系统调用（copy_from_user），将要发送的数据存拷贝到**内核缓存区**中。

2. 接收方开辟一段新的内存空间，内核通过系统调用（copy_to_user）将内核缓存区中的数据，拷贝到接收方的**内存**缓存区。

- 种传统 IPC 机制存在 2 个问题：

	1. 需要进行 2 次数据拷贝，第 1 次是从发送方用户空间拷贝到内核缓存区，第2  次是从内核缓存区拷贝到接收方用户空间。

	2. 接收方进程不知道事先要分配多大的空间来接收数据，可能存在空间上的浪费









## 设计模式



#### 观察者模式 ★★

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

  



#### [建造者模式](https://www.bilibili.com/video/BV1G4411c7N4?p=57) ★★

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



#### [享元模式](https://blog.csdn.net/weixin_38810239/article/details/79441247) ★★

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



#### 单例 ★★

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