> java并发图谱
+ java内存模型
    - 内存模型（如果不用volatile，内存模型允许"无序写入"）
    - 主内存和工作内存的交互（lock，unlock，read，load，use，assign，store，write）
    - happens-before
+ volatile
    - volatile实现机制
    - 可见性（工作内存变更会更新主内存，工作内存每次都从主内存中取值）
    - 阻止指令从排序
    - 应用场景
+ synchronized
    - synchronized原理
    - 锁优化（明白锁的升级流程）
        * 偏向锁：没有在锁竞争的条件下，偏向于当前线程，一旦出现竞争，偏向锁撤销
        * 轻量级锁（自旋锁）
        * 重量级锁
+ DCL(Double-checkedLocking)
    - 存在的问题
    - 解决方案
        * 单例（静态属性）
        * DCL + volatile
+ 并发基础
    - AQS（源码）
        * AQS同步器
        * CLH同步队列
        * 同步状态的获取和释放
        * 线程阻塞与唤醒
    - CAS
        * compare and swap
        * 缺陷
            - ABA问题
            - 自旋时间太长
+ 锁
    - ReentrantLock（源码）
    - ReentrantReadWriteLock（源码）
    - Condition（源码）-Lock
+ 同步工具类
    - 栅栏 CyclicBarrier
    - 闭锁 CountDownLatch
    - 信号量 Semaphore（资源池、容器边界等应用）
+ 并发容器
    - ConcurrentHashMap（源码）
    - ConcurrentMap（复合操作的线程安全实现）
    - ConcurrentLinkedQueue
    - BlockingQueue （消费者-生产者）
        * ArrayBlockingQueue（源码）
        * LinkedBlockingQueue（源码）
    - CodyOnWriteArrayList（源码）
+ 原子操作
    - 基本类型
        * AtomicBoolean（源码）
        * AtomicInteger（源码）
        * AtomicLong（源码）
    - 数组
        * AtomicIntegerArray（源码）
        * AtomicLongArray（源码）
        * AtomicReferenceArray（源码）
    - 引用类型
        * AtomicReference（源码）
        * AtomicReferenceFieldUpdater（源码）
+ 线程池
    - Callable和Future（源码）
        * Callable 雷同于Runable，有返回值
        * Future 设置任务的返回值
    - Executor（源码）
    - ThreadPoolExecutor(源码)
    - ScheduleThreadPoolExecutor（源码）
        * 添加一个工作线程Worker，根据Worker设定coreThreadSize，将这个Work放入队列中
        * 循环添加，循环执行
+ 其他
    - ThreadLocal（源码）
    - Fork/Join
    - Thread