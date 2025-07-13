package quru.qa.petproject.countries.countries.service;

import quru.qa.petproject.countries.countries.dto.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAll();

    Country add(Country country);

    Country updateName(Country country);
}
