package tech.inovasoft.inevolving.ms.finance.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinanceControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void addPlanningWhenRegistering_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void updateWage_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void addTransactionCostOfLiving_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void addTransactionInvestment_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void addTransactionExtraContribution_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void deleteTransaction_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void getTransaction_ok() {
        //TODO: Desenvolver teste do End-Point
    }

    @Test
    public void getInfosFinanceInDateRange_ok() {
        //TODO: Desenvolver teste do End-Point
    }

}
