package me.neogui350.utilutility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

//타임스탬프를 우리가 알아 볼수 있게 바꿔주는 클래스

public class TimeSteamp {


    public static String TimeSteamp(Long steamp){
        Instant instant = Instant.ofEpochMilli(steamp);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;

    }

}
