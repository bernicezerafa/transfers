package transfers.models.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferTransaction {

    private final long id;
    private final TransactionParticipation source;
    private final TransactionParticipation destination;
    private final BigDecimal transferAmount;
    private final LocalDate transferredOn;

    TransferTransaction(final long id,
                        final TransactionParticipation source,
                        final TransactionParticipation destination,
                        final BigDecimal transferAmount,
                        final LocalDate transferredOn) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.transferAmount = transferAmount;
        this.transferredOn = transferredOn;
    }

    public long getId() {
        return id;
    }

    public TransactionParticipation getSource() {
        return source;
    }

    public TransactionParticipation getDestination() {
        return destination;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public LocalDate getTransferredOn() {
        return transferredOn;
    }

    @Override
    public String toString() {
        return "\nTransferTransaction[" +
                "\nid = " + id +
                ",\nsource = " + source +
                ",\ndestination = " + destination +
                ",\ntransferAmount = " + transferAmount +
                ",\ntransferredOn = " + transferredOn +
                ']';
    }
}
