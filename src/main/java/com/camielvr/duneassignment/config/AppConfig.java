package com.camielvr.duneassignment.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@PropertySource("classpath:config.properties")
public class AppConfig {

    @Value("${customers}")
    private String[] customers;

    @Getter
    @Value("${currentSpicePrice}")
    private BigDecimal currentSpicePrice;

    @Getter
    @Value("${padishahTax}")
    private BigDecimal padishahTax;


    public List<String> getCustomers() {
        return List.of(customers);
    }
}
