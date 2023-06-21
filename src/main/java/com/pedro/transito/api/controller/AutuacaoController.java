package com.pedro.transito.api.controller;

import com.pedro.transito.api.assembler.AutuacaoAssembler;
import com.pedro.transito.api.representationmodel.AutuacaoModel;
import com.pedro.transito.api.representationmodel.input.AutuacaoInput;
import com.pedro.transito.domain.model.Autuacao;
import com.pedro.transito.domain.model.Veiculo;
import com.pedro.transito.domain.service.RegistroAutuacaoService;
import com.pedro.transito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class AutuacaoController {

    private final RegistroAutuacaoService registroAutuacaoService;
    private final AutuacaoAssembler autuacaoAssembler;
    private final RegistroVeiculoService registroVeiculoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoModel registrar(@PathVariable Long veiculoId, @Valid @RequestBody AutuacaoInput autuacaoInput){
        Autuacao novaAutuacao = autuacaoAssembler.toEntity(autuacaoInput);
        Autuacao autuacaoRegistrada = registroAutuacaoService.registrar(veiculoId, novaAutuacao);
        return autuacaoAssembler.toModel(autuacaoRegistrada);
    }

    @GetMapping
    public List<AutuacaoModel> listar(@PathVariable Long veiculoId){
        Veiculo veiuculo = registroVeiculoService.buscar(veiculoId);
        return autuacaoAssembler.toCollectionModel(veiuculo.getAutoacoes());

    }
}
