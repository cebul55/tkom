package SemanticAnalysis;

import Parser.Symbol;
import Token.Token;

public class VariableDeclarationNode extends Node{

    private Node predefinedTypeNode;
    private Node idNode;
    //this node either gets MethodValues Node or Expression Node
    private Node predefinedType2Node;
    private Node methodValuesNode;

    private Node expressionNode;

    private boolean isNewDeclaration;

    public VariableDeclarationNode(Node parent, Symbol symbol){
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
        if( symbolName.equals("PredefinedType")  && predefinedTypeNode == null){
            this.predefinedTypeNode = node;
        }
        else if( symbolName.equals("PredefinedType") ){
            this.predefinedType2Node = node;
            isNewDeclaration = true;
        }
        else if(symbolName.equals("id")){
            this.idNode = node;
        }
        else if(symbolName.equals("MethodValues")){
            this.methodValuesNode = node;
            isNewDeclaration = false;
        }
        else if(symbolName.equals("Expression")){
            this.expressionNode = node;
            isNewDeclaration = false;
        }
    }

    @Override
    public Node getLastChild(){
        if(expressionNode != null)
            return expressionNode;
        else if( methodValuesNode != null )
            return methodValuesNode;
        else if( predefinedType2Node != null)
            return predefinedType2Node;
        else if(idNode != null)
            return idNode;
        else
            return predefinedTypeNode;
    }

    public Node getPredefinedTypeNode() {
        return predefinedTypeNode;
    }

    public Node getIdNode() {
        return idNode;
    }

    public Node getPredefinedType2Node() {
        return predefinedType2Node;
    }

    public Node getMethodValuesNode() {
        return methodValuesNode;
    }

    public Node getExpressionNode() {
        return expressionNode;
    }

    public boolean isNewDeclaration(){
        return this.isNewDeclaration;
    }
}

