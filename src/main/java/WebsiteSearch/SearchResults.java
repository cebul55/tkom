package WebsiteSearch;

import java.util.Vector;

public class SearchResults {
    private Vector<SearchResult> searchResultVector;

    public SearchResults(){
        searchResultVector = new Vector<SearchResult>();
    }

    public void add(SearchResult sr){
        System.out.println("SearchResults.add()");
        return;
    }

    public void saveResultsToFile(){
        System.out.println("SearchResults.sabeResultsToFIle");
        this.setSearchResultsToFile("Results.txt");
        return;
    }

    public void setSearchResultsToFile(String filename){
        System.out.println("SearchResults.sabeResultsToFIle(Filename)");
        return;
    }
}
