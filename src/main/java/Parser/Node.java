package Parser;

import Token.Token;
import javafx.util.Pair;

import java.util.Vector;

class Node<T> {
    private Node parent;
    private Vector<Node> children = new Vector<Node>();
    private Symbol symbol;
    private Token token;

    Node(Node parent, Symbol symbol){
        this.parent = parent;
        this.symbol = symbol;
    }

    Node(Node parent, Symbol symbol, Token token){
        this.parent = parent;
        this.symbol = symbol;
        this.token = token;
    }

    public void addChild(Symbol symbol){
        Node node = new Node(this, symbol);
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
}
