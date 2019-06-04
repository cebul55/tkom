import Exceptions.AnalyzerException;

public class Main {

    public static void main(String[] args) throws AnalyzerException {

       Interpreter interpreter = new Interpreter("LL1-grammar");
       if(interpreter.isAnalysisSuccessfull()){
           System.out.println("Interpreter pomyślnie zakończył pracę!");
       }

    }
}
