package com.bootcamp.msProductosReport.services;

import com.bootcamp.msProductosReport.models.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductreportService {

    Mono<Savingaccount> getSavingAccount(String customerIdentityNumber);

    Flux<Currentaccount> getCurrentAccount(String customerIdentityNumber);

    Mono<Fixedtermaccount> getFixedTermAccount(String customerIdentityNumber);

    Flux<Credit> getCredit(String customerIdentityNumber);

    Mono<Creditcard> getCreditCard(String customerIdentityNumber);

    Mono<Customer> getCustomer(String customerIdentityNumber);

}
