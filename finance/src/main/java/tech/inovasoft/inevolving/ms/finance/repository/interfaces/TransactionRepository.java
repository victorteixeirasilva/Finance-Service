package tech.inovasoft.inevolving.ms.finance.repository.interfaces;

import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.util.UUID;

public interface TransactionRepository {

    Transaction saveTransaction(Transaction transaction);

    Transaction findByIdAndIdUser(UUID idTransaction, UUID idUser);

    ResponseMessageDTO deleteTransaction(UUID idTransaction);
}
