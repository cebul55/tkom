package SimpleTests;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Token.Token;

public class LekserSimpleTest {
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
                "    Keywords,";

        Lekser lekser = new Lekser();
        lekser.convertTextToTokens(input);
//        for(Token t : lekser.getTokens() ){
//            System.out.println(t);
//        }

        for(Token t : lekser.getTokensWithoutWhiteSpaces() ){
            System.out.println(t);
        }
    }
}
