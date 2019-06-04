package WebsiteSearch;

import java.util.HashMap;

public class Query {
    private HashMap<Keyword, Integer> keywordhMap;
    private WebsiteWWW websiteWWW;

    public Query() {
        keywordhMap = new HashMap<Keyword, Integer>();
    }

    public Query(String keyword) {
        keywordhMap = new HashMap<Keyword, Integer>();
        Keyword k = new Keyword(keyword);
        keywordhMap.put(k, 10);
    }

    public SearchResult searchIn(WebsiteWWW w){
        System.out.println("Invoke Query.searchIn()");
        return new SearchResult();
    }

    public void search(){
        System.out.println("Query.search()");
        return;
    }

    public void addKeyword(Keyword keyword){
        System.out.println("Query.addKeyword()");
        return;
    }
}
