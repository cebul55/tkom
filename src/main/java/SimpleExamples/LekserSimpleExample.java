package SimpleExamples;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Token.Token;

public class LekserSimpleExample {
    public void runSimpleTestNoWhiteSpaces() throws AnalyzerException {
        Lekser lekser = new Lekser("src/main/resources/testFile");
        System.out.println("TokenType\tLine:\t[BEG,LEN]\tTokenString\n");
        for (Token t : lekser.getTokensWithoutWhiteSpaces()) {
            System.out.println(t);
        }
    }

    public void runSimpleTest() {
        Lekser lekser = new Lekser("src/main/resources/testFile");
        for( Token t : lekser.getTokens()){
            System.out.println(t);
        }
    }
}
