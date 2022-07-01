package kz.example.controller;

import kz.example.config.com.CustomResponse;
import kz.example.dto.MessageDto;
import kz.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity<?> receiveMessage(@RequestBody @Valid MessageDto msg) throws Exception {
        String messageText = msg.getMessage();

        //Если сообщение начинается со слова history - то пробуем пропарсить количество, которое необходимо вывести.
        //В случае ошибки (например не выдержан формат и нам передали не строку вида "history <цифра>" - сообщение просто сохраняется.
        if (messageText.startsWith("history")) {
            int msgsCount = 0;
            try {
                msgsCount = Integer.parseInt(messageText.replace("history ", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (msgsCount > 0) {
                //Возвращаются только сообщения указанного в dto-шке пользователя
                //Сверка имени пользователя в токене и в запросе истории - не выполняется
                //Главное условие - пользователь должен существовать (и иметь сообщения)
                List<String> msgsList = messageService.loadMesageHistoryByName(msg.getName(), msgsCount);
                return new ResponseEntity<>(new CustomResponse("history", msgsList), HttpStatus.OK);
            }
        }

        if (messageService.saveMessage(msg)) {
            return ResponseEntity.ok("Message saved succesfully");
        } else {
            return ResponseEntity.badRequest().body("Message save error");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> delete(@PathVariable("id") long userId) throws Exception {
        if (messageService.deleteUserMessages(userId))
            return ResponseEntity.ok("Messages deleted");
        else
            return ResponseEntity.internalServerError().body("Processing error");
    }
}
