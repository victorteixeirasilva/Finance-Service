package tech.inovasoft.inevolving.ms.finance.domain.model;

import jakarta.persistence.*;
import lombok.*;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "id_finance_planning")
    private FinancePlanning financePlanning;
    private String type;
    private Date transactionDate;
    private String descriptionTransaction;
    private Double amount;

    public Transaction(FinancePlanning planning, RequestTransactionDTO requestDTO, String type) {
        this.financePlanning = planning;
        this.type = type;
        this.transactionDate = Date.valueOf(requestDTO.date());
        this.descriptionTransaction = requestDTO.description();
        this.amount = requestDTO.value();
    }
}
