package com.bootcamp.msProductosReport.services.impl;

import com.bootcamp.msProductosReport.models.dto.*;
import com.bootcamp.msProductosReport.models.entities.Productreport;
import com.bootcamp.msProductosReport.services.IProductreportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductreportServiceImpl implements IProductreportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductreportServiceImpl.class);


    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<Customer> getCustomer(final String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("initializing client query");
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder.baseUrl("http://CUSTOMER-SERVICE/customer")
                .build()
                .get()
                .uri("/findCustomerCredit/{customerIdentityNumber}", customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Customer.class))
                .doOnNext(c -> LOGGER.info("Customer Response: Customer={}", c.getName()));
    }

    @Override
    public Mono<Savingaccount> getSavingAccount(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("Searching SavingAccount by: {}" + customerIdentityNumber);
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder.baseUrl("http://SAVINGACCOUNT-SERVICE/api/savingAccount")
                .build()
                .get()
                .uri("/{customerIdentityNumber}", customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Savingaccount.class))
                .switchIfEmpty(Mono.just(Savingaccount.builder().accountNumber(null).build()))
                .doOnNext(c -> LOGGER.info("SavingAccount Response: savingaccount={}", c.getAccountNumber()));
    }

    @Override
    public Flux<Currentaccount> getCurrentAccount(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("Searching CurrentAccount by: {}" + customerIdentityNumber);
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder.baseUrl("http://CURRENTACCOUNT-SERVICE/api/currentAccount")
                .build()
                .get()
                .uri("/{customerIdentityNumber}", customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Currentaccount.class))
                .switchIfEmpty(Mono.just(Currentaccount.builder().accountNumber(null).build()))
                .doOnNext(c -> LOGGER.info("CurrentAccount Response: CurrentAccount={}", c.getAccountNumber()));
    }

    @Override
    public Mono<Fixedtermaccount> getFixedTermAccount(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("Searching FixedTermAccount by: {}" + customerIdentityNumber);
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder.baseUrl("http://FIXEDTERMACCOUNT-SERVICE/api/fixedTermAccound")
                .build()
                .get()
                .uri("/{customerIdentityNumber}", customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Fixedtermaccount.class))
                .switchIfEmpty(Mono.just(Fixedtermaccount.builder().accountNumber(null).build()))
                .doOnNext(c -> LOGGER.info("FixedTermAccount Response: FixedTermAccount={}", c.getAccountNumber()));
    }

    @Override
    public Flux<Credit> getCredit(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("Searching Credit by: {}" + customerIdentityNumber);
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder.baseUrl("http://CREDIT-SERVICE/api/credit")
                .build()
                .get()
                .uri("/customer/{customerIdentityNumber}", customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Credit.class))
                .switchIfEmpty(Mono.just(Credit.builder().contractNumber(null).build()))
                .doOnNext(c -> LOGGER.info("Credit Response: Credit={}", c.getContractNumber()));
    }

    @Override
    public Mono<Creditcard> getCreditCard(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("Searching CreditCard by: {}" + customerIdentityNumber);
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder.baseUrl("http://CREDITCARD-SERVICE/api/creditcard")
                .build()
                .get()
                .uri("/customer/{customerIdentityNumber}", customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Creditcard.class))
                .switchIfEmpty(Mono.just(Creditcard.builder().pan(null).build()))
                .doOnNext(c -> LOGGER.info("CreditCard Response: CreditCard={}", c.getPan()));
    }

    }
