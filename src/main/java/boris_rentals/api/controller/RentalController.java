package boris_rentals.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import boris_rentals.api.dto.RentalDto;
import boris_rentals.api.model.Rental;
import boris_rentals.api.model.User;
import boris_rentals.api.security.encoder.CustomEncoderService;
import boris_rentals.api.security.token.TokenService;
import boris_rentals.api.services.RentalService;
import boris_rentals.api.services.UserService;
import boris_rentals.api.storage.StorageService;

import org.springframework.http.ResponseEntity;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    
    private RentalDto convertToDto(Object rental) {
        RentalDto rentalDto = modelMapper.map(rental, RentalDto.class);
        return rentalDto;
    }

    private Rental convertToEntity(RentalDto rentalDto) {
        Rental rental = modelMapper.map(rentalDto, Rental.class);
        return rental;
    }


    private final RentalService rentalService;

    private final ModelMapper modelMapper;
    private final TokenService tokenService;
    private final CustomEncoderService customEncoderService;
    private final UserService userService;
    private final StorageService storageService;
    
    RentalController(RentalService rentalService, ModelMapper modelMapper, TokenService tokenService,  CustomEncoderService customEncoderService, UserService userService, StorageService storageService){
        this.rentalService = rentalService;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
        this.customEncoderService = customEncoderService;  
        this.userService = userService;
        this.storageService = storageService;

    }

    @GetMapping("")
    public ResponseEntity<RentalsResponse> getAllRentals() {

        List<Rental> rentals = rentalService.getAllRentals();
  
        if(rentals != null){
            List<RentalDto> rentalDtos = rentals.stream().map(this::convertToDto).collect(Collectors.toList());
            return ResponseEntity.ok(new RentalsResponse(rentalDtos));
        } else {
            return ResponseEntity.status(404).build();
        }
     
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getOneRentalById(@PathVariable String id) {
        Optional<Rental> rental = rentalService.getOneRentalById(id);
        if(rental != null){
            RentalDto rentalDto = convertToDto(rental);
            return ResponseEntity.ok(rentalDto);
        } else {
            return ResponseEntity.status(401).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<RentalMessage> updateRental(@PathVariable String id, MultipartHttpServletRequest request) {

        String token = request.getHeader("Authorization").replace("Bearer", "");
        Jwt jwt = customEncoderService.jwtDecoder().decode(token);
        String email = jwt.getSubject();
        User user =  userService.getUserByEmail(email);

        Rental newRental = new Rental();

        String description = request.getParameter("description");
        Float surface =  Float.parseFloat(request.getParameter("surface"));
        Float price = Float.parseFloat(request.getParameter("price"));
        String name = request.getParameter("name");

        newRental.setDescription(description);
        newRental.setName(name);
        newRental.setSurface(surface);
        newRental.setPrice(price);
        newRental.setOwner_id(user.getId());


        String message = rentalService.updateRental(id, newRental);
        if(message == "rental updated"){
            return ResponseEntity.ok(new RentalMessage(message));
        } else {
            return ResponseEntity.status(401).build();
        }
    }
    

    @PostMapping("")
    public ResponseEntity<RentalMessage> createRental(MultipartHttpServletRequest request) {
        
        
        String token = request.getHeader("Authorization").replace("Bearer", "");
        Jwt jwt = customEncoderService.jwtDecoder().decode(token);
        String email = jwt.getSubject();
        User user =  userService.getUserByEmail(email);

        Rental newRental = new Rental();
        String description = request.getParameter("description");
        Float surface =  Float.parseFloat(request.getParameter("surface"));
        Float price = Float.parseFloat(request.getParameter("price"));
        String name = request.getParameter("name");
        newRental.setDescription(description);
        newRental.setName(name);
        newRental.setSurface(surface);
        newRental.setPrice(price);
        newRental.setOwner_id(user.getId());
   

        MultipartFile file = request.getFile("picture");
        if (file != null && !file.isEmpty()) {
            String fileName = "http://localhost:3001/" + file.getOriginalFilename();
            storageService.store(file);
            newRental.setPicture(fileName);
        }

      
        rentalService.createRental(newRental);
        return ResponseEntity.ok(new RentalMessage("Rental created !"));
        
    }


    public record CreateRentalRequest(String name, String surface, String price, MultipartFile file, String description) {
    }

    public record RentalMessage(String message){}
    public record RentalsResponse(List<RentalDto> rentals){

    }
    
}
