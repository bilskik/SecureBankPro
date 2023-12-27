package pl.bilskik.backend.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.data.dto.TransferDTO;
import pl.bilskik.backend.data.response.ResponseMessage;
import pl.bilskik.backend.service.TransferService;

import java.security.Principal;
import java.util.List;

import static pl.bilskik.backend.controller.mapping.UrlMapping.PAYMENT_PATH;
import static pl.bilskik.backend.controller.mapping.UrlMapping.TRANSFER_PATH;

@RestController
@RequestMapping(value = TRANSFER_PATH)
@CrossOrigin
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
    public ResponseEntity<ResponseMessage> sendTransfer(
            @RequestBody @Valid TransferDTO transfer
    ) {
        return new ResponseEntity(
                new ResponseMessage(transferService.sendTransfer(transfer)),
                HttpStatus.CREATED
        );
    }
}
