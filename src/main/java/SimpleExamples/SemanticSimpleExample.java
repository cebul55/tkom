package SimpleExamples;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Parser.Parser;
import SemanticAnalysis.SemanticAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class SemanticSimpleExample {
    public void runSimpleExampleAnalysis() throws AnalyzerException {
        Lekser lekser = new Lekser("src/main/resources/testFile");
        Parser parser = new Parser();
        SemanticAnalysis analysis;
        File grammarFile = getFileFromResource("LL1-grammar");
        try {
            parser.parse(grammarFile, lekser.getTokensWithoutWhiteSpaces());
            analysis = new SemanticAnalysis(parser.getSequenceOfAppliedProducions(), parser.getTokenList());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private File getFileFromResource(String filename){
        URL resource = getClass().getClassLoader().getResource(filename);
        if ( resource == null){
            throw new IllegalArgumentException("file not found!");
        }
        else return new File(resource.getFile());
    }
}
