package com.bootcamp.msProductosReport.handler;

import com.bootcamp.msProductosReport.models.dto.Credit;
import com.bootcamp.msProductosReport.models.dto.Creditcard;
import com.bootcamp.msProductosReport.models.dto.CustomerDTO;
import com.bootcamp.msProductosReport.models.dto.Producto;
import com.bootcamp.msProductosReport.models.entities.Productreport;
import com.bootcamp.msProductosReport.services.IProductreportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
public class ProductreportHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductreportHandler.class);

    @Autowired
    private IProductreportService service;

    public Mono<ServerResponse> generateReportCustomer(ServerRequest request) {
        Productreport reportProduct = new Productreport();
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        LOGGER.info("El customerIdentityNumber es " + customerIdentityNumber);
        return Mono.just(reportProduct).flatMap(report -> service.getCustomer(customerIdentityNumber)
                        .flatMap(customer -> {
                            reportProduct.setCustomer(CustomerDTO.builder()
                                    .name(customer.getName()).code(customer.getCustomerType().getCode())
                                    .customerIdentityNumber(customer.getCustomerIdentityNumber()).build());
                            return service.getSavingAccount(customerIdentityNumber);
                        }).flatMap(savingAccount -> {
                            if(savingAccount.getAccountNumber()!= null) {
                                reportProduct.getProductos().add(savingAccount);
                            }
                            return service.getFixedTermAccount(customerIdentityNumber);
                         }).flatMap(fixedtermaccount -> {
                            if(fixedtermaccount.getAccountNumber()!= null) {
                                reportProduct.getProductos().add(fixedtermaccount);
                            }
                            return service.getCurrentAccount(customerIdentityNumber).collectList();
                        }).flatMap(currentaccount -> {
                            if(currentaccount.get(0).getAccountNumber()!= null) {
                                reportProduct.getProductos().add(currentaccount);
                            }
                            return service.getCreditCard(customerIdentityNumber);
                        }).flatMap(creditcard -> {
                            if(creditcard.getPan()!= null) {
                                creditcard.setTypeOfAccount("CREDITCARD");
                                reportProduct.getProductos().add(creditcard);
                            }
                            return service.getCredit(customerIdentityNumber).collect(Collectors.toList());
                        })
                        .flatMap(credit -> {
                            if(credit.get(0).getContractNumber() != null) {
                                credit.stream().forEach(creditObjet -> {
                                    creditObjet.setTypeOfAccount("CREDIT");
                                });

                                reportProduct.getProductos().add(credit);
                            }

                            return Mono.just(reportProduct);
                        }))
                        .flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


}
