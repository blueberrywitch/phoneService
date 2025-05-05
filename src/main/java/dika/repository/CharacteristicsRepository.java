package dika;

import dika.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacteristicsRepository extends JpaRepository<Phone, Long> {
    List<Phone> findByProcessor(String processor);
    List<Phone> findByScreenDiagonal(String screenDiagonal);
    List<Phone> findByCameraResolution(String cameraResolution);
    List<Phone> findByInternalStorage(String internalStorage);
}
