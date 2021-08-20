package com.bootcamp.msProductosReport.services;

import com.bootcamp.msProductosReport.models.entities.Productreport;
import reactor.core.publisher.Mono;

public interface IProductreportService extends ICRUDService<Productreport,String> {

    Mono<Productreport> getReporByCustomer(String customerIdentityNumber);

    Mono<Productreport> findByCustomer_CustomerIdentityNumber(String customerIdentityNumber);
}
