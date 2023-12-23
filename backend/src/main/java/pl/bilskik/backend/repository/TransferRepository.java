package pl.bilskik.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bilskik.backend.entity.Transfer;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    @Query("SELECT t FROM Users u JOIN u.transferList t WHERE u.username = ?1")
    List<Transfer> findAllUsersTransfersByUsername(String username);
}
