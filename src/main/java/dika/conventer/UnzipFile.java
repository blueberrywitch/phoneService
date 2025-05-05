package dika;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.*;

@Component
public class UnzipFile {
    public void unzip(String zipFilePath, String destDir){

        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) destDirFile.mkdirs();

        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            File zipFile = new File(zipFilePath);
            if (!zipFile.exists()) {
                System.err.println("Архив не найден по пути: " + zipFilePath);
                return;
            }

            while ((entry = zipIn.getNextEntry()) != null) {
                System.out.println("Extracting: " + entry.getName());
                File file = new File(destDir, entry.getName());
                if (!entry.isDirectory()) {
                    extractFile(zipIn, file);
                } else {
                    file.mkdirs();
                }
                zipIn.closeEntry();
                System.out.println("Extracted: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error while unzipping: " + e.getMessage());
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

