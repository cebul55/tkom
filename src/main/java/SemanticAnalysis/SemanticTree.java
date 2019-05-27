package SemanticAnalysis;

import Parser.Production;
import Parser.Symbol;
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
                tmpNode = new Node(parent, s);
                parent.addChild(s);
                nodeCount++;
                this.addRightSideChildren(parent.getLastChild(), appliedProductions.get(iteratorProductions).getRightSide());
            }

        }
    }

    Node findNode(Token token){
        Node currentNode = rootNode;
        return findNode(token, currentNode);
    }

    private Node findNode(Token t, Node parent){
        List<Node> children = parent.getChildren();
        Node tmp;
        if( parent.getSymbol().isTerminal() && parent.getSymbol().getName() != "EPSILON"){

            if(parent.getToken().equals(t)){
                return parent;
            }
        }
        else {
            for (Node c : children) {
                tmp = findNode(t, c);
                if(tmp != null){
                    return tmp;
                }
            }
        }
        return null;
    }
}
