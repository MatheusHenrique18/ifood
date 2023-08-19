package com.github.MatheusHenrique18.ifood.cadastro;

import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.approvaltests.JsonApprovals;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.github.MatheusHenrique18.ifood.cadastro.dto.AlterarRestauranteDTO;
import com.github.MatheusHenrique18.ifood.cadastro.dto.CadastrarRestauranteDTO;
import com.github.MatheusHenrique18.ifood.cadastro.entity.Restaurante;
import com.github.MatheusHenrique18.ifood.cadastro.utils.Constants;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifeCycleManager.class)
public class RestauranteResourceTest extends TestTokenConfig{

    @Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testBuscarRestaurantes() {
        String resultado = given()
		          .when().get("/restaurantes")
		          .then()
		          .statusCode(Status.OK.getStatusCode())
		          .extract().asString();
        JsonApprovals.verifyJson(resultado);
    }
    
    @Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testAlterarRestaurante() {
    	AlterarRestauranteDTO dto = new AlterarRestauranteDTO();
    	dto.nomeFantasia = "novo nome";
    	Long idRestaurante = 123L;
    	
    	given()
    	.with().pathParam("id", idRestaurante)
    	.body(dto)
    	.when().put("/restaurantes/{id}")
    	.then()
    	.statusCode(Status.NO_CONTENT.getStatusCode());
    	
    	Restaurante restaurante = Restaurante.findById(idRestaurante);
    	
    	Assert.assertEquals(dto.nomeFantasia, restaurante.nome);
    	
    }
    
    @Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testIncluirRestaurante() {
    	CadastrarRestauranteDTO dto = new CadastrarRestauranteDTO();
    	dto.nomeFantasia = "novo restaurante 123321";
    	dto.proprietario = "teste";
    	
    	given()
    	.body(dto)
    	.when().post("/restaurantes")
    	.then()
    	.statusCode(Status.CREATED.getStatusCode());
    	
    	Restaurante restaurante = Restaurante.find("nome", dto.nomeFantasia).singleResult();
    	
    	Assert.assertEquals(dto.nomeFantasia, restaurante.nome);
    }
    
    @Test
    @DataSet(Constants.DATASET_RESTAURANTES_PRATOS)
    public void testExcluirRestaurante() {
    	Long idRestaurante = 234L;
    	
    	given()
    	.with().pathParam("id", idRestaurante)
    	.when().delete("/restaurantes/{id}")
    	.then()
    	.statusCode(Status.NO_CONTENT.getStatusCode());
    	
    	Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    	
    	Assert.assertTrue(restauranteOp.isEmpty());
    }

}
