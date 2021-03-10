package com.dev.availabilitytracker.controller;

import com.dev.availabilitytracker.Repository.RickshawRepository;
import com.dev.availabilitytracker.model.Rickshaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RickshawController {

    @Autowired
    RickshawRepository repository;

    @GetMapping(path = {"","/"})
    List<Rickshaw> getRickshawList(){
        return repository.findAll();
    }

    @GetMapping(path = "/range/{lat}/{lng}/{distance}")
    List<Rickshaw> getRickshawWithinRange(@PathVariable double lat, @PathVariable double lng, @PathVariable long distance){
        return repository.findWithinRange(lat,lng,distance);
    }

    @PostMapping(path = {"/rickshaw"})
    Rickshaw addRickshaw(@RequestBody Rickshaw rickshaw){
        return repository.save(rickshaw);
    }
}
