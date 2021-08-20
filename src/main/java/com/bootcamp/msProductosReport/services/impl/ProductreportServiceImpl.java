package com.bootcamp.msProductosReport.services.impl;

import com.bootcamp.msProductosReport.models.entities.Productreport;
import com.bootcamp.msProductosReport.repositories.ProductreportRepository;
import com.bootcamp.msProductosReport.services.IProductreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductreportServiceImpl implements IProductreportService {

    @Autowired
    private ProductreportRepository repository;

    @Override
    public Mono<Productreport> create(Productreport o) {
        return repository.save(o);
    }

    @Override
    public Flux<Productreport> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Productreport> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<Productreport> update(Productreport o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(Productreport o) {
        return repository.delete(o);
    }

    @Override
    public Mono<Productreport> getReporByCustomer(String customerIdentityNumber) {
        return repository.findAll()
                .filter(report -> report.getCustomer().getCustomerIdentityNumber().equals(customerIdentityNumber))
                .single().switchIfEmpty(Mono.just(Productreport.builder()
                        .customer(null).build()));
    }

    @Override
    public Mono<Productreport> findByCustomer_CustomerIdentityNumber(String customerIdentityNumber) {
        return repository.findByCustomer_CustomerIdentityNumber(customerIdentityNumber);
    }
}
