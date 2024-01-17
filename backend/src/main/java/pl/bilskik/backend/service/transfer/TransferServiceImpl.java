package pl.bilskik.backend.service.transfer;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.dto.TransferDTO;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.TransferRepository;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.TransferService;
import pl.bilskik.backend.service.exception.UserException;
import pl.bilskik.backend.service.exception.UsernameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TransferServiceImpl(
            TransferRepository transferRepository,
            ModelMapper modelMapper,
            UserRepository userRepository
    ) {
        this.transferRepository = transferRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<TransferDTO> getTransferHistory(String username) {
        if(username == null) {
            throw new UsernameException("Error! Current logged user not found!");
        }
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserException("User not found!");
        }
        return mapToTransferDTO(user.get());
    }

    private List<TransferDTO> mapToTransferDTO(Users user) {
        List<Transfer> transferList = user.getTransferList();
        List<TransferDTO> transferDTOList = new ArrayList<>();
        for(var transfer : transferList) {
            TransferDTO currTransfer = modelMapper.map(transfer, TransferDTO.class);
            transferDTOList.add(currTransfer);
        }
        return transferDTOList;
    }

    @Override
    @Transactional
    public String sendTransfer(TransferDTO transferDTO) {
        Optional<Users> senderUser = userRepository.findByAccountNo(transferDTO.getSenderAccNo());
        Optional<Users> receiverUser = userRepository.findByAccountNo(transferDTO.getReceiverAccNo());
        if(senderUser.isEmpty()) {
            throw new UserException("Invalid senderUser!");
        }
        if(receiverUser.isEmpty()) {
            throw new UserException("Invalid receiverUser!");
        }
        Transfer transfer = mapToTransferObj(transferDTO, senderUser.get(), receiverUser.get());
        transferRepository.save(transfer);
        return "Completed!";
    }

    private Transfer mapToTransferObj(TransferDTO transferDTO,
                                      Users senderUser,
                                      Users receiverUser) {

        Transfer transfer = modelMapper.map(transferDTO, Transfer.class);
        transfer.setUser(List.of(senderUser, receiverUser));

        updateBalance(transfer.getAmount(), senderUser, receiverUser);

        senderUser.addTransfer(transfer);
        receiverUser.addTransfer(transfer);

        return transfer;
    }

    private void updateBalance(long balance,
                               Users senderUser,
                               Users receiverUser) {
        long currSenderUserBalance = senderUser.getBalance();
        long currReceiverUserBalance = receiverUser.getBalance();
        if(currSenderUserBalance < balance) {
            throw new UserException("Cannot perform transfer -> sender doesnt have enoguh money!");
        }
        senderUser.setBalance(currSenderUserBalance - balance);
        receiverUser.setBalance(currReceiverUserBalance + balance);
    }
}
