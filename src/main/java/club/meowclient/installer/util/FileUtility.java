package club.meowclient.installer.util;

/*
    Author: ClientSiders
    Created: 26.04.2024
*/

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtility {
    public static void downloadAndExtractZip(String fileURL, String fileName, String destinationDirectory) {
        try {
            URL url = new URL(fileURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            String userHome = System.getProperty("user.home");
            Path downloadsPath = FileSystems.getDefault().getPath(userHome, "Downloads");
            Path zipFilePath = downloadsPath.resolve(fileName);

            try (BufferedInputStream in = new BufferedInputStream(inputStream);
                 FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath.toFile())) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }

            httpURLConnection.disconnect();

            extractZip(zipFilePath, destinationDirectory);

            Files.delete(zipFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractZip(Path zipFilePath, String destinationDirectory) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath.toFile()))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path entryDestination = Paths.get(destinationDirectory, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(entryDestination);
                } else {
                    try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(entryDestination.toFile()))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = zipInputStream.read(buffer, 0, 1024)) != -1) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }

                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
