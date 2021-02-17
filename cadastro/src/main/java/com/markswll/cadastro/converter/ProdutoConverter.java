package com.markswll.cadastro.converter;

import com.markswll.cadastro.domain.ProdutoVO;
import com.markswll.cadastro.entity.Produto;
import org.modelmapper.ModelMapper;

public class ProdutoConverter {

    public static Produto produtoParse(ProdutoVO produtoVO) {
        return new ModelMapper().map(produtoVO, Produto.class);
    }

    public static ProdutoVO produtoVOParse(Produto produto) {
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
