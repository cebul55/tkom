package SimpleTests;

import Exceptions.AnalyzerException;
import Lekser.Lekser;
import Parser.Parser;
import Token.Token;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class LekserSimpleTest {
    public void runSimpleTestNoWhiteSpaces() throws AnalyzerException{
        Lekser lekser = new Lekser("/Users/bartoszcybulski/Documents/Informatyka_WEITI/Projekty/TKOM_Projekt/tkom/testFile");
//        System.out.println("TokenType\tLine:\t[BEG,LEN]\tTokenString\n");
//        for( Token t : lekser.getTokensWithoutWhiteSpaces()){
//            System.out.println(t);
//        }
        Parser parser = new Parser();
        File grammarFile = getFileFromResource("LL1-grammar");
        try {
            parser.parseProductions(grammarFile);
            parser.convertTokensToStack(lekser.getTokensWithoutWhiteSpaces());
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

    public void runSimpleTest() {
        Lekser lekser = new Lekser("/Users/bartoszcybulski/Documents/Informatyka_WEITI/Projekty/TKOM_Projekt/tkom/testFile");
        for( Token t : lekser.getTokens()){
            System.out.println(t);
        }
    }
}
