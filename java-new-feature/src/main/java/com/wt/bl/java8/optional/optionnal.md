> Optional 

+ 类的方法

    方法        | 描述
    ----       |----
    empty      | 返回一个空的Optional实例 
    filter     | 如果值存在并且满足提供的谓词，就返回包含该值的Optional对象；否则返回一个空的Optional对象
    flatMap    | 如果值存在，就对该值执行提供的mapping函数调用，返回一个Optional类型的值，否则就返回一个空的Optional对象
    get        | 如果该值存在，将该值用Optional封装返回，否则抛出一个NoSuchElementException异常
    ifPresent  | 如果值存在，将执行使用该值的方法调用，否则什么都不用
    isPresent  | 如果值存在就返回true，否则返回false
    map        | 如果值存在，就对该值执行提供的mapping函数调用
    of         | 将指定值用Optional封装之后返回，如果该值为null，则抛出一个NullPointerException异常
    ofNullable | 将指定值用Optional封装后返回，如果该值为null，则返回一个空的Optional对象
    orElse     | 如果有值则将其返回，否则返回一个默认值
    orElseGet  | 如果有值则将其返回，否则返回一个由指定的Supplier接口生成的值
    orElseThrow| 如果有值则将其返回，否则抛出一个由指定的Supplier接口生成的异常

> 在与模型中使用Optional，以及为什么他们无法序列化？
+ Optional创建的初衷仅仅是要支持能返回Optional对象的语法
+ 由于Optional类设计时没特别考虑将其作为类的字段使用，所以它也并未实现Serializable接口