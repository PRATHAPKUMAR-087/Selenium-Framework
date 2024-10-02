package seleniumframework.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {

  // Method to read JSON data from a file and convert it to a list of HashMaps
  public List<HashMap<String, String>> getJsonDataToMap() throws IOException {
    // Read JSON content as a string
    String jsonContent = FileUtils.readFileToString(
        new File(System.getProperty("user.dir") + "//src//test//java//seleniumframework//data//PurchaseOrder.json"),
        StandardCharsets.UTF_8
    );

    // Convert JSON string to List of HashMaps using Jackson Databind
    ObjectMapper mapper = new ObjectMapper();
    List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
    
    return data; // Return the converted data
  }
}
