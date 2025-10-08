package com.bookstore.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String getString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public static String getNonEmptyString(String prompt){
        String input;
        do {
            input = getString(prompt);
            if (input.isEmpty()){
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static int getInt(String prompt){
        while (true){
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e){
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    public static double getDouble(String prompt){
        while (true){
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e){
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static Date getDate(String prompt){
        while (true){
            try {
                System.out.print(prompt + " (yyyy-MM-dd): ");
                String input = scanner.nextLine().trim();
                return DATE_FORMAT.parse(input);
            } catch (Exception e){
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }





}
