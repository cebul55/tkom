package WebsiteSearch;

public class Keyword {

    private String searchText;
    private int importance;

    public Keyword(){};

    public void setImportance(int importance){
        System.out.println("Keyword.setIMportance");
        this.importance = importance;
        return;
    }
}
