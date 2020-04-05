# 细碎知识点






## Android



### Handler



#### 如何在ThreadLocalMap中，ThreadLocal如何作为键值对中的key？

> - 通过ThreadLocal计算出Hash key，通过这个哈希值来进行存储和读取的。



#### 如何获取到当前线程

> - Thread.currentThread()`就是当前线程。



#### Looper.loop()在什么情况下会退出？

> 1. next方法返回的msg == null
> 2. 线程意外终止



####  Looper.loop()是如何阻塞的？MessageQueue.next()是如何阻塞的？ 

> - 通过native方法：nativePollOnce()进行精准时间的阻塞。



#### Handler.post的逻辑在哪个线程执行的，是由Looper所在线程还是Handler所在线程决定的？

> - 由Looper所在线程决定的
> - 最终逻辑是在Looper.loop()方法中，从MsgQueue中拿出msg，并且执行其逻辑，这是在Looper中执行的，因此有Looper所在线程决定。




## Java











## 计网











## 算法















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
