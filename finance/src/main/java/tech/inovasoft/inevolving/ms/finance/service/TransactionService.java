package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FinancePlanningRepository financePlanningRepository;

    /**
     * @description - Creates a new transaction | Cria uma nova transação
     * @param idUser - The id of the user | O id do usuário
     * @param requestDTO - The transaction data | Os dados da transação
     * @param type - The type of the transaction | O tipo da transação
     * @return - The created transaction | A transação criada
     */
    public ResponseTransactionDTO addTransaction(
            UUID idUser,
            RequestTransactionDTO requestDTO,
            String type
    ) {
        var planning = financePlanningRepository.findById(idUser);

        var newTransaction = new Transaction(planning, requestDTO, type);

        return new ResponseTransactionDTO(transactionRepository.saveTransaction(newTransaction));
    }

    public ResponseMessageDTO deleteTransaction(
            UUID idUser,
            UUID idTransaction
    ) {
        Transaction transaction = transactionRepository.findByIdAndIdUser(idTransaction, idUser);

        return transactionRepository.deleteTransaction(transaction.getId());
        //TODO: Refatorando código.
    }

    public ResponseTransactionDTO getTransaction(
            UUID idUser,
            UUID idTransaction
    ) {
        //TODO: Criar Teste que falhe.
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }

}
