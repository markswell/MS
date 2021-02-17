package com.markswll.cadastro.service;


import org.springframework.data.domain.Page;
import com.markswll.cadastro.domain.ProdutoVO;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.markswll.cadastro.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.markswll.cadastro.exception.ResourceNotFoundExecption;
import static com.markswll.cadastro.converter.ProdutoConverter.produtoParse;
import static com.markswll.cadastro.converter.ProdutoConverter.produtoVOParse;
import static java.lang.String.format;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoVO create(ProdutoVO produtoVO) {
        return produtoVOParse(produtoRepository.save(produtoParse(produtoVO)));
    }

    public Page<ProdutoVO> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable).map(m -> produtoVOParse(m));
    }

    public ProdutoVO findById(Long id) {
        var retorno = produtoRepository.findById(id)
                                            .orElseThrow(() -> getResourceNotFoundExecption(id));
        return produtoVOParse(retorno);
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
