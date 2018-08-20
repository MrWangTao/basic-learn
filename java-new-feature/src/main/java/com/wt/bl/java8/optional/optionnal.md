> optional 方法
+ Optional.of() , Optional.ofNullable()
+ Optional.empty()
+ optional.get(), optional.orElse()
+ filter(Predicate), flatMap(Function), map(Function)

> 在与模型中使用Optional，以及为什么他们无法序列化？
+ Optional创建的初衷仅仅是要支持能返回Optional对象的语法
+ 由于Optional类设计时没特别考虑将其作为类的字段使用，所以它也并未实现Serializable接口