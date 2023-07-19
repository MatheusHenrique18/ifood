package com.github.MatheusHenrique18.ifood.cadastro;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.JsonApprovals;
import org.junit.Assert;
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
public class RestauranteResourceTest {

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testBuscarRestaurantes() {
        String resultado = given()
		          .when().get("/restaurantes")
		          .then()
		          .statusCode(200)
		          .extract().asString();
        JsonApprovals.verifyJson(resultado);
    }
    
    @Test
    @DataSet("restaurantes-cenario-1.yml")
    public void testAlterarRestaurante() {
    	Restaurante dto = new Restaurante();
    	dto.nome = "novo nome";
    	Long parameterValue = 123L;
    	
    	given()
    	.contentType(ContentType.JSON)
    	.with().pathParam("id", parameterValue)
    	.body(dto)
    	.when().put("/restaurantes/{id}")
    	.then()
    	.statusCode(Status.NO_CONTENT.getStatusCode());
    	
    	Restaurante restaurante = Restaurante.findById(parameterValue);
    	
    	Assert.assertEquals(dto.nome, restaurante.nome);
    	
    }

}
