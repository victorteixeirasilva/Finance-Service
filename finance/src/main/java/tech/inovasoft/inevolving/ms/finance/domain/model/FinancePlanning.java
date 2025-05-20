package tech.inovasoft.inevolving.ms.finance.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "finance_planning")
public class FinancePlanning {

    @Id
    private UUID idUser;
    private Double wage;

}
