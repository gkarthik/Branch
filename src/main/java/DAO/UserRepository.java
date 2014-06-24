package DAO;
import DAO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import Tests.Test;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);


	
}