package me.neogui350.utilutility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Paths {

    //현제 시간 불러오기
    static Date today = new Date();
    //현제 시간을 양식에 마춰 다시 써줌
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    //유저 uuid 저장될 파일 경로
    public static String jsonFileUuid(String uuid) {
        return ".\\LOGS\\JSON\\uuid\\JSONFile-uuid\\" + uuid + ".json";
    }
    //메인으로 사용되는 경로, 인수로 경로를 바꿈
    public static String jsonFileCommand(String type, String nameS, String name) throws IOException {
        String path = ".\\LOGS\\JSON\\JSONFile-" + type + "\\" + nameS + "\\" + name + "-" + format.format(today) + ".json";
        String dirsPath = ".\\LOGS\\JSON\\JSONFile-" + type + "\\" + nameS;
        if (new File(dirsPath).mkdirs()){
            System.out.println("created new dirs" + dirsPath);
        }
        return path;
    }
}
