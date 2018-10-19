package transactions.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import transactions.models.transactions.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

}
