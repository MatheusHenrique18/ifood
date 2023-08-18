package com.github.MatheusHenrique18.ifood.cadastro.config;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@OpenAPIDefinition(
		info = @Info(
				title = "Ifood - Módulo de Cadastro",
				description = "Coleção de APIs do Módulo de Cadastro para o projeto Ifood.",
				version = "1.0.0"
		),
		components = @Components(
				securitySchemes = {
						@SecurityScheme(
								securitySchemeName = "ifood-oauth", 
								type = SecuritySchemeType.OAUTH2,
								flows = @OAuthFlows(
										password = @OAuthFlow(
												tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token"
										)
								)
						)
				}
		)
)



public class CadastroApplication extends Application{

}
