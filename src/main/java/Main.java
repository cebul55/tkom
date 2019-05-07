import Exceptions.AnalyzerException;
import Parser.Parser;
import SimpleTests.LekserSimpleTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws AnalyzerException {

        LekserSimpleTest lekserSimpleTest = new LekserSimpleTest();
        lekserSimpleTest.runSimpleTestNoWhiteSpaces();
    }
}
