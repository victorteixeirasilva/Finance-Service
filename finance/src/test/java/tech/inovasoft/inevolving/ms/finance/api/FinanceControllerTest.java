package tech.inovasoft.inevolving.ms.finance.api;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestTransactionDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.request.RequestUpdateWageDTO;
import tech.inovasoft.inevolving.ms.finance.domain.dto.response.ResponseFinanceInDateRangeDTO;
import tech.inovasoft.inevolving.ms.finance.domain.model.FinancePlanning;
import tech.inovasoft.inevolving.ms.finance.domain.model.Type;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.patch;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FinanceControllerTest {

    @LocalServerPort
    private int port;

    private UUID idUser = UUID.randomUUID();

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
