package com.wictro.cacttus.backend.repository;

import com.wictro.cacttus.backend.model.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {
    City findCityByName(String name);
    List<City> findAll();
}
