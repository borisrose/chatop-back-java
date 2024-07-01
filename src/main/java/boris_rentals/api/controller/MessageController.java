package boris_rentals.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris_rentals.api.model.Message;
import boris_rentals.api.services.MessageService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/messages")
public class MessageController {


    private final MessageService messageService;

    private final ModelMapper modelMapper;


    MessageController(MessageService messageService, ModelMapper modelMapper){
        this.messageService = messageService;
        this.modelMapper = modelMapper;
    }

    // Les middlewares 
    @PostMapping("")
    public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest body) {

        Message newMessage = new Message();
        newMessage.setUser_id(body.user_id);
        newMessage.setRental_id(body.rental_id);
        newMessage.setMessage(body.message);
        this.messageService.createMessage(newMessage);
        
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
    
    

    public record MessageResponse(String message){

    }

    public record MessageRequest(String message, Integer user_id, Integer rental_id){}

}
