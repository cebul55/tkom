package Exceptions;

public class AnalyzerException extends Exception {
    private int errorPosition;
    private String message;

    public AnalyzerException(int errorPosition){
        this.errorPosition = errorPosition;
    }

    public AnalyzerException(String message, int errorPosition){
        this.message = message;
        this.errorPosition = errorPosition;
    }

    public int getErrorPosition(){ return errorPosition ;}

    @Override
    public String getMessage() {
        return message;
    }
}
