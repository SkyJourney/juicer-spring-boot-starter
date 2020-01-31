package top.icdat.juicer.boot;

import io.swagger.v3.oas.models.OpenAPI;
import top.icdat.juicer.JuicerActuator;
import top.icdat.juicer.JuicerHandlerFactory;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;

/**
 * @author SkyJourney
 */
@Configuration(
        proxyBeanMethods = false
)
@ComponentScan(basePackages = "top.icdat.juicer.boot")
@EnableConfigurationProperties(JuicerProperties.class)
public class JuicerConfiguration {

    @Resource
    private JuicerProperties juicerProperties;

    @Bean
    public JuicerHandlerFactory juicerHandlerFactory() {
        return new JuicerHandlerFactory(juicerProperties.getScanPackages());
    }

    @Bean
    public JuicerActuator juicerActuator(JuicerHandlerFactory juicerHandlerFactory) {
        Properties properties = new Properties();
        if (juicerProperties.isInterruptSave()) {
            properties.setProperty("juicer.data.interrupt-save","true");
        }
        if (juicerProperties.isPersistence()) {
            properties.setProperty("juicer.data.persistence", "true");
        }
        properties.setProperty("juicer.data.save-path", juicerProperties.getSavePath());
        if (juicerProperties.isStandalonePool()) {
            return new JuicerActuator(
                    juicerHandlerFactory
                    ,new ForkJoinPool(juicerProperties.getPoolThread())
                    ,properties
            );
        } else {
            return new JuicerActuator(juicerHandlerFactory, properties);
        }
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setBasenames("templates/juicer/handler");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(2);
        return messageSource;
    }

    @Bean
    public OpenAPI juicerOpenApi(){
        return new OpenAPI().info(new Info()
                .title("Juicer Spring Boot Starter API")
                .description("Juicer Spring Boot Starter API")
                .version("v1.0")
                .license(new License()
                        .name("MIT License")
                        .url("https://github.com/SkyJourney/juicer-spring-boot-starter/raw/master/LICENSE")
                )
        )
        .externalDocs(new ExternalDocumentation()
                .description("Juicer Spring Boot Starter")
                .url("https://github.com/SkyJourney/juicer-spring-boot-starter")
        );
    }

}
