package boris_rentals.api.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boris_rentals.api.dto.UserDto;
import boris_rentals.api.model.User;
import boris_rentals.api.security.encoder.CustomEncoderService;
import boris_rentals.api.security.token.TokenService;
import boris_rentals.api.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    private UserDto convertToDto(Object user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    // private User convertToEntity(UserDto userDto) {
    //     User user = modelMapper.map(userDto, User.class);
    //     return user;
    // }

    private UserService userService;
    private ModelMapper modelMapper;
    private TokenService tokenService;
    private CustomEncoderService customEncoderService;

    UserController(UserService userService, ModelMapper modelMapper, TokenService tokenService, CustomEncoderService customEncoderService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
        this.customEncoderService = customEncoderService;
        
    }

    // Les middlewares

    @GetMapping(path = "/auth/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader(name = "Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);
        String jwtToken = authorizationHeader.replace("Bearer ", "");
        Jwt jwt = customEncoderService.jwtDecoder().decode(jwtToken);
        String username = jwt.getSubject(); 
        User user = userService.getUserByEmail(username);
        System.out.print(user);
        UserDto userDto = convertToDto(user);
        if(userDto != null){
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    
    @PostMapping("/auth/register")
    public ResponseEntity<ResponseToken> registerUser(@RequestBody RegisterBody body) {

        User newUser = userService.createUser(body.email, body.name, body.password);
        if (newUser != null) {
            String token = tokenService.generateRegisteringToken(newUser.getEmail());
            return ResponseEntity.ok(new ResponseToken(token));
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/auth/login")
    public ResponseEntity<ResponseToken> loginUser(@RequestBody LoginBody body) {

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(body.email,
                body.password);

        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        String token = tokenService.generateToken(authenticationResponse);

        if (token != null) {
            return ResponseEntity.ok(new ResponseToken(token));
        } else {
            return ResponseEntity.status(401).build();
        }

    }


    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable String id) {

        Optional<User> user = userService.getOneUserById(id);
        if(user.isPresent()){
            UserDto userDto = convertToDto(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public record RegisterBody(String email, String password, String name) {
    }

    public record LoginBody(String email, String password) {
    }

    public record ResponseToken(String token){}

}
