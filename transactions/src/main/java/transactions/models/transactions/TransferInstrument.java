package transactions.models.transactions;

public class TransferInstrument {

    private final String reference;

    TransferInstrument(final String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public String toString() {
        return "TransferInstrument[reference = " + reference + ']';
    }
}
