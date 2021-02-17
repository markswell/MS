package com.markswell.venda.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "estoque"})
public class ProdutoVO extends RepresentationModel<VendaVO> implements Serializable {

    private static final long serialVersionUID = 3425358828606551012L;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("estoque")
    private Integer estoque;

}
