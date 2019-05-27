package SemanticAnalysis;

import Parser.Production;
import Token.Token;

import java.util.List;

public class SemanticAnalysis {
    private SemanticTree semanticTree;


    public SemanticAnalysis(List<Production> sequenceOfAppliedProducions, List<Token> tokenList){
        this.semanticTree = new SemanticTree(sequenceOfAppliedProducions, tokenList);
    }
}
