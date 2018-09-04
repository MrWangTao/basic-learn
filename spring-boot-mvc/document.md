### springmvc 实现客户端和服务端，枚举值的类型转换步骤
+ 定义一个类实现`Formatter<T>`接口，并重写它的两个方法
+ 在实现了WebMvcConfigurer接口的配置中，重写addFormatters方法
+ @JsonValue 注解对应要作为key的值，默认情况下枚举的名字作为key，这样我们可以自定义返回前端的值

### 实现代码层面中和mybatis之间的转换步骤
+ mybatis 3.4.5之后版本支持的类型处理器可以上官网进行查看
+ 实现自己的typeHandler（extends BaseTypeHandler<T>）并重写其方法
+ 在yml配置中添加mybaits.type-handlers-package: com.wt.bl.typehandler配置
> 高级一点的操作可以是如下操作
+ 自定义扫描器
+ 自定义ValuableEnum 和 ValuableEnumDeserier（继承StdDeserializer<E>） 反序列化
+ 初始化时加载所有实现了ValuableEnum接口枚举类实现缓存