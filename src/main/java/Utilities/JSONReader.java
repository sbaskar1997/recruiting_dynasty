package Utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;

public class JSONReader {
    public static void main(String[] args) {
        String path = "/json/TestTeam.xml";
        JSONReader test = new JSONReader(path);
    }
    JSONObject document;
    String path;
    public JSONReader(){}

    public JSONReader(String path) {
        // Function to read JSON
        this.path = (String) System.getProperty("user.dir") + path;
        try {
            // Parse JSON and cast to JSONObject
            Object obj = new JSONParser().parse(new FileReader(this.path));
            JSONObject jo = (JSONObject) obj;
            this.document = jo;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
