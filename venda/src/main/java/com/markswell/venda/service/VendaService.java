package com.markswell.venda.service;

import com.markswell.venda.converter.VOConverter;
import com.markswell.venda.domain.VendaVO;
import com.markswell.venda.entity.ProdutoVenda;
import com.markswell.venda.entity.Venda;
import com.markswell.venda.exception.ResourceNotFoundExecption;
import com.markswell.venda.repository.ProdutoVendaRepository;
import com.markswell.venda.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    private VOConverter<Venda, VendaVO> voConverter;

    public VendaVO create(VendaVO vendaVO) {
        var venda = vendaRepository.save(voConverter.voParse(vendaVO, new Venda()));
        var produtosVenda = vendaVO.getProdutosVenda();

        produtosVenda.forEach(p -> {
            p.setVenda(venda);
            produtoVendaRepository.save(p);
        });
        return voConverter.entityParse(venda, new VendaVO());
    }

    public Page<VendaVO> findAll(Pageable pageable) {
        return vendaRepository.findAll(pageable).map(m -> voConverter.entityParse(m, new VendaVO()));
    }

    public VendaVO findById(Long id) {
        var retorno = vendaRepository.findById(id)
                .orElseThrow(() -> getResourceNotFoundExecption(id));
        return voConverter.entityParse(retorno, new VendaVO());
    }

    private ResourceNotFoundExecption getResourceNotFoundExecption(Long id) {
        return new ResourceNotFoundExecption(format("Produto com id %d não foi encontrado!", id));
    }

    public VendaVO update(VendaVO produtoVO) {

        var produto = vendaRepository.findById(produtoVO.getId());

        if(!produto.isPresent()) {
            throw getResourceNotFoundExecption(produtoVO.getId());
        }
        return create(produtoVO);
    }

    public void delete(Long id) {
        var produto = vendaRepository.findById(id);
        if(!produto.isPresent()) {
            throw getResourceNotFoundExecption(id);
        }
        vendaRepository.delete(produto.get());
    }

}
