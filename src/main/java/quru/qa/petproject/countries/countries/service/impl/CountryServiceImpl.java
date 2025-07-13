package quru.qa.petproject.countries.countries.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import quru.qa.petproject.countries.countries.db.entity.CountryEntity;
import quru.qa.petproject.countries.countries.db.repository.CountryRepository;
import quru.qa.petproject.countries.countries.dto.Country;
import quru.qa.petproject.countries.countries.mapper.CountryMapper;
import quru.qa.petproject.countries.countries.service.CountryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;
    private final CountryMapper mapper;

    @Override
    public List<Country> getAll() {
        return repository.findAll()
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    @Override
    public Country add(Country country) {
        CountryEntity countryEntity = repository.save(mapper.toEntity(country));
        return mapper.toDto(countryEntity);
    }

    @Override
    public Country updateName(Country country) {
        CountryEntity entity = repository.findById(country.id())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Country not found with id: " + country.id())
            );

        mapper.updateEntityFromDto(country, entity);

        CountryEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }
}
