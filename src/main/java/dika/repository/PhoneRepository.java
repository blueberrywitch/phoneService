package dika.repository;

import dika.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByBrand(String brand);

    List<Phone> findByModel(String model);

    List<Phone> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Phone> findByCharacteristicsProcessor(String processor);

    List<Phone> findByCharacteristicsScreenDiagonal(String screenDiagonal);

    List<Phone> findByCharacteristicsCameraResolution(String cameraResolution);

    List<Phone> findByCharacteristicsInternalStorage(String internalStorage);

    @Query("SELECT p FROM Phone p " +
            "WHERE (:brand = '' OR p.brand LIKE %:brand%) " +
            "AND (:model = '' OR p.model LIKE %:model%) " +
            "AND (:processor = '' OR p.characteristics.processor LIKE %:processor%) " +
            "AND (:screenDiagonal = '' OR p.characteristics.screenDiagonal LIKE %:screenDiagonal%) " +
            "AND (:cameraResolution = '' OR p.characteristics.cameraResolution LIKE %:cameraResolution%)")
    List<Phone> search(String brand, String model, String processor, String screenDiagonal, String cameraResolution);

}