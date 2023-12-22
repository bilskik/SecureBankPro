package pl.bilskik.backend.service.transfer;

import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.dto.TransferDTO;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.repository.TransferRepository;
import pl.bilskik.backend.service.TransferService;
import pl.bilskik.backend.service.auth.exception.UsernameException;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }
    @Override
    public TransferDTO getTransferHistory(String username) {
        if(username == null) {
            throw new UsernameException("Error! Principal is null!");
        }
        List<Transfer> transferList = transferRepository.findAllBySenderUsername();

        return null;
    }
}
