package com.github.MatheusHenrique18.ifood.cadastro;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.JsonApprovals;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifeCycleManager.class)
public class PratoResourceTest {

	@Test
    @DataSet("pratos-cenario-1.yml")
    public void testBuscarPratos() {
		Long parameterValue = 123L;
        String resultado = given()
					.contentType(ContentType.JSON)
					.with().pathParam("idRestaurante", parameterValue)
					.when().get("/restaurantes/{idRestaurante}/pratos")
					.then()
					.statusCode(Status.OK.getStatusCode())
					.extract().asString();
        JsonApprovals.verifyJson(resultado);
    }
	
}
