package pl.bilskik.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;

import java.util.List;
import java.util.Optional;

public interface PasswordRepository extends JpaRepository<Password, Integer> {
    List<Password> findPasswordByUser(Users users);
}
