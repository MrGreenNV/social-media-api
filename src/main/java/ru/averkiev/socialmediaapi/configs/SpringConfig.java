package ru.averkiev.socialmediaapi.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Настройка конфигурации Spring.
 * @author mrGreenNV
 */
@Configuration
@ComponentScan(basePackages = "ru.averkiev.socialmediaapi")
@EnableJpaRepositories(basePackages = "ru.averkiev.socialmediaapi.repositories")
@EnableTransactionManagement
public class SpringConfig {

    /**
     * Создание Bean для преобразования DTO к модели и наоборот.
     * @return объект ModelMapper.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
