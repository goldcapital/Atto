package com.compay.controller;



import com.compay.dto.Profile;
import com.compay.service.AuthService;
import com.compay.util.ScannerUtil;

import java.util.Scanner;

public class AuthController {

    public void start() {
        boolean game = true;
        while (game) {
            menu();
            int action = ScannerUtil.getAction();
            switch (action) {
                case 1:
                    login();
                    break;
                case 2:
                    registration();
                    break;
                case 0:
                    game = false;
                default:
                    System.out.println("Mazgi nima bu");
            }
        }
    }

    public void menu() {
        System.out.println("********************Menu***********************");
        System.out.println("1. Login > ");
        System.out.println("2. Registration > ");
        System.out.println("0. Exit > ");
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter phone:");
        String phone = scanner.nextLine();

        System.out.print("Enter pswd:");
        String password = scanner.next();

        AuthService profileService = new AuthService();
        profileService.login(phone, password);
    }

    private void registration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name:");
        String name = scanner.next();

        System.out.print("Enter surname:");
        String surname = scanner.next();

        System.out.print("Enter phone:");
        String phone = scanner.next();

        System.out.print("Enter pswd:");
        String password = scanner.next();

        Profile profile = new Profile();
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPassword(password);


        AuthService authService = new AuthService();
        authService.registration(profile);
    }

}
