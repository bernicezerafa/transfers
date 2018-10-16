package transfers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import transfers.exceptions.BadRequestException;
import transfers.exceptions.NotFoundException;
import transfers.models.accounts.BankAccount;
import transfers.models.transactions.Transfer;
import transfers.models.transactions.TransferInstrument;
import transfers.service.TransferRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
