package kz.example.service;

import kz.example.dto.MessageDto;
import kz.example.dto.UserDto;
import kz.example.model.User;

import java.util.List;

public interface MessageService {

    boolean saveMessage(MessageDto messageDto);

    List<String> loadMesageHistoryByName(String name, int count);

}
