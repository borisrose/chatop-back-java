package boris_rentals.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boris_rentals.api.model.Message;
import boris_rentals.api.repository.MessageRepository;


@Service
public class MessageService {
    
    private final MessageRepository messageRepository;

    MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;

    }

    public Message createMessage(Message message){
        return this.messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Optional<Message> getOneMessageById(String id){
        Integer identifer = Integer.parseInt(id);
        return messageRepository.findById(identifer);
    }

    public Optional<Message> deleteMessageById(String id){
        Integer identifier = Integer.parseInt(id);
        return messageRepository.findById(identifier);
    }
}
