package dika.conventer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
@Slf4j
public class UnzipFile {
    public void unzip(String zipFilePath, String destDir) {

        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) destDirFile.mkdirs();

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            File zipFile = new File(zipFilePath);
            if (!zipFile.exists()) {
                log.info("Архив не найден по пути: {}", zipFilePath);
                return;
            }

            while ((entry = zipIn.getNextEntry()) != null) {
                log.info("Extracting: {}", entry.getName());
                File file = new File(destDir, entry.getName());
                if (!entry.isDirectory()) {
                    extractFile(zipIn, file);
                } else {
                    file.mkdirs();
                }
                zipIn.closeEntry();
                log.info("Extracted: {}", file.getAbsolutePath());
            }
        } catch (IOException e) {
            log.info("Error while unzipping: {}", e.getMessage());
        }
    }

    private void extractFile(ZipInputStream zipIn, File file) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}

