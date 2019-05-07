package Parser;

import Exceptions.AnalyzerException;
import Token.Token;
import sun.jvm.hotspot.debugger.cdbg.Sym;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

/**
 * The {@code Parser} class represents predictive parser. It accepts only LL(1)
 * grammar. Productions in the grammar use the following format, for example:
 *
 * <blockquote>
 *
 * <pre>
 *  Goal -> A
 *  A -> ( A ) | Two
 *  Two -> a
 * </pre>
 *
 * </blockquote>
 *
 * Symbol is inferred as nonterminal by first uppercase char. "->" designates
 * definition, "|" designates alternation, and newlines designate termination.
 * Use "EPSILON" to represent an empty string.
 */


public class Parser {
    public static Terminal epsilon = new Terminal(0, "EPSILON");
    public static Terminal endOfProgram = new Terminal(-1 , "ENDOFPROGRAM");

    private NonTerminal startSymbol;
    private List<Production> productionsList;
    private Set<Symbol> alphabet;
    private Map<String, Symbol> nameToSymbol;
    private Map<Symbol, Set<Terminal> > firstSet;
    private Map<Symbol, Set<Terminal> > followSet;
    private Map<SimpleEntry<NonTerminal, Terminal>, Symbol[] > parsingTable;
    private Stack<Terminal> input;
    private List<Production> sequenceOfAppliedProducions;

    public Parser(){
        productionsList = new ArrayList<Production>();
        alphabet = new HashSet<Symbol>();
        alphabet.add(epsilon);
        nameToSymbol = new HashMap<String, Symbol>();
        firstSet = new HashMap<Symbol, Set<Terminal>>();
        followSet = new HashMap<Symbol, Set<Terminal>>();
        parsingTable = new HashMap<SimpleEntry<NonTerminal, Terminal>, Symbol[]>();
        sequenceOfAppliedProducions = new ArrayList<Production>();
    }

    public List<Production> getSequenceOfAppliedProducions() {
        return this.sequenceOfAppliedProducions;
    }

    public void parse(File grammarFile, List<Token> tokenList) throws FileNotFoundException, AnalyzerException{
        parseProductions(grammarFile);
        calculateFirst();
        calculateFollow();
        buildParsingTable();
        input = convertTokensToStack(tokenList);
        performParsingAlgorithm();
    }

    public void parseProductions(File grammarFile) throws FileNotFoundException {
        nameToSymbol.put("EPSILON", epsilon);

        Scanner file = new Scanner(grammarFile);
        int code = 1;
        int productionNumber = 0;
        while (file.hasNext()){
            StringTokenizer stringTokenizer = new StringTokenizer(file.nextLine());
            String symbolName = stringTokenizer.nextToken();
            if(!nameToSymbol.containsKey(symbolName)){
                Symbol symbol = new NonTerminal(code, symbolName);
                if(code == 1){
                    startSymbol = (NonTerminal) symbol;
                }
                nameToSymbol.put(symbolName, symbol);
                alphabet.add(symbol);
                code++;
            }
            // get assignment arrow '->' and skipping it.
            stringTokenizer.nextToken();

            NonTerminal leftSide = (NonTerminal) nameToSymbol.get(symbolName);
            while (stringTokenizer.hasMoreTokens()){
                List<Symbol> rightSide = new ArrayList<Symbol>();
                do {
                    symbolName = stringTokenizer.nextToken();
                    if(!symbolName.equals("|")) {
                        if(!nameToSymbol.containsKey(symbolName)) {
                            Symbol symbol;
                            if(Character.isUpperCase(symbolName.charAt(0)) && !checkIfSymbolNameIsNotPredefinedType(symbolName))
                                symbol = new NonTerminal(code++, symbolName);
                            else
                                symbol = new Terminal(code++, symbolName);

                            nameToSymbol.put(symbolName, symbol);
                            alphabet.add(symbol);
                        }
                        rightSide.add(nameToSymbol.get(symbolName));
                    }
                } while ( !symbolName.equals("|") && stringTokenizer.hasMoreTokens());
                productionsList.add(new Production(productionNumber++, leftSide, rightSide.toArray(new Symbol[] {})));
            }
        }
    }

    private boolean checkIfSymbolNameIsNotPredefinedType(String symbolName){
        // predefined types that start with capital letter
        // String | SearchEngine | SearchResult | SearchResults | Query | File | Keyword
        return (symbolName.equals("String") || symbolName.equals("SearchEngine") || symbolName.equals("SearchResult")
                || symbolName.equals("SearchResults") || symbolName.equals("Query")
                || symbolName.equals("File") || symbolName.equals("Keyword"));
    }

    private void calculateFirst() {
        throw new NotImplementedException();
        //todo calculateFirst
    }

    private void calculateFollow() {
        throw new NotImplementedException();
        //todo calculatefollow
    }

    private void buildParsingTable() {
        for(Production prod : productionsList){
            Symbol[] rightside = prod.getRightSide();
            NonTerminal leftSide = prod.getLeftSide();
            Set<Terminal> firstSetForRightSIde = first(rightside);
            Set<Terminal> followSetForLeftSide = followSet.get(leftSide);

            for (Terminal t: firstSetForRightSIde){
                parsingTable.put(new SimpleEntry<NonTerminal, Terminal>(leftSide, t), rightside);
            }

            if (firstSetForRightSIde.contains(epsilon)){
                for(Terminal t : followSetForLeftSide){
                    parsingTable.put(new SimpleEntry<NonTerminal, Terminal>(leftSide, t), rightside);
                }
            }
        }
    }

    public Stack<Terminal> convertTokensToStack(List<Token> tokenList) {
        Stack<Terminal> input = new Stack<Terminal>();
        Collections.reverse(tokenList);
        input.push(endOfProgram);
        for(Token token : tokenList){
            Terminal tmp = (Terminal) nameToSymbol.get(token.getTokenString());
            if( tmp == null ){
                switch(token.getTokenType()){
                    case IDENTIFIER:
                        tmp = (Terminal) nameToSymbol.get("id");
                        break;
                    case INT_CONSTANT:
                        tmp = (Terminal) nameToSymbol.get("intConst");
                        break;
                    case DOUBLE_CONSTANT:
                        tmp = (Terminal) nameToSymbol.get("doubleConst");
                        break;
                    case STRING_CONSTANT:
                        tmp = (Terminal) nameToSymbol.get("stringConst");
                        break;
                    default:
                        throw new RuntimeException("Somethig is wrong! " + token.getTokenString());
                }
            }
            input.push(tmp);
        }
        System.out.println(input);
        return input;
    }

    private void performParsingAlgorithm() throws AnalyzerException {
        throw new NotImplementedException();
        //todo performParsingAlgorithm
    }

    private Set<Terminal> first(Symbol[] rightside) {
        throw new NotImplementedException();
        //todo first
    }

}
