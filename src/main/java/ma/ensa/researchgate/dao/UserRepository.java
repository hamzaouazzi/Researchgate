package ma.ensa.researchgate.dao;

import ma.ensa.researchgate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    //public User findByEmail(String email);
    public Optional<User> findByEmail(String email);
    public Optional<User> findById(long userid);
}
