package com.markswll.cadastro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({"id", "nome", "preco", "estoque"})
public class ProdutoVO extends RepresentationModel<ProdutoVO> implements Serializable {

    private static final long serialVersionUID = 1412592328695219599L;

    public ProdutoVO() {}

    @JsonProperty("id")
    private Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("preco")
    private Double preco;
    @JsonProperty("estoque")
    private Integer estoque;
}


