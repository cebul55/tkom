package Token;

public enum TokenType {
    BLOCK_COMMENT,
    LINE_COMMENT,
    WHITE_SPACE,
    TAB,
    OPEN_BRACKET,
    CLOSE_BRACKET,
    OPEN_CURLY_BRACKET,
    CLOSE_CURLY_BRACKET,
    COMMA,
    VOID,
    FALSE,
    TRUE,
    NULL,
    RETURN,
    NEW,
    CLASS,
    IF,
    ELSE,
    WHILE,
    FOR,
    CLONE,
    SEMICOLON,
    COLON,
    PLUS,
    MINUS,
    MULTIPLY,
    DEVIDE,
    POINT,
    EQUAL,
    EQUALX_2,
    DIFFER,
    GREATER,
    LESS,
    STATIC,
    PUBLIC,
    PRIVATE,
    INT,
    DOUBLE,
    STRING,
    STRING_CONSTANT,
    DOUBLE_CONSTANT,
    INT_CONSTANT,
    SEARCH_ENGINE,
    SEARCH_RESULT,
    SEARCH_RESULTS,
    QUERY,
    FILE,
    KEYWORD,
    IDENTIFIER;
    //todo create more types


    public boolean isCommentOrWhiteSign(){
        return this == BLOCK_COMMENT || this == LINE_COMMENT || this == WHITE_SPACE || this == TAB;
    }
}
