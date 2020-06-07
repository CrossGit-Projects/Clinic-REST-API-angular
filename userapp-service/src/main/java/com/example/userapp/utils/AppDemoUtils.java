package com.example.userapp.utils;

import java.util.Random;

public class AppDemoUtils {

    //Todo Jak najbardziej pochwalam, za tworzenie klas Utils. Dobrą praktyką jeszcze jest w takich klasach
    // tworzyć prywatny konstruktorów (tak jak np w java.lang.Math) Żeby ktoś przez pomyłkę nie tworzył obiektów z klasy Utils.

    public static String randomStringGenerator() {
        String randomString = "";

        String signs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        Random rnd = new Random();

        for (int i = 0; i < 32; i++) {
            int number = rnd.nextInt(signs.length());
            randomString += signs.substring(number, number + 1);
        }
        return randomString;
    }
}
