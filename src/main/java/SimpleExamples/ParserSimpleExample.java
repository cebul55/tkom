package SimpleExamples;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Parser.Parser;
import Parser.Production;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class ParserSimpleExample {
    public void runSimpleExampleParser() throws AnalyzerException {
        Lekser lekser = new Lekser("src/main/resources/testFile");
        Parser parser = new Parser();
        File grammarFile = getFileFromResource("LL1-grammar");
        try {
            parser.parse(grammarFile, lekser.getTokensWithoutWhiteSpaces());
            for( Production p : parser.getSequenceOfAppliedProducions()){
                System.out.println(p);
            }
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
