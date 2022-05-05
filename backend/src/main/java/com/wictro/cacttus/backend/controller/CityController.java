package com.wictro.cacttus.backend.controller;

import com.wictro.cacttus.backend.dto.http.ErrorResponse;
import com.wictro.cacttus.backend.dto.http.GenericJsonResponse;
import com.wictro.cacttus.backend.exception.CityWithIdDoesNotExistException;
import com.wictro.cacttus.backend.model.City;
import com.wictro.cacttus.backend.service.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    //get all the cities
    @GetMapping
    public GenericJsonResponse<List<City>> getAllCities(){
        return new GenericJsonResponse<List<City>>(true, cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public GenericJsonResponse<?> getCityWithId(@PathVariable Long id, HttpServletResponse response){
        try{
            return new GenericJsonResponse<City>(true, cityService.getCityWithId(id));
        }
        catch (CityWithIdDoesNotExistException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return new GenericJsonResponse<>(false, new ErrorResponse(e.getLocalizedMessage()));
        }
    }
}
