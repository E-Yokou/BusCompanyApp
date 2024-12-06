package com.example.BusCompanyApp.services;

import com.example.BusCompanyApp.models.Route;
import com.example.BusCompanyApp.models.Route;
import com.example.BusCompanyApp.repositories.RouteRepository;
import com.example.BusCompanyApp.repositories.RouteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class RouteService {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository RouteRepository) {
        this.routeRepository = RouteRepository;
    }

    public List<Route> findAllRoutes() {
        return StreamSupport.stream(routeRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<Route> findRouteById(Long id) {
        return routeRepository.findById(id);
    }

    @Transactional
    public Route saveRoute(Route Route) {
        return routeRepository.save(Route);
    }

    @Transactional
    public void deleteRouteById(Long id) {
        routeRepository.deleteById(id);
    }

    public List<Route> searchRoutesByKeyword(String keyword) {
        return routeRepository.searchByKeyword(keyword);
    }
}