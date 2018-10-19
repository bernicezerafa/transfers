package transactions.models.transactions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "transferredOn" }, allowGetters = true, ignoreUnknown = true)
public class Transfer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(nullable = false)
    private String source;

    @NotBlank
    @Column(nullable = false)
    private String destination;

    @DecimalMax("1000.00")
    @Column(nullable = false)
    private BigDecimal transferAmount;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date transferredOn;

    private Transfer() {
        // Required for Hibernate
    }

    Transfer(final long id,
             final String source,
             final String destination,
             final BigDecimal transferAmount,
             final Date transferredOn) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.transferAmount = transferAmount;
        this.transferredOn = transferredOn;
    }

    public long getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public Date getTransferredOn() {
        return transferredOn;
    }

    @Override
    public String toString() {
        return "\nTransfer[" +
                "\nid = " + id +
                ",\nsource = " + source +
                ",\ndestination = " + destination +
                ",\ntransferAmount = " + transferAmount +
                ",\ntransferredOn = " + transferredOn +
                ']';
    }
}
