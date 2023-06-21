package com.pedro.transito.api.representationmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pedro.transito.domain.model.Proprietario;
import com.pedro.transito.domain.model.StatusVeiculo;
import com.pedro.transito.domain.validation.ValidationGroup;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class VeiculoModel {

    private Long id;
    private ProprietarioResumoModel proprietario;
    private String marca;
    private String modelo;
    private String placa;
    private StatusVeiculo status;
    private OffsetDateTime dataCadastro;
    private OffsetDateTime dataApreensao;
}
