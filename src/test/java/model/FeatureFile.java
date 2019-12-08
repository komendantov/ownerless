package model;

public class FeatureFile {
    private String name;
    private String text;

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public FeatureFile(String name, String text) {
        this.name = name;
        this.text = text;
    }
}
