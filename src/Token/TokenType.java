package Token;

public enum TokenType {
    BlockComment,
    LineComment,
    WhiteSpace,
    Tab,
    OpenBracket,
    CloseBracket,
    OpenCurlyBracket,
    CloseCurlyBracket,
    Comma,
    Void,
    False,
    True,
    Null,
    Return,
    New,
    Class,
    If,
    Else,
    While,
    For,
    Semicolon,
    Colon,
    Plus,
    Minus,
    Multiply,
    Devide,
    Point,
    Equal,
    Equalx2,
    Differ,
    Greater,
    Less,
    Static,
    Public,
    Private,
    Int,
    Double,
    DoubleConstant,
    IntConstant,
    Identifier;
    //todo create more types


    public boolean isCommentOrWhiteSign(){
        return this == BlockComment || this == LineComment || this == WhiteSpace || this == Tab ;
    }
}
