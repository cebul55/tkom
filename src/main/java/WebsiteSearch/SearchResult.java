package WebsiteSearch;

public class SearchResult {
    private String websiteAddress;
    private String result;
    private double compatibility;

    public SearchResult(){}

    public void saveResultsToFile(String fileName){
        System.out.println("Invoke SearchResult.saveResultsToFile()");
        return;
    }

    public boolean storeResultsWithGreaterCompability(double compatibility){
        System.out.println("SearchResult.storeResultsWithGreaterCompability()");
        return true;
    }
}
