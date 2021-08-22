package com.bootcamp.msProductosReport.models.entities;

import com.bootcamp.msProductosReport.models.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Productreport {

    private String id;

    private CustomerDTO customer;

    private List<Object> productos = new ArrayList<>();
}
