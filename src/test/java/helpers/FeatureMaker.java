package helpers;


import io.cucumber.datatable.dependency.com.fasterxml.jackson.core.JsonParser;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.type.MapType;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.type.TypeFactory;
import model.FeatureFile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Feature file generation
 */
public class FeatureMaker {

    private static String FEATURE_FILES_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\features\\";


    /**
     * Generate cucumber scenario
     *
     * @return example table
     */
    private static FeatureFile createFeatureText(String featureName, String steps, HashMap stepsMap) {
        ArrayList<String> stepsList = new ArrayList<>();
        String featureText = "";
        Pattern pattern = Pattern.compile("([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(steps);
        while (matcher.find())
            stepsList.add(matcher.group());
        StringBuilder sb = new StringBuilder();
        stepsList.forEach(step -> sb.append("Given ").append(stepsMap.get(step).toString().replaceAll("\\$", "")).append("\n"));
        String stepsString = sb.toString();
        featureText = "@" + featureName + "\nFeature: Generated scenario\nScenario: " + featureName + "\n" +
                stepsString + "\n";
        return new FeatureFile(featureName, featureText);
    }


    public static void writeFeatureFiles(JSONObject scenarios, HashMap stepsMap) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        jsonParser.parse(scenarios.toString());
        for (Object scenario : scenarios.entrySet()) {
            Map.Entry entry = (Map.Entry) scenario;
            FeatureFile file = createFeatureText(entry.getKey().toString(), entry.getValue().toString(), stepsMap);
            writeToFile(file);
        }
    }

    /**
     * Write text of feature to .feature file
     *
     * @throws IOException
     */
    private static void writeToFile(FeatureFile featureFile) throws IOException {
        File gherkinFileFolder = new File(FEATURE_FILES_PATH);
        if (!gherkinFileFolder.exists()) {
            gherkinFileFolder.mkdir();
        }

        File file = new File(FEATURE_FILES_PATH + featureFile.getName() + ".feature");
        if (file.exists()) {
            file.delete();
        }

        FileWriter fw = new FileWriter(file);
        fw.write(featureFile.getText());
        fw.close();
    }
}