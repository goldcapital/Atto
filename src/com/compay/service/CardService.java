package com.compay.service;



import com.compay.dto.Card;
import com.compay.enums.GeneralStatus;
import com.compay.enums.TransactionType;
import com.compay.repository.CardRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CardService {
    private CardRepository cardRepository = new CardRepository();
    private TransactionService transactionService = new TransactionService();
    private TerminalService terminalService = new TerminalService();

    public void addCardToProfile(String phone, String cardNum) {
        Card exists = cardRepository.getCardByNumber(cardNum);
        if (exists == null) {
            System.out.println("Card not found");
            return;
        }
        cardRepository.assignPhoneToCard(phone, cardNum);

    }

    public void profileCardList(String phone) {
        List<Card> cardList = cardRepository.getCardByProfilePhone(phone);
        for (Card card : cardList) {
            System.out.println(card);
        }
    }

    public void userChangeCardStatus(String phone, String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println("Mazgi card not belongs to you");
            return;
        }

        if (card.getStatus().equals(GeneralStatus.ACTIVE)) {
            cardRepository.updateCardStatus(cardNumber, GeneralStatus.BLOCK);
        } else if (card.getStatus().equals(GeneralStatus.BLOCK)) {
            cardRepository.updateCardStatus(cardNumber, GeneralStatus.ACTIVE);
        }


    }

    public void userDeleteCard(String phone, String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println("Card not belongs to you");
            return;
        }

        int n = cardRepository.deleteCard(cardNumber);
        if (n != 0) {
            System.out.println("Card deleted");
        }
    }

    public void adminCreateCard(String cardNumber, String expiredDate) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist != null) {
            System.out.println("Card Number is exist");
            return;
        }

        Card card = new Card();
        card.setCardNumber(cardNumber);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.parse(expiredDate, timeFormatter);
        card.setExpDate(localDate);

        card.setBalance(0d);
        card.setStatus(GeneralStatus.ACTIVE);
        card.setCreatedDate(LocalDateTime.now());
        int n = cardRepository.save(card);

        if (n != 0) {
            System.out.println("Card successfully added");
            return;
        } else {
            System.out.println("ERROR");
        }
    }

    public void cardList() {
        List<Card> cardList = cardRepository.getList();
        for (Card card : cardList) {
            System.out.println(card);
        }
    }

    public void adminDeleteCard(String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }
        int n = cardRepository.deleteCard(cardNumber);
        if (n != 0) {
            System.out.println("Card deleted");
        }
    }

    public void adminChangeStatus(String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getStatus().equals(GeneralStatus.ACTIVE)) {
            cardRepository.updateCardStatus(cardNumber, GeneralStatus.BLOCK);
        } else if (card.getStatus().equals(GeneralStatus.BLOCK)) {
            cardRepository.updateCardStatus(cardNumber, GeneralStatus
                    .ACTIVE);
        }

    }

    public void adminUpdateCard(String cardNumber, String expiredDate) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        Card card = new Card();
        card.setCardNumber(cardNumber);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.parse(expiredDate, timeFormatter);
        card.setExpDate(localDate);

        int n = cardRepository.updateCard(card);
        if (n != 0) {
            System.out.println("Card Updated");
        }
    }

    public void userRefillCard(String phone, String cardNumber, Double amount) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println("Mazgi card not belongs to you.");
            return;
        }

        // refill card
        cardRepository.refillCard(cardNumber, card.getBalance() + amount);
        // make transaction
        transactionService.createTransaction(card.getId(), null, amount, TransactionType.ReFill);

    }

}
