package boris_rentals.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import boris_rentals.api.model.Rental;
import boris_rentals.api.repository.RentalRepository;

@Service
public class RentalService {
    
    private final RentalRepository rentalRepository;

    RentalService(RentalRepository rentalRepository){
        this.rentalRepository = rentalRepository;
    }

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }

    public Rental createRental(Rental newRental){
        return rentalRepository.save(newRental);
    }

    public Optional<Rental> getOneRentalById(String id){
        Integer identifer = Integer.parseInt(id);
        return rentalRepository.findById(identifer);
    }

    public Optional<Rental> deleteRentalById(String id){
        Integer identifier = Integer.parseInt(id);
        return rentalRepository.findById(identifier);
    }

    public String updateRental(final String id, final Rental rental){
        if(rentalRepository.existsById(Integer.parseInt(id))){
            rental.setId(Integer.parseInt(id));
            rentalRepository.save(rental);
            return "rental updated";
        } else {
            return "not existing rental";
        }
    
    }


}
