package transfers.models.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public Set<Transfer> parse() {
        return statements.stream().map(values -> {
            final BigDecimal transferAmount = getTransferAmount(values);
            return new Transfer(getTransferId(values),
                    getSourceBankAccountReference(values),
                    getDestinationBankAccountReference(values),
                    transferAmount,
                    new Date());
        }).collect(Collectors.toSet());
    }

    private String getSourceBankAccountReference(final List<String> values) {
        return values.get(SOURCE_BANK_ACCOUNT_ID_INDEX);
    }

    private String getDestinationBankAccountReference(final List<String> values) {
        return values.get(DESTINATION_BANK_ACCOUNT_ID_INDEX);
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
