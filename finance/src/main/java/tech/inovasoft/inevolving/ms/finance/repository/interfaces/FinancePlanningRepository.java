package tech.inovasoft.inevolving.ms.finance.repository.interfaces;

import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface FinancePlanningRepository {
    FinancePlanning savePlanningForUser(UUID idUser) throws DataBaseException;

    FinancePlanning findById(UUID idUser) throws DataBaseException, NotFoundFinancePlanning;

    FinancePlanning savePlanning(FinancePlanning planning) throws DataBaseException;

}
