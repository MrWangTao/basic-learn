## java8时间类库 **日期-时间对象是不可变的** ，`线程安全`
> 常用类
+ LocalDate
    - This class does not store or represent a `time` or `time-zone`.
    - It cannot represent an instant on the time-line without additional information such as an offset or time-zone.
+ LocalDateTime
    - This class does not store or represent a `time-zone`.
    - It cannot represent an instant on the time-line without additional information such as an offset or time-zone.
+ Instant `机器日期和时间格式`
    - This class models a single instantaneous`瞬时的` point on the time-line.
    - This might be used to record event time-stamps in the application.
+ Duration
    - This class models a quantity or amount of time in terms of seconds and nanoseconds.
    - `example`: 
        * Duration.between(LocalDateTime.of(2018, 8, 17, 16, 50, 00), LocalDateTime.of(LocalDate.now(), LocalTime.now())).getSeconds();
+ Period
    - This class models a quantity or amount of time in terms of years, months and days.
        * Period.between(LocalDate.now(), LocalDate.of(2018, 8, 18)).getDays();
+ TemporalAdjusters
    - This class contains a standard set of adjusters, available as static methods.
    - These include:
       <ul>
           <li>finding the first or last day of the month
           <li>finding the first day of next month
           <li>finding the first or last day of the year
           <li>finding the first day of next year
           <li>finding the first or last day-of-week within a month, such as "first Wednesday in June"
           <li>finding the next or previous day-of-week, such as "next Thursday"
       </ul>
    - `example`: LocalDate.now().`with`(TemporalAdjusters.firstDayOfMonth())
+ ChronoField
    - This set of fields provide field-based access to manipulate a date, time or date-time.
    - The standard set of fields can be extended by implementing {@link TemporalField}.
    -  `example` : LocalDate.now().`get`(ChronoField.DAY_OF_WEEK);
    
> LocalDate、LocalDateTime、LocalTime、Instant
    
+ common method description

    |method name | is static | description|
    |     ----   |   ----    |    ----    |
    | from       |   yes     | 根据传入的Temporal对象创建对象实例
    | now        |   yes     | 根据系统时钟创建Temporal对象
    | of         |   yes     | 由`Temporal`对象的某个部分创建该对象的实例
    | parse      |   yes     | 由字符串创建`Temporal`对象的实例
    | atOffset   |   no      | 将`Temporal`对象和某个时区偏移相结合
    | atZone     |   no      | 将`Temporal`对象和某个时区相结合
    | format     |   no      | 使用某个指定的格式器将`Temporal`对象转换成字符串（`Instant`类不提供该方法）
    | get        |   no      | 读取`Temporal`对象的某一部分的值
    | minus      |   no      | 创建`Temporal`对象的一个副本，通过将当前`Temporal`对象的值减去一定的时长创建该副本
    | plus       |   no      | 创建`Temporal`对象的一个副本，通过将当前`Temporal`对象的值加上一定的时长创建该副本
    | with       |   no      | 以该`Temporal`对象为模板，对某些对象进行修改创建该对象的副本
    
> The Factory method of `TemporalAdjuster` class 

+ table about method description

    method name               | description
    ----                      | ----
    dayOfWeekInMonth          | 创建一个新日期，它的值为同一个月中每一周的第几天
    firstDayOfMonth           | 创建一个新日期，它的值为当月的第一天
    firstDayOfNextMonth       | 创建一个新日期，它的值为下月的第一天
    firstDayOfNextYear        | 创建一个新日期，它的值为明年的第一天
    firstDayOfYear            | 创建一个新日期，它的值为当年的第一天
    firstInMonth              | 创建一个新日期，它的值为同一个月中，第一个符合星期几要求的值
    lastDayOfMonth            | 创建一个新日期，它的值为当月的最后一天
    lastDayOfNextMonth        | 创建一个新日期，它的值为下月的最后一天
    lastDayOfNextYear         | 创建一个新日期，它的值为明年的最后一天
    lastDayOfYear             | 创建一个新日期，它的值为今年的最后一天
    lastInMonth               | 创建一个新日期，它的值为同一个月中，最后一个符合星期几要求的值
    next/previous             | 创建一个新日期，并将其值设定为日期调整后或者调整前，第一个符合指定星期几要求的日期
    nextOrSame/previousOrSame | 创建一个新日期，并将其值设定为日期调整后或者调整前，第一个符合星期几要求的日期，如果该日期已经符合要求，直接返回该对象
    
+ impletements `TemporalAdjuster`，override `adjustInto` method