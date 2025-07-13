package quru.qa.petproject.countries.countries.mapper;

import org.mapstruct.*;
import quru.qa.petproject.countries.countries.db.entity.CountryEntity;
import quru.qa.petproject.countries.countries.dto.Country;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country toDto(CountryEntity entity);

    CountryEntity toEntity(Country dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(Country dto, @MappingTarget CountryEntity entity);
}
