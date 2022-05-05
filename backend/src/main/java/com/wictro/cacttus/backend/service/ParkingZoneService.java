package com.wictro.cacttus.backend.service;

import com.wictro.cacttus.backend.dto.parkingSlot.ParkingSlotDto;
import com.wictro.cacttus.backend.dto.parkingZone.ParkingZoneDto;
import com.wictro.cacttus.backend.exception.CityWithIdDoesNotExistException;
import com.wictro.cacttus.backend.exception.ParkingZoneWithIdDoesNotExistException;
import com.wictro.cacttus.backend.model.City;
import com.wictro.cacttus.backend.model.ParkingZone;
import com.wictro.cacttus.backend.repository.CityRepository;
import com.wictro.cacttus.backend.repository.ParkingZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingZoneService {
    private final CityRepository cityRepository;
    private final ParkingZoneRepository parkingZoneRepository;

    public ParkingZoneService(CityRepository cityRepository, ParkingZoneRepository parkingZoneRepository) {
        this.cityRepository = cityRepository;
        this.parkingZoneRepository = parkingZoneRepository;
    }

    public ParkingZoneDto saveParkingZone(ParkingZoneDto data) throws CityWithIdDoesNotExistException {
        ParkingZone zone = new ParkingZone();

        Optional<City> city = cityRepository.findById(data.getCityId());

        if(city.isEmpty())
            throw new CityWithIdDoesNotExistException(data.getCityId());

        zone.setCity(city.get());

        if(data.getName() != null)
            zone.setName(data.getName());
        if(data.getLocationLatitude() != null)
            zone.setLocationLatitude(data.getLocationLatitude());
        if(data.getLocationLongitude() != null)
            zone.setLocationLongitude(data.getLocationLongitude());
        if(data.getOperating() != null)
            zone.setOperating(data.getOperating());
        else
            zone.setOperating(true);

        zone.setActive(true);

        parkingZoneRepository.save(zone);

        ParkingZoneDto response = new ParkingZoneDto();
        response.setId(zone.getId());
        response.setCityId(city.get().getId());
        response.setName(zone.getName());
        response.setLocationLatitude(zone.getLocationLatitude());
        response.setLocationLongitude(zone.getLocationLongitude());
        response.setId(zone.getId());
        response.setOperating(zone.getOperating());
        return response;
    }

    public List<ParkingZoneDto> getAllParkingZones() {
        return parkingZoneRepository.getAllParkingZonesAsDto();
    }

    public ParkingZone getParkingZoneWithId(Long id) throws ParkingZoneWithIdDoesNotExistException {
        Optional<ParkingZone> zone = parkingZoneRepository.findById(id);

        if(zone.isEmpty())
            throw new ParkingZoneWithIdDoesNotExistException(id);

        return zone.get();
    }

    public ParkingZoneDto editParkingZone(Long id, ParkingZoneDto newZone) throws ParkingZoneWithIdDoesNotExistException, CityWithIdDoesNotExistException {
        Optional<ParkingZone> zone = parkingZoneRepository.findById(id);

        if(zone.isEmpty())
            throw new ParkingZoneWithIdDoesNotExistException(id);

        if(newZone.getName() != null)
            zone.get().setName(newZone.getName());

        if(newZone.getCityId() != null) {
            Optional<City> city = cityRepository.findById(newZone.getCityId());
            if(city.isEmpty())
                throw new CityWithIdDoesNotExistException(newZone.getCityId());
            zone.get().setCity(city.get());
        }

        if(newZone.getLocationLatitude() != null){
            zone.get().setLocationLatitude(newZone.getLocationLatitude());
        }

        if(newZone.getLocationLongitude() != null){
            zone.get().setLocationLongitude(newZone.getLocationLongitude());
        }

        if(newZone.getOperating() != null){
            zone.get().setOperating(newZone.getOperating());
        }

        parkingZoneRepository.save(zone.get());

        return parkingZoneRepository.getParkingZoneDtoBy(id);
    }

    public void deleteParkingZoneWithId(Long id) throws ParkingZoneWithIdDoesNotExistException {
        Optional<ParkingZone> zone = parkingZoneRepository.findById(id);

        if(zone.isEmpty())
            throw new ParkingZoneWithIdDoesNotExistException(id);

        zone.get().setActive(false);

        parkingZoneRepository.save(zone.get());
    }

}
