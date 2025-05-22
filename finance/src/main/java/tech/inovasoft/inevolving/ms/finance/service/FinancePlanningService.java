package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseFinanceInDateRangeDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseUserWageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FinancePlanningService {

    @Autowired
    private FinancePlanningRepository financePlanningRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * @description - Adds a financial plan for the user | Adiciona um plano financeiro para o usuario.
     * @param idUser - User id | Id do usuario
     * @return - A financial plan for the user | Um plano financeiro para o usuario
     */
    public FinancePlanning addPlanningWhenRegistering(UUID idUser) {
        return financePlanningRepository.savePlanningForUser(idUser);
    }

    /**
     * @description - Updates the wage of the user | Atualiza o salario do usuario
     * @param idUser - User id | Id do usuario
     * @param wage - Wage | Salario
     * @return - A response with the updated wage | Uma resposta com o salario atualizado
     */
    public ResponseUserWageDTO updateWage(UUID idUser, Double wage) {
        var planning = financePlanningRepository.findById(idUser);

        planning.setWage(wage);
        var newPlanning = financePlanningRepository.savePlanning(planning);

        return new ResponseUserWageDTO(newPlanning.getIdUser(), newPlanning.getWage());
    }

    public ResponseFinanceInDateRangeDTO getInfosFinanceInDateRange(
            UUID idUser,
            LocalDate startDate,
            LocalDate endDate
    ) {
        var planning = financePlanningRepository.findById(idUser);
        Double totalBalance = 0.0;
        Double availableCostOfLivingBalance = planning.getWage()*0.9;
        Double balanceAvailableToInvest = planning.getWage()*0.1;
        Double extraBalanceAdded = 0.0;

        List<Transaction> transactionsCostOfLivingDb = transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.COST_OF_LIVING);
        List<ResponseTransactionDTO> transactionsCostOfLiving = transactionsCostOfLivingDb.stream().map(ResponseTransactionDTO::new).toList();

        for (Transaction transaction : transactionsCostOfLivingDb) {
            availableCostOfLivingBalance -= transaction.getAmount();
        }

        List<Transaction> transactionsInvestmentDb = transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.INVESTMENT);
        List<ResponseTransactionDTO> transactionsInvestment = transactionsInvestmentDb.stream().map(ResponseTransactionDTO::new).toList();

        for (Transaction transaction : transactionsInvestmentDb) {
            balanceAvailableToInvest -= transaction.getAmount();
        }

        List<Transaction> transactionsExtraBalanceDb = transactionRepository.findAllTransactionsInDateRangeWithType(idUser, startDate, endDate, Type.EXTRA_CONTRIBUTION);
        List<ResponseTransactionDTO> transactionsExtraBalance = transactionsExtraBalanceDb.stream().map(ResponseTransactionDTO::new).toList();

        for (Transaction transaction : transactionsExtraBalanceDb) {
            extraBalanceAdded += transaction.getAmount();
        }

        totalBalance = availableCostOfLivingBalance + balanceAvailableToInvest + extraBalanceAdded;

        return new ResponseFinanceInDateRangeDTO(
            idUser,
            planning.getWage(),
            totalBalance,
            availableCostOfLivingBalance,
            balanceAvailableToInvest,
            extraBalanceAdded,
            transactionsCostOfLiving,
            transactionsInvestment,
            transactionsExtraBalance
        );
        //TODO: Refatorando código.
    }
}
