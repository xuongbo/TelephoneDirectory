package com.example.telephonedirectory.Validation;

public class Validation {

    public static boolean isNumber(String number){
        int isNumber;
        try{
            isNumber = Integer.parseInt(number);
        } catch (Exception e){
            return false;
        }
        if (number.length() != 11) return false;
        return true;
    }
    public static boolean isString(String string){
        String number ="0123456789";
        for (int i = 0; i < 10; i++){
            if (string.contains(number.charAt(i)+"")) return false;

        }
        return true;
    }
}
