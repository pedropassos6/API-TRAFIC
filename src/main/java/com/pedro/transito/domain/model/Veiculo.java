package com.pedro.transito.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedro.transito.domain.exception.NegocioException;
import com.pedro.transito.domain.validation.ValidationGroup;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroup.ProprietarioId.class)
    @NotNull
    @ManyToOne
    @JoinColumn(name = "proprietario_id")
    private Proprietario proprietario;

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}[0-9][0-9A-Z][0-9]{2}")
    private String placa;

    @Enumerated(EnumType.STRING)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private StatusVeiculo status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataApreensao;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Autuacao> autoacoes = new ArrayList<>();

    public Autuacao adicionarAutuacao(Autuacao autuacao){
        autuacao.setDataOcorrencia(OffsetDateTime.now());
        autuacao.setVeiculo(this);
        getAutoacoes().add(autuacao);
        return autuacao;
    }

    public void apreender(){
        if(estaApreendido()){
            throw new NegocioException("Veiculo já está apreendido");
        }

        setStatus(StatusVeiculo.APREENDIDO);
        setDataApreensao(OffsetDateTime.now());
    }

    public boolean estaApreendido(){
        return StatusVeiculo.APREENDIDO.equals(getStatus());
    }

    public void removerApreensao(){
        if(naoEstaApreendido()){
            throw new NegocioException("Veiculo não está apreendido");
        }

        setStatus(StatusVeiculo.REGULAR);
        setDataApreensao(null);
    }

    public boolean naoEstaApreendido(){
        return !estaApreendido();
    }
}
