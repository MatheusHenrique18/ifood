package com.github.MatheusHenrique18.ifood.cadastro;

import static io.restassured.RestAssured.given;

import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.JsonApprovals;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.MatheusHenrique18.ifood.cadastro.dto.AlterarPratoDTO;
import com.github.MatheusHenrique18.ifood.cadastro.entity.Prato;
import com.github.MatheusHenrique18.ifood.cadastro.utils.Constants;
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
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testBuscarPratos() {
		Long idRestaurante = 567L;
        String resultado = given()
					.contentType(ContentType.JSON)
					.with().pathParam("idRestaurante", idRestaurante)
					.when().get("/restaurantes/{idRestaurante}/pratos")
					.then()
					.statusCode(Status.OK.getStatusCode())
					.extract().asString();
        JsonApprovals.verifyJson(resultado);
    }
	
	@Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testAlterarPrato() {
		Long idRestaurante = 567L;
		Long idPrato = 12345L;
		AlterarPratoDTO dto = new AlterarPratoDTO();
		dto.nome = "teste alteracao";
		
        given()
			.contentType(ContentType.JSON)
			.with().pathParam("idRestaurante", idRestaurante)
			.with().pathParam("idPrato", idPrato)
			.body(dto)
			.when().put("/restaurantes/{idRestaurante}/pratos/{idPrato}")
			.then()
			.statusCode(Status.NO_CONTENT.getStatusCode())
			.extract().asString();
        
        Prato prato = Prato.findById(idPrato);
    	Assert.assertEquals(dto.nome, prato.nome);
    }
	
	@Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testIncluirPrato() {
		Long idRestaurante = 567L;
		AlterarPratoDTO dto = new AlterarPratoDTO();
		dto.nome = "teste inclusao 123456";
		
        given()
			.contentType(ContentType.JSON)
			.with().pathParam("idRestaurante", idRestaurante)
			.body(dto)
			.when().post("/restaurantes/{idRestaurante}/pratos")
			.then()
			.statusCode(Status.CREATED.getStatusCode())
			.extract().asString();
        
        Prato prato = Prato.find("nome", dto.nome).singleResult();
    	Assert.assertEquals(dto.nome, prato.nome);
    }
	
	@Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testExcluirPrato() {
		Long idRestaurante = 567L;
		Long idPrato = 12345L;
		
        given()
			.contentType(ContentType.JSON)
			.with().pathParam("idRestaurante", idRestaurante)
			.with().pathParam("idPrato", idPrato)
			.when().delete("/restaurantes/{idRestaurante}/pratos/{idPrato}")
			.then()
			.statusCode(Status.NO_CONTENT.getStatusCode())
			.extract().asString();
        
        Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);
    	Assert.assertTrue(pratoOp.isEmpty());
    }
	
}
