package br.com.banksecure.app.config;

import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * Configuração de Timezone para a aplicação.
 * Define São Paulo (America/Sao_Paulo) como timezone padrão.
 */
@Configuration
public class TimezoneConfig {


    public static void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        System.setProperty("user.timezone", "America/Sao_Paulo");
    }
}

