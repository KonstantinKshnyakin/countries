package quru.qa.petproject.countries.countries.controller;

import guru.qa.countries.xml.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import quru.qa.petproject.countries.countries.dto.Country;
import quru.qa.petproject.countries.countries.mapper.CountryMapper;
import quru.qa.petproject.countries.countries.service.CountryService;

import java.util.List;

import static quru.qa.petproject.countries.countries.config.AppConfig.SOAP_NAMESPACE;

@Endpoint
@RequiredArgsConstructor
public class CountrySoapController {

    private final CountryService countryService;
    private final CountryMapper mapper;

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "getAllCountriesRequest")
    @ResponsePayload
    public GetAllCountriesResponse getAll() {
        List<Country> countries = countryService.getAll();
        GetAllCountriesResponse response = new GetAllCountriesResponse();
        response.getCountries().addAll(
            countries.stream()
                .map(mapper::toXml)
                .toList()
        );
        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "addCountryRequest")
    @ResponsePayload
    public AddCountryResponse add(@RequestPayload AddCountryRequest request) {
        Country country = mapper.toDto(request.getCountry());
        Country saved = countryService.add(country);
        AddCountryResponse response = new AddCountryResponse();
        response.setCountry(mapper.toXml(saved));
        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "updateCountryNameRequest")
    @ResponsePayload
    public UpdateCountryNameResponse updateName(@RequestPayload UpdateCountryNameRequest request) {
        Country country = mapper.toDto(request.getCountry());
        Country updated = countryService.updateName(country);
        UpdateCountryNameResponse response = new UpdateCountryNameResponse();
        response.setCountry(mapper.toXml(updated));
        return response;
    }
}
