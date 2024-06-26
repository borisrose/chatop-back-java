package boris_rentals.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import boris_rentals.api.model.Message;



public interface MessageRepository extends JpaRepository<Message, Integer> {
    
}
