package spring.ms_country.service;

import org.springframework.stereotype.Service;
import spring.ms_country.model.CountryResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    public List<CountryResponse> getAllAvailableCountries(String currency){
        if(currency.equals("USD")){
            return List.of(new CountryResponse("USA", BigDecimal.valueOf(5000)),
                    new CountryResponse("GER", BigDecimal.TEN));
        }
        return new ArrayList<>();
    }
}
