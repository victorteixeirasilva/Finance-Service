package tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryJPA extends JpaRepository<Transaction, UUID> {
    @Query(
            "SELECT t FROM Transaction t " +
                    "WHERE t.id = :id " +
                    "AND t.financePlanning.id = :idUser" // Ajuste aqui
    )
    Optional<Transaction> findByIdAndIdUser(
            @Param("id") UUID id,
            @Param("idUser") UUID idUser
    );

    @Query(
            "SELECT t FROM Transaction t WHERE t.financePlanning.id = :idUser AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate "
    )
    List<Transaction> findAllTransactionsInDateRangeWithType(
            @Param("idUser") UUID idUser,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("type") String type
    );
}
