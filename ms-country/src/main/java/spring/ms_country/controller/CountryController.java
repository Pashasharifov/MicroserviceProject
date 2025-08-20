package spring.ms_country.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.ms_country.model.CountryResponse;
import spring.ms_country.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService countryService;
    @GetMapping
    public List<CountryResponse> getALlAvailableCountries(@RequestParam String currency){
        return countryService.getAllAvailableCountries(currency);
    }
}
