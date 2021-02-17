package com.markswell.venda.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import com.markswell.venda.entity.ProdutoVenda;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "data", "produtosVenda", "valorTotal"})
public class VendaVO extends RepresentationModel<VendaVO> implements Serializable {

    private static final long serialVersionUID = 429271606982284726L;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("data ")
    private Date data;
    @JsonProperty("produtosVenda")
    private List<ProdutoVenda> produtosVenda;
    @JsonProperty("valorTotal")
    private float valorTotal;
}
