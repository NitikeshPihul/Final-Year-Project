import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonDataFetcher {
    public static void main(String[] args) {
        String url = "https://www.jsonsite.com"; // Replace this with the actual URL

        try {
            // Make an HTTP GET request
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Check if the response code indicates success
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Read the response data
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseData.append(line);
                }
                reader.close();

                // Parse JSON data
                JSONObject jsonObject = new JSONObject(responseData.toString());

                // Now you have the JSON data as a JSONObject, and you can work with it.
                // For example, you can access specific fields like this:
                String name = jsonObject.getString("name");
                int age = jsonObject.getInt("age");

                // Or you can convert the whole JSONObject into a custom Java object if you have a corresponding class.

                // For demonstration purposes, let's assume you have a Person class:
                // class Person {
                //    private String name;
                //    private int age;
                //    // Add getters and setters
                // }

                // To convert the JSONObject to a Person object:
                // Person person = new Person();
                // person.setName(jsonObject.getString("name"));
                // person.setAge(jsonObject.getInt("age"));

            } else {
                System.out.println("HTTP Request failed with response code: " + connection.getResponseCode());
            }

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

class Module {
    String name;
    String value;
    List<Module> children;

    // Constructors, getters, setters, etc.
}

public class JsonToHtmlConverter {

    private static int nodeId = 0;

    public static void main(String[] args) {
        String json = "[{\"name\":\"skava-framework-bom\",\"value\":\"8.13.7.RELEASE\",\"children\":[{\"name\":\"nuskin-account-plugin\",\"value\":\"1.1.0.RELEASE\"},{\"name\":\"nuskin-address-plugin\",\"value\":\"1.1.0.RELEASE\"},{\"name\":\"nuskin-loyalty-plugin\",\"value\":\"1.1.0.RELEASE\"},{\"name\":\"nuskin-ecommtax-plugin\",\"value\":\"1.1.0.RELEASE\"}]},{\"name\":\"skava-framework-bom\",\"value\":\"8.15.0.RELEASE\",\"children\":[{\"name\":\"loyaltyservices\",\"value\":\"8.15.0.RELEASE\"}]}]";

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Module>>(){}.getType();
        List<Module> modules = gson.fromJson(json, listType);

        StringBuilder htmlBuilder = new StringBuilder();
        for (Module module : modules) {
            generateHtml(module, null, htmlBuilder);
        }

        System.out.println(htmlBuilder.toString());
    }

    private static void generateHtml(Module module, String parent, StringBuilder htmlBuilder) {
        int currentId = nodeId++;
        String firstChild = null;
        String nextSibling = null;

        if (module.children != null && !module.children.isEmpty()) {
            firstChild = String.valueOf(nodeId);
        }

        if (parent != null) {
            nextSibling = String.valueOf(nodeId);
        }

        htmlBuilder.append("<div id=\"node_").append(currentId).append("\" class=\"window hidden\" data-id=\"")
                .append(currentId).append("\" data-parent=\"").append(parent == null ? "" : parent)
                .append("\" data-first-child=\"").append(firstChild == null ? "" : firstChild)
                .append("\" data-next-sibling=\"").append(nextSibling == null ? "" : nextSibling)
                .append("\">\n")
                .append("  <div class=\"div-container\">\n")
                .append("    <div class=\"module-name\">").append(module.name).append("</div>\n")
                .append("    <div class=\"module-version\">").append(module.value).append("</div>\n")
                .append("  </div>\n")
                .append("</div>\n");

        if (module.children != null) {
            for (Module child : module.children) {
                generateHtml(child, String.valueOf(currentId), htmlBuilder);
            }
        }
    }
}





<script src="https://d3js.org/d3.v6.min.js"></script>

 <div id="graph-container"></div>
<script>
  $(document).ready(function () {
    // Your JSON data (replace this with your actual data)
    const jsonData = {
      "name": "Root",
      "children": [
        { "name": "Child 1" },
        { "name": "Child 2" },
        // Add more children as needed
      ]
    };

    // Create a hierarchical layout using D3.js tree layout
    const root = d3.hierarchy(jsonData);
    const treeLayout = d3.tree().size([500, 300]);
    treeLayout(root);

    // Create an SVG element within the graph container
    const svg = d3.select("#graph-container")
      .append("svg")
      .attr("width", 600)
      .attr("height", 400)
      .append("g")
      .attr("transform", "translate(50, 50)");

    // Create links between nodes
    const links = root.links();
    svg.selectAll(".link")
      .data(links)
      .enter()
      .append("path")
      .attr("class", "link")
      .attr("d", d3.linkHorizontal()
        .x(d => d.y)
        .y(d => d.x));

    // Create nodes
    const nodes = root.descendants();
    const node = svg.selectAll(".node")
      .data(nodes)
      .enter()
      .append("g")
      .attr("class", "node")
      .attr("transform", d => `translate(${d.y},${d.x})`);

    node.append("circle")
      .attr("r", 4);

    node.append("text")
      .attr("dy", "0.31em")
      .attr("x", d => d.children ? -8 : 8)
      .attr("text-anchor", d => d.children ? "end" : "start")
      .text(d => d.data.name);
  });
</script>
            


function changeTheme(themeName) {
            const link = $('#theme');
            link.attr('href', `${themeName}.css`);
        }

        // Add click event listeners to the theme links
        $('#theme1-link').on('click', () => {
            changeTheme('theme1');
        });

        $('#theme2-link').on('click', () => {
            changeTheme('theme2');
        });




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

