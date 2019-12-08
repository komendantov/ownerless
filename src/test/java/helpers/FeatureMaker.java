package helpers;


import model.FeatureFile;
import model.TestParameters;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Feature file generation
 */
public class FeatureMaker {

    private static String FEATURE_FILES_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\features\\";
    private static String ALL_FLOWS_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\testflows\\all_flows.json";


    /**
     * Generate cucumber scenario
     *
     * @return example table
     */
    private static FeatureFile createFeatureText(String featureName, String steps, HashMap<String, TestParameters> stepsMap) {
        ArrayList<String> stepsList = new ArrayList<>();
        String featureText = "";
        Pattern pattern = Pattern.compile("([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(steps);
        while (matcher.find())
            stepsList.add(matcher.group());
        StringBuilder sb = new StringBuilder();
        for (String step : stepsList) {
            sb.append("Given ").append(stepsMap.get(step).getGherkinName().replaceAll("\\$", ""));
            String[] testData = stepsMap.get(step).getTestData();
            if (testData.length != 0) {
                sb.append("\n");
                sb.append("|");
                for (String parameter : testData)
                    sb.append(parameter).append("|");
                sb.append("\n");
                sb.append("|");
                for (String parameter : testData)
                    sb.append("default").append("|");
                sb.append("\n");
            }
        }
        String stepsString = sb.toString();
        featureText = "@" + featureName.replaceAll(" ", "") + "\nFeature: Generated scenario\nScenario: " + featureName + "\n" +
                stepsString + "\n";
        return new FeatureFile(featureName, featureText);
    }


    public static void writeFeatureFiles(HashMap<String, TestParameters> stepsMap) throws IOException, ParseException {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> strings = (ArrayList) Files.readAllLines(Paths.get(ALL_FLOWS_PATH));
        strings.forEach(string -> sb.append(string));
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());
        for (Object scenario : jsonObject.entrySet()) {
            try {


                Map.Entry entry = (Map.Entry) scenario;
                FeatureFile file = createFeatureText(entry.getKey().toString(), entry.getValue().toString(), stepsMap);
                writeToFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
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