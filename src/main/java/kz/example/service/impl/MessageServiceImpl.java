package kz.example.service.impl;

import kz.example.dto.MessageDto;
import kz.example.model.Message;
import kz.example.model.User;
import kz.example.repository.MessageRepository;
import kz.example.service.MessageService;
import kz.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserService userService;

	@Override
	public boolean saveMessage(MessageDto messageDto) {
		User user = userService.findUser(messageDto.getName());
		if (user != null) {
			Message msg = new Message();
			msg.setUser(user);
			msg.setMessage(messageDto.getMessage());
			messageRepository.save(msg);
			return true;
		}
		return false;
	}

	@Override
	public List<String> loadMesageHistoryByName(String name, int msgsCount) {
		User user = userService.findUser(name);
		if (user != null) {
			return messageRepository.findMessagesByUserId(user.getId(), PageRequest.of(0, msgsCount));
		} else {
			return new ArrayList<>();
		}
	}
}
