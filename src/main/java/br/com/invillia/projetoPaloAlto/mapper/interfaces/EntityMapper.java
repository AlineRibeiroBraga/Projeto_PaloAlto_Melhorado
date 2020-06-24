package br.com.invillia.projetoPaloAlto.mapper.interfaces;

import org.mapstruct.Mapping;

import java.util.List;

public interface EntityMapper<D,E> {

    @Mapping(source = "id", target="", ignore = true)
    D toDTO (E entity);

    @Mapping(source = "", target="id", ignore = true)
    E toEntity( D DTO);

    List<E> toListEntity(List<D> listDTO);
    List<D> toListDTO(List<E> listEntity);
}
