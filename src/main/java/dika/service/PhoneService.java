package dika;

import dika.model.Phone;

import java.util.List;

public interface PhoneService {
    Phone createPhone(Phone phone);

    Phone updatePhone(Long id, Phone phone);

    Phone getPhoneById(Long id);

    List<Phone> getAllPhones();

    void deletePhone(Long id);

    List<Phone> searchPhones(String brand, String model, String processor, String screenDiagonal, String cameraResolution);

    List<Phone> filterPhonesByPrice(Double minPrice, Double maxPrice);

    List<Phone> filterPhonesByProcessor(String processor);

    List<Phone> filterPhonesByScreenDiagonal(String screenDiagonal);

    List<Phone> filterPhonesByCameraResolution(String cameraResolution);

    List<Phone> filterPhonesByInternalStorage(String internalStorage);

    List<Phone> filterPhonesByBrand(String brand);
}
