package Lekser;

import Token.Token;
import Token.TokenType;
import Exceptions.AnalyzerException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Lekser - class represents lexical analyzer for custom script language */

public class Lekser {
    /** Mapping type of token to regular expression */
    private Map<TokenType, String> regularExpression;

    /** List of found tokens */
    private List<Token> tokenResultList;

    private CustomScanner scanner;

    public Lekser(String filePath){
        regularExpression = new HashMap<TokenType, String>();
        tokenResultList = new ArrayList<Token>();
        scanner = new CustomScanner(filePath);
        this.convertFileToTokens();

    }

    private void convertFileToTokens() {
        int pos = 0;
        int line = 0;
        Token token = null;
        do {
            try {
                token = getSingleToken(pos, line);
                if (token != null) {
                    pos = scanner.getNumberOfChar();
                    line = scanner.getNumberOfLine();
                    tokenResultList.add(token);
                }
            } catch (AnalyzerException e){
                //e.printStackTrace();
                System.out.println(e.getMessage());
                System.exit(0);
            }
        } while (token != null);
    }

    private TokenType checkTokenType(String str){
        switch (str){
            case "static":
                return TokenType.STATIC;
            case "public":
                return TokenType.PUBLIC;
            case "private":
                return TokenType.PRIVATE;
            case "void":
                return TokenType.VOID;
            case "false":
                return TokenType.FALSE;
            case "true":
                return TokenType.TRUE;
            case "null":
                return TokenType.NULL;
            case "return":
                return TokenType.RETURN;
            case "new":
                return TokenType.NEW;
            case "class":
                return TokenType.CLASS;
            case "if":
                return TokenType.IF;
            case "else":
                return TokenType.ELSE;
            case "while":
                return TokenType.WHILE;
            case "for":
                return TokenType.FOR;
            case "clone":
                return TokenType.CLONE;
            case "int":
                return TokenType.INT;
            case "double":
                return TokenType.DOUBLE;
            case "String":
                return TokenType.STRING;
            case "SearchEngine":
                return TokenType.SEARCH_ENGINE;
            case "SearchResult":
                return TokenType.SEARCH_RESULT;
            case "SearchResults":
                return TokenType.SEARCH_RESULTS;
            case "Query":
                return TokenType.QUERY;
            case "File":
                return TokenType.FILE;
            case "Keyword":
                return TokenType.KEYWORD;
            default:
                return TokenType.IDENTIFIER;
        }
    }

    private Token getSingleToken(int index, int line) throws AnalyzerException {
        if(index < 0){
            throw new IllegalArgumentException("Illegal index in the input stream.");
        }
        Token tmpToken = null;
        TokenType tmpType = null;
        StringBuilder tokenString = new StringBuilder();
        char atom;
        atom = scanner.getChar();
        int begCharAt = scanner.getNumberOfChar();
        int begCharLine = scanner.getNumberOfLine();


        if (CharacterChecker.isAlphabeticalCharacter(atom)){
            tmpType = TokenType.IDENTIFIER;
            while (CharacterChecker.isAcceptableInIdentifier(atom)){
                tokenString.append(atom);
                atom = scanner.getChar();
            }
            scanner.setCurrentChar(atom);
            tmpType = checkTokenType(tokenString.toString());
            if(tokenString.toString().length() >= 32){
                throw new AnalyzerException("Too long variable name in Line:" + begCharLine + " at position:" + begCharAt + ".\n", begCharAt, begCharLine);
            }
        }
        else if(CharacterChecker.isNumeric(atom)){
            tmpType = TokenType.INT_CONSTANT;
            while (CharacterChecker.isNumeric(atom)){
                tokenString.append(atom);
                atom = scanner.getChar();
            }
            if(CharacterChecker.isDot(atom)){
                tmpType = TokenType.DOUBLE_CONSTANT;
                tokenString.append(atom);
                atom = scanner.getChar();
                while (CharacterChecker.isNumeric(atom)){
                    tokenString.append(atom);
                    atom = scanner.getChar();
                }
            }
            scanner.setCurrentChar(atom);
        }
        else {
            switch (atom) {
                case ' ': {
                    tmpType = TokenType.WHITE_SPACE;
                    tokenString.append(atom);
                    break;
                }
                case '\n': {
                    tmpType = TokenType.END_LINE;
                    tokenString.append(atom);
                    break;
                }
                case '\t': {
                    tmpType = TokenType.TAB;
                    tokenString.append(atom);
                    break;
                }
                case '/': {
                    tokenString.append(atom);
                    atom = scanner.getChar();
                    if (atom == '/') {
                        tmpType = TokenType.LINE_COMMENT;
                        while (atom != '\n') {
                            tokenString.append(atom);
                            atom = scanner.getChar();
                        }
                        scanner.setCurrentChar(atom);
                    } else if (atom == '*') {
                        tmpType = TokenType.BLOCK_COMMENT;
                        tokenString.append(atom);
                        atom = scanner.getChar();
                        char prev = atom;
                        while (prev != '*' && atom != '/') {
                            tokenString.append(atom);
                            prev = atom;
                            atom = scanner.getChar();
                            if(atom == (char)(-1)){
                                throw new AnalyzerException("Comment not finished with '*/'. Comment starts at:"+begCharAt+" Line:"+ begCharLine +".",begCharAt,begCharLine);
                            }
                        }
                        tokenString.append(atom);
                    } else {
                        tmpType = TokenType.DIVIDE;
                        scanner.setCurrentChar(atom);
                    }
                    break;
                }
                case '\"':{
                    tmpType = TokenType.STRING_CONSTANT;
                    tokenString.append(atom);
                    atom = scanner.getChar();
                    while (atom != '\"') {
                        tokenString.append(atom);
                        atom = scanner.getChar();
                    }
                    tokenString.append(atom);
                    break;
                }
                case '(': {
                    tmpType = TokenType.OPEN_BRACKET;
                    tokenString.append(atom);
                    break;
                }
                case ')': {
                    tmpType = TokenType.CLOSE_BRACKET;
                    tokenString.append(atom);
                    break;
                }
                case '{': {
                    tmpType = TokenType.OPEN_CURLY_BRACKET;
                    tokenString.append(atom);
                    break;
                }
                case '}': {
                    tmpType = TokenType.CLOSE_CURLY_BRACKET;
                    tokenString.append(atom);
                    break;
                }
                case ',': {
                    tmpType = TokenType.COMMA;
                    tokenString.append(atom);
                    break;
                }
                case ':': {
                    tmpType = TokenType.COLON;
                    tokenString.append(atom);
                    break;
                }
                case ';': {
                    tmpType = TokenType.SEMICOLON;
                    tokenString.append(atom);
                    break;
                }
                case '+': {
                    tmpType = TokenType.PLUS;
                    tokenString.append(atom);
                    break;
                }
                case '-': {
                    tmpType = TokenType.MINUS;
                    tokenString.append(atom);
                    break;
                }
                case '*': {
                    tmpType = TokenType.MULTIPLY;
                    tokenString.append(atom);
                    break;
                }
                case '.': {
                    tmpType = TokenType.POINT;
                    tokenString.append(atom);
                    break;
                }
                case '<': {
                    tmpType = TokenType.GREATER;
                    tokenString.append(atom);
                    break;
                }
                case '>': {
                    tmpType = TokenType.LESS;
                    tokenString.append(atom);
                    break;
                }
                case '=': {
                    tmpType = TokenType.EQUAL;
                    tokenString.append(atom);
                    atom = scanner.getChar();
                    if (atom == '=') {
                        tokenString.append(atom);
                        tmpType = TokenType.EQUALX_2;
                    } else {
                        scanner.setCurrentChar(atom);
                    }
                    break;
                }
                case '!': {
                    tmpType = TokenType.SCREAMER;
                    tokenString.append(atom);
                    atom = scanner.getChar();
                    if (atom == '=') {
                        tmpType = TokenType.DIFFER;
                        tokenString.append(atom);
                    } else {
                        scanner.setCurrentChar(atom);
                    }
                    break;
                }
                case (char) (-1): {
                    return null;
                }
                default: {
                    throw new AnalyzerException(scanner.getNumberOfChar(), scanner.getNumberOfLine());
                }
            }
        }

        tmpToken = new Token(index, begCharAt, begCharLine, tokenString.length(), tmpType, tokenString.toString());
        return tmpToken;
    }


    public List<Token> getTokens() {
        return tokenResultList;
    }

    public List<Token> getTokensWithoutWhiteSpaces() {
        List<Token> filteredTokens = new ArrayList<Token>();
        for(Token token : this.tokenResultList){
            if ( !token.getTokenType().isCommentOrWhiteSign() ){
                filteredTokens.add(token);
            }
        }
        return filteredTokens;
    }

    /* Metody @Depracated zostały zastąpione metodami operującymi na pojedynczym odczytywanym znaku z pliku. Poniższe metody operowały na całym pliku */
    @Deprecated
    private void setUpeRegEx(){
        regularExpression.put(TokenType.BLOCK_COMMENT, "(/\\*.*?\\*/).*");
        regularExpression.put(TokenType.LINE_COMMENT, "(//(.*?)[\r$]?\n).*");
        regularExpression.put(TokenType.WHITE_SPACE, "( ).*");
        //regularExpression.put(TokenType.TAB, "(\\t).*");
        regularExpression.put(TokenType.OPEN_BRACKET, "(\\().*");
        regularExpression.put(TokenType.CLOSE_BRACKET, "(\\)).*");
        regularExpression.put(TokenType.OPEN_CURLY_BRACKET, "(\\{).*");
        regularExpression.put(TokenType.CLOSE_CURLY_BRACKET, "(\\}).*");
        regularExpression.put(TokenType.COMMA,"(,).*");
        regularExpression.put(TokenType.SEMICOLON, "(;).*");
        regularExpression.put(TokenType.COLON, "(:).*");
        regularExpression.put(TokenType.VOID,"\\b(void)\\b.*");
        regularExpression.put(TokenType.FALSE,"\\b(false)\\b.*");
        regularExpression.put(TokenType.TRUE,"\\b(true)\\b.*");
        regularExpression.put(TokenType.NULL,"\\b(null)\\b.*");
        regularExpression.put(TokenType.RETURN,"\\b(return)\\b.*");
        regularExpression.put(TokenType.NEW,"\\b(new)\\b.*");
        regularExpression.put(TokenType.CLASS,"\\b(class)\\b.*");
        regularExpression.put(TokenType.IF,"\\b(if)\\b.*");
        regularExpression.put(TokenType.ELSE,"\\b(else)\\b.*");
        regularExpression.put(TokenType.WHILE,"\\b(while)\\b.*");
        regularExpression.put(TokenType.FOR,"\\b(for)\\b.*");
        regularExpression.put(TokenType.STATIC,"\\b(static)\\b.*");
        regularExpression.put(TokenType.PUBLIC,"\\b(public)\\b.*");
        regularExpression.put(TokenType.PRIVATE,"\\b(private)\\b.*");
        regularExpression.put(TokenType.CLONE,"\\b(clone)\\b.*");
        regularExpression.put(TokenType.INT,"\\b(int)\\b.*");
        regularExpression.put(TokenType.INT_CONSTANT,"\\b(\\d{1,9})\\b.*");
        regularExpression.put(TokenType.DOUBLE,"\\b(double)\\b.*");
        regularExpression.put(TokenType.DOUBLE_CONSTANT,"\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regularExpression.put(TokenType.STRING ,"\\b(String)\\b.*");
        regularExpression.put(TokenType.STRING_CONSTANT,"(\".*?\").*");
        regularExpression.put(TokenType.SEARCH_ENGINE ,"\\b(SearchEngine)\\b.*");
        regularExpression.put(TokenType.SEARCH_RESULT ,"\\b(SearchResult)\\b.*");
        regularExpression.put(TokenType.SEARCH_RESULTS ,"\\b(SearchResults)\\b.*");
        regularExpression.put(TokenType.QUERY ,"\\b(Query)\\b.*");
        regularExpression.put(TokenType.FILE ,"\\b(File)\\b.*");
        regularExpression.put(TokenType.KEYWORD,"\\b(Keywords)\\b.*");
        regularExpression.put(TokenType.POINT,"(\\.).*");
        regularExpression.put(TokenType.PLUS,"(\\+{1}).*");
        regularExpression.put(TokenType.MINUS,"(\\-{1}).*");
        regularExpression.put(TokenType.MULTIPLY,"(\\*).*");
        regularExpression.put(TokenType.DIVIDE,"(/).*");
        regularExpression.put(TokenType.EQUAL,"(=).*");
        regularExpression.put(TokenType.EQUALX_2,"(==).*");
        regularExpression.put(TokenType.DIFFER,"(\\!=).*");
        regularExpression.put(TokenType.GREATER,"(>).*");
        regularExpression.put(TokenType.LESS,"(<).*");
        regularExpression.put(TokenType.IDENTIFIER,"\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
    }

    @Deprecated
    private Token convertSingleToken(String source, int fromIndex){
        if( fromIndex < 0 || fromIndex >= source.length()){
            throw new IllegalArgumentException("Illegal index in the input stream.");
        }
        for (TokenType tokenType : TokenType.values() ){
            Pattern pattern = Pattern.compile(".{"+ fromIndex +"}" + regularExpression.get(tokenType), Pattern.DOTALL);
            Matcher m = pattern.matcher(source);
            if( m.matches()){
                String lexem = m.group(1);
                return new Token(fromIndex, fromIndex + lexem.length(), 0,0, tokenType, lexem);
            }
            //todo wczytywanie po znaku
            // expressiom logiczne wyrażenie
        }
        return null;
    }

    @Deprecated
    public void convertTextToTokens(String source) throws AnalyzerException {
        int pos = 0;
        Token token = null;
        do {
            token = convertSingleToken(source, pos);
            if( token != null ){
                pos = token.getEndIndex();
                tokenResultList.add(token);
            }
        } while (token != null && pos != source.length());
        if ( pos != source.length() ) {
            throw new AnalyzerException(pos, pos);
        }
    }
}
