package com.compay.service;



import com.compay.dto.Profile;
import com.compay.enums.GeneralStatus;
import com.compay.repository.ProfileRepository;

import java.util.List;

public class ProfileService {

    private ProfileRepository profileRepository = new ProfileRepository();

    public void profileList() {
        List<Profile> profileList = profileRepository.getProfileList();

        for (Profile profile : profileList) {
            System.out.println(profile);
        }
    }

    public void changeProfileStatus(String phone) {
        Profile profile = profileRepository.getProfileByPhone(phone);
        if (profile == null) {
            System.out.println("Profile not found");
            return;
        }

        if (profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            profileRepository.changeProfileStatus(phone, GeneralStatus.BLOCK);
        } else {
            profileRepository.changeProfileStatus(phone, GeneralStatus.ACTIVE);
        }
    }

}
