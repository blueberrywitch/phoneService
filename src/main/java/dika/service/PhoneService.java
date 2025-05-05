package dika.service;

import dika.model.Phone;

import java.io.IOException;
import java.util.List;

public interface PhoneService {
    Phone createPhone(Phone phone);

    void updatePhone(Long id, Phone phone);

    Phone getPhoneById(Long id);

    List<Phone> getAllPhones();

    void deletePhone(Long id);

    void saveCSV(String csvContent) throws IOException;

}
