package pl.bilskik.backend.service;

import pl.bilskik.backend.data.dto.TransferDTO;

import java.util.List;

public interface TransferService {
    List<TransferDTO> getTransferHistory(String username);

    String sendTransfer(TransferDTO transfer);
}
