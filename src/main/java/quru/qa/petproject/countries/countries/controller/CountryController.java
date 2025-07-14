package quru.qa.petproject.countries.countries.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quru.qa.petproject.countries.countries.dto.Country;
import quru.qa.petproject.countries.countries.dto.group.OnCreate;
import quru.qa.petproject.countries.countries.dto.group.OnUpdate;
import quru.qa.petproject.countries.countries.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/all")
    public List<Country> getAll() {
        return countryService.getAll();
    }

    @PostMapping("/add")
    public Country add(@Validated(OnCreate.class) @RequestBody Country country) {
        return countryService.add(country);
    }

    @PatchMapping("/edit")
    public Country updateName(@Validated(OnUpdate.class) @RequestBody Country country) {
        return countryService.updateName(country);
    }
}
