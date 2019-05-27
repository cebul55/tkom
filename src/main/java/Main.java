import Exceptions.AnalyzerException;
import SemanticAnalysis.SemanticAnalysis;
import SimpleExamples.LekserSimpleExample;
import SimpleExamples.ParserSimpleExample;
import SimpleExamples.SemanticSimpleExample;
import Token.Token;
import Token.TokenType;

public class Main {

    public static void main(String[] args) throws AnalyzerException {

        SemanticSimpleExample semanticSimpleExample = new SemanticSimpleExample();
        semanticSimpleExample.runSimpleExampleAnalysis();

//        ParserSimpleExample parserSimpleExample = new ParserSimpleExample();
//        parserSimpleExample.runSimpleExampleParser();


//        LekserSimpleExample lekserSimpleExample = new LekserSimpleExample();
//        lekserSimpleExample.runSimpleTestNoWhiteSpaces();
    }
}
