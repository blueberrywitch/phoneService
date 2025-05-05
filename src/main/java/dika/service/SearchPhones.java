package dika.service;

import dika.model.Phone;

import java.util.List;

public interface SearchPhones {
    List<Phone> searchAndSortPhones(
            List<String> brands,
            List<String> processor,
            List<String> diag,
            List<String> internalStorage,
            List<String> batteryCapacity,
            Double priceMin,
            Double priceMax,
            String model,
            String sortDirection
    );
}
