package tech.inovasoft.inevolving.ms.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestUpdateWageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseFinanceInDateRangeDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseMessageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseUserWageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.exception.DataBaseException;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundFinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.exception.NotFoundTransactionException;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;
import tech.inovasoft.inevolving.ms.finance.service.FinancePlanningService;
import tech.inovasoft.inevolving.ms.finance.service.TransactionService;
import tech.inovasoft.inevolving.ms.finance.service.client.Auth_For_MService.TokenService;
import tech.inovasoft.inevolving.ms.finance.service.client.Auth_For_MService.dto.TokenValidateResponse;

import java.sql.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Tag(name = "Finance | Finanças", description = "Personal Finance Endpoint Manager | Gerenciador dos endpoints de Finanças Pessoais")
@RestController
@RequestMapping("/ms/finance")
public class FinanceController {

    @Autowired
    private FinancePlanningService planningService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TokenService tokenService;

    @Operation(
            summary = "Adds new financial planning to the user | Adiciona um novo planejamento financeiro ao usuário.",
            description = "Returns confirmation that the financial plan has been created. | Retorna a confirmação que o planejamento financeiro foi criado."
    )
    @Async("asyncExecutor")
    @PostMapping("/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<FinancePlanning>> addPlanningWhenRegistering(
            @PathVariable("idUser") UUID idUser,
            @PathVariable String token
    ) throws DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                planningService.addPlanningWhenRegistering(idUser)
        ));
    }

    @Operation(
            summary = "Updates the salary of the user | Atualizar o salario do usuario.",
            description = "Returns the updated salary of the user | Retorna o salario do usuário atualizado."
    )
    @Async("asyncExecutor")
    @PutMapping("/wage/{idUser}/{token}")
    public CompletableFuture<ResponseEntity<ResponseUserWageDTO>> updateWage(
            @PathVariable("idUser") UUID idUser,
            @RequestBody RequestUpdateWageDTO requestDTO,
            @PathVariable String token
    ) throws NotFoundFinancePlanning, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                planningService.updateWage(idUser, requestDTO.wage())
        ));
    }

    @Operation(
            summary =   " Add a cost of life to the user | Adicionar uma despesa ao Custo de vida",
            description =   " Returns confirmation that the transaction has been recorded, along with its type and " +
                            "amount information. | Retorna a confirmação que a transação foi registrada. junto com suas" +
                            " informações de tipo e valor."
    )
    @Async("asyncExecutor")
    @PostMapping("/transaction/cost_of_living/{token}")
    public CompletableFuture<ResponseEntity<ResponseTransactionDTO>> addTransactionCostOfLiving(
            @RequestBody RequestTransactionDTO requestDTO,
            @PathVariable String token
    ) throws NotFoundFinancePlanning, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                transactionService.addTransaction(requestDTO.idUser(), requestDTO, Type.COST_OF_LIVING)
        ));
    }

    @Operation(
            summary =   " Add a Investment to the user | Adicionar uma despesa ao Investimento",
            description =   " Returns confirmation that the transaction has been recorded, along with its type and " +
                    "amount information. | Retorna a confirmação que a transação foi registrada. junto com suas" +
                    " informações de tipo e valor."
    )
    @Async("asyncExecutor")
    @PostMapping("/transaction/investment/{token}")
    public CompletableFuture<ResponseEntity<ResponseTransactionDTO>> addTransactionInvestment(
            @RequestBody RequestTransactionDTO requestDTO,
            @PathVariable String token
    ) throws NotFoundFinancePlanning, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                transactionService.addTransaction(requestDTO.idUser(), requestDTO, Type.INVESTMENT)
        ));
    }

    @Operation(
            summary =   " Add a balance entry transaction. | Adicionar uma transação de entrada de saldo.",
            description =   " Returns confirmation that the transaction has been recorded, along with its type and " +
                    "amount information. | Retorna a confirmação que a transação foi registrada. junto com suas" +
                    " informações de tipo e valor."
    )
    @Async("asyncExecutor")
    @PostMapping("/transaction/extra_contribution/{token}")
    public CompletableFuture<ResponseEntity<ResponseTransactionDTO>> addTransactionExtraContribution(
            @RequestBody RequestTransactionDTO requestDTO,
            @PathVariable String token
    ) throws NotFoundFinancePlanning, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                transactionService.addTransaction(requestDTO.idUser(), requestDTO, Type.EXTRA_CONTRIBUTION)
        ));
    }

    @Operation(
            summary = "Delete a transaction | Deletar uma transação",
            description =   "Returns confirmation that the transaction has been deleted. | " +
                            "Retorna a confirmação que a transação foi deletada."
    )
    @Async("asyncExecutor")
    @DeleteMapping("/transaction/{idUser}/{idTransaction}/{token}")
    public CompletableFuture<ResponseEntity<ResponseMessageDTO>> deleteTransaction(
            @PathVariable("idUser") UUID idUser,
            @PathVariable("idTransaction") UUID idTransaction,
            @PathVariable String token
    ) throws NotFoundTransactionException, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                transactionService.deleteTransaction(idUser, idTransaction)
        ));
    }

    @Operation(
            summary = "Get a transaction | Obter uma transação",
            description =   "Returns the transaction information. | " +
                            "Retorna as informações da transação."
    )
    @Async("asyncExecutor")
    @GetMapping("/transaction/{idUser}/{idTransaction}/{token}")
    public CompletableFuture<ResponseEntity<ResponseTransactionDTO>> getTransaction(
            @PathVariable UUID idUser,
            @PathVariable UUID idTransaction,
            @PathVariable String token
    ) throws NotFoundTransactionException, DataBaseException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                transactionService.getTransaction(idUser, idTransaction)
        ));
    }

    @Operation(
            summary =   "Get all financial information in date range. | " +
                        "Obter todas as informações de financeiro no intervalo de datas",
            description =   "Returns the financial information in the date range. | " +
                            "Retorna as informações de financeiro no intervalo de datas."
    )
    @Async("asyncExecutor")
    @GetMapping("/{idUser}/{startDate}/{endDate}/{token}")
    public CompletableFuture<ResponseEntity<ResponseFinanceInDateRangeDTO>> getInfosFinanceInDateRange(
            @PathVariable UUID idUser,
            @PathVariable Date startDate,
            @PathVariable Date endDate,
            @PathVariable String token
    ) throws NotFoundFinancePlanning, DataBaseException, NotFoundTransactionException {
        TokenValidateResponse tokenValidateResponse = null;

        try {
            tokenValidateResponse = tokenService.validateToken(token);
            if (tokenValidateResponse == null) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Invalid token")) {
                return CompletableFuture.completedFuture(ResponseEntity.status(
                        HttpStatus.UNAUTHORIZED
                ).build());
            }
        }

        return CompletableFuture.completedFuture(ResponseEntity.ok(
                planningService.getInfosFinanceInDateRange(idUser, startDate, endDate)
        ));
    }

}
