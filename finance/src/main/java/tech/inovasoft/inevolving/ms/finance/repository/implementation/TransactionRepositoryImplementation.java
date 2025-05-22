package tech.inovasoft.inevolving.ms.finance.repository.implementation;

import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TransactionRepositoryImplementation implements TransactionRepository {
    @Override
    public Transaction saveTransaction(Transaction transaction) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return null;
    }

    @Override
    public Transaction findByIdAndIdUser(UUID idTransaction, UUID idUser) {
        //TODO: Criar teste que falhe
        //TODO: Desenvolver método para o teste passar
        //TODO: Refatorar Código
        return null;
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
