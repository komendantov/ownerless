package model;

import java.util.ArrayList;

public class TestParameters {
    private String gherkinName;
    private String[] testData;

    public void setGherkinName(String gherkinName) {
        this.gherkinName = gherkinName;
    }

    public void setTestData(String[] testData) {
        this.testData = testData;
    }

    public String getGherkinName() {
        return gherkinName;
    }

    public String[] getTestData() {
        return testData;
    }

    public TestParameters(String gherkinName, String[] testData) {
        this.gherkinName = gherkinName;
        this.testData = testData;
    }
}
