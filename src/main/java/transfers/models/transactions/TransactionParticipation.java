package transfers.models.transactions;

import java.math.BigDecimal;

public class TransactionParticipation {

    private final long id;
    private final BigDecimal deltaAmount;

    TransactionParticipation(final long id,
                             final BigDecimal deltaAmount) {
        this.id = id;
        this.deltaAmount = deltaAmount;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getDeltaAmount() {
        return deltaAmount;
    }

    @Override
    public String toString() {
        return "TransactionParticipation[id = " + id + ", deltaAmount = " + deltaAmount + ']';
    }
}
