package com.pedro.transito.api.assembler;

import com.pedro.transito.api.representationmodel.VeiculoModel;
import com.pedro.transito.api.representationmodel.input.VeiculoInput;
import com.pedro.transito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class VeiculoAssembler {

    private final ModelMapper modelMapper;

    public Veiculo toEntity(VeiculoInput veiculoInput){
        return modelMapper.map(veiculoInput, Veiculo.class);
    }

    public VeiculoModel toModel(Veiculo veiculo){
        return modelMapper.map(veiculo, VeiculoModel.class);
    }

    public List<VeiculoModel> toColletionModel(List<Veiculo> veiculos){
        return veiculos.stream().map(this::toModel).toList();
    }
}
