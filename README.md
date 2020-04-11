# 個人開源項目



## EyeFind

#### Thread 内存泄漏/空指针

- Handler 内存泄漏 - 静态内部类 - 无法访问 activity 方法 - 软引用（讲下四大引用）
- handleMessage 时，活动已释放 - 空指针 - onDestroy 释放
- 线程太多了，反复创建销毁速度太慢，线程池提高线程复用（线程池原理）

- 线程池种 Thread 停止  - hashSet - key used WeakReference<Thread> - onDestroy 遍历销毁
- 每个 Activity/fragment 一个线程池，相同图片存在不同线程池里，全局单例线程池 - 懒加载（讲下各种单例，线程安全）



#### 图片压缩

- OOM - 先把 Bittmap 读进来压缩后后显示 - （ Android 3.2后相机默认 SRGB-8888 ）RGB-565
- 如果超大图还没压缩就崩溃 - 查百度发现 Resource 图片可以设置固定 options.inSampleSize  
- 类比 stream 的形式，用decodestream(inputstream, null, options) 设置 insameplesize
- 由于大小参差不齐固定的压缩结果有差距 - 加载时设置 inJustDecodeBounds - 再测量 inSampleSize（ 原图和 item（固定 80dp）比例）
- 后期由于 Bmob 图片不让用了，换了随机图片。由于每次读的图片不一样，而且 stream 流只能读一次，所以每次测只能设置设固定的 inSampleSize（10）



#### 图片缓存

- 每次下载耗时且耗流量 - 特别是大图需要读两次流 - 引入 LruCache 存压缩过的图片（不压缩太大存不了几张）
- 第一次下载时还是从网上下载，消耗大量流量 - （比如个人拾取，每次打开基本没变化）引入 DiskLruCache
- 加载先去内存，没有去硬盘，没有去下载，再存内存，再存硬盘





## SitUp



#### Tinker 原理

- 解决 Bug ：下载补丁，通过类加载机制，覆盖掉之前的 class
- Tinker做了对应的DexDiff、ResDiff、BsDiff来产出一个patch.apk,里面具体内容也是由lib、res和dex文件组成，assets中还有对应的dex、res和so信息
- 在android系统中有两种classload，分别是PathClassLoader和DexClassLoader，它们都继承自BaseDexClassLoader
- Android系统通过PathClassLoader来加载系统类和主dex中的类。而DexClassLoader则可用于加载指定路径的apk、jar或dex文件。
- 系统在加载一个类的时候其实是从一个dex数组去加载的，当在前面的dex文件加载到这个类的时候就会把这个类返回而不会去管后面的dex文件
- 运行时通过反射把这个合成dex文件插入到PathClassLoader中的dexElements数组的前面，保证类加载时优先加载补丁dex中的class。
- 对比 AndFix（阿里）
- ![](https://img-blog.csdn.net/20151113113141551)

- 对比
- ![](https://pic1.zhimg.com/80/v2-e0f58ddb754f67ef0c2974d4b7c069b8_720w.jpg)



#### 封装 - View - RecyclerView

- 



#### ARouter

- 



#### Glide

- 





# MIT License

Copyright (c) 2020 Knowledge-Precipitation-Tribe

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.