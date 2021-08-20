package com.bootcamp.msProductosReport.repositories;

import com.bootcamp.msProductosReport.models.entities.Productreport;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductreportRepository extends ReactiveMongoRepository<Productreport,String> {
    Mono<Productreport> findByCustomer_CustomerIdentityNumber(String customerIdentityNumber);
}
