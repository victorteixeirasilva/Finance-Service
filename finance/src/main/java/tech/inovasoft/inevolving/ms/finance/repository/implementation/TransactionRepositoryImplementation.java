package tech.inovasoft.inevolving.ms.finance.repository.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.jpa.TransactionRepositoryJPA;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionRepositoryImplementation implements TransactionRepository {

    @Autowired
    private TransactionRepositoryJPA repositoryJPA;

    /**
     * @description - Save a transaction | Salva uma transação
     * @param transaction - Transaction | Transação
     * @return - Transaction | Transação
     * @throws DataBaseException - Database error | Erro no banco de dados
     */
    @Override
    public Transaction saveTransaction(
            Transaction transaction
    ) throws DataBaseException {
        try {
            return repositoryJPA.save(transaction);
        } catch (Exception e) {
            throw new DataBaseException("(Transaction.save)");
        }
    }

    /**
     * @description - Find a transaction by id | Encontra uma transação pelo id
     * @param idTransaction - UUID | Id da transação
     * @param idUser - UUID | Id do usuário
     * @return - Transaction | Transação
     * @throws DataBaseException - Database error | Erro no banco de dados
     * @throws NotFoundTransactionException - Transaction not found | Transação não encontrada
     */
    @Override
    public Transaction findByIdAndIdUser(
            UUID idTransaction,
            UUID idUser
    ) throws DataBaseException, NotFoundTransactionException {
        Optional<Transaction> optionalTransaction;
        try {
            optionalTransaction = repositoryJPA.findByIdAndIdUser(idTransaction, idUser);
        } catch (Exception e) {
            throw new DataBaseException("(Transaction.findByIdAndIdUser)");
        }
        if (optionalTransaction.isEmpty()) {
            throw new NotFoundTransactionException();
        }
        return optionalTransaction.get();
    }

    /**
     * @description - Delete a transaction | Deleta uma transação
     * @param idTransaction - Id of the transaction | Id da transação
     * @return - Confirmation message | Confirmação de exclusão
     * @throws DataBaseException - Database error | Erro no banco de dados
     */
    @Override
    public ResponseMessageDTO deleteTransaction(
            UUID idTransaction
    ) throws DataBaseException {
        try {
            repositoryJPA.deleteById(idTransaction);
        } catch (Exception e) {
            throw new DataBaseException("(Transaction.deleteById)");
        }
        return new ResponseMessageDTO("Transaction deleted successfully");
    }

    @Override
    public List<Transaction> findAllTransactionsInDateRangeWithType(
            UUID idUser,
            LocalDate startDate,
            LocalDate endDate,
            String type
    ) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return List.of();
    }
}
