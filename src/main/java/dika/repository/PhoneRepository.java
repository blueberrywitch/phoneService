package dika;

import dika.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByBrand(String brand);
    List<Phone> findByModel(String model);
    List<Phone> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Phone> findByCharacteristicsProcessor(String processor);
    List<Phone> findByCharacteristicsScreenDiagonal(String screenDiagonal);
    List<Phone> findByCharacteristicsCameraResolution(String cameraResolution);
    List<Phone> findByCharacteristicsInternalStorage(String internalStorage);
}
