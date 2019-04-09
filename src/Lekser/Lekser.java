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
        try {
            this.convertFileToTokens();
        } catch (AnalyzerException e) {
            e.printStackTrace();
        }
    }

    public void convertFileToTokens() throws AnalyzerException{
        int pos = 0;
        int line = 0;
        Token token = null;
        do {
            token = getSingleToken(pos, line);
            if(token != null){
                pos = scanner.getNumberOfChar();
                line = scanner.getNumberOfLine();
                tokenResultList.add(token);
            }
        } while (token != null);
    }

    private Token getSingleToken(int index, int line){
        if(index < 0){
            throw new IllegalArgumentException("Illegal index in the input stream.");
        }
        Token tmpToken = null;
        TokenType tmpType = null;
        StringBuilder tokenString = new StringBuilder();
        char atom;
        atom = scanner.nextChar();
        switch (atom){
            case ' ':{
                tmpType = TokenType.WHITE_SPACE;
                tokenString.append(atom);
                break;
            }
            case '\t':{
                tmpType = TokenType.TAB;
                tokenString.append(atom);
                break;
            }
            case '/':{
                tokenString.append(atom);
                atom = scanner.nextChar();
                if( atom == '/'){
                    tmpType = TokenType.LINE_COMMENT;
                    while (atom != '\n'){
                        tokenString.append(atom);
                        atom = scanner.nextChar();
                    }
                }
                else if(atom == '*'){
                    tmpType = TokenType.BLOCK_COMMENT;
                    tokenString.append(atom);
                    atom = scanner.nextChar();
                    char prev = atom;
                    while ( prev != '*' && atom != '/'){
                        tokenString.append(atom);
                        prev = atom;
                        atom = scanner.nextChar();
                    }
                }
                else{
                    tmpType = TokenType.DIVIDE;
                }
                break;
            }
            case '(':{
                tmpType = TokenType.OPEN_BRACKET;
                tokenString.append(atom);
                break;
            }
            case ')':{
                tmpType = TokenType.CLOSE_BRACKET;
                tokenString.append(atom);
                break;
            }
            case '{':{
                tmpType = TokenType.OPEN_CURLY_BRACKET;
                tokenString.append(atom);
                break;
            }
            case '}':{
                tmpType = TokenType.CLOSE_CURLY_BRACKET;
                tokenString.append(atom);
                break;
            }
            case ',':{
                tmpType = TokenType.COMMA;
                tokenString.append(atom);
                break;
            }
            case ':':{
                tmpType = TokenType.COLON;
                tokenString.append(atom);
                break;
            }
            case ';':{
                tmpType = TokenType.SEMICOLON;
                tokenString.append(atom);
                break;
            }
            case '+':{
                tmpType = TokenType.PLUS;
                tokenString.append(atom);
                break;
            }
            case '-':{
                tmpType = TokenType.MINUS;
                tokenString.append(atom);
                break;
            }
            case '*':{
                tmpType = TokenType.MULTIPLY;
                tokenString.append(atom);
                break;
            }
            case '.':{
                tmpType = TokenType.POINT;
                tokenString.append(atom);
                break;
            }
            case '=':{
                tmpType = TokenType.EQUAL;
                tokenString.append(atom);
                atom = scanner.nextChar();
                if(atom == '='){
                    tokenString.append(atom);
                    tmpType = TokenType.EQUALX_2;
                }//todo popraw =
                break;
            }
            case '!':{
                tmpType = TokenType.SCREAMER;
                tokenString.append(atom);
                atom = scanner.nextChar();
                if(atom == '='){
                    tmpType = TokenType.DIFFER;
                    tokenString.append(atom);
                }//todo popraw !=
                break;
            }
            case (char)(-1):{
                return null;
            }
            default:{
                return new Token(0,index+1,0,0,TokenType.KEYWORD, "dsa");
            }
        }

        tmpToken = new Token(index, scanner.getNumberOfChar(), scanner.getNumberOfLine(), tokenString.length(), tmpType, tokenString.toString());
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

    /* Metody @Depracated zostały zastąpione metodami operującymi na pojedynczym odczytywanym znaku z pliku */
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
            throw new AnalyzerException("Lexical error at position # "+ pos, pos);
        }
    }
}
