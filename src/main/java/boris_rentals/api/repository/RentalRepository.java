package boris_rentals.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boris_rentals.api.model.Rental;



public interface RentalRepository extends JpaRepository<Rental, Integer> {
    
}
