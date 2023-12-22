package pl.bilskik.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bilskik.backend.entity.Transfer;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    List<Transfer> findAllBySenderUsername();
}
