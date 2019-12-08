package helpers;


public class AnnotationData {

    public AnnotationData() {
    }

    //Gherkin keyword (e.g. Given, And, When...)
    private String gherkinKeyword;
    // Pretty short step name (e.g. "createAccount")
    private String shortName;
    //parameters from annotation
    private String[] preconditionSteps;
    // name on Gherkin language (e.g. "^I create a Korean account$")
    private String gherkinName;

    public boolean isStartNode() {
        return isStartNode;
    }

    public void setStartNode(boolean startNode) {
        isStartNode = startNode;
    }

    boolean isStartNode;

    /**
     * Get short step name
     * @return short step name
     */
    public String getShortName() {
        return shortName;
    }


    /**
     * Set short step name
     * @param shortName short name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * Get step parameters
     * @return step parameters
     */
    public String[] getPreconditionSteps() {
        return preconditionSteps;
    }

    /**
     * Set step parameters
     * @param preconditionSteps step parameters
     */
    public void setPreconditionSteps(String[] preconditionSteps) {
        this.preconditionSteps = preconditionSteps;
    }


    /**
     * Set Gherkin step name
     * @param gherkinName Gherkin name
     */
    public void setGherkinName(String gherkinName) {
        this.gherkinName = gherkinName;
    }
    /**
     * Get Gherkin step name
     * @return Gherkin name
     */
    public String getGherkinName() {
        return gherkinName;
    }

    /**
     * Get Gherkin keyword
     * @return Gherkin keyword
     */
    public String getGherkinKeyword() {
        return gherkinKeyword;
    }

    /**
     * Set Gherkin keyword
     * @param gherkinKeyword Gherkin keyword
     */
    public void setGherkinKeyword(String gherkinKeyword) {
        this.gherkinKeyword = gherkinKeyword;
    }



}
