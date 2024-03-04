package com.compay.service;



import com.compay.dto.Terminal;
import com.compay.enums.GeneralStatus;
import com.compay.repository.TerminalRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TerminalService {
    private TerminalRepository terminalRepository = new TerminalRepository();


    public void addTerminal(Terminal terminal) {
        Terminal exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist != null) {
            System.out.println("Terminal code exists");
            return;
        }
        terminal.setCreatedDate(LocalDateTime.now());
        terminal.setStatus(GeneralStatus.ACTIVE);
        terminalRepository.save(terminal);
    }

    public void terminalList() {
        List<Terminal> terminalList = terminalRepository.getTerminalList();
        for (Terminal terminal : terminalList) {
            System.out.println(terminal);
        }
    }

    public void updateTerminal(Terminal terminal) {
        Terminal exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist == null) {
            System.out.println("Terminal not found");
            return;
        }

        terminalRepository.updateTerminal(terminal);
    }

    public void changeTerminalStatus(String code) {
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return;
        }
        if (terminal.getStatus().equals(GeneralStatus.ACTIVE)) {
            terminalRepository.changeTerminalStatus(code, GeneralStatus.BLOCK);
        } else {
            terminalRepository.changeTerminalStatus(code, GeneralStatus.BLOCK);
        }

    }

    public void deleteTerminal(String code) {
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return;
        }

        terminalRepository.deleteTerminal(code);
    }
}
