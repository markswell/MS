package com.markswell.venda.resource;

import com.markswell.venda.domain.VendaVO;
import com.markswell.venda.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/venda")
public class VendaResource {

    @Autowired
    private VendaService vendaService;
    @Autowired
    private PagedResourcesAssembler<VendaVO> assembler;

    @GetMapping(value = "/{id}", produces = "application/json")
    public VendaVO find(@PathVariable Long id) {
        var vendaVO = vendaService.findById(id);
        vendaVO.add(linkTo(getProdutoResource().find(id)).withSelfRel());
        return vendaVO;
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var order = direction.equals("desc") ? DESC : ASC;
        var pageable = PageRequest.of(page, limit, by(order, "nome"));
        var vendaVOS = vendaService.findAll(pageable).map(p -> p.add(linkTo(getProdutoResource().find(p.getId())).withSelfRel()));
        PagedModel<EntityModel<VendaVO>> pageModel = assembler.toModel(vendaVOS);
        return ResponseEntity.ok(pageModel);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public VendaVO create(@RequestBody VendaVO produtoVO) {
        var retorno = vendaService.create(produtoVO);
        retorno.add(linkTo(getProdutoResource().find(retorno.getId())).withSelfRel());
        return retorno;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public VendaVO update(@RequestBody VendaVO vendaVO) {
        var retorno = vendaService.update(vendaVO);
        retorno.add(linkTo(getProdutoResource().find(retorno.getId())).withSelfRel());
        return retorno;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        vendaService.delete(id);
        return status(NO_CONTENT).build();
    }

    private VendaResource getProdutoResource() {
        return methodOn(VendaResource.class);
    }

}
