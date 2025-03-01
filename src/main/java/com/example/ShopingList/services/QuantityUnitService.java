package com.example.ShopingList.services;


import com.example.ShopingList.repos.QuantityUnitRepository;
import com.example.ShopingList.entities.QuantityUnit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuantityUnitService {

    private final QuantityUnitRepository unitRepository;

    public QuantityUnitService(QuantityUnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    // Tüm QuantityUnit'leri getirir
    public List<QuantityUnit> getAllQuantityUnits() {
        return unitRepository.findAll();  // Veritabanından tüm QuantityUnitleri alır
    }

    // Id'ye göre bir QuantityUnit'i getirir
    public QuantityUnit getQuantityUnitById(Long id) {
        Optional<QuantityUnit> quantityUnit = unitRepository.findById(id);
        return quantityUnit.orElse(null);  // Eğer id ile bulunan bir öğe yoksa, null döner
    }

    // Yeni bir QuantityUnit ekler
    public void addQuantityUnit(QuantityUnit quantityUnit) {
        unitRepository.save(quantityUnit);  // Veritabanına yeni QuantityUnit kaydeder
    }

    // Id'ye göre bir QuantityUnit'i siler
    public void deleteQuantityUnit(Long id) {
        unitRepository.deleteById(id);  // Veritabanından belirtilen id'ye sahip QuantityUnit'i siler
    }
}