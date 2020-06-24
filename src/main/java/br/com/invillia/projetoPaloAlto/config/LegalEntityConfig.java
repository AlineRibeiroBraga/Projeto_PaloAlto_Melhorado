package br.com.invillia.projetoPaloAlto.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LegalEntityConfig {

    @Bean
    public Faker createFaker(){
        return Faker.instance();
    }

}