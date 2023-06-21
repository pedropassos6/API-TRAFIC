package com.pedro.transito.api.controller;

import com.pedro.transito.api.assembler.VeiculoAssembler;
import com.pedro.transito.api.representationmodel.VeiculoModel;
import com.pedro.transito.api.representationmodel.input.VeiculoInput;
import com.pedro.transito.domain.model.Veiculo;
import com.pedro.transito.domain.repository.VeiculoRepository;
import com.pedro.transito.domain.service.ApreensaoVeiculoService;
import com.pedro.transito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
@AllArgsConstructor
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final VeiculoAssembler veiculoAssembler;
    private final ApreensaoVeiculoService apreensaoVeiculoService;

    @GetMapping
    public List<VeiculoModel> listar(){
        return veiculoAssembler.toColletionModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoModel> buscar(@PathVariable Long veiculoId){
        return veiculoRepository.findById(veiculoId)
                .map(veiculoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VeiculoModel cadastrar(@Valid @RequestBody VeiculoInput veiculoInput){
        Veiculo novoVeiculo = veiculoAssembler.toEntity(veiculoInput);
        Veiculo veiculoCadastrado = registroVeiculoService.cadastrar(novoVeiculo);

        return veiculoAssembler.toModel(veiculoCadastrado);

    }

    @PutMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(@PathVariable Long veiculoId){
        apreensaoVeiculoService.apreender(veiculoId);
    }

    @DeleteMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerApreensao(@PathVariable Long veiculoId){
        apreensaoVeiculoService.removerApreensao(veiculoId);
    }

}
