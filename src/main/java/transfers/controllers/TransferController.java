package transfers.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import transfers.exceptions.BadRequestException;
import transfers.models.accounts.BankAccount;
import transfers.files.CsvReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import transfers.models.transactions.TransactionParticipation;
import transfers.models.transactions.TransferTransaction;
import transfers.models.transactions.TransferTransactionParser;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

@RestController
public class TransferController {

    private static final int NO_BALANCE_BANK_ACCOUNT_ID = 0;

    @GetMapping("/transactions")
    public Set<TransferTransaction> getTransferTransactions() {
        final CsvReader csvReader = new CsvReader("/statement.csv");
        final List<List<String>> statements = csvReader.readCsvWithHeader();
        return new TransferTransactionParser(statements).parse();
    }

    @RequestMapping("/bank-accounts")
    public Set<BankAccount> getBankAccounts(@RequestParam(required = false) final boolean highestBalance,
                                            @RequestParam(required = false) final boolean mostFrequentlyDebited) {
        final Set<TransferTransaction> transferTransactions = getTransferTransactions();
        final Set<BankAccount> bankAccounts = getBankAccountsWithBalance(transferTransactions);

        if (highestBalance && mostFrequentlyDebited) {
            throw new BadRequestException("Filters highestBalance and mostFrequentlyDebited cannot be used together!");
        }

        if (highestBalance) {
            return Collections.singleton(getBankAccountWithHighestBalance(bankAccounts));
        } else if (mostFrequentlyDebited) {
            final Long frequentlyUsedSourceAccountId = getMostFrequentlyDebitedBankAccount(transferTransactions);
            return Collections.singleton(bankAccounts
                    .stream()
                    .filter(b -> b.getId() == frequentlyUsedSourceAccountId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Most frequently debited Bank Account not found in all bank accounts list.")));
        }

        return bankAccounts;
    }

    private static long getMostFrequentlyDebitedBankAccount(final Set<TransferTransaction> transferTransactions) {
        return transferTransactions
                .stream()
                .map(t -> t.getSource().getId())
                .filter(id -> id != NO_BALANCE_BANK_ACCOUNT_ID)
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

    private static Set<BankAccount> getBankAccountsWithBalance(final Set<TransferTransaction> transferTransactions) {
        return getAllTransferParticipations(transferTransactions)
                .stream()
                .filter(t -> t.getId() != NO_BALANCE_BANK_ACCOUNT_ID)
                .collect(groupingBy(TransactionParticipation::getId, reducing(BigDecimal.ZERO, TransactionParticipation::getDeltaAmount, BigDecimal::add)))
                .entrySet()
                .stream()
                .map(e -> new BankAccount(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }

    private static Set<TransactionParticipation> getAllTransferParticipations(final Set<TransferTransaction> transferTransactions) {
        return Stream.concat(transferTransactions.stream()
                .map(TransferTransaction::getSource), transferTransactions.stream().map(TransferTransaction::getDestination))
                .collect(Collectors.toSet());
    }
}
