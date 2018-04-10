import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;

public class JsonWriter implements Writer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void makeFile(Products products, String filename) {
        try(FileWriter writer = new FileWriter(filename + ".json")){
            Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
            gson.toJson(products, writer);
        } catch (IOException e) {
            logger.error("Something went wrong with Json converting");
        }
    }
}
