package SimpleTests;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Token.Token;

public class LekserSimpleTest {
    public void runSimpleTestNoWhiteSpaces() throws AnalyzerException{
        String input = "Lekser lekser = new Lekser();" +
                "lekser.convertTextToTokens(input);" +
                "for(Token t : lekser.getTokens() ){" +
                "            System.out.println(t);" +
                "        } String \"ddsadsa\"" +
                "     SearchEngine," +
                "    SearchResult," +
                " SearchResults" +
                "    Query," +
                "    File," +
                "    Keywords," +
                "clone(zmienna)";

        Lekser lekser = new Lekser();
        lekser.convertTextToTokens(input);
        for(Token t : lekser.getTokensWithoutWhiteSpaces() ){
            System.out.println(t);
        }
    }

    public void runSimpleTest() throws AnalyzerException{
        String input = "Lekser lekser = new Lekser();" +
                "lekser.convertTextToTokens(input);" +
                "for(Token t : lekser.getTokens() ){" +
                "            System.out.println(t);" +
                "        } String \"ddsadsa\"" +
                "     SearchEngine," +
                "    SearchResult," +
                " SearchResults" +
                "    Query," +
                "    File," +
                "    Keywords," +
                "clone(zmienna)";

        Lekser lekser = new Lekser();
        lekser.convertTextToTokens(input);
        for(Token t : lekser.getTokens() ){
            System.out.println(t);
        }
    }
}
