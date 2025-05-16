package dika.conventer;

import dika.exception.FileReadException;
import dika.model.Characteristics;
import dika.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CSVToModelConverter implements AutoCloseable{

    public static List<Phone> convertCSVToPhones(Iterable<CSVRecord> records) {
        List<Phone> phones = new ArrayList<>();
        for (CSVRecord record : records) {
            String[] values = record.get("Model Name").split(" ");
            StringBuilder modelName = new StringBuilder();
            for (int i = 0; i < values.length - 1; i++) {
                modelName.append(values[i]).append(" ");
            }
            phones.add(Phone.builder()
                    .brand(record.get("Company Name"))
                    .model(modelName.toString())
                    .price(Double.parseDouble(record.get("Launched Price (USA)").replaceAll("[^0-9.]",
                            "").replace(",", ".").trim()))
                    .characteristics(
                            Characteristics.builder()
                                    .processor(record.get("Processor"))
                                    .screenDiagonal(Double.parseDouble(record.get("Screen Size").split(",")[0]
                                            .replaceAll("[^0-9\\.]", "")))
                                    .cameraResolution(record.get("Back Camera"))
                                    .weight(Double.parseDouble(record.get("Mobile Weight").replaceAll("\\p{L}",
                                            "").replace(",", ".").trim()))
                                    .batteryCapacity(Double.parseDouble(record.get("Battery Capacity")
                                            .replaceAll("\\p{L}", "").
                                            replace(",", "").trim()))
                                    .internalStorage(values[values.length - 1])
                                    .build()
                    )
                    .build());
        }
        return phones;
    }


    public static List<Phone> processCSVFile(String filePath){
        try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(in);
            return convertCSVToPhones(records);
        } catch (IOException e) {
            log.info("Ошибка чтения файла: {}", e.getMessage());
            throw new FileReadException("Ошибка чтения файла: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
    }
}

