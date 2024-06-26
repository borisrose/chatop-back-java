package boris_rentals.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boris_rentals.api.model.User;
import boris_rentals.api.repository.UserRepository;
import boris_rentals.api.security.encoder.CustomEncoderService;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final CustomEncoderService customEncoderService;

    UserService(UserRepository userRepository, CustomEncoderService customEncoderService){
        this.userRepository = userRepository;
        this.customEncoderService = customEncoderService;
    }

    public User createUser(String email, String name, String password){
        User newUser = new User(name, email, password);
        String visiblePassword = newUser.getPassword();
		newUser.setPassword(customEncoderService.passwordEncoder().encode(visiblePassword));
		User savedUser = userRepository.save(newUser);
        return userRepository.save(savedUser);
        
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getOneUserById(String id){
        Integer identifer = Integer.parseInt(id);
        return userRepository.findById(identifer);
    }

    public Optional<User> deleteUserById(String id){
        Integer identifier = Integer.parseInt(id);
        return userRepository.findById(identifier);
    }
}
