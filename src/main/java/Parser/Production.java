package Parser;

import java.util.Arrays;

/**
 * Class {@code Production} represents productions in context-free grammar.
 * Left side of productions always contain only one nonterminal symbol!
 */
class Production {



    private int productionNumber;

    private NonTerminal leftSide;

    private Symbol[] rightSide;

    public Production(int productionNumber, NonTerminal leftSide, Symbol[] rightSide){
        this.productionNumber = productionNumber;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public int getProductionNumber() {
        return productionNumber;
    }

    public NonTerminal getLeftSide() {
        return leftSide;
    }

    public Symbol[] getRightSide() {
        return rightSide;
    }

    @Override
    public String toString(){
        return "Production number: " + productionNumber + "| " + leftSide + " -> " + Arrays.toString(rightSide);
    }
}
