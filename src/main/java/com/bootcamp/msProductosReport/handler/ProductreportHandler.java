package com.bootcamp.msProductosReport.handler;

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

    public Mono<ServerResponse> findReportById(ServerRequest request){
        return service.findById(request.pathVariable("id"))
                .flatMap(report -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(report)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAllReports(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Productreport.class);
    }

    public Mono<ServerResponse> findByAccountNumber(ServerRequest request) {
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        LOGGER.info("El customerIdentityNumber es " + customerIdentityNumber);
        return service.findByCustomer_CustomerIdentityNumber(customerIdentityNumber)
                        .flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> newReport(ServerRequest request){

        Mono<Productreport> productreportMono = request.bodyToMono(Productreport.class);

        return productreportMono.flatMap( Request -> service.create(Request))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)));
    }

    public Mono<ServerResponse> updateProductReport(ServerRequest request){
        Mono<Productreport> productreportMono = request.bodyToMono(Productreport.class);
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");

        return service.findByCustomer_CustomerIdentityNumber(customerIdentityNumber)
                .zipWith(productreportMono, (db,req) -> {
                    db.setProducto(req.getProducto());
                    return db;
                }).flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.update(c),Productreport.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteProductFromReport(ServerRequest request){

        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        String accountNumber = request.pathVariable("accountNumber");

        return service.findByCustomer_CustomerIdentityNumber(customerIdentityNumber)
                .flatMap(customerReport -> {
                   Producto producto = customerReport.getProducto().stream()
                            .filter(product -> product.getAccountNumber().equals(accountNumber))
                           .collect(Collectors.toList()).get(0);
                    customerReport.getProducto().remove(producto);
                   return Mono.just(customerReport);
                }).then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }


}
