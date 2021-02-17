package com.markswell.cadastro.converter;

import com.markswell.cadastro.domain.ProdutoVO;
import com.markswell.cadastro.entity.Produto;
import org.modelmapper.ModelMapper;

public class ProdutoConverter {

    public static Produto produtoParse(ProdutoVO produtoVO) {
        return new ModelMapper().map(produtoVO, Produto.class);
    }

    public static ProdutoVO produtoVOParse(Produto produto) {
        return new ModelMapper().map(produto, ProdutoVO.class);
    }
}
