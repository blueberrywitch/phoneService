package dika;

import dika.model.Characteristics;
import dika.model.Phone;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.replaceAll;

@Component
public class CSVToModelConverter {

    public List<Phone> convertCSVToPhones(String csvContent) {
        String[] lines = csvContent.split("\n");
        String[] headers = lines[0].split(",");

        return Arrays.stream(lines)
                .skip(1) // Skip the header line
                .map(line -> convertToPhone(line))
                .collect(Collectors.toList());
    }

    private Phone convertToPhone(String csvLine) {
        String[] values = csvLine.split(",");
        List<String> headers = Arrays.asList("Company Name", "Model Name", "Launched Price (USA)", "Processor",
                "Screen Size", "Camera Resolution", "Weight", "Battery Capacity", "Internal Storage");

        String[] split = values[headers.indexOf("Model Name")].split(" ");
        String modelName = "";
        for (int i = 0; i<split.length-2; i++) {
            modelName += split[i] + " ";

        }

        return Phone.builder()
                .brand(values[headers.indexOf("Company Name")])
                .model(modelName)
                .price(values[headers.indexOf("Launched Price (USA)")].replaceAll("[a-zA-Z]", "") + "$")
                .characteristics(
                        Characteristics.builder()
                                .processor(values[headers.indexOf("Processor")])
                                .screenDiagonal(values[headers.indexOf("Screen Size")])
                                .cameraResolution(values[headers.indexOf("Camera Resolution")])
                                .wieght(values[headers.indexOf("Weight")])
                                .batteryCapacity(values[headers.indexOf("Battery Capacity")])
                                .internalStorage(split[split.length-2] + " GB")
                                .build()
                )
                .build();
    }
}
