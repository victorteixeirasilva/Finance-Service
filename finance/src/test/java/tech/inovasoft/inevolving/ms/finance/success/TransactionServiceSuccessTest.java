package tech.inovasoft.inevolving.ms.finance.success;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;
import tech.inovasoft.inevolving.ms.finance.service.TransactionService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceSuccessTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private FinancePlanningRepository planningRepository;

    @InjectMocks
    private TransactionService service;

    @Test
    public void addTransaction() throws NotFoundFinancePlanning, DataBaseException {
        //Given
        UUID idUser = UUID.randomUUID();
        RequestTransactionDTO requestDTO = new RequestTransactionDTO(
                idUser,
                LocalDate.now(),
                "description",
                100.0
        );
        String type = Type.INVESTMENT;

        var planning = new FinancePlanning(
                idUser,
                4500.0
        );

        var newTransaction = new Transaction(planning, requestDTO, type);

        var expectedTransaction = new Transaction(
                UUID.randomUUID(),
                planning,
                type,
                Date.valueOf(requestDTO.date()),
                "description",
                100.0
        );

        //When
        when(planningRepository.findById(idUser)).thenReturn(new FinancePlanning(idUser, 4500.0));
        when(repository.saveTransaction(newTransaction)).thenReturn(expectedTransaction);
        var result = service.addTransaction(idUser, requestDTO, type);

        //Then
        assertNotNull(result);
        assertEquals(expectedTransaction.getId(), result.id());
        assertEquals(expectedTransaction.getFinancePlanning().getIdUser(), result.idUser());
        assertEquals(expectedTransaction.getType(), result.type());
        assertEquals(expectedTransaction.getTransactionDate(), Date.valueOf(result.date()));
        assertEquals(expectedTransaction.getDescriptionTransaction(), result.description());
        assertEquals(expectedTransaction.getAmount(), result.value());

        verify(planningRepository, times(1)).findById(idUser);
        verify(repository, times(1)).saveTransaction(newTransaction);

    }

    @Test
    public void deleteTransaction() throws NotFoundTransactionException, DataBaseException {
        //Given
        var idTransaction = UUID.randomUUID();
        var idUser = UUID.randomUUID();

        when(repository.findByIdAndIdUser(any(), any()))
                .thenReturn(
                        new Transaction(
                                idTransaction,
                                new FinancePlanning(idUser, 4500.0),
                                "type",
                                Date.valueOf(LocalDate.now()),
                                "description",
                                100.0
                        )
                );
        //When
        when(repository.deleteTransaction(idTransaction)).thenReturn(new ResponseMessageDTO("Transaction deleted"));
        var result = service.deleteTransaction(idTransaction, idUser);

        //Then
        assertNotNull(result);
        assertEquals("Transaction deleted", result.message());
    }

    @Test
    public void getTransaction() throws NotFoundTransactionException, DataBaseException {
        //Given
        var idTransaction = UUID.randomUUID();
        var idUser = UUID.randomUUID();

        var expectedTransaction = new Transaction(
                idTransaction,
                new FinancePlanning(idUser, 4500.0),
                "type",
                Date.valueOf(LocalDate.now()),
                "description",
                100.0
        );

        //When
        when(repository.findByIdAndIdUser(any(), any())).thenReturn(expectedTransaction);
        var result = service.getTransaction(idTransaction, idUser);


        //Then
        assertNotNull(result);
        assertEquals(expectedTransaction.getId(), result.id());
        assertEquals(expectedTransaction.getFinancePlanning().getIdUser(), result.idUser());
        assertEquals(expectedTransaction.getType(), result.type());
    }

}
