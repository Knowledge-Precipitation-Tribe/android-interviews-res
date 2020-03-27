# hashMap



## 为什么建议在实例化 Hashmap 的时候传入大小？★

- 因为他初始化要进行 1<<4 位移运算获得 16 的大小，这个过程要耗时



## Node 和 Entry ？★

- 1.7 中 table[] 类型为 Entry implement Map.Entry<,>
- 1.8 中 table[] 类型为 Node implement Map.Entry<,>



## hashmap 的 put 和 get 方法怎么实现？★

- put 就是 resize 之前，hash&（length - 1）求 Entry<,>[] 的 index，然后就是能放，替换（跟另一个方法 replace 效果一样，但 replace 不能添加元素）和不能放，不能放就扩容的结果
- get 就更简单了，直接 hash&（length - 1）.找到 index 位置，再用equals 去找链表/树上的位置



## 为什么 String 和 Integer 适合被作为键值？★

- 一方面 String 是放运行时常量池的，他的优点也就是他的缺点，就是不可变，就是说它没有 .append 方法，一次创建一个对象不能修改，如果拼接的话，比如 String s = s + "qwe" ，就会 new 出一个新的 String，然后但前的引用 s 指向新 String(s + "qwe") 的地址
- 其他很多对象没有重写 hashcode 和 equals 方法，这就导致本应该相同的对象 .equals 没重写，结果地址比较，直接拉跨，hashcode 没写，结果频繁冲突



## HashMap 是怎么 hash 的？★

1. 第一次是将 key 的 hashcode() 的方法得出的 hash 值，由于这个是 int ，int 4字节，也就是 32 位，大小范围在 -21亿多到 +21亿多之间，所以先得跟长度 length 取余，得到这个余数又太短容易冲突，所以把他右移 16 位，真好变成 32 位的一半，也就分成了高低两个区，这时把高半区和低半区异或，就混合了原始的高低位，增加了随机性。
2. 第二次就是将第一次得到的 hash 值和 （length - 1）做 & 运算得到 Entry[] 数组最后的存放位置



## 除了拉链法还有什么解决冲突的办法？★

- 开放地址法 和 再哈希法
- 开发地址法包含三方法：线行探查法、平方探查法、双重散列法
  - 线性探查法，就越是一个个往后找，找到为空的 停下、插入
  - 平方探查法也简单，字如其名就是把当前位置加 1^2 放，要还是冲突就找 2^2 ，然后3^2...以此类推
  - 双重散列法和线性探测法类似，不同的是，线性探测每次都往后走一格，而双重散列法拥有一个附加的散列函数，每次散列结果作为向后走的格数
-  再哈希法更简单，就是设计一堆 hash 函数，这个冲突用下一个，还冲突在下一个，以此类推



## 为什么要使用 2 的幂作为 hashMap 的大小？★

1. 因为第一次 hash 后，第二次 hash 本来是要 hash%length 这么算，这时候如果 length 时 2 的 幂，那 hash%length <=等价于=> hash&(length-1)，由于 hash&(length-1) 的效率比取余高，所以 设置为 2 的幂，这样就可以用 & 运算代替 % 运算，提高效率
2. 之所以hash&(length-1) 效率高是因为 length 的值为 2 的幂时，length - 1 二进制全为 1 ，那么跟所传来的 hash 值做与运算就直接是后几位，这样做效率又高又均匀




## 头插法和尾插法怎么实现？★

- 首先是 1.7 头插法，先把链表遍历一遍，看有没有存在，没有的话就把这个链表的头赋值到它的 next，然后把头的位置指向它
- 1.8 尾插法就是直接遍历到最后一个，边遍历，边判断有没有冲突的，有就替换，遍历到最后一个都没有，没有就把最后一个的 next 指向自己完事，贼简单



## hashmap Entry<> 上节点太多，查询复杂度 O(n) 怎么半？★

- 在 1.8 中当单个链表上节点大于 64 时，会将单链表转换为红黑树，查找的时间复杂的也就随之变为 O(logn)




## hashmap 什么时候扩容？★

- 随着 hash 碰撞次数增加，单个 Entry 上的链表或者红黑树的节点越来越多，当数量 > capacity(table[]数组长度) * loadfactor(默认阈值0.75) 时，进行扩容




## hashmap 是这么扩容的？★

- 首先调用 resize 方法把当前 table[] 大小 *2 ，得到新数组，然后调用 transfer 方法
- 在 transfer 方法里面，把节点键值 key ，进行 rehash 后转移过去，再把值传到表中




## hashmap 是怎么 resize 的？★

- resize 就是扩容，resize 时扩容入口方法，在里面 new 新的 Node 数组



## 为什么 hashMap 不是线程安全的？★

- hashmap 线程不安全主要发生在 put 和 resize 方法
- 两个线程同时往 hashmap 里 put 东西，A 线程本来要往最后一个节点 node 后添加新元素，这是由于时间片轮到 B 线程，B 线程也往 node 后添加元素，并且添加成功，这是时间片轮转会 A 线程，A 线程再把自己的东西添加到 node 后，这是，就造成了 B 线程所添加数据的丢失
- 还有一种是发生在 resize 方法里
    - 1.7 由于时头插法，原来的尾巴移过去可能变成头，原来的头变成尾，但之前上个 Entry 里，又有头尾关系，结构就变成了环状
    - 由于 1.8 变为尾插，之前啥顺序，之后还是啥循序，顶多所在 Entry 的位置变了，所以不影响



## 为什么会形成头尾相接的环状结构？★

- 首先我们 1.7 用的是头插法，所以新 Map 是逆序的，原来 A -> B，现在会变成 B -> A，这就可能有问题
- 假设我们有 A->B->C  ,线程1 首先执行 Node next = e.next， 保存 next（B） 和 e（A） 的状态，准备移动 e 和 next.
- 这时线程2来了，所以线程1的状态暂时保持在本地内存里，然后线程2完成 resize 全部操作，得到两个新链表：B->A->null  ; C -> null.
- 这时轮转到线程1执行，线程1，先把 当前的 e（A） 移过去，变成 A->null， 再把 next（B）执行 Node next = e.next，但这时由于线程2 ，B现在 -> A, 所以next 为A，e为 B
- 然后 e 移过去变成 B->A->null，再去移 next（A）；首先还是 Node next = e.next， 保存 next（nul） 和 e（A） 再移过去加变成了 A->B->A->B（由于头插，所以A由 A->null 变为 A->B）
- 这时由于 next == null 所以停止，此时 A->B->A->B 循环




## 如何让 Hashmap 变成线程安全？★

- Map m = Collections.synchronizeMap(hashMap);



## HashMap与HashTable区别是啥？★

1. hashmap 的父类是 abstractmap, 而 hashtable 父类是 Dictionary（字典） 类
2. hashmap 是线程不安全的，hashtable 加了 synchorized 同步锁线程安全
3. hashmap 没加锁，所以比 hashtable 效率高
4. hashmap 的 key 和 value 可以为空，但 hashtable 不行
5. hashmap 是用的自己定义的 hash算法，hashtable 是用 key 对象自己的 hashcode() 值
6. hashmap 扩容是 *2 而 hashtable 扩容是 *2+1
7. 迭代器 hashmap 用的是Iterator（其原理是fail-fast，就说，一个线程在遍历元素，另一个线程在修改结构，就会抛 ConcurrentModificationException 并发修改异常） hashtable用的是 enumerator 原理是不 fail-fast（它加锁，线程安全，所以不会抛异常）



## 为什么不用 HashTable？★

- Directry 类已被废弃，所以子类 Hashtable 自然也被废弃，它内部有很多没有优化的冗余代码
- 所有方法加synchronized 效率低，有更好的 ConcurrentHashMap 类替代



# ConcurrentHashMap 

