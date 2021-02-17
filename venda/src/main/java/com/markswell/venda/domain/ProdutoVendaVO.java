package com.markswell.venda.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.markswell.venda.entity.Venda;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "idProduto", "quantidade", "venda"})
public class ProdutoVendaVO extends RepresentationModel<ProdutoVendaVO> implements Serializable {

    private static final long serialVersionUID = -261544976238939299L;

    @JsonProperty("id")
    private Long id;
    @JsonProperty("idProduto")
    private Long idProduto;
    @JsonProperty("quantidade")
    private Integer quantidade;
    @JsonProperty("venda")
    private Venda venda;
}
