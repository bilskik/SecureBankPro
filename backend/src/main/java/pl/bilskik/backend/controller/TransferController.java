package pl.bilskik.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bilskik.backend.entity.Transfer;
import pl.bilskik.backend.service.TransferService;

import java.security.Principal;

import static pl.bilskik.backend.controller.mapping.RequestPath.PAYMENT_PATH;
import static pl.bilskik.backend.controller.mapping.RequestPath.TRANSFER_PATH;

@RestController
@RequestMapping(value = TRANSFER_PATH)
@Slf4j
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping()
    public ResponseEntity<String> getTransferHistory(Principal principal) {
        transferService.getTransferHistory(principal.getName());
        return ResponseEntity.ok("Ok");
    }
    @PostMapping(value = PAYMENT_PATH)
    public ResponseEntity<String> sendTransfer() {
        return ResponseEntity.ok("OK");
    }
}
