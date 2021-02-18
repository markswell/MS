package com.markswell.cadastro.service;


import com.markswell.cadastro.converter.ProdutoConverter;
import com.markswell.cadastro.exception.ResourceNotFoundExecption;
import com.markswell.cadastro.message.ProdutoSendMessage;
import com.markswell.cadastro.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import com.markswell.cadastro.domain.ProdutoVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoSendMessage produtoSendMessage;

    public ProdutoVO create(ProdutoVO produtoVO) {
        ProdutoVO retorno = ProdutoConverter.produtoVOParse(produtoRepository.save(ProdutoConverter.produtoParse(produtoVO)));
        produtoSendMessage.send(retorno);
        return retorno;

    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(m -> ProdutoConverter.produtoVOParse(m));
    }

    public ProdutoVO findById(Long id) {
        var retorno = produtoRepository.findById(id)
                                            .orElseThrow(() -> getResourceNotFoundExecption(id));
        return ProdutoConverter.produtoVOParse(retorno);
    }

    private ResourceNotFoundExecption getResourceNotFoundExecption(Long id) {
        return new ResourceNotFoundExecption(format("Produto com id %d não foi encontrado!", id));
    }

    public ProdutoVO update(ProdutoVO produtoVO) {

            var produto = produtoRepository.findById(produtoVO.getId());

            if(!produto.isPresent()) {
                throw getResourceNotFoundExecption(produtoVO.getId());
            }
            return create(produtoVO);
    }

    public void delete(Long id) {
        var produto = produtoRepository.findById(id);
        if(!produto.isPresent()) {
            throw getResourceNotFoundExecption(id);
        }
        produtoRepository.delete(produto.get());
    }

}
