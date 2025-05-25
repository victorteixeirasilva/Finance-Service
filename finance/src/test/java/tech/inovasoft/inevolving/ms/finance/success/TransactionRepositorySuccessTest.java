package tech.inovasoft.inevolving.ms.finance.success;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;
import tech.inovasoft.inevolving.ms.finance.repository.implementation.TransactionRepositoryImplementation;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa.TransactionRepositoryJPA;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositorySuccessTest {

    @Mock
    private TransactionRepositoryJPA transactionRepositoryJPA;

    @InjectMocks
    private TransactionRepositoryImplementation transactionRepositoryImplementation;

    @Test
    public void saveTransaction() throws DataBaseException {
        //Given
        UUID id = UUID.randomUUID();
        FinancePlanning financePlanning = new FinancePlanning(UUID.randomUUID(), 1000.0);
        String type = Type.COST_OF_LIVING;
        Date date =Date.valueOf("2025-05-23");
        String description = "descriptionTransaction";
        Double amount = 100.0;
        var expectedTransaction = new Transaction(
                id,
                financePlanning,
                type,
                date,
                description,
                amount
        );

        //When
        when(transactionRepositoryJPA.save(expectedTransaction)).thenReturn(expectedTransaction);
        var result = transactionRepositoryImplementation.saveTransaction(expectedTransaction);

        //Then
        assertNotNull(result);
        assertEquals(expectedTransaction.getId(), result.getId());
        assertEquals(expectedTransaction.getDescriptionTransaction(), result.getDescriptionTransaction());
        assertEquals(expectedTransaction.getType(), result.getType());
        assertEquals(expectedTransaction.getAmount(), result.getAmount());
        assertEquals(expectedTransaction.getTransactionDate(), result.getTransactionDate());
        assertEquals(expectedTransaction.getFinancePlanning().getIdUser(), result.getFinancePlanning().getIdUser());
        assertEquals(expectedTransaction.getFinancePlanning().getWage(), result.getFinancePlanning().getWage());

        verify(transactionRepositoryJPA).save(expectedTransaction);
    }

    @Test
    public void findByIdAndIdUser() throws NotFoundTransactionException, DataBaseException {
        //Given
        var id = UUID.randomUUID();
        var idUser = UUID.randomUUID();

        var transaction = new Transaction();
        transaction.setId(id);
        transaction.setFinancePlanning(new FinancePlanning(idUser, 1000.0));


        //When
        when(transactionRepositoryJPA.findByIdAndIdUser(id, idUser)).thenReturn(Optional.of(transaction));
        var result = transactionRepositoryImplementation.findByIdAndIdUser(id, idUser);

        //Then
        assertNotNull(result);
        assertEquals(transaction.getId(), result.getId());
        assertEquals(transaction.getFinancePlanning().getIdUser(), result.getFinancePlanning().getIdUser());

        verify(transactionRepositoryJPA).findByIdAndIdUser(id, idUser);
    }

    @Test
    public void deleteTransaction() throws DataBaseException {
        //Given
        var id = UUID.randomUUID();

        //When
        doNothing().when(transactionRepositoryJPA).deleteById(id);
        var result = transactionRepositoryImplementation.deleteTransaction(id);

        //Then
        assertNotNull(result);
        assertEquals("Transaction deleted successfully", result.message());

        verify(transactionRepositoryJPA).deleteById(id);

    }

    @Test
    public void findAllTransactionsInDateRangeWithType() throws NotFoundTransactionException, DataBaseException {
        //Given
        var idUser = UUID.randomUUID();
        LocalDate startDate = Date.valueOf("2025-05-01").toLocalDate();
        LocalDate endDate = Date.valueOf("2025-05-31").toLocalDate();
        String type = Type.COST_OF_LIVING;
        List<Transaction> transactions = List.of(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, 1000.0),
                type,
                Date.valueOf("2025-05-23"),
                "descriptionTransaction",
                100.0
        ));

        //When
        when(transactionRepositoryJPA
                .findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, type))
                .thenReturn(transactions);
        var result = transactionRepositoryImplementation
                .findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, type);

        //Then
        assertNotNull(result);
        assertEquals(transactions.getFirst().getType(), result.getFirst().getType());

        verify(transactionRepositoryJPA).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, type);

    }

}
