package tech.inovasoft.inevolving.ms.finance.domain.dto.request;

import java.time.LocalDate;
import java.util.UUID;

public record RequestTransactionDTO(
        UUID idUser,
        LocalDate date,
        String description,
        Double value
) {
}
