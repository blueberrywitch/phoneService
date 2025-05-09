package dika.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Component
public class KaggleAPI {

    private static final String USERNAME = "dinaramatugylina";
    @Value("${spring.app.kaggle.name}")
    private String dataset;
    @Value("${spring.app.kaggle.url}")
    private String kaggleUrl;

    public void responseCreate() throws IOException, InterruptedException {
        String apiKey = getKaggleAPIKey();
        String apiBase = "https://www.kaggle.com/api/v1";
        String endpoint = apiBase + "/datasets/download/" + dataset;
        String version = "1";
        String downloadUrl = endpoint + "?datasetVersionNumber=" + version;

        String cred = Base64.getEncoder()
                .encodeToString((USERNAME + ":" + apiKey).getBytes(StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(downloadUrl))
                .header("Authorization", "Basic " + cred)
                .build();


        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();


        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers
                .ofFile(Paths.get("mobiles-dataset-2025.zip")));

        if (response.statusCode() == 200) {
            System.out.println("Файл успешно загружен в директорию: " + response.body());
        } else {
            System.out.println("Ошибка загрузки: " + response.statusCode());
        }
    }

    private String getKaggleAPIKey() throws IOException {
        String jsonFilePath = "src/main/resources/kaggle.json";
        File jsonFile = new File(jsonFilePath);

        if (!jsonFile.exists()) {
            throw new FileNotFoundException
                    ("Файл kaggle.json не найден. Убедитесь, что он находится в директории ~/.kaggle/");
        }

        String content = new String(Files.readAllBytes(jsonFile.toPath()));
        return extractApiKey(content);
    }

    private String extractApiKey(String jsonContent) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonContent);
        JsonNode keyNode = rootNode.path("key");

        if (keyNode.isMissingNode()) {
            throw new IllegalArgumentException("API-ключ не найден в файле kaggle.json");
        }
        return keyNode.asText();
    }
}
