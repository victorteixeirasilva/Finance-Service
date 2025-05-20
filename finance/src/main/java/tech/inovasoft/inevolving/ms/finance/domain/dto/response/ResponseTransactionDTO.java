package tech.inovasoft.inevolving.ms.finance.domain.dto.response;

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
}
