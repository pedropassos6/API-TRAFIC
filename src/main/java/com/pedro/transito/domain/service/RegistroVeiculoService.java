package com.pedro.transito.domain.service;

import com.pedro.transito.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.transito.domain.exception.NegocioException;
import com.pedro.transito.domain.model.Proprietario;
import com.pedro.transito.domain.model.StatusVeiculo;
import com.pedro.transito.domain.model.Veiculo;
import com.pedro.transito.domain.repository.ProprietarioRepository;
import com.pedro.transito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class RegistroVeiculoService {

    private VeiculoRepository veiculoRepository;
    private RegistroProprietarioService registroProprietarioService;

    public Veiculo buscar(Long veiculoId){
        return veiculoRepository.findById(veiculoId).orElseThrow(() -> new EntidadeNaoEncontradaException("Veiculo não encontrado"));
    }

    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo){
        if (novoVeiculo.getId() != null){
            throw new NegocioException("Veiculo a ser cadastrado não deve possuir ID");
        }

        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                        .filter(veiculo -> !veiculo.equals(novoVeiculo)).isPresent();

        if(placaEmUso){
            throw new NegocioException("Placa já cadastrada");
        }

        Proprietario proprietario = registroProprietarioService.buscar(novoVeiculo.getProprietario().getId());

        novoVeiculo.setProprietario(proprietario);
        novoVeiculo.setStatus(StatusVeiculo.REGULAR);
        novoVeiculo.setDataCadastro(OffsetDateTime.now());

        return veiculoRepository.save(novoVeiculo);
    }
}
