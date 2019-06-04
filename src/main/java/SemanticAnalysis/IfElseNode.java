package SemanticAnalysis;

import Parser.Symbol;
import Token.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class IfElseNode extends Node {
    private Node boolExpression;
    private Node ifStatement;
    private Node elseStatement;

    public IfElseNode(Node parent, Symbol symbol){
        super(parent, symbol);
    }

    @Override
    public void addChild(Symbol symbol){
        String symbolName = symbol.getName();
        Node node = new Node(this,symbol);
        this.addNodeChild(symbolName, node);
    }

    @Override
    public void addChild(Symbol symbol, Token token){
        String symbolName = symbol.getName();
        Node node = new Node(this, symbol, token);
        this.addNodeChild(symbolName, node);
    }

    private void addNodeChild(String symbolName, Node node){
        if( symbolName.equals("BoolExpression") ){
            this.boolExpression = node;
        }
        else if(symbolName.equals("Statement") && ifStatement == null){
            this.ifStatement = node;
        }
        else if(symbolName.equals("Statement")){
            this.elseStatement = node;
        }
    }

    @Override
    public Node getLastChild(){
        if(elseStatement != null)
            return elseStatement;
        else if( ifStatement != null )
            return ifStatement;
        else
            return boolExpression;
    }

    public Node getBoolExpression() {
        return boolExpression;
    }

    public Node getIfStatement() {
        return elseStatement;
    }

    public Node getElseStatement() {
        return elseStatement;
    }
}
