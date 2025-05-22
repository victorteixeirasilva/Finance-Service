package tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.util.UUID;

public interface TransactionRepositoryJPA extends JpaRepository<Transaction, UUID> {
}
