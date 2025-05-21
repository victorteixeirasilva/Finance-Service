package tech.inovasoft.inevolving.ms.finance.repository.interfaces;

import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;

import java.util.UUID;

public interface FinancePlanningRepository {
    FinancePlanning savePlanningForUser(UUID idUser);

    FinancePlanning findById(UUID idUser);

    FinancePlanning savePlanning(FinancePlanning planning);
}
