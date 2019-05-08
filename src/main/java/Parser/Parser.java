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

    private List<Token> tokenList;

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

    public void parseForTesting(File grammarFile, List<Token> tokenList) throws FileNotFoundException, AnalyzerException{
        parseProductions(grammarFile);
        calculateFirst();
        input = convertTokensToStack(tokenList);
    }

    private void parseProductions(File grammarFile) throws FileNotFoundException {
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
//        System.out.println("ProductionsList:" + productionsList);
//        System.out.println("Alphabet: " + alphabet);
    }

    private boolean checkIfSymbolNameIsNotPredefinedType(String symbolName){
        // predefined types that start with capital letter
        // String | SearchEngine | SearchResult | SearchResults | Query | File | Keyword
        // used by parseProductions() to add types as Terminal Symbols
        return (symbolName.equals("String") || symbolName.equals("SearchEngine") || symbolName.equals("SearchResult")
                || symbolName.equals("SearchResults") || symbolName.equals("Query") || symbolName.equals("WebsiteWWW")
                || symbolName.equals("File") || symbolName.equals("Keyword"));
    }

    private void calculateFirst() {
        for (Symbol s : alphabet){
            firstSet.put(s, new HashSet<Terminal>());
        }
        for ( Symbol s : alphabet){
            first(s);
        }
    }

    /**
     * @param symbol
     * Func calculates first set for sybmol.
     *
     * 1. if x is terminal, FIRST(X) is {X}
     * 2. if x -> EPSILON is production, then add EPSILON to FIRST(X)
     * 3. If X is nonterminal and X-> Y1 Y2 ... Yk is a production, than place <i>a</i> (terminal) in FIRST(X) if for some i <i>a</i> is in FIRST(Yi), and Y1, ... ,Yi-1 -> EPSILON.
     *  If EPSILON is in FIRST(Yj) for all j = 1, 2, ... , k, then add EPSILON to FIRST(X).
     */
    private void first(Symbol symbol) {
        Set<Terminal> first = firstSet.get(symbol);
        Set<Terminal> tmpSet;
        if (symbol.isTerminal()){
            first.add((Terminal) symbol);
            return;
        }

        for( Production production : getPredictionsLeftSide((NonTerminal)symbol)){
            Symbol[] rightSide = production.getRightSide();
            first(rightSide[0]);
            tmpSet = new HashSet<Terminal>(firstSet.get(rightSide[0]));
            tmpSet.remove(epsilon);
            first.addAll(tmpSet);

            for (int i = 1; i< rightSide.length && firstSet.get(rightSide[i - 1]).contains(epsilon); i++){
                first(rightSide[i]);
                tmpSet = new HashSet<Terminal>(firstSet.get(rightSide[i]));
                tmpSet.remove(epsilon);
                first.addAll(tmpSet);
            }

            boolean containsEpsilon = true;
            for (Symbol rightside : rightSide){
                if(!firstSet.get(rightside).contains(epsilon)){
                    containsEpsilon = false;
                    break;
                }
            }
            if(containsEpsilon)
                first.add(epsilon);
        }
//        System.out.println(first);
    }

    private Set<Terminal> first(Symbol[] symbolList){
        Set<Terminal> firstSetForList = new HashSet<Terminal>();
        Set<Terminal> tmpSet = new HashSet<Terminal>(firstSet.get(symbolList[0]));
        tmpSet.remove(epsilon);
        firstSetForList.addAll(tmpSet);

        for (int i = 1; i < symbolList.length && firstSet.get(symbolList[i - 1]).contains(epsilon); i++){
            tmpSet = new HashSet<Terminal>(firstSet.get(symbolList[i]));
            tmpSet.remove(epsilon);
            firstSetForList.addAll(tmpSet);
        }

        boolean containsEpsilon = true;
        for(Symbol s : symbolList){
            if(!firstSet.get(s).contains(epsilon)){
                containsEpsilon = false;
                break;
            }
        }
        if(containsEpsilon)
            firstSetForList.add(epsilon);

        return firstSetForList;
    }

    private Set<Production> getPredictionsLeftSide(NonTerminal nonTerminalSymbol) {
        Set<Production> prodSet = new HashSet<Production>();
        for(Production p : productionsList){
            if(p.getLeftSide().equals(nonTerminalSymbol))
                prodSet.add(p);
        }
        return prodSet;
    }

    private void calculateFollow() {
        for (Symbol s : alphabet){
            if(s.isNonTerminal())
                followSet.put(s, new HashSet<Terminal>());
        }

        Map<SimpleEntry<Symbol, Symbol>, Boolean> callTable = new HashMap<SimpleEntry<Symbol, Symbol>, Boolean>();
        for( Symbol firstSymbol : alphabet){
            for( Symbol secondSymbol : alphabet){
                callTable.put(new SimpleEntry<Symbol, Symbol>(firstSymbol, secondSymbol), false);
            }
        }

        NonTerminal firstSymbol = productionsList.get(0).getLeftSide();
        followSet.get(firstSymbol).add(endOfProgram);
        for(Symbol s : alphabet){
            if(s.isNonTerminal()){
                follow((NonTerminal) s, null, callTable);
            }
        }
    }

    private void follow(NonTerminal symbol, Symbol caller, Map<SimpleEntry<Symbol,Symbol>, Boolean> callTable) {
        Boolean called = callTable.get(new SimpleEntry<Symbol, Symbol>(caller, symbol));
        if(called != null){
            if(called == true)
                return;
            else
                callTable.put(new SimpleEntry<Symbol, Symbol>(caller, symbol), true);
        }

        Set<Terminal> follow = followSet.get(symbol);
        Set<Terminal> tmpSet;

        List<SimpleEntry<NonTerminal, Symbol[]>> list = getPairsLeftSideRightListOfPrediction(symbol);
        for (SimpleEntry<NonTerminal, Symbol[]> pair : list){
           Symbol[] rightSideList = pair.getValue();
           NonTerminal leftSide = pair.getKey();
           if(rightSideList.length != 0){
               tmpSet = first(rightSideList);
               tmpSet.remove(epsilon);
               follow.addAll(tmpSet);
               if(first(rightSideList).contains(epsilon)){
                   follow(leftSide, symbol, callTable);
                   follow.addAll(followSet.get(leftSide));
               }
           }
           else {
               follow(leftSide, symbol, callTable);
               follow.addAll(followSet.get(leftSide));
           }
        }
    }

    private List<SimpleEntry<NonTerminal, Symbol[]>> getPairsLeftSideRightListOfPrediction(NonTerminal symbol) {
        List<SimpleEntry<NonTerminal, Symbol[]>> list = new ArrayList<SimpleEntry<NonTerminal, Symbol[]>>();
        for(Production p : productionsList){
            Symbol[] rightSideList = p.getRightSide();
            int index = Arrays.asList(rightSideList).indexOf(symbol);
            if(index  != -1){
                rightSideList = Arrays.copyOfRange(rightSideList, index + 1, rightSideList.length);
                list.add(new SimpleEntry<NonTerminal, Symbol[]>(p.getLeftSide(), rightSideList));
            }
        }
        return list;
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

    private Stack<Terminal> convertTokensToStack(List<Token> tokenList) {
        this.tokenList = tokenList;
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
//        System.out.println(input);
        Collections.reverse(this.tokenList);
        return input;
    }

    private void performParsingAlgorithm() throws AnalyzerException {
        Stack<Symbol> stack = new Stack<Symbol>();
        stack.push(endOfProgram);
        stack.push(startSymbol);
        int parsedTokenCount = 0;
        Symbol stackTop;
        Terminal inputTop;
        do {
            stackTop = stack.peek();
            inputTop = input.peek();
            if(stackTop.isTerminal()){
                if(stackTop.equals(inputTop)){
                    stack.pop();
                    input.pop();
                    parsedTokenCount++;
                }
                else {
                    Token currToken = tokenList.get(parsedTokenCount);
//                    System.out.println("curr symbol: " + stackTop.getName() + "\n curr token: " + currToken.getTokenString() );
                    throw new AnalyzerException("Syntax error after token #" + parsedTokenCount + " " + currToken.getTokenString() + " in line: " + currToken.getLineNumber(), parsedTokenCount, currToken.getLineNumber());
                }
            }
            else {
                SimpleEntry<NonTerminal, Terminal> tableKey = new SimpleEntry<NonTerminal, Terminal>((NonTerminal) stackTop, inputTop);
                if(parsingTable.containsKey(tableKey)) {
                    stack.pop();
                    Symbol[] tableEntry = parsingTable.get(tableKey);
                    for(int j = tableEntry.length - 1; j > -1 ; j--){
                        if(!tableEntry[j].equals(epsilon))
                            stack.push(tableEntry[j]);
                    }
                    sequenceOfAppliedProducions.add(getPrediction((NonTerminal) stackTop, tableEntry));
                }
                else {
                    Token currToken = tokenList.get(parsedTokenCount + 1);
//                    System.out.println("curr symbol: " + stackTop.getName() + "\n curr token: " + currToken.getTokenString() );
                    throw new AnalyzerException("Syntax error after token #" + parsedTokenCount + " " + currToken.getTokenString() + " in line: " + currToken.getLineNumber(), parsedTokenCount, currToken.getLineNumber());
                }
            }
        } while ( !stack.isEmpty() && !input.isEmpty() );

        if (!input.isEmpty()){
            Token currToken = tokenList.get(parsedTokenCount);
//            System.out.println("curr symbol: " + stackTop.getName() + "\n curr token: " + currToken.getTokenString() );
            throw new AnalyzerException("Syntax error after token #" + parsedTokenCount + " " + currToken.getTokenString() + " in line: " + currToken.getLineNumber(), parsedTokenCount, currToken.getLineNumber());
        }
    }

    private Production getPrediction(NonTerminal leftSide, Symbol[] rightSide) {
        Set<Production> setOfProductions = getPredictionsLeftSide(leftSide);
        for(Production p : setOfProductions){
            if( rightSide.length != p.getRightSide().length)
                continue;
            for(int i = 0; i < rightSide.length; i++){
                if(p.getRightSide()[i] != rightSide[i])
                    break;
                else {
                    if ( i == rightSide.length - 1){
                        return  p;
                    }
                }
            }
        }
        return null;
    }

}
