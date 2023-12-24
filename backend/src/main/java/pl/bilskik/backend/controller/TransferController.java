package pl.bilskik.backend.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.data.dto.TransferDTO;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.service.TransferService;

import java.security.Principal;
import java.util.List;

import static pl.bilskik.backend.controller.mapping.RequestPath.PAYMENT_PATH;
import static pl.bilskik.backend.controller.mapping.RequestPath.TRANSFER_PATH;

@RestController
@RequestMapping(value = TRANSFER_PATH)
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public ResponseEntity<List<TransferDTO>> getTransferHistory(Principal principal) {
        return ResponseEntity.ok(transferService.getTransferHistory(principal.getName()));
    }
    @PostMapping(value = PAYMENT_PATH)
    public ResponseEntity<String> sendTransfer(@RequestBody @Valid TransferDTO transfer) {

        return ResponseEntity.ok(transferService.sendTransfer(transfer));
    }
}
