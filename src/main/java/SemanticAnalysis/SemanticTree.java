package SemanticAnalysis;

import Exceptions.AnalyzerException;
import Parser.Production;
import Parser.Symbol;
import Token.Token;
import Token.TokenType;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class SemanticTree {
    private Node rootNode;
    private int nodeCount;

    //parameter used to build the tree after building becomes null;
    private int iteratorTokens, iteratorProductions;
    private List<Token> tokenList;
    private List<Production> appliedProductions;

    private SymbolTable symbolTable;



    SemanticTree(List<Production> appliedProductions, List<Token> tokenList){
        this.nodeCount = 0;
        this.iteratorTokens = 0;
        this.iteratorProductions = 0;
        this.tokenList = tokenList;
        this.appliedProductions = appliedProductions;
        symbolTable = new SymbolTable();
        symbolTable.enterScope();
        this.setUpSemanticTree();
        this.constructSymbolTables(rootNode);
        symbolTable.toString();
    }

    private void setUpSemanticTree(){
        this.rootNode = new Node(null, appliedProductions.get(iteratorProductions).getLeftSide());
        this.addRightSideChildren(rootNode, appliedProductions.get(iteratorProductions).getRightSide());
    }

    private Token getCurrToken(){
        Token tmp = tokenList.get(iteratorTokens);
        iteratorTokens++;

        return tmp;
    }

    private void addRightSideChildren(Node parent, Symbol[] rightSideOfProduction){
        iteratorProductions++;
        Node tmpNode;
        for(Symbol s : rightSideOfProduction){
            System.out.println(s.getName());

            if(s.isTerminal()){
//                tmpNode = new Node(parent, s, this.getCurrToken());
                if(s.getName() == "EPSILON"){
                    parent.addChild(s);
                }
                else {
                    parent.addChild(s, this.getCurrToken());
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

    private void addSymbolToTable(Node parent){
        String className = ((VariableDeclarationNode)parent).getPredefinedTypeNode().getLastChild().getToken().getTokenString();
        if( Character.isUpperCase(className.charAt(0)) && !className.equals("String")) {
            className = "WebsiteSearch." + className;
            Class<?> c = null;
            try {
                c = Class.forName(className);
                Constructor<?> cons = c.getConstructor();
                Object object = cons.newInstance();
                symbolTable.defineOrSet(((VariableDeclarationNode)parent).getIdNode().getToken().getTokenString(), object);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else if(className.equals("int")){
            int intValue = Integer.parseInt(((VariableDeclarationNode)parent).getExpressionNode().getChildren().get(0).getLastChild().getToken().getTokenString());
            symbolTable.defineOrSet(((VariableDeclarationNode)parent).getIdNode().getToken().getTokenString(), intValue);

        }
        else if(className.equals("String")){
            String stringValue = ((VariableDeclarationNode)parent).getExpressionNode().getChildren().get(0).getLastChild().getToken().getTokenString();
            symbolTable.defineOrSet(((VariableDeclarationNode)parent).getIdNode().getToken().getTokenString(), stringValue);

        }


    }

    private void constructSymbolTables(Node parent){
        String currentSymbolName = parent.getSymbol().getName();
        String className;
        if( currentSymbolName.equals("VariableDeclaration") ){
            if( ((VariableDeclarationNode)parent).isNewDeclaration()){
                if(((VariableDeclarationNode)parent).getPredefinedTypeNode().getToken().equals(((VariableDeclarationNode)parent).getPredefinedType2Node().getToken())){
                    addSymbolToTable(parent);

                }
                else{
                    try {
                        throw new AnalyzerException("Type mismatch at: line: ", ((VariableDeclarationNode)parent).getPredefinedTypeNode().getToken().getLineNumber(), ((VariableDeclarationNode)parent).getPredefinedTypeNode().getToken().getBeginIndex());
                    } catch (AnalyzerException e) {
                        e.printStackTrace();
                    }
                }

                constructSymbolTables(((VariableDeclarationNode)parent).getMethodValuesNode());
            }
            else{
                addSymbolToTable(parent);

                constructSymbolTables(((VariableDeclarationNode)parent).getExpressionNode());

            }
        }
        else if(currentSymbolName.equals("WhileBlock")){
            constructSymbolTables(((WhileNode)parent).getBoolExpression());
            symbolTable.enterScope();
            constructSymbolTables(((WhileNode)parent).getStatementExpression());
            symbolTable.exitScope();
        }
        else if(currentSymbolName.equals("IfElseBlock")){
            constructSymbolTables(((IfElseNode)parent).getBoolExpression());

            symbolTable.enterScope();
            constructSymbolTables(((IfElseNode)parent).getIfStatement());
            symbolTable.exitScope();

            symbolTable.enterScope();
            constructSymbolTables(((IfElseNode)parent).getElseStatement());
            symbolTable.exitScope();
        }
        else{
            for(Node n: parent.getChildren()){
                if( n.getToken() != null && n.getSymbol().getName().equals("id") ) {
                    String id = n.getToken().getTokenString();
                    if ( id.equals("main") ){
                        symbolTable.enterScope();
                    }
                    else if(symbolTable.lookup(id) == null && !n.getParent().getSymbol().getName().equals("Link")) {
//                        symbolTable.defineOrSet(id, "");

                            throw new IllegalArgumentException("Symbol " + id + " does not exist in any scope");
                    }
                }
                 constructSymbolTables(n);
            }
        }

    }

}
