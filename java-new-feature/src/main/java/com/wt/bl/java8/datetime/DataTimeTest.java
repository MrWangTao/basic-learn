package com.wt.bl.java8.datetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;

/**
 * <p> java8时间类库中的常用方法 </p>
 * @author WangTao
 *         Created at 18/8/17 上午10:18.
 */
public class DataTimeTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataTimeTest.class);

    private static void localDateTest() {
        System.out.println("======================> static method <========================");
        LocalDate localDateByStringFormat = LocalDate.parse("2018-08-17");
        System.out.println("parse by String '2018-08-17' ==> " + localDateByStringFormat);
        LocalDate localDateByStringFormatAndDateTimeFormat1 = LocalDate.parse("2018-08-17", DateTimeFormatter.ISO_DATE);
        System.out.println("1 parse by String '2018-08-17' and DateTimeFormat ==> " + localDateByStringFormatAndDateTimeFormat1);
        LocalDate localDateByStringFormatAndDateTimeFormat2 = LocalDate.parse("2018-08-17", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("2 parse by String '2018-08-17' and DateTimeFormat ==> " + localDateByStringFormatAndDateTimeFormat2);
        LocalDate localDateByStringFormatAndDateTimeFormat3 = LocalDate.parse("2018-08-17T12:00:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("3 parse by String '2018-08-17' and DateTimeFormat ==> " + localDateByStringFormatAndDateTimeFormat3);
        System.out.println("the max localdate ==> " + LocalDate.MAX +", the min localdate ==> " + LocalDate.MIN);
        LocalDate localDateOfIntYearIntMonthIntDay = LocalDate.of(2018, 8, 1);
        LocalDate localDateOfIntYearMonthMonthIntDay = LocalDate.of(2018, Month.AUGUST, 1);
        System.out.println("LocalDate.of(parameters) ==> " + localDateOfIntYearIntMonthIntDay + ", and ==> " + localDateOfIntYearMonthMonthIntDay);
        LocalDate from = LocalDate.from(LocalDateTime.now());
        System.out.println("from ==> " + from);
        System.out.println("======================> general method <========================");
        LocalDate start = LocalDate.now();
        System.out.println(Duration.ofDays(1));
        System.out.println(Period.ofDays(1));
        /**
         * 注：Period： This class models a quantity or amount of time in terms of years, months and days.
         */
        LocalDate minus = start.minus(Period.ofDays(1));
        System.out.println(minus);
        /**
         * 注：Duration： This class models a quantity or amount of time in terms of seconds and nanoseconds.
         */
        LocalDateTime minus1 = LocalDateTime.now().minus(Duration.ofDays(1));
        System.out.println(minus1);
        Period between = Period.between(LocalDate.now(), LocalDate.now().plus(Period.ofDays(2)));
        System.out.println("Period accumulate date different ==> " + between.getDays());

        System.out.println(LocalDate.now().get(ChronoField.DAY_OF_WEEK));
    }

    private static void instantTest() {
        LOGGER.info("static method now() ==> result: {}", Instant.now());
        LOGGER.info("static method parse(CharSequence ==> result: {})", Instant.parse("2007-12-03T10:15:30.00Z"));
        LOGGER.info("ZonedDateTime.now() ==> " + ZonedDateTime.now());
        /**
         * 注： Instant.from(TemporalAccessor)
         *     TemporalAccessor的实现类可以使用： ZonedDateTime
         * throw  java.time.DateTimeException: Unable to obtain Instant from TemporalAccessor
         *      Unsupported field: InstantSeconds: DayOfWeek, LocalDate, LocalDateTime, LocalTime, Year, Month
         */
        LOGGER.info("static method from(TemporalAccessor) ==> result: {}", Instant.from(ZonedDateTime.now()));
        /*LOGGER.info("static method from(TemporalAccessor) ==> result: {}", ZoneId.from(ZonedDateTime.now()));
        LOGGER.info("LocalDateTime.now(ZoneId) ==> result: {}", LocalDateTime.now(ZoneId.systemDefault()));
        LOGGER.info("LocalDateTime from ZonedDateTime ==> result: {}", LocalDateTime.from(ZonedDateTime.now()));*/
        /**
         * 存在疑问
         */
        ZonedDateTime date = ZonedDateTime.now();
        System.out.println(date);
        Instant instant = Instant.parse("2017-02-03T10:37:30.00Z");
        date = (ZonedDateTime)instant.adjustInto(date);
        System.out.println(date);
        LOGGER.info("current Instant`s Nano ==> {}", Instant.now().getNano());
    }

    private static void durationTest(){
        Duration between = Duration.between(LocalDateTime.of(2018, 8, 17, 16, 50, 00), LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        System.out.println(between.getSeconds());
    }

    private static void periodTest(){
        Period between = Period.between(LocalDate.now(), LocalDate.of(2018, 8, 18));
        System.out.println(between.getDays());
    }

    private static void temporalAdjustersTest(){
        System.out.println("first Day of Month ==> " + LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        System.out.println("last Day of next Month ==> " + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("first Day of next Month ==> " + LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()));
        System.out.println("first Day of next Year ==> " + LocalDate.now().with(TemporalAdjusters.firstDayOfNextYear()));
        System.out.println("first In Month ==> " + LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY)));
    }

    public static void main(String[] args) {
        localDateTest();
//        instantTest();
//        durationTest();
//        periodTest();
//        temporalAdjustersTest();
    }

}
