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



import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        // Specify the path to the folder relative to the resources directory
        String folderPath = "templates";

        // Specify the destination path where the folder will be copied
        String destinationPath = "path/to/destination/folder";

        copyFolderFromResources(folderPath, destinationPath);

        System.out.println("Folder copied successfully.");
    }

    private static void copyFolderFromResources(String folderPath, String destinationPath) {
        try {
            // Create the destination folder if it doesn't exist
            File destinationFolder = new File(destinationPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            // Get the list of files in the folder from the resources
            File[] files = getFilesFromResources(folderPath);

            // Copy each file to the destination folder
            for (File file : files) {
                String relativePath = folderPath + "/" + file.getName();
                String destinationFilePath = destinationPath + "/" + file.getName();

                try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(relativePath);
                     OutputStream outputStream = new FileOutputStream(destinationFilePath)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File[] getFilesFromResources(String folderPath) throws IOException {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(folderPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Folder not found: " + folderPath);
            }

            return reader.lines()
                    .map(line -> new File(line))
                    .toArray(File[]::new);
        }
    }
}




................
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Specify the path to the folder relative to the resources directory
        String folderPath = "templates";

        // Specify the destination path where the folder will be copied
        String destinationPath = "path/to/destination/folder";

        copyFolderFromResources(folderPath, destinationPath);

        System.out.println("Folder copied successfully.");
    }

    private static void copyFolderFromResources(String folderPath, String destinationPath) {
        try {
            // Create the destination folder if it doesn't exist
            File destinationFolder = new File(destinationPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            // Get the list of files in the folder from the resources
            List<String> fileNames = getFileNamesFromResources(folderPath);

            // Copy each file to the destination folder
            for (String fileName : fileNames) {
                try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(fileName);
                     OutputStream outputStream = new FileOutputStream(destinationPath + "/" + fileName)) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getFileNamesFromResources(String folderPath) throws IOException {
        List<String> fileNames = new ArrayList<>();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(folderPath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Folder not found: " + folderPath);
            }

            String fileName;
            while ((fileName = reader.readLine()) != null) {
                fileNames.add(fileName);
            }
        }

        return fileNames;
    }
}
