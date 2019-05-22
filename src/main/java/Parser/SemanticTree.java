package Parser;

import Token.Token;

import java.util.ArrayList;
import java.util.List;

class SemanticTree {
    private Node rootNode;
    private int nodeCount;

    //parameter used to build the tree after building becomes null;
    private int iteratorTokens, iteratorProductions;
    private List<Token> tokenList;
    private List<Production> appliedProductions;

    SemanticTree(List<Production> appliedProductions, List<Token> tokenList){
        this.nodeCount = 0;
        this.iteratorTokens = 0;
        this.iteratorProductions = 0;
        this.tokenList = tokenList;
        this.appliedProductions = appliedProductions;
        this.setUpSemanticTree();
    }

    private void setUpSemanticTree(){
        this.rootNode = new Node(null, appliedProductions.get(iteratorProductions).getLeftSide());
        this.addRightSideChildren(rootNode, appliedProductions.get(iteratorProductions).getRightSide());
    }

    private Token getNextToken(){
        Token tmp = tokenList.get(iteratorTokens);
        iteratorTokens++;
        if(iteratorTokens >= tokenList.size()){
            System.out.print("dsaad");
        }
        return tmp;
    }

    private void addRightSideChildren(Node parent, Symbol[] rightSideOfProduction){
        iteratorProductions++;
        Node tmpNode;
        for(Symbol s : rightSideOfProduction){
            if(s.isTerminal()){
//                tmpNode = new Node(parent, s, this.getNextToken());
                if(s.getName() == "EPSILON"){
                    parent.addChild(s);
                }
                else {
                    parent.addChild(s, this.getNextToken());
                }
                nodeCount++;
            }
            else{
//                tmpNode = new Node(parent, s);
                parent.addChild(s);
                nodeCount++;
                this.addRightSideChildren(parent.getLastChild(), appliedProductions.get(iteratorProductions).getRightSide());
            }

        }
    }
}
