package ru.eninja.flang.lab3;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("To find phones execute:");
            System.out.println("phone_finder <filename>");
            return;
        }

        for (String phone : PhoneFinder.findPhoneNumbers(args[0])) {
            System.out.println(phone);
        }
    }
}
