package com.asapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;

@Data
public class ProductsDTO {

    @JsonProperty("product_name")
    String productName;
    @JsonProperty("product_qty")
    int productQty;
    @JsonProperty("product_descr")
    String productDescription;

}
