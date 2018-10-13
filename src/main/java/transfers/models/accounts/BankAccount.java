package transfers.models.accounts;

import java.math.BigDecimal;

public class BankAccount {

    private final long id;
    private final BigDecimal amount;

    public BankAccount(final long id,
                       final BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return id + " - " + amount;
    }
}
