package SimpleTests;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Token.Token;

public class LekserSimpleTest {
    public void runSimpleTestNoWhiteSpaces() throws AnalyzerException{
        Lekser lekser = new Lekser("/Users/bartoszcybulski/Documents/Informatyka_WEITI/Projekty/TKOM_Projekt/tkom/testFile");
        System.out.println("TokenType\tLine:\t[BEG,LEN]\tTokenString\n");
        for( Token t : lekser.getTokensWithoutWhiteSpaces()){
            System.out.println(t);
        }
    }

    public void runSimpleTest() {
        Lekser lekser = new Lekser("/Users/bartoszcybulski/Documents/Informatyka_WEITI/Projekty/TKOM_Projekt/tkom/testFile");
        for( Token t : lekser.getTokens()){
            System.out.println(t);
        }
    }
}
