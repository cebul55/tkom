import Exceptions.AnalyzerException;
import SimpleExamples.LekserSimpleExample;
import SimpleExamples.ParserSimpleExample;

public class Main {

    public static void main(String[] args) throws AnalyzerException {

        ParserSimpleExample parserSimpleExample = new ParserSimpleExample();
        parserSimpleExample.runSimpleExampleParser();

//        LekserSimpleExample lekserSimpleExample = new LekserSimpleExample();
//        lekserSimpleExample.runSimpleTestNoWhiteSpaces();
    }
}
