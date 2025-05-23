package tech.inovasoft.inevolving.ms.finance.success;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;
import tech.inovasoft.inevolving.ms.finance.repository.implementation.TransactionRepositoryImplementation;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa.TransactionRepositoryJPA;

import java.sql.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositorySuccessTest {

    @Mock
    private TransactionRepositoryJPA transactionRepositoryJPA;

    @InjectMocks
    private TransactionRepositoryImplementation transactionRepositoryImplementation;

    @Test
    public void saveTransaction() {
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

}
