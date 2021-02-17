package com.markswell.cadastro.resource;

import org.springframework.hateoas.PagedModel;
import com.markswell.cadastro.domain.ProdutoVO;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import com.markswell.cadastro.service.ProdutoService;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.data.domain.Sort.by;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private PagedResourcesAssembler<ProdutoVO> assembler;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ProdutoVO find(@PathVariable  Long id) {
        var produtoVO = produtoService.findById(id);
        produtoVO.add(linkTo(getProdutoResource().find(id)).withSelfRel());
        return produtoVO;
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var order = direction.equals("desc") ? DESC : ASC;
        var pageable = PageRequest.of(page, limit, by(order, "nome"));
        var produtoVOS = produtoService.findAll(pageable).map(p -> p.add(linkTo(getProdutoResource().find(p.getId())).withSelfRel()));
        PagedModel<EntityModel<ProdutoVO>> pageModel = assembler.toModel(produtoVOS);
        return ResponseEntity.ok(pageModel);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ProdutoVO create(@RequestBody ProdutoVO produtoVO) {
        var retorno = produtoService.create(produtoVO);
        retorno.add(linkTo(getProdutoResource().find(retorno.getId())).withSelfRel());
        return retorno;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ProdutoVO update(@RequestBody ProdutoVO produtoVO) {
        var retorno = produtoService.update(produtoVO);
        retorno.add(linkTo(getProdutoResource().find(retorno.getId())).withSelfRel());
        return retorno;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return status(NO_CONTENT).build();
    }

    private ProdutoResource getProdutoResource() {
        return methodOn(ProdutoResource.class);
    }

}
