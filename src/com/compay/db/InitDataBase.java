package com.compay.db;


import com.compay.dto.Card;
import com.compay.dto.Profile;
import com.compay.enums.GeneralStatus;
import com.compay.enums.ProfileRole;
import com.compay.repository.CardRepository;
import com.compay.repository.ProfileRepository;
import com.compay.util.MD5Util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InitDataBase {

    public static void adminInit() {

        Profile profile = new Profile();
        profile.setName("Admin");
        profile.setSurname("Adminjon");
        profile.setPhone("123");
        profile.setPassword(MD5Util.encode("123"));
        profile.setCreatedDate(LocalDateTime.now());
        profile.setStatus(GeneralStatus.ACTIVE);
        profile.setRole(ProfileRole.ADMIN);


        ProfileRepository profileRepository = new ProfileRepository();

        Profile profile1 = profileRepository.getProfileByPhone(profile.getPhone());
        if (profile1 != null) {
            return;
        }
        profileRepository.saveProfile(profile);
    }

    public static void addCompanyCard() {
        Card card = new Card();
        card.setCardNumber("5555");
        card.setExpDate(LocalDate.of(2025, 12, 01));

        card.setPhone("123");
        card.setBalance(0d);
        card.setCreatedDate(LocalDateTime.now());
        card.setStatus(GeneralStatus.ACTIVE);

        CardRepository cardRepository = new CardRepository();
        Card exists = cardRepository.getCardByNumber(card.getCardNumber());

        if (exists != null) {
            return;
        }
        cardRepository.save(card);
    }
}
