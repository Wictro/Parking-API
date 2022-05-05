package com.wictro.cacttus.backend.service;

import com.wictro.cacttus.backend.exception.CityWithIdDoesNotExistException;
import com.wictro.cacttus.backend.model.City;
import com.wictro.cacttus.backend.repository.CityRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }


    public City getCityWithId(Long id) throws CityWithIdDoesNotExistException {
        Optional<City> city = cityRepository.findById(id);

        if(city.isEmpty())
            throw new CityWithIdDoesNotExistException(id);

        return city.get();
    }
}
