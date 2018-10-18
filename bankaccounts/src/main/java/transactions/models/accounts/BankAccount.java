package transactions.models.accounts;

import java.math.BigDecimal;

public class BankAccount {

    private final String reference;
    private final BigDecimal amount;

    public BankAccount(final String reference,
                       final BigDecimal amount) {
        this.reference = reference;
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return reference + " - " + amount;
    }
}
