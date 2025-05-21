package tech.inovasoft.inevolving.ms.finance.repository.interfaces;

import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

public interface TransactionRepository {

    Transaction saveTransaction(Transaction expectedTransaction);

}
