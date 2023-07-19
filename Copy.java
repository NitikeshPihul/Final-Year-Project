
$(document).ready(function() {
    $('#printButton').click(function() {
      // Convert webpage contents to PDF using a library like jsPDF
      var doc = new jsPDF();
      doc.fromHTML($('body').get(0), 10, 10, {
        'width': 180
      });

      // Initiate download of the PDF file
      doc.save('webpage.pdf');
    });
  });



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



-------------------------------

 import java.io.*;
import java.nio.file.*;

public class CopyFilesFromJarExample {

    public static void main(String[] args) {
        String jarFilePath = "path/to/your.jar";
        String destinationFolderPath = "path/to/destination/folder";

        copyFilesFromJar(jarFilePath, destinationFolderPath);
    }

    private static void copyFilesFromJar(String jarFilePath, String destinationFolderPath) {
        try (InputStream jarStream = new FileInputStream(jarFilePath);
             ZipInputStream zipStream = new ZipInputStream(jarStream)) {

            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    String entryName = entry.getName();
                    String destinationFilePath = destinationFolderPath + File.separator + entryName;

                    try (OutputStream outputStream = new FileOutputStream(destinationFilePath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                    }
                }
                zipStream.closeEntry();
            }

            System.out.println("Files successfully copied from JAR to the destination folder.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


 +++++++++++++++++++++++


     import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FolderToZipConverter {

    public static void main(String[] args) {
        String sourceFolderPath = "path/to/versionTree";  // Path to the source folder
        String zipFilePath = "path/to/output.zip";        // Path to the output zip file

        zipFolder(sourceFolderPath, zipFilePath);
    }

    public static void zipFolder(String sourceFolderPath, String zipFilePath) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            addFolderToZip(sourceFolderPath, sourceFolderPath, zos);

            zos.close();
            fos.close();

            System.out.println("Folder successfully compressed into zip file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFolderToZip(String folderPath, String sourceFolderPath, ZipOutputStream zos) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    addFolderToZip(file.getAbsolutePath(), sourceFolderPath, zos);
                } else {
                    String relativePath = file.getAbsolutePath().substring(sourceFolderPath.length() + 1);
                    ZipEntry zipEntry = new ZipEntry(relativePath);
                    zos.putNextEntry(zipEntry);

                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    fis.close();
                    zos.closeEntry();
                }
            }
        }
    }
}

