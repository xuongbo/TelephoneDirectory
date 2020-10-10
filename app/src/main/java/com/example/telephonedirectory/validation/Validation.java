package com.example.telephonedirectory.validation;

public class Validation {

    public static boolean isNumber(String number){
        int isNumber;
        try{
            isNumber = Integer.parseInt(number);
        } catch (Exception e){
            return false;
        }
        return number.length() == 11;
    }
    public static boolean isString(String string){
        String number ="0123456789";
        for (int i = 0; i < 10; i++){
            if (string.contains(number.charAt(i)+"")) return false;

        }
        return true;
    }
}
