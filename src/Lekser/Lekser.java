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

    public Lekser(){
        regularExpression = new HashMap<TokenType, String>();
        tokenResultList = new ArrayList<Token>();
        setUpeRegEx();
    }

    private void setUpeRegEx(){
        regularExpression.put(TokenType.BlockComment, "(/\\*.*?\\*/).*");
        regularExpression.put(TokenType.LineComment, "(//(.*?)[\r$]?\n).*");
        regularExpression.put(TokenType.WhiteSpace, "( ).*");
        regularExpression.put(TokenType.Tab, "(\\t).*");
        regularExpression.put(TokenType.OpenBracket, "(\\().*");
        regularExpression.put(TokenType.CloseBracket, "(\\)).*");
        regularExpression.put(TokenType.OpenCurlyBracket, "(\\{).*");
        regularExpression.put(TokenType.CloseCurlyBracket, "(\\}).*");
        regularExpression.put(TokenType.Comma ,"(,).*");
        regularExpression.put(TokenType.Semicolon, "(;).*");
        regularExpression.put(TokenType.Colon, "(:).*");
        regularExpression.put(TokenType.Void ,"\\b(void)\\b.*");
        regularExpression.put(TokenType.False ,"\\b(false)\\b.*");
        regularExpression.put(TokenType.True ,"\\b(true)\\b.*");
        regularExpression.put(TokenType.Null ,"\\b(null)\\b.*");
        regularExpression.put(TokenType.Return ,"\\b(return)\\b.*");
        regularExpression.put(TokenType.New ,"\\b(new)\\b.*");
        regularExpression.put(TokenType.Class ,"\\b(class)\\b.*");
        regularExpression.put(TokenType.If ,"\\b(if)\\b.*");
        regularExpression.put(TokenType.Else ,"\\b(else)\\b.*");
        regularExpression.put(TokenType.While ,"\\b(while)\\b.*");
        regularExpression.put(TokenType.For ,"\\b(for)\\b.*");
        regularExpression.put(TokenType.Static ,"\\b(static)\\b.*");
        regularExpression.put(TokenType.Public ,"\\b(public)\\b.*");
        regularExpression.put(TokenType.Private ,"\\b(private)\\b.*");
        regularExpression.put(TokenType.Int ,"\\b(int)\\b.*");
        regularExpression.put(TokenType.IntConstant ,"\\b(\\d{1,9})\\b.*");
        regularExpression.put(TokenType.Double ,"\\b(double)\\b.*");
        regularExpression.put(TokenType.DoubleConstant ,"\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regularExpression.put(TokenType.Point ,"(\\.).*");
        regularExpression.put(TokenType.Plus ,"(\\+{1}).*");
        regularExpression.put(TokenType.Minus ,"(\\-{1}).*");
        regularExpression.put(TokenType.Multiply ,"(\\*).*");
        regularExpression.put(TokenType.Devide ,"(/).*");
        regularExpression.put(TokenType.Equal ,"(=).*");
        regularExpression.put(TokenType.Equalx2 ,"(==).*");
        regularExpression.put(TokenType.Differ,"(\\!=).*");
        regularExpression.put(TokenType.Greater,"(>).*");
        regularExpression.put(TokenType.Less,"(<).*");
        regularExpression.put(TokenType.Identifier,"\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
    }

    private Token convertSingleToken(String source, int fromIndex){
        if( fromIndex < 0 || fromIndex >= source.length()){
            throw new IllegalArgumentException("Illegal index in the input stream.");
        }
        for (TokenType tokenType : TokenType.values() ){
            Pattern pattern = Pattern.compile(".{"+ fromIndex +"}" + regularExpression.get(tokenType), Pattern.DOTALL);
            Matcher m = pattern.matcher(source);
            if( m.matches()){
                String lexem = m.group(1);
                return new Token(fromIndex, fromIndex + lexem.length(), tokenType, lexem);
            }
        }
        return null;
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
