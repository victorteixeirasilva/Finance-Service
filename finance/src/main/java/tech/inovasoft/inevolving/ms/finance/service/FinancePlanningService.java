package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseUserWageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FinancePlanningService {

    @Autowired
    private FinancePlanningRepository financePlanningRepository;

    /**
     * @description - Adds a financial plan for the user | Adiciona um plano financeiro para o usuario.
     * @param idUser - User id | Id do usuario
     * @return - A financial plan for the user | Um plano financeiro para o usuario
     */
    public FinancePlanning addPlanningWhenRegistering(UUID idUser) {
        return financePlanningRepository.savePlanningForUser(idUser);
    }

    public ResponseUserWageDTO updateWage(UUID idUser, Double wage) {
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }

    public ResponseFinanceInDateRangeDTO getInfosFinanceInDateRange(UUID idUser, LocalDate startDate, LocalDate endDate) {
        //TODO: Criar Teste que falhe.
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }
}
