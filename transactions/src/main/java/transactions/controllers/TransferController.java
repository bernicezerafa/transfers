package transactions.controllers;

import transactions.exceptions.NotFoundException;
import transactions.models.transactions.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import transactions.service.TransferRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferRepository transferRepository;

    @Autowired
    TransferController(final TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @PostMapping
    public Transfer submitTransfer(@Valid @RequestBody final Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @GetMapping
    public List<Transfer> getTransferTransactions() {
        return transferRepository.findAll();
    }

    @GetMapping("/{id}")
    public Transfer getTransferById(@PathVariable(value = "id") final long transferId) {
        return transferRepository.findById(transferId).orElseThrow(() -> new NotFoundException("Transfer", "id", transferId));
    }
}
