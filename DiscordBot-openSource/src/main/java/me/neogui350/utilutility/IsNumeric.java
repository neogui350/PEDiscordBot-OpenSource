package me.neogui350.utilutility;

//대충 문자열이 숫자인지 확인하는 클래스

public class IsNumeric {
    public static boolean intNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }

}
