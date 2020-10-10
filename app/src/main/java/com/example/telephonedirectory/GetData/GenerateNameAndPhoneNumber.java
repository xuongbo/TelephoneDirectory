package com.example.telephonedirectory.getData;

import com.example.telephonedirectory.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateNameAndPhoneNumber {

    public static final String number = "1234567890";

    List<String> name = new ArrayList<>();

    public List<Phone> nameGenerate(){
        List<Phone> phones = new ArrayList<>();

        name.add("Ông. Phí Thọ");
        name.add("Cụ. Bảo Minh");
        name.add("Trần Thanh");
        name.add("Trà Nhạn");
        name.add("Hứa Tùy Hằng");
        name.add("Đàm Toàn");
        name.add("Chiêm Liên");
        name.add("Trần Hương");
        name.add("Hồng Thảo");
        name.add("Lại Kim Trâm");
        name.add("Huỳnh Hiền");
        name.add("Bồ Tùng Kiều");
        name.add("Bồ Lợi");
        name.add("Nông Linh");
        name.add("Từ Định Dinh");

        for (int i = 0; i < 15; i++){
            Phone phone = new Phone();
            phone.setName(name.get(i));
            phone.setPhoneNumber("0"+generateNumber());

            phones.add(phone);
        }
        return phones;

    }
    
    public String generateNumber(){
        String phoneNumber = "";
        for (int i = 0; i < 10; i++){
            Random random = new Random();
            int index = random.nextInt(number.length());
            phoneNumber += number.charAt(index);
        }
        return phoneNumber;
    }
}
