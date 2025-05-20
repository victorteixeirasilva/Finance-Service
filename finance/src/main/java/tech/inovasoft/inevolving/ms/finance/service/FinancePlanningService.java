package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseUserWageDTO;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FinancePlanningService {
    public ResponseMessageDTO addPlanningWhenRegistering(UUID idUser) {
        //TODO: Criar Teste que falhe.
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }

    public ResponseUserWageDTO updateWage(UUID idUser, Double wage) {
        //TODO: Criar Teste que falhe.
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }

    public ResponseTransactionDTO addTransaction(UUID idUser, RequestTransactionDTO requestDTO, String type) {
        //TODO: Criar Teste que falhe.
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }

    public ResponseMessageDTO deleteTransaction(UUID idUser, UUID idTransaction) {
        //TODO: Criar Teste que falhe.
        //TODO: Desenvolver método para o teste passar.
        //TODO: Refatorando código.
        return null;
    }

    public ResponseTransactionDTO getTransaction(UUID idUser, UUID idTransaction) {
        //TODO: Criar Teste que falhe.
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
