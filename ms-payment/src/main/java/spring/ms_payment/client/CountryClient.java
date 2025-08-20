package spring.ms_payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.ms_payment.model.client.CountryDto;

import java.util.List;

@FeignClient(name = "ms-country", url = "${client.ms-country.endpoint}")
public interface CountryClient {

    @GetMapping("/api/countries")
    List<CountryDto> getAllAvailableCountries(@RequestParam String currency);
}
