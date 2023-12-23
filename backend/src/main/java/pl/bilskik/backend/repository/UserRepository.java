package pl.bilskik.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bilskik.backend.entity.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByAccountNo(String accountNo);

    Optional<Users> findByUsername(String username);

    @Query("SELECT p.ranges FROM Users u JOIN u.passwordList p WHERE u.username = ?1")
    List<String> findPasswordRangeByUsername(String username);

}
