package kz.example.service;

import kz.example.dto.MessageDto;

import java.util.List;

public interface MessageService {

    boolean saveMessage(MessageDto messageDto);

    List<String> loadMesageHistoryByName(String name, int count);

    boolean deleteUserMessages(Long userId);
}
