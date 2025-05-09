package dika.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "characteristics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Characteristics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String processor;
    private double screenDiagonal;
    private String cameraResolution;
    private double weight;
    private double batteryCapacity;
    private String internalStorage;
}