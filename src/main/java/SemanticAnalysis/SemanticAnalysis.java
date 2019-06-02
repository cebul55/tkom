package SemanticAnalysis;

import Parser.Production;
import Token.Token;

import java.util.List;

public class SemanticAnalysis {
    private SemanticTree semanticTree;
    private SymbolTable symbolTable;


    public SemanticAnalysis(List<Production> sequenceOfAppliedProducions, List<Token> tokenList){
        this.semanticTree = new SemanticTree(sequenceOfAppliedProducions, tokenList);
    }

    private void setUpSymbolTable(){
        symbolTable = semanticTree.setUpSymbolTable();
    }
}
