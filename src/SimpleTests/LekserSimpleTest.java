package SimpleTests;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Token.Token;
import Lekser.CustomScanner;

public class LekserSimpleTest {
    public void runSimpleTestNoWhiteSpaces() throws AnalyzerException{

    }

    public void runSimpleTest() {
        Lekser lekser = new Lekser("/Users/bartoszcybulski/Documents/Informatyka_WEITI/Projekty/TKOM_Projekt/tkom/testFile");
        for( Token t : lekser.getTokens()){
            System.out.println(t);
        }
    }
}
