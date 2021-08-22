package com.bootcamp.msProductosReport.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * The DTO of All pasive product.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private String typeOfProduct;

    private String accountNumber;

    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();
}
