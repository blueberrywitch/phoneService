package dika.service.impl;

import dika.exception.PhoneNotFoundException;
import dika.conventer.CSVToModelConverter;
import dika.model.Phone;
import dika.repository.PhoneRepository;
import dika.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;
    private final CSVToModelConverter csvToModelConverter;


    @Override
    public Phone createPhone(Phone phone) {
        phoneRepository.save(phone);
        return null;
    }

    @Override
    public void updatePhone(Long id, Phone phone) {
        Phone existingPhone = getPhoneById(id);
        updateFiles(phone.getBrand(), existingPhone::setBrand);
        updateFiles(phone.getModel(), existingPhone::setModel);
        updateFiles(phone.getPrice(), existingPhone::setPrice);
        updateFiles(phone.getCharacteristics().getProcessor(), existingPhone.getCharacteristics()::setProcessor);
        updateFiles(phone.getCharacteristics().getScreenDiagonal(), existingPhone.getCharacteristics()::setScreenDiagonal);
        updateFiles(phone.getCharacteristics().getCameraResolution(), existingPhone.getCharacteristics()::setCameraResolution);
        updateFiles(phone.getCharacteristics().getInternalStorage(), existingPhone.getCharacteristics()::setInternalStorage);
    }

    @Override
    public Phone getPhoneById(Long id) {
        return phoneRepository.findById(id).orElseThrow(
                () -> new PhoneNotFoundException("Phone not found"));
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
    public void saveCSV(String filePath) throws IOException {
        List<Phone> phones = csvToModelConverter.convertCSVToPhones(CSVToModelConverter.processCSVFile(filePath));
        for (Phone phone : phones) {
            createPhone(phone);
        }
    }

    private <T> void updateFiles(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

}
