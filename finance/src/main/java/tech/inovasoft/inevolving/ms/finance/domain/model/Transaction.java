package tech.inovasoft.inevolving.ms.finance.domain.model;

import jakarta.persistence.*;
import lombok.*;

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

}
