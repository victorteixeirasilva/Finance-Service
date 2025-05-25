package tech.inovasoft.inevolving.ms.finance.repository.interfaces;

import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    Transaction saveTransaction(Transaction transaction) throws DataBaseException;

    Transaction findByIdAndIdUser(UUID idTransaction, UUID idUser) throws DataBaseException, NotFoundTransactionException;

    ResponseMessageDTO deleteTransaction(UUID idTransaction) throws DataBaseException;

    List<Transaction> findAllTransactionsInDateRangeWithType(UUID idUser, LocalDate startDate, LocalDate endDate, String type) throws DataBaseException, NotFoundTransactionException;
}
