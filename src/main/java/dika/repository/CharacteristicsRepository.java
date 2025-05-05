package dika.repository;

import dika.model.Characteristics;
import dika.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacteristicsRepository extends JpaRepository<Characteristics, Long> {
    List<Phone> findByProcessor(String processor);

    List<Phone> findByScreenDiagonal(String screenDiagonal);

    List<Phone> findByCameraResolution(String cameraResolution);

    List<Phone> findByInternalStorage(String internalStorage);
}
