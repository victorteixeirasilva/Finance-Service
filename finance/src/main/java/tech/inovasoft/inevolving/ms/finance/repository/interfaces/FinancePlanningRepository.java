package tech.inovasoft.inevolving.ms.finance.repository.interfaces;

import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;

import java.util.UUID;

public interface FinancePlanningRepository {
    FinancePlanning savePlanningForUser(UUID idUser);
}
