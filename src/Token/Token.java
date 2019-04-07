package Token;

public class Token {

    private int beginIndex;
    private int endIndex;
    private TokenType tokenType;
    private String tokenString;

    public Token(int beginIndex, int endIndex, TokenType tokenType, String tokenString){
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
        this.tokenType = tokenType;
        this.tokenString = tokenString;
    }

    public int getBeginIndex(){ return this.beginIndex; }

    public int getEndIndex() { return endIndex; }

    public TokenType getTokenType() { return tokenType; }

    public String getTokenString() { return tokenString; }

    @Override
    public String toString(){
        if(!this.tokenType.isCommentOrWhiteSign())
            return tokenType + "  '" + tokenString + "' [" + beginIndex + ";" + endIndex + "] ";
        else
            return tokenType + "   [" + beginIndex + ";" + endIndex + "] ";
    }
}
