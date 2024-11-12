package com.forme.school.service;

import com.forme.school.entity.Rate;
import com.forme.school.repository.RateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateService {
    private final RateRepository rateRepository;

    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Rate create(final Rate rate) {
        return rateRepository.save(rate);
    }

    public Rate findRateById(final Long id) {
        return rateRepository.findById(id).orElse(null);
    }

    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    public Rate updateValue(final Long rateId, final double value) {
        Rate rateToUpdate = findRateById(rateId);

        if (rateToUpdate != null && value != rateToUpdate.getRate_v()) {
            rateToUpdate.setRate_v(value);
            return rateRepository.save(rateToUpdate);
        }
        return null;
    }

    public void delete(final Long id) {
        try {
            rateRepository.deleteById(id);
        } catch (final Exception e) {
            throw new RuntimeException("Erreur lors de la deletion du rate", e);
        }
    }
}
