import java.io.*;
import java.nio.file.*;
import java.util.*;

private static File[] getFilesFromResources(String folderPath) throws IOException {
    try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(folderPath);
         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

        if (inputStream == null) {
            throw new IOException("Folder not found: " + folderPath);
        }

        List<File> fileList = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            Path filePath = Paths.get(line);
            File file = filePath.toFile();
            fileList.add(file);
        }

        return fileList.toArray(new File[0]);
    }
}
