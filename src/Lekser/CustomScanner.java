package Lekser;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class CustomScanner {
    private String filePath;
    private File file;
    private FileReader reader;
    private int totalErrors = 0, errorsInLine = 0;

    private int numberOfLine = 1;
    private int numberOfChar = 0;
    private char currentChar = '0';


    CustomScanner(String filePath) {
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

    private char nextChar(){
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

    char getChar(){
        // check if currentChar is null
        if(currentChar == '0'){
            return nextChar();
        }
        else {
            char tmp = currentChar;
            currentChar = '0';
            return tmp;
        }
    }

    void setCurrentChar(char currentChar) {
        this.currentChar = currentChar;
    }

    int getNumberOfChar() {
        return numberOfChar;
    }

    int getNumberOfLine() {
        return numberOfLine;
    }
}

