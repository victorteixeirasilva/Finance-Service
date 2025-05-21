package tech.inovasoft.inevolving.ms.finance.domain.dto.response;

import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.time.LocalDate;
import java.util.UUID;

public record ResponseTransactionDTO(
        UUID id,
        UUID idUser,
        LocalDate date,
        String description,
        Double value,
        String type
) {
    public ResponseTransactionDTO(Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getFinancePlanning().getIdUser(),
                transaction.getTransactionDate().toLocalDate(),
                transaction.getDescriptionTransaction(),
                transaction.getAmount(),
                transaction.getType()
        );
    }
}
