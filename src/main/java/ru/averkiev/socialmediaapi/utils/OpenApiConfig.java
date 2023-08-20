package ru.averkiev.socialmediaapi.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Класс для настройки конфигурации Swagger.
 * @author mrGreenNV
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Social Media API",
                description = "Social Media", version = "1.0.0",
                contact = @Contact(
                        name = "mrGreenNV",
                        email = "averkievnv@gmail.com",
                        url = "https://github.com/MrGreenNV"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
