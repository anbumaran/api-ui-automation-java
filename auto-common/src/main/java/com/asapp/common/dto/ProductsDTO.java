package com.asapp.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductsDTO {

    @JsonProperty("product_name")
    String productName;
    @JsonProperty("product_qty")
    int productQty;
    @JsonProperty("product_descr")
    String productDescription;
    @JsonProperty("product_category")
    String productCategory;

}
