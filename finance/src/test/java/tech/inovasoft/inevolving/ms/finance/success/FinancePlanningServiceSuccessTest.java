package tech.inovasoft.inevolving.ms.finance.success;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;
import tech.inovasoft.inevolving.ms.finance.service.FinancePlanningService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FinancePlanningServiceSuccessTest {

    @Mock
    private FinancePlanningRepository repository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private FinancePlanningService service;

    @Test
    public void addPlanningWhenRegistering(){
        //Given
        var idUser = UUID.randomUUID();
        var expectedFinancePlanning = new FinancePlanning();
        expectedFinancePlanning.setIdUser(idUser);
        expectedFinancePlanning.setWage(0.0);

        //When
        when(repository.savePlanningForUser(idUser)).thenReturn(expectedFinancePlanning);
        var result = service.addPlanningWhenRegistering(idUser);

        //Then
        assertNotNull(result);
        assertEquals(result.getIdUser(), idUser);
        assertEquals(0.0, result.getWage());

        verify(repository).savePlanningForUser(idUser);
    }

    @Test
    public void updateWage() throws NotFoundFinancePlanning, DataBaseException {
        //Given
        var idUser = UUID.randomUUID();
        var expectedFinancePlanning = new FinancePlanning();
        expectedFinancePlanning.setIdUser(idUser);
        expectedFinancePlanning.setWage(4300.0);

        var oldFinancePlanning = new FinancePlanning();
        oldFinancePlanning.setIdUser(idUser);
        oldFinancePlanning.setWage(0.0);

        //When
        when(repository.findById(idUser)).thenReturn(oldFinancePlanning);
        when(repository.savePlanning(any())).thenReturn(expectedFinancePlanning);
        var result = service.updateWage(idUser, 4300.0);

        //Then
        assertNotNull(result);
        assertEquals(result.wage(), expectedFinancePlanning.getWage());

        verify(repository, times(1)).findById(idUser);
        verify(repository, times(1)).savePlanning(any());
    }

    @Test
    public void getInfosFinanceInDateRange() throws NotFoundFinancePlanning, DataBaseException {
        //Given
        var idUser = UUID.randomUUID();
        LocalDate startDate = Date.valueOf("2025-05-01").toLocalDate();
        LocalDate endDate = Date.valueOf("2025-05-31").toLocalDate();
        Double wage = 1300.0;
        Double totalBalance = 1000.0;
        Double availableCostOfLivingBalance = 870.0;
        Double balanceAvailableToInvest = 130.0;
        Double extraBalanceAdded = 0.0;
        List<ResponseTransactionDTO> transactionsCostOfLiving = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            transactionsCostOfLiving.add(new ResponseTransactionDTO(
                UUID.randomUUID(),
                idUser,
                Date.valueOf("2025-05-02").toLocalDate(),
                Type.COST_OF_LIVING,
                100.0,
                Type.COST_OF_LIVING
            ));
        }

        //When
        when(repository.findById(idUser)).thenReturn(new FinancePlanning(idUser, wage));
        List<Transaction> transactionsCostOfLivingDB = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                100.0
            ));
        }
        when(transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.COST_OF_LIVING)).thenReturn(transactionsCostOfLivingDB);
        List<Transaction> transactionsInvestmentDB = new ArrayList<>();
        when(transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.INVESTMENT)).thenReturn(transactionsInvestmentDB);
        List<Transaction> transactionsExtraAddedDB = new ArrayList<>();
        when(transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.EXTRA_CONTRIBUTION)).thenReturn(transactionsExtraAddedDB);
        var result = service.getInfosFinanceInDateRange(idUser, startDate, endDate);

        //Then
        assertNotNull(result);
        assertEquals(wage, result.wage());
        assertEquals(totalBalance, result.totalBalance());
        assertEquals(availableCostOfLivingBalance, result.availableCostOfLivingBalance());
        assertEquals(balanceAvailableToInvest, result.balanceAvailableToInvest());
        assertEquals(extraBalanceAdded, result.extraBalanceAdded());
        assertEquals(transactionsCostOfLiving.size(), result.transactionsCostOfLiving().size());
        assertEquals(0, result.transactionsInvestment().size());
        assertEquals(0, result.transactionsExtraAdded().size());

        verify(repository, times(1)).findById(idUser);
        verify(transactionRepository, times(1)).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.COST_OF_LIVING);
        verify(transactionRepository, times(1)).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.INVESTMENT);
        verify(transactionRepository, times(1)).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.EXTRA_CONTRIBUTION);
    }

    @Test
    public void getInfosFinanceInDateRangeWithMock() throws NotFoundFinancePlanning, DataBaseException {
        //Given
        var idUser = UUID.randomUUID();
        LocalDate startDate = Date.valueOf("2025-05-01").toLocalDate();
        LocalDate endDate = Date.valueOf("2025-05-31").toLocalDate();
        Double wage = 3300.0;
        Double totalBalance = 1504.57;
        Double availableCostOfLivingBalance = 1174.57;
        Double balanceAvailableToInvest = 330.0;
        Double extraBalanceAdded = 0.0;

        //When
        when(repository.findById(idUser)).thenReturn(new FinancePlanning(idUser, wage));
        List<Transaction> transactionsCostOfLivingDB = new ArrayList<>();
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                252.0
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                455.0
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                217.99
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                29.90
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                14.90
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                80.90
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                44.74
        ));
        transactionsCostOfLivingDB.add(new Transaction(
                UUID.randomUUID(),
                new FinancePlanning(idUser, wage),
                Type.COST_OF_LIVING,
                Date.valueOf("2025-05-02"),
                "description",
                700.0
        ));

        when(transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.COST_OF_LIVING)).thenReturn(transactionsCostOfLivingDB);
        List<Transaction> transactionsInvestmentDB = new ArrayList<>();
        when(transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.INVESTMENT)).thenReturn(transactionsInvestmentDB);
        List<Transaction> transactionsExtraAddedDB = new ArrayList<>();
        when(transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.EXTRA_CONTRIBUTION)).thenReturn(transactionsExtraAddedDB);
        var result = service.getInfosFinanceInDateRange(idUser, startDate, endDate);

        //Then
        assertNotNull(result);
        assertEquals(wage, result.wage());
        assertEquals(totalBalance, result.totalBalance());
        assertEquals(availableCostOfLivingBalance, result.availableCostOfLivingBalance());
        assertEquals(balanceAvailableToInvest, result.balanceAvailableToInvest());
        assertEquals(extraBalanceAdded, result.extraBalanceAdded());
        assertEquals(transactionsCostOfLivingDB.size(), result.transactionsCostOfLiving().size());
        assertEquals(0, result.transactionsInvestment().size());
        assertEquals(0, result.transactionsExtraAdded().size());

        verify(repository, times(1)).findById(idUser);
        verify(transactionRepository, times(1)).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.COST_OF_LIVING);
        verify(transactionRepository, times(1)).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.INVESTMENT);
        verify(transactionRepository, times(1)).findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.EXTRA_CONTRIBUTION);
    }

}
