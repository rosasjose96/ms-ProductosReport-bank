package com.bootcamp.msProductosReport.config;

import com.bootcamp.msProductosReport.handler.ProductreportHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * The type Router config.
 */
@Configuration
public class RouterConfig {

    /**
     * Routes router function.
     *
     * @param productreportHandler the credit card handler
     * @return the router function
     */
    @Bean
    public RouterFunction<ServerResponse> routes(ProductreportHandler productreportHandler){

        return route(GET("/api/report/customer/{customerIdentityNumber}"), productreportHandler::generateReportCustomer);

    }
}
