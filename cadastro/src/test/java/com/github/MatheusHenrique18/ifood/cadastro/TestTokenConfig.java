package com.github.MatheusHenrique18.ifood.cadastro;

import org.junit.jupiter.api.BeforeEach;

import com.github.MatheusHenrique18.ifood.cadastro.util.TokenUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;

public class TestTokenConfig {

	private String token;
	
	@BeforeEach
	private void generateToken() throws Exception {
		token = TokenUtils.generateTokenString("JWTProprietarioClaims.json", null);
	}
	
	protected RequestSpecification given() {
		return RestAssured.given().contentType(ContentType.JSON).header(new Header("Authorization", "Bearer " + token));
	}
	
}
