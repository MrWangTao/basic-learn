> lambda 表达式
+ 基本语法
    - (paramters) -> expression
    - (paramters) -> { statements; }
+ 说明
    - return 是一个控制流语句。要使用此lambda有效，需要使用花括号。如： (Integer i) -> {return "Alan" + i; }
+ 使用案例
    
    使用案例 | Lambda示例 | 对应函数式接口
    ----    | ---- | ----
    布尔表达式|（List<String> list） -> list.isEmpty() | Predicate<List<String>>
    创建对象| () -> new Apple(10) | Supplier<Apple>
    消费一个对象| (Apple a) -> { System.out.println(a.getWeight()); } | Consumer<Apple>
    从一个对象中选择/抽取| (String s) -> s.lenght() | Function<String, Integer> 或 ToIntFunction<String>
    组合两个值| (int a, int b) -> a * b | IntBinaryOperator
    比较两个对象| (Apple a, Apple b) -> a.getWeight().compareTo(b.getWeight()) | Comparator<Apple> 或 BiFunction<Apple, Apple, Integer> 或 ToIntBiFunction<Apple, Apple>

> 方法引用
+ 基本思想
    如果一个Lambda代表的只是 "直接调用的这个方法"，那最好还是用名称去调用它， 而不是去描述如何调用它。 `可读性更好`
+ 如何构建方法应用
    - 指向`静态方法`的方法引用。 （例如：Integer的parseInt方法，写作Integer::parseInt）
    - 指向`任意类型实例方法`的方法引用。 （例如： String的length方法，写作String::length）
    - 指向`现有对象的实例方法`的方法引用。 （假如有一个局部变量expresiveTransaction用于存放Transaction类型的对象，它支持实例方法getValue，
        那么你就可以写expensiveTransaction::getValue）
    
> java8 中的常用函数式接口

+ 函数式接口
    - 定义且只定义一个抽象方法
    - 函数式接口的抽象方法的签名称为`函数描述符`

+ 表格
    
    函数式接口 | 函数描述符 | 原始类型特化 | 示例
    ----      | ----      |     ----   | ----
    Predication<T>| T -> boolean| IntPredicate, LongPredicate, DoublePredication | Stream.filter()
    Consumer<T> | T -> void | IntConsumer, LongConsumer, DoubleConsumer | Stream.forEach()
    Function<T, R> | T -> R | IntFunction<R>, IntToDoubleFunction, IntToLongFunction, LongFunction<T>, LongToDoubleFunction, LongToIntFunction, DoubleFunction<R>, ToIntFunction<T>, ToDoubleFunction<T>, ToLongFunction<T> | Stream.map()
    Supplier<T> | () -> T | BooleanSupplier, IntSupplier, LongSupplier| Stream.collect()
    UnaryOperator<T> | T -> T | IntUnaryOperator, LongUnaryOperator, DoubleUnaryOperatore
    BinaryOperator<T> | (T, T) -> T | IntBinaryOperator, LongBinaryOperator, DoubleBinaryOperator
    BiPredicate<L, R> | (L, R) -> boolean
    BiConsumer<T, U> | (T, U) -> void | ObjIntConsumer<T, U>, ObjLongConsumer<T, U>, ObjDoubleConsumer<T, U>
    BiFunction<T, U, R> | (T, U) -> R | ToIntBiFunction<T, U>, ToLongBiFunction<T, U>, ToDoubleBiFunction<T, U>

+ StreamsAPI 可以表达复杂的数据处理查询
+ 可以使用`filter`、`distinct`、`skip`和`limit`对流做筛选和切片
+ 使用`map`和`flatMap`提取或者转换流中的元素
> flatMap 和 map 的区别？
    flatMap将流扁平化，可以理解为把多个列表（map）合并成一个列表
+ 使用`findFirst` 和 `findAny` 方法查找流中元素；可以用`allMatch`、`nonMatch`、`anyMatch` 方法让流匹配给定的
    谓词
+ 规约：利用`reduce`方法将流中所有的元素迭代合并成一个结果，例如求和或查找最大值
+ `filter` 和 `map` 等操作是无状态的，他们并不存储任何状态。reduce等操作要存储转改才能计算出一个值。
    `sorted` 和 `distinct` 等操作也要存储状态，因为他们需要把六中的所欲元素缓存起来才能返回一个新的流，这种
    操作称为有状态操作
+ 流有三种基本的原始类型特化： IntStream、DoubleStream、LongStream，他们的操作也有相应的特化
+ 流不仅可以从集合创建，也可以从值、数组、文件以及`iterate`与`generate`等特定方法创建

> 中间操作是产生一系列的Stream流

> Collectors
> `collect` 方法的设计就是要改变容器，从而累计要输出的结果
`collect` 方法特别适合表达`可变容器`上的归约  ？？？

+  `Collectors.reducing`工厂方法是所有这些特殊情况的一般化
 
+ Collectors类的静态工厂方法
    
    工厂方法|返回类型|用于|实例
    ----|----|----|----
    toList|List<T>|把流中所有项目收集到一个List|List<Dish> dishes = menuStream.collect(toList());
    toSet|Set<T>|把流中所有项目收集到一个set，删除重复项|Set<Dish> dishes = menuStream.collect(toSet());
    toCollection|Collection<T>|把流中所有项目收集到给定的供应源创建的集合|Collection<Dish> dishes = menuStream.collect(toCollection(), ArrayList::new);
    counting|Long|计算流中元素的个数|long howManyDishes = menuStream.collect(counting());
    summingInt|Integer|对流中项目的一个整数属性求和|int totalCalories = menuStream.collect(summingInt(Dish::getCalories));
    averagingInt|Double|计算流中项目Integer属性的平均值|double avgCalories = menuStream.collect(averagingInt(Dish::getCalories));
    summarizingInt|IntSummaryStatistics|收集关于流中项目Integer属性的统计值，例如最大、最小、总和与平均值|IntSummaryStatistics menuStatistics = menuStream.collect(summarizingInt(Dish::getCalories));
    joining|String|连接对流中每个项目调用toString方法所生成的字符串，其中使用了StringBuilder，提升拼接效率|String shortMenu = menuStream.map(Dish::getName).collect(joining(", "));
    maxBy|Optional<T>|一个包裹了流中按照给定比较器选出的最大元素的Optional或如果流为空则为Optional.empty()|Optional<Dish> fattest = menuStream.collect(maxBy(comparingInt(Dish::getCalories)));
    minBy|Optional<T>|一个包裹了流中按照给定比较器选出的最小元素的Optional或如果流为空则为Optional.empty()|Optional<Dish> lightest = menuStream.collect(minBy(comparingInt(Dish::getCalories)));
    reducing|规约操作产生的类型|从一个作为累加器的初始值开始，利用BinaryOperator与流中的元素逐个结合，从而将流归约为单个值|int totalCalories = menuStream.collect(reducing(0, Dish::getCalories, Integer::sum));
    collectingAndThen|转换函数返回的类型|包裹另一个收集器，对其结果应用转换的函数|int howManayDishes = menuStream.collect(collectingAndThen(toList(), List::size));
    groupingBy|Map<K, List<T>>|根据项目的一个属性的值对流中的项目做分组，并将属性值作为结果Map的key|Map<Dish.Type,List<Dish>> dishesByType = menuStream.collect(groupingBy(Dish::getType));
    partitioningBy|Map<Boolean, List<T>>|根据对流中每个项目应用谓词的结果来对项目进行分区|Map<Boolean,List<Dish>> vegetarianDishes = menuStream.collect(partitioningBy(Dish::isVegetarian));
    
> groupingBy `分组` 和 partitioningBy `分区`
+ partitioningBy 可以理解为是一个特殊的groupingBy，只是partitioningBy需要传递的是一个谓词，即boolean值
+ 两者都可以通过两参数的类型，实现多级分区或分组
    
> CompletableFuture
+ 简介
    java7中引入`使用分支/合并框架`和java8中引入`并行流`能以更简单、更有效的方式实现这一目标。
    Future接口在Java5中被引用，设计的初衷是对`将来某个时刻会发生的结果`进行建模（异步计算）；要使用Future，通常只需要将耗时的操作封装在一个Callable对象中，再将它
    提交给ExecutorService就ok了。
    - Stream 和 CompletableFuture的设计都遵循类似的模式： 他们都使用了Lambda表达式以及流水线的思想
    - 从这个角度上来说： CompletableFuture 和 Future的关系就和Stream 和 Collection的关系一样
+ 常用方法
    - 是用工厂方法`supplyAsync`创建CompletableFuture对象
    - join方法（和Future中的get方法有相同含义，唯一的不同是join不会抛出任何检测到的异常）
    > 考虑到流水线之间的延迟特性，如果在单一流水线中处理流，发现不同商家的请求只能以同步、顺序执行的方式才能成功。
    因此，每个创建CompletableFuture对象只能在前一个操作结束之后才能执行下一个操作、通知Join方法返回计算结果
    - thenCombine方法： 允许对两个一步操作完成后，将其合并
    - thenCompose方法： 允许对两个异步操作进行流水线，第一个操作完成时，将其作为参数传递给第二个操作
+ 并行：使用流还是CompletableFuture？
    - 如果进行的是`计算密集型`的操作，并且没有I/O，推荐使用Stream接口，因为实现简单，同时效率也可能是最高的（如果所有的线程都是计算密集型的，
    那就没有必要创建比处理器核数更所的线程）
    - 如果并行的工作单元还涉及等待I/O的操作（包括网络连接等待），那么使用CompletableFuture灵活性更好，依据等待/计算，或者W/C的比率设定需要
    使用的线程数。这种情况不使用并行流的另一个原因是，处理流的流水线中如果发生I/O等待， 流的延迟特性会让我们很难判断什么时候触发了等待