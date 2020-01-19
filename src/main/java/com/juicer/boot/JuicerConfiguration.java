package com.juicer.boot;

import com.juicer.JuicerActuator;
import com.juicer.JuicerHandlerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;

/**
 * @author SkyJourney
 */
@Configuration(
        proxyBeanMethods = false
)
@ComponentScan(basePackages = "com.juicer.boot")
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
}
