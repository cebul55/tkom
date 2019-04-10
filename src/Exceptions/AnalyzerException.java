package Exceptions;

public class AnalyzerException extends Exception {
    private int errorPosition = -1;
    private int lineNumber = -1;
    private String message;

    public AnalyzerException(int errorPosition, int lineNumber){
        this.errorPosition = errorPosition;
        this.lineNumber = lineNumber;
        this.message = "Unexpected character in Line:" + lineNumber + " at position:" + errorPosition + ".\n";
    }

    public AnalyzerException(String message, int errorPosition, int lineNumber){
        this.errorPosition = errorPosition;
        this.lineNumber = lineNumber;
        this.message = message;
    }

    public int getErrorPosition(){ return errorPosition ;}

    @Override
    public String getMessage() {
        return message;
    }
}
