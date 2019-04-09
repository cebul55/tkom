package Lekser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
//todo change class def to package private
public class CustomScanner {
    private String filePath;
    private File file;
    private FileReader reader;
    private int totalErrors = 0, errorsInLine = 0;

    private int numberOfLine = 0;
    private int numberOfChar = 0;


    public CustomScanner(String filePath) {
        this.filePath = filePath;
        file = new File(filePath);
        if(!file.canRead()){
            System.out.println("Fatal error. Couldn't open file at specified path: " + filePath);
            System.exit(0);
        }
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    public char nextChar(){
        char nextChar;
            try{
                nextChar = (char) reader.read();
                if (nextChar == '\n') {
                    numberOfChar = 0;
                    numberOfLine++;
                }
                else {
                    numberOfChar++;
                }
                return nextChar;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        return (char)(-1) ;
    }

    public int getNumberOfChar() {
        return numberOfChar;
    }

    public int getNumberOfLine() {
        return numberOfLine;
    }
}

