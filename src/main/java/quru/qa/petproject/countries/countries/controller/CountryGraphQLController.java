package quru.qa.petproject.countries.countries.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import quru.qa.petproject.countries.countries.dto.Country;
import quru.qa.petproject.countries.countries.service.CountryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CountryGraphQLController {

    private final CountryService countryService;

    @QueryMapping
    public List<Country> countries() {
        return countryService.getAll();
    }

    @MutationMapping
    public Country addCountry(@Argument("country") Country country) {
        return countryService.add(country);
    }

    @MutationMapping
    public Country updateCountry(@Argument("country") Country country) {
        return countryService.updateName(country);
    }
}
