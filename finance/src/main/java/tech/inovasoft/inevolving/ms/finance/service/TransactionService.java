package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;
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
    ) throws NotFoundFinancePlanning, DataBaseException {
        var planning = financePlanningRepository.findById(idUser);

        var newTransaction = new Transaction(planning, requestDTO, type);

        return new ResponseTransactionDTO(transactionRepository.saveTransaction(newTransaction));
    }

    /**
     * @description - Delete a transaction. | Deletar uma transação.
     * @param idUser - The id of the user | O id do usuário
     * @param idTransaction - The id of the transaction | O id da transação
     * @return - Returns a confirmation that the transaction has been deleted. | Retorna uma confirmação de que a transação foi deletada.
     */
    public ResponseMessageDTO deleteTransaction(
            UUID idUser,
            UUID idTransaction
    ) throws NotFoundTransactionException, DataBaseException {
        Transaction transaction = transactionRepository.findByIdAndIdUser(idTransaction, idUser);

        return transactionRepository.deleteTransaction(transaction.getId());
    }

    /**
     * @description - Get a transaction | Obter uma transação
     * @param idUser - The id of the user | O id do usuário
     * @param idTransaction - The id of the transaction | O id da transação
     * @return - The transaction | A transação
     */
    public ResponseTransactionDTO getTransaction(
            UUID idUser,
            UUID idTransaction
    ) throws NotFoundTransactionException, DataBaseException {
        Transaction transaction = transactionRepository.findByIdAndIdUser(idTransaction, idUser);
        return new ResponseTransactionDTO(transaction);
    }

}
