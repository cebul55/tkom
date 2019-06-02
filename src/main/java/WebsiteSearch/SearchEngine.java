package WebsiteSearch;

public class SearchEngine {
    private String name;
    private String address;

    public SearchEngine() {}

    public boolean connectionStatus(){
        System.out.print("SearchEngine.connectionStatus()");
        return true;
    }

    public SearchResult executeQuery(Query query){
        System.out.print("SearchEngine.executeQuery(query)");
        return new SearchResult();
    }
}
