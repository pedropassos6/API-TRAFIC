package com.pedro.transito.domain.service;

import com.pedro.transito.domain.model.Autuacao;
import com.pedro.transito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistroAutuacaoService {

    private RegistroVeiculoService registroVeiculoService;

    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao novaAutuacao){
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);

        return veiculo.adicionarAutuacao(novaAutuacao);
    }
}
