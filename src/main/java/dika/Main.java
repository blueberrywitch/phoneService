package dika;

import dika.conventer.CSVToModelConverter;
import dika.service.PhoneService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws IOException {
        BeanFactory context = SpringApplication.run(Main.class, args);
        CSVToModelConverter converter = context.getBean(CSVToModelConverter.class);
        PhoneService phoneService = context.getBean(PhoneService.class);
        phoneService.saveCSV("src/main/resources/unzip/Mobiles Dataset (2025).csv");
    }
}