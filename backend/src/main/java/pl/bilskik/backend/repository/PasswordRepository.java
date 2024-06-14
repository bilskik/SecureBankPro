package pl.bilskik.backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Integer> {
    List<Password> findPasswordByUser(Users users);

    @Transactional
    @Modifying
    @Query("DELETE FROM Password p WHERE p.user = ?1")
    void deleteAllByUserId(Users user);
}
