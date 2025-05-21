package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.Transaction;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.FinancePlanningRepository;
import tech.inovasoft.inevolving.ms.finance.repository.interfaces.TransactionRepository;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FinancePlanningRepository financePlanningRepository;

    public ResponseTransactionDTO addTransaction(
            UUID idUser,
            RequestTransactionDTO requestDTO,
            String type
    ) {
        var planning = financePlanningRepository.findById(idUser);

        var newTransaction = new Transaction(planning, requestDTO, type);

        return new ResponseTransactionDTO(transactionRepository.saveTransaction(newTransaction));

        //TODO: Refatorando código.
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

}
