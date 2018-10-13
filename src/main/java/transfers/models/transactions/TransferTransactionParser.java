package transfers.models.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransferTransactionParser {

    private static final int SOURCE_BANK_ACCOUNT_ID_INDEX = 0;
    private static final int DESTINATION_BANK_ACCOUNT_ID_INDEX = 1;
    private static final int TRANSFER_AMOUNT_INDEX = 2;
    private static final int TRANSFERRED_ON_INDEX = 3;
    private static final int TRANSFER_ID_INDEX = 4;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final List<List<String>> statements;

    public TransferTransactionParser(final List<List<String>> statements) {
        this.statements = statements;
    }

    public Set<TransferTransaction> parse() {
        return statements.stream().map(values -> {
            final BigDecimal transferAmount = getTransferAmount(values);
            return new TransferTransaction(getTransferId(values),
                    new TransactionParticipation(getSourceBankAccountId(values), transferAmount.negate()),
                    new TransactionParticipation(getDestinationBankAccountId(values), transferAmount),
                    transferAmount,
                    getTransferredOn(values));
        }).collect(Collectors.toSet());
    }

    private Long getSourceBankAccountId(final List<String> values) {
        return Long.valueOf(values.get(SOURCE_BANK_ACCOUNT_ID_INDEX));
    }

    private Long getDestinationBankAccountId(final List<String> values) {
        return Long.valueOf(values.get(DESTINATION_BANK_ACCOUNT_ID_INDEX));
    }

    private BigDecimal getTransferAmount(final List<String> values) {
        return BigDecimal.valueOf(Double.valueOf(values.get(TRANSFER_AMOUNT_INDEX)));
    }

    private LocalDate getTransferredOn(final List<String> values) {
        return LocalDate.parse(values.get(TRANSFERRED_ON_INDEX), DATE_TIME_FORMATTER);
    }

    private Long getTransferId(final List<String> statement) {
        return Long.valueOf(statement.get(TRANSFER_ID_INDEX));
    }
}
