package Token;

public class Token {

    private int beginLineIndex;
    private int endLineIndex;
    private int lineNumber;
    private int lexemLength;
    private TokenType tokenType;
    private String tokenString;

    public Token(int beginLineIndex, int endLineIndex, int lineNumber, int lexemLength,  TokenType tokenType, String tokenString){
        this.beginLineIndex = beginLineIndex;
        this.endLineIndex = endLineIndex;
        this.lineNumber = lineNumber;
        this.lexemLength = lexemLength;
        this.tokenType = tokenType;
        this.tokenString = tokenString;
    }

    public int getBeginIndex(){ return this.beginLineIndex; }

    public int getEndIndex() { return endLineIndex; }

    public TokenType getTokenType() { return tokenType; }

    public String getTokenString() { return tokenString; }

    public int getLineNumber() {return lineNumber;}

    public int getLexemLength() { return lexemLength; }

    @Override
    public String toString(){
        if(!this.tokenType.isCommentOrWhiteSign())
            return tokenType  + "\tLine:"+ lineNumber  + "\t[BEG:" + beginLineIndex + ",LEN:" + lexemLength + "]" + "\t'" + tokenString + "'";
        else
            return tokenType + "\tLine:"+ lineNumber  +"\t[" + beginLineIndex + ";" + endLineIndex + "] ";
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;

        if(!(obj instanceof Token))
            return false;

        return (this.beginLineIndex == ((Token) obj).beginLineIndex && this.endLineIndex == ((Token) obj).endLineIndex && this.lineNumber == ((Token) obj).lineNumber && this.lexemLength == ((Token) obj).lexemLength && this.tokenType == ((Token) obj).tokenType && this.tokenString.equals(((Token) obj).tokenString));
    }
}
