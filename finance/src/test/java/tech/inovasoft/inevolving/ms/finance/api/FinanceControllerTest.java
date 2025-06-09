package tech.inovasoft.inevolving.ms.finance.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestUpdateWageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;

import java.time.LocalDate;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinanceControllerTest {

    @LocalServerPort
    private int port;

    private final UUID idUser = UUID.randomUUID();

    private FinancePlanning addPlanningWhenRegistering(UUID idUser){

        // Cria a especificação da requisição
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .post("http://localhost:" + port + "/ms/finance/" + idUser)
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString()));

        return new FinancePlanning(
                UUID.fromString(response.extract().body().jsonPath().get("idUser")),
                response.extract().body().jsonPath().get("wage") instanceof Float
                        ? ((Float) response.extract().body().jsonPath().get("wage")).doubleValue()
                        : (Double) response.extract().body().jsonPath().get("wage")

        );
    }

    @Test
    public void addPlanningWhenRegistering_ok() {
        var planning = addPlanningWhenRegistering(idUser);

        Assertions.assertEquals(idUser, planning.getIdUser());
        }

    @Test
    public void updateWage_ok() {
        var planning = addPlanningWhenRegistering(idUser);

        RequestUpdateWageDTO wageDTO = new RequestUpdateWageDTO(3300.00);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(wageDTO)
                .when()
                .patch("http://localhost:" + port + "/ms/finance/wage/" + idUser)
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("wage", equalTo(3300.00F));

    }

    @Test
    public void addTransactionCostOfLiving_ok() {
        RequestTransactionDTO requestTransactionDTO = new RequestTransactionDTO(
                idUser,
                LocalDate.now(),
                "description",
                100.00
        );

        addPlanningWhenRegistering(idUser);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(requestTransactionDTO)
                .when()
                .post("http://localhost:" + port + "/ms/finance/transaction/cost_of_living")
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("id", notNullValue()).and()
                .body("date", equalTo(LocalDate.now().toString())).and()
                .body("description", equalTo(requestTransactionDTO.description())).and()
                .body("type", equalTo(Type.COST_OF_LIVING));

    }

    @Test
    public void addTransactionInvestment_ok() {
        RequestTransactionDTO requestTransactionDTO = new RequestTransactionDTO(
                idUser,
                LocalDate.now(),
                "description",
                100.00
        );

        addPlanningWhenRegistering(idUser);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(requestTransactionDTO)
                .when()
                .post("http://localhost:" + port + "/ms/finance/transaction/investment")
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("id", notNullValue()).and()
                .body("date", equalTo(LocalDate.now().toString())).and()
                .body("description", equalTo(requestTransactionDTO.description())).and()
                .body("type", equalTo(Type.INVESTMENT));
    }

    @Test
    public void addTransactionExtraContribution_ok() {
        RequestTransactionDTO requestTransactionDTO = new RequestTransactionDTO(
                idUser,
                LocalDate.now(),
                "description",
                100.00
        );

        addPlanningWhenRegistering(idUser);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .body(requestTransactionDTO)
                .when()
                .post("http://localhost:" + port + "/ms/finance/transaction/extra_contribution")
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("id", notNullValue()).and()
                .body("date", equalTo(LocalDate.now().toString())).and()
                .body("description", equalTo(requestTransactionDTO.description())).and()
                .body("type", equalTo(Type.EXTRA_CONTRIBUTION));
    }

    @Test
    public void getTransaction_ok() {
        RequestTransactionDTO requestTransactionDTO = new RequestTransactionDTO(
                idUser,
                LocalDate.now(),
                "description",
                100.00
        );

        var planning = addPlanningWhenRegistering(idUser);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse responseAdd = requestSpecification
                .body(requestTransactionDTO)
                .when()
                .post("http://localhost:" + port + "/ms/finance/transaction/extra_contribution")
                .then();

        UUID idTransaction = UUID.fromString(responseAdd.extract().body().jsonPath().get("id"));


        // Faz a requisição GET e armazena a resposta
        ValidatableResponse response = requestSpecification
                .when()
                .get("http://localhost:" + port + "/ms/finance/transaction/" + planning.getIdUser() + "/" + idTransaction)
                .then();


        // Valida a resposta
        response.assertThat().statusCode(200).and()
                .body("id", equalTo(idTransaction.toString()));
    }

    @Test
    public void deleteTransaction_ok() {
        RequestTransactionDTO requestTransactionDTO = new RequestTransactionDTO(
                idUser,
                LocalDate.now(),
                "description",
                100.00
        );

        var planning = addPlanningWhenRegistering(idUser);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        // Faz a requisição GET e armazena a resposta
        ValidatableResponse responseAdd = requestSpecification
                .body(requestTransactionDTO)
                .when()
                .post("http://localhost:" + port + "/ms/finance/transaction/extra_contribution")
                .then();

        UUID idTransaction = UUID.fromString(responseAdd.extract().body().jsonPath().get("id"));

        ValidatableResponse responseGet = requestSpecification
                .when()
                .get("http://localhost:" + port + "/ms/finance/transaction/" + planning.getIdUser() + "/" + idTransaction)
                .then();

        // Valida a resposta
        responseGet.assertThat().statusCode(200).and()
                .body("id", equalTo(idTransaction.toString()));

        ValidatableResponse responseDelete = requestSpecification
                .when()
                .delete("http://localhost:" + port + "/ms/finance/transaction/" + planning.getIdUser() + "/" + idTransaction)
                .then();

        responseGet.assertThat().statusCode(200);
    }

    @Test
    public void getInfosFinanceInDateRange_ok() {
        var planning = addPlanningWhenRegistering(idUser);

        updateWage_ok();

        mockTransaction(idUser);

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        ValidatableResponse responseGet = requestSpecification
                .when()
                .get("http://localhost:" + port + "/ms/finance/"
                        + planning.getIdUser() + "/" + "2025-06-01/2025-06-30")
                .then();

        // Valida a resposta
        responseGet.assertThat().statusCode(200).and()
                .body("idUser", equalTo(idUser.toString())).and()
                .body("wage", equalTo(3300.00F)).and()
                .body("totalBalance", equalTo(62.40F)).and()
                .body("availableCostOfLivingBalance", equalTo(-314.04F)).and()
                .body("balanceAvailableToInvest", equalTo(115.65F)).and()
                .body("extraBalanceAdded", equalTo(260.79F));

    }

    private void mockTransaction(UUID idUser) {
        addTransaction(
                Type.INVESTMENT,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 1),
                        "1",
                        214.35
                )
        );
        addTransaction(
                Type.EXTRA_CONTRIBUTION,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 2),
                        "2",
                        139.00
                )
        );
        addTransaction(
                Type.EXTRA_CONTRIBUTION,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 3),
                        "3",
                        40.00
                )
        );
        addTransaction(
                Type.EXTRA_CONTRIBUTION,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 4),
                        "4",
                        81.79
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 5),
                        "5",
                        252.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 6),
                        "6",
                        455.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 7),
                        "7",
                        217.99
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 8),
                        "8",
                        29.90
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 9),
                        "9",
                        14.90
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 10),
                        "10",
                        80.90
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 11),
                        "11",
                        44.74
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 30),
                        "12",
                        49.60
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 12),
                        "13",
                        21.90
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 13),
                        "14",
                        70.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 14),
                        "15",
                        96.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 15),
                        "16",
                        394.47
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 16),
                        "17",
                        37.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 17),
                        "18",
                        197.91
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 18),
                        "19",
                        200.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 19),
                        "20",
                        64.66
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 20),
                        "21",
                        80.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 21),
                        "22",
                        80.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 22),
                        "23",
                        157.68
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 23),
                        "24",
                        81.79
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 24),
                        "25",
                        30.91
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 25),
                        "26",
                        109.69
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 26),
                        "27",
                        13.00
                )
        );
        addTransaction(
                Type.COST_OF_LIVING,
                new RequestTransactionDTO(
                        idUser,
                        LocalDate.of(2025, 6, 27),
                        "28",
                        504.00
                )
        );
    }

    private void addTransaction(String type, RequestTransactionDTO requestTransactionDTO) {

        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON);

        String url = switch (type) {
            case Type.INVESTMENT -> "http://localhost:" + port + "/ms/finance/transaction/investment";
            case Type.EXTRA_CONTRIBUTION -> "http://localhost:" + port + "/ms/finance/transaction/extra_contribution";
            case Type.COST_OF_LIVING -> "http://localhost:" + port + "/ms/finance/transaction/cost_of_living";
            default -> "cost_of_living";
        };

        ValidatableResponse responseAdd = requestSpecification
                .body(requestTransactionDTO)
                .when()
                .post(url)
                .then();

        responseAdd.assertThat().statusCode(200);
    }

}
