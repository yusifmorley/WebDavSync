package com.yusif.utils;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
//封装 时间

@Component
public class MyDateTime {
    private final ZoneOffset offset = ZoneId.systemDefault()
            .getRules().getOffset(Instant.now());

    private final String Pattern = "yyyy年-MM月-dd日 HH:mm:ss"; //时间格式
    private final DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy年-MM月-dd日");


    public String getPattern() {
        return Pattern;
    }

    public  long getDuration(LocalDateTime start, LocalDateTime end) {//两个时间相减 返回天数
        Duration duration = Duration.between(start, end);
        return duration.toDays();
    }
    public   LocalDate getTimeFromString(String time) { //从字符串获取时间
        DateTimeFormatter df = new DateTimeFormatterBuilder()
                .appendPattern(Pattern)
                .toFormatter(Locale.CHINESE);
        return  LocalDate.parse(time,df);
    }

    public  String getNowTime(){ //得到现在时间
        return  LocalDateTime.now().format(DateTimeFormatter.ofPattern(Pattern));
    }


    public  long getLocalDateTimeToMIll(LocalDate localDate){

        return  localDate.atStartOfDay(offset).toInstant().toEpochMilli();
    }

   public long getMillFromString(String str){//从字符串获取 时间戳
       return getLocalDateTimeToMIll(getTimeFromString(str));
   }

    public  String getTimeFromMIll(long epochmilli){ //从时间戳获取 日期对象
        LocalDate localDate= Instant.ofEpochMilli(epochmilli)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate.format(dateTimeFormatter);
    }




}
