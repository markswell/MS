package com.markswell.venda.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    private static final long serialVersionUID = 3243267902763689023L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;

    @OneToMany(mappedBy = "venda")
    private List<ProdutoVenda> produtosVenda;

    @Column(name = "valor_total")
    private float valorTotal;
}
