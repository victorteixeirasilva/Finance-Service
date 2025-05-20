package tech.inovasoft.inevolving.ms.finance.service;

import org.springframework.stereotype.Service;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;

import java.util.UUID;

@Service
public class TransactionService {

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

}
