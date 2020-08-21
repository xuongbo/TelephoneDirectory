package com.example.telephonedirectory.GetData;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateNameAndPhoneNumber {

    public static final String number = "1234567890";

    public List<Phone> nameGenerate(){
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            Phone phone = new Phone();
            Faker faker = new Faker();

            phone.setName(faker.name().firstName()+ " " +faker.name().lastName());
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
