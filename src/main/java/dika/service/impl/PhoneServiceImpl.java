package dika.service.impl;

import dika.conventer.CSVToModelConverter;
import dika.enums.InternalStorage;
import dika.enums.PhoneBrand;
import dika.enums.Processor;
import dika.enums.ScreenDiagonal;
import dika.model.Phone;
import dika.repository.PhoneRepository;
import dika.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;

import static dika.specification.PhoneSpecifications.*;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;
    private final CSVToModelConverter converter;

    @Override
    public Phone createPhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public Phone updatePhone(Long id, Phone phone) {
        Phone existing = getPhoneById(id);
        existing.updateFrom(phone);
        return phoneRepository.save(existing);
    }

    @Override
    public Phone getPhoneById(Long id) {
        return phoneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Phone " + id + " not found"));
    }

    @Override
    public List<Phone> getAllPhones() {
        return phoneRepository.findAll();
    }

    @Override
    public void deletePhone(Long id) {
        phoneRepository.delete(getPhoneById(id));
    }

    @Override
    public List<Phone> searchAndSortPhones(
            List<PhoneBrand> brands,
            List<Processor> processors,
            List<ScreenDiagonal> diagonals,
            List<InternalStorage> storages,
            String model,
            String sortDirection
    ) {
        Specification<Phone> spec = Specification.where(null);

        if (brands != null && !brands.isEmpty()) {
            spec = spec.and(byBrands(brands));
        }
        if (processors != null && !processors.isEmpty()) {
            spec = spec.and(byProcessors(processors));
        }
        if (diagonals != null && !diagonals.isEmpty()) {
            spec = spec.and(byScreenDiagonals(diagonals));
        }
        if (storages != null && !storages.isEmpty()) {
            spec = spec.and(byStorages(storages));
        }
        if (model != null && !model.isBlank()) {
            spec = spec.and(byModel(model));
        }

        // dynamic sort direction
        boolean asc = "asc".equalsIgnoreCase(sortDirection);
        return phoneRepository.findAll(spec, asc ?
                PageRequest.of(0, Integer.MAX_VALUE).withSort(org.springframework.data.domain.Sort.by("model").ascending()) :
                PageRequest.of(0, Integer.MAX_VALUE).withSort(org.springframework.data.domain.Sort.by("model").descending())
        ).getContent();
    }

    @Override
    public Page<Phone> getPhonesWithPagination(int page, int size) {
        return phoneRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void saveCSV(String filePath) throws IOException {
        var phones = converter.convertCSVToPhones(
                CSVToModelConverter.processCSVFile(filePath));
        phoneRepository.saveAll(phones);
    }
}