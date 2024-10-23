# practice
note and practice

将学习过程中学习到的一些思想或者设计模式
或者底层原理或者算法或者实现某个需求的策略做个笔记，实际的演练一下，提升自己的理解深度。

### 并发

- 死锁
- juc
  - Lock
  - readWriteLock
  - semaphore
  - stampedLock
  - CountDownLatch
  - CyclicBarrier
  - Executor与线程池
  - Future
  - CompletableFuture
  - CompletionService
  - Fork/Join
  - disruptor
- 案例
  - 限流器：限流算法有两个，一个是令牌桶算法（Token Bucket），另一个是漏桶算法（Leaky Bucket）
  - 基于 MVCC 的软件事务内存（Software Transactional Memory，简称 STM）
- 模型
  - actor

### 设计模式
- 单例模式
- 上下文模式
- 装饰器模式
- future模式
- 生产者消费者模式
- Worker Thread 设计模式
- 命令行模式

### git 

- git主要对象
### java新特性

- 文字块
- 档案类
- 封闭类
- 模式匹配（类型匹配）
- switch表达式
- 异常处理
- Flow异步编程
- 矢量运算
- 外部内存接口
- 外部函数接口
- 空指针异常处理
- 模块化
- normal
  - initialization
  - cleanup
  - polymorphism
  - innerClass
  - interface
  - stream
  - funtional
  - exception
  - generic
  - reflection
  - reference
  - enum
  - collection
  - annotation
### miniSpring

- IoC
- SpringMVC
- jdbcTemplate
- AOP
### 数据结构和算法
- 单链表，双链表
- LRU
  - 链表
  - hashMap+ 双向链表
  - LinkedHashMap
- 栈和队列
- 表达式计算
- Hanoi塔
- 括号匹配
- 数字十进制转换其他进制
- BM & KMP $ BF
- 插入排序
  - 直接插入排序
  - 折半插入排序
  - 希尔排序
- 交换排序
  - 冒泡排序
  - 快速排序
- 选择排序
  - 简单选择排序
  - 堆排序
- 归并排序
  - 2路归并
- 基数排序
  - 链式基数排序
- 折半查找
- 跳表
- 二叉排序树
- 平衡二叉树
- 二叉树递归和非递归遍历
- B树
- 带头节点二叉树中序线索化及其遍历
- 树/森林 
  - 兄弟孩子表示法实现 树的先根遍历和后根遍历 和森林的先序遍历和中序遍历
  - 双亲表示法
  - 表示法之间的转换
- huffmanTree
- 哈夫曼前缀编码
- 二叉树表达式解析和计算
- 图的存储结构
  - 邻接矩阵
  - 邻接表
  - 有向图的十字链表存储结构
  - 无向图的多重邻接表存储结构
- 图的遍历
  - 深度优先遍历
  - 广度优先遍历
  - 简单路径
  - 最少节点路径
  - 最小生成树 KrusKal & Prim
  - 关节点
  - 最短路径
  - 拓扑排序
  - 关键路径