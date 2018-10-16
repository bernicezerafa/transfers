package transfers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import transfers.exceptions.BadRequestException;
import transfers.models.accounts.BankAccount;
import transfers.models.transactions.Transfer;
import transfers.service.TransferRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountsController {

    private final TransferRepository transferRepository;

    @Autowired
    BankAccountsController(final TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @GetMapping
    public Set<BankAccount> getBankAccounts(@RequestParam(required = false) final boolean highestBalance,
                                            @RequestParam(required = false) final boolean mostFrequentlyDebited) {
        final List<Transfer> transfers = transferRepository.findAll();
        final Set<BankAccount> bankAccounts = getBankAccountsWithBalance(transfers);

        if (highestBalance && mostFrequentlyDebited) {
            throw new BadRequestException("Filters highestBalance and mostFrequentlyDebited cannot be used together!");
        }

        if (highestBalance) {
            return Collections.singleton(getBankAccountWithHighestBalance(bankAccounts));
        } else if (mostFrequentlyDebited) {
            final String frequentlyUsedSourceAccountId = getMostFrequentlyDebitedBankAccount(transfers);
            return Collections.singleton(bankAccounts
                    .stream()
                    .filter(b -> b.getReference().equals(frequentlyUsedSourceAccountId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Most frequently debited Bank Account not found in all bank accounts list.")));
        }

        return bankAccounts;
    }

    private static String getMostFrequentlyDebitedBankAccount(final List<Transfer> transfers) {
        return transfers
                .stream()
                .map(Transfer::getSource)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException(""));
    }

    private static BankAccount getBankAccountWithHighestBalance(final Set<BankAccount> bankAccounts) {
        return Collections.max(bankAccounts, Comparator.comparing(BankAccount::getAmount));
    }

    private static Set<BankAccount> getBankAccountsWithBalance(final List<Transfer> transfers) {
        return getFundMovements(transfers)
                .entrySet()
                .stream()
                .map(e -> new BankAccount(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }

    private static Map<String, BigDecimal> getFundMovements(final List<Transfer> transfers) {
        return Stream.concat(getSourceNegations(transfers).entrySet().stream(), getDestinationAdditions(transfers).entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, BigDecimal::add));
    }

    private static Map<String, BigDecimal> getDestinationAdditions(final List<Transfer> transfers) {
        return transfers.stream().collect(Collectors.toMap(Transfer::getDestination, Transfer::getTransferAmount));
    }

    private static Map<String, BigDecimal> getSourceNegations(final List<Transfer> transfers) {
        return transfers.stream().collect(Collectors.toMap(Transfer::getSource, t -> t.getTransferAmount().negate()));
    }
}
