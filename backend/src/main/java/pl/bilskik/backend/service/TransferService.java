package pl.bilskik.backend.service;

import pl.bilskik.backend.data.dto.TransferDTO;

public interface TransferService {
    TransferDTO getTransferHistory(String username);
}
