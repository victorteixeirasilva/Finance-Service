package tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryJPA extends JpaRepository<Transaction, UUID> {
    @Query("") // TODO: Criar query JPQL
    Optional<Transaction> findByIdAndIdUser(UUID id, UUID idUser);

    @Query("") // TODO: Criar query JPQL
    List<Transaction> findAllTransactionsInDateRangeWithType(UUID idUser, LocalDate startDate, LocalDate endDate, String type);
}
