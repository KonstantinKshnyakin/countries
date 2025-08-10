package quru.qa.petproject.countries.countries.mapper;

import org.mapstruct.*;
import quru.qa.petproject.countries.countries.db.entity.CountryEntity;
import quru.qa.petproject.countries.countries.dto.Country;
import quru.qa.petproject.countries.grpc.AddCountryRequest;
import quru.qa.petproject.countries.grpc.CountriesResponse;
import quru.qa.petproject.countries.grpc.CountryRequest;
import quru.qa.petproject.countries.grpc.CountryResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country toDto(CountryEntity entity);

    CountryEntity toEntity(Country dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(Country dto, @MappingTarget CountryEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CountryRequest dto, @MappingTarget CountryEntity entity);

    @Mapping(target = "id", ignore = true)
    CountryEntity toCountry(AddCountryRequest request);

    CountryResponse toProtoResponse(CountryEntity country);

    List<CountryResponse> toProtoResponseList(List<CountryEntity> countries);

    default CountriesResponse toProtoResponse(List<CountryEntity> countries) {
        List<CountryResponse> responses = toProtoResponseList(countries);
        return CountriesResponse.newBuilder()
            .addAllCountries(responses)
            .build();
    }

    guru.qa.countries.xml.Country toXml(Country dto);

    Country toDto(guru.qa.countries.xml.Country xml);
}
