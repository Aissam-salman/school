package com.forme.school.controller;

import com.forme.school.entity.Rate;
import com.forme.school.service.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/rates")
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping
    public ResponseEntity<Rate> createRate(@RequestBody Rate rate) {
        Rate createdRate = rateService.create(rate);
        return new ResponseEntity<>(createdRate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rate> getRateById(@PathVariable Long id) {
        Rate rate = rateService.findRateById(id);
        if (rate != null) {
            return ResponseEntity.ok(rate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Rate>> getAllRates() {
        List<Rate> rates = rateService.findAll();
        return ResponseEntity.ok(rates);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rate> updateRate(@PathVariable Long id, @RequestBody double newValue) {
        Rate updatedRate = rateService.updateValue(id, newValue);
        if (updatedRate != null) {
            return ResponseEntity.ok(updatedRate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable Long id) {
        Rate rate = rateService.findRateById(id);
        if(Objects.nonNull(rate)) {
            rateService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
