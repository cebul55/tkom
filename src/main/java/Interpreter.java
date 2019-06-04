import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Parser.Parser;
import SemanticAnalysis.SemanticAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Interpreter {
    private Lekser lekser;
    private Parser parser;
    private SemanticAnalysis semanticAnalysis;
    private File grammarFile;
    private boolean isAnalysisSuccessfull;

    public Interpreter(String grammarFileName){
        this.grammarFile = getFileFromResource(grammarFileName);
        lekser = new Lekser("src/main/resources/testFile");
        parser = new Parser();
        startInterpreter();
        isAnalysisSuccessfull = true;
    }

    private void startInterpreter() {
        try {
            parser.parse(grammarFile, lekser.getTokensWithoutWhiteSpaces());
            semanticAnalysis = new SemanticAnalysis(parser.getSequenceOfAppliedProducions(), parser.getTokenList());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (AnalyzerException e) {
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

    public boolean isAnalysisSuccessfull(){
        return isAnalysisSuccessfull;
    }

}
