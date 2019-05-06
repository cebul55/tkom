import Exceptions.AnalyzerException;
import Lekser.Lekser;
import SimpleTests.LekserSimpleTest;
import Token.Token;

public class Main {

    public static void main(String[] args) throws AnalyzerException {

        LekserSimpleTest lekserSimpleTest = new LekserSimpleTest();
        lekserSimpleTest.runSimpleTestNoWhiteSpaces();
    }
}
