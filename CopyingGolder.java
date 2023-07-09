import java.io.*;

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
                String fileName = file.getName();
                InputStream inputStream = new FileInputStream(file);
                OutputStream outputStream = new FileOutputStream(new File(destinationFolder, fileName));

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                // Close the streams
                inputStream.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File[] getFilesFromResources(String folderPath) throws IOException {
        ClassLoader classLoader = Main.class.getClassLoader();
        File folder = new File(classLoader.getResource(folderPath).getFile());

        if (folder.isDirectory()) {
            return folder.listFiles();
        } else {
            throw new IOException("Folder not found: " + folderPath);
        }
    }
    }
