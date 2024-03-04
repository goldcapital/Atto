package com.compay.service;



import com.compay.controller.AdminController;
import com.compay.controller.ProfileController;
import com.compay.dto.Profile;
import com.compay.enums.GeneralStatus;
import com.compay.enums.ProfileRole;
import com.compay.repository.ProfileRepository;
import com.compay.util.MD5Util;

import java.time.LocalDateTime;

public class AuthService {
    private ProfileRepository profileRepository;

    public AuthService() {
        profileRepository = new ProfileRepository();
    }

    public void login(String phone, String password) {
        Profile profile = profileRepository.getProfileByPhoneAndPassword(phone, MD5Util.encode(password));

        if (profile == null) {
            System.out.println("Phone or Password incorrect");
            return;
        }

        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            System.out.println("You not allowed.MF");
            return;
        }

        if (profile.getRole().equals(ProfileRole.ADMIN)) {
            AdminController adminController = new AdminController();
            adminController.start(profile);
        } else if (profile.getRole().equals(ProfileRole.USER)) {
            ProfileController profileController = new ProfileController();
            profileController.start(profile);
        } else {
            System.out.println("You don't have any role.");
        }

    }

    public void registration(Profile profile) {
        // check
        Boolean exist = profileRepository.isPhoneExist(profile.getPhone()); // unique
        if (exist) {
            System.out.println(" Phone already exist.");
            return;
        }

        profile.setStatus(GeneralStatus.ACTIVE);
        profile.setCreatedDate(LocalDateTime.now());
        profile.setRole(ProfileRole.USER);
        profile.setPassword(MD5Util.encode(profile.getPassword()));
        int result = profileRepository.saveProfile(profile);

        if (result != 0) {
            System.out.println("Profile created.");
        }

    }
}
