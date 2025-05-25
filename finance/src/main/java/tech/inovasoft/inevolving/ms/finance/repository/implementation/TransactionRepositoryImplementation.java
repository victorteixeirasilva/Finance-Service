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
    public Transaction saveTransaction(Transaction transaction) throws DataBaseException {
        try {
            return repositoryJPA.save(transaction);
        } catch (Exception e) {
            throw new DataBaseException("(Transaction.save)");
        }
    }

    @Override
    public Transaction findByIdAndIdUser(UUID idTransaction, UUID idUser) throws DataBaseException, NotFoundTransactionException {
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
        //TODO: Refatorar Código
    }

    @Override
    public ResponseMessageDTO deleteTransaction(UUID idTransaction) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return null;
    }

    @Override
    public List<Transaction> findAllTransactionsInDateRangeWithType(UUID idUser, LocalDate startDate, LocalDate endDate, String type) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return List.of();
    }
}
