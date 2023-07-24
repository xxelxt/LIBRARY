package library.application.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cloudinary.Cloudinary;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;

    public static Cloudinary getCloudinaryInstance() {
        if (cloudinary == null) {
            Map<String, String> config = readCloudinaryConfigFromFile();
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }

    private static Map<String, String> readCloudinaryConfigFromFile() {
        Map<String, String> config = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/library/application/util/cloudinary_config.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    config.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return config;
    }
}