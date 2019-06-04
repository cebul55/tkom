package SemanticAnalysis;

import Parser.Symbol;
import Token.Token;

import java.util.Vector;

class Node {
    private Node parent;
    private Vector<Node> children = new Vector<Node>();
    private Symbol symbol;
    //symbolTable
//    public SymbolTable symbolTable;

    private Token token;

    Node(Node parent, Symbol symbol){
        this.parent = parent;
        this.symbol = symbol;
//        this.symbolTable = new SymbolTable();

    }

    Node(Node parent, Symbol symbol, Token token){
        this.parent = parent;
        this.symbol = symbol;
        this.token = token;
//        this.symbolTable = new SymbolTable();

    }

    public void addChild(Symbol symbol){
        String symbolName = symbol.getName();
        Node node;
        if(symbolName.equals("IfElseBlock")){
            node = new IfElseNode(this, symbol);
        }
        else if(symbolName.equals("WhileBlock")){
            node = new WhileNode(this, symbol);
        }
        else if(symbolName.equals("VariableDeclaration")){
            node = new VariableDeclarationNode(this, symbol);
        }
        else {
            node = new Node(this, symbol);
        }
        children.add(node);
    }

    public void addChild(Symbol symbol, Token token){
        Node node = new Node(this, symbol, token);
        children.add(node);
    }

    public Node getLastChild(){
        return children.get(children.size() - 1);
    }

    public Vector<Node> getChildren(){
        return this.children;
    }

    public Node getParent(){
        return parent;
    }

    public Symbol getSymbol(){
          return symbol;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == this )
            return true;
        else
            return false;
    }
}
