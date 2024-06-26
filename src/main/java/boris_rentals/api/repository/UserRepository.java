package boris_rentals.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boris_rentals.api.model.User;




@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
