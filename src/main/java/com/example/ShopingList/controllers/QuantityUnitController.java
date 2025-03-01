package com.example.ShopingList.controllers;

import com.example.ShopingList.services.QuantityUnitService;
import com.example.ShopingList.entities.QuantityUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quantity-units")
public class QuantityUnitController {

    private final QuantityUnitService quantityUnitService;

    public QuantityUnitController(QuantityUnitService quantityUnitService) {
        this.quantityUnitService = quantityUnitService;
    }

    @GetMapping
    public ResponseEntity<List<QuantityUnit>> getAllQuantityUnits() {
        List<QuantityUnit> quantityUnits = quantityUnitService.getAllQuantityUnits();
        if (quantityUnits.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quantityUnits, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuantityUnit> getQuantityUnitById(@PathVariable("id") Long id) {
        QuantityUnit quantityUnit = quantityUnitService.getQuantityUnitById(id);
        if (quantityUnit == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quantityUnit, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<QuantityUnit> addQuantityUnit(@RequestBody QuantityUnit quantityUnit) {
        if (quantityUnit == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        quantityUnitService.addQuantityUnit(quantityUnit);
        return new ResponseEntity<>(quantityUnit, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuantityUnit(@PathVariable Long id) {
        // Id'ye sahip bir QuantityUnit var mı kontrol et
        QuantityUnit quantityUnit = quantityUnitService.getQuantityUnitById(id);
        if (quantityUnit == null) {
            // Eğer yoksa, Not Found (404) döner
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404: Verilen id'ye sahip bir öğe yok
        }

        // Var ise, silme işlemini yap
        quantityUnitService.deleteQuantityUnit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Silme işlemi başarıyla tamamlandı
    }
}