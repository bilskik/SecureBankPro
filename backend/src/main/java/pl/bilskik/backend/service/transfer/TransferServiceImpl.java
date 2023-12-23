package pl.bilskik.backend.service.transfer;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pl.bilskik.backend.data.dto.TransferDTO;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.TransferRepository;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.TransferService;
import pl.bilskik.backend.service.auth.exception.UserException;
import pl.bilskik.backend.service.auth.exception.UsernameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
            throw new UsernameException("Error! Principal is null!");
        }
        List<Transfer> transferList = transferRepository.findAllUsersTransfersByUsername(username);
        if(transferList == null || transferList.isEmpty()) {
            return null;
        }
        return mapTransferToDTO(transferList);
    }

    @Override
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
        return "GIT";
    }

    private List<TransferDTO> mapTransferToDTO(List<Transfer> transferList) {
        List<TransferDTO> transferDTOList = new ArrayList<>();
        for(var transfer : transferList) {
            transferDTOList.add(modelMapper.map(transfer, TransferDTO.class));
        }
        return transferDTOList;
    }

    private Transfer mapToTransferObj(TransferDTO transferDTO,
                                      Users senderUser,
                                      Users receiverUser) {
        Transfer transfer = modelMapper.map(transferDTO, Transfer.class);
        transfer.setUser(List.of(senderUser, receiverUser));
        return transfer;
    }
}
