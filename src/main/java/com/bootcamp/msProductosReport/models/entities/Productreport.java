package com.bootcamp.msProductosReport.models.entities;

import com.bootcamp.msProductosReport.models.dto.Customer;
import com.bootcamp.msProductosReport.models.dto.Producto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "productreport")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Productreport {

    @Id
    private String id;

    private Customer customer;

    private List<Producto> producto;
}
