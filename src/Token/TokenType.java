package Token;

enum TokenType {
    BlockComment,
    LineComment,
    WhiteSpace,
    Tab,
    NewLine,
    OpenBracket,
    CLoseBracket,
    OpenCurlyBracket,
    CloseCurlyBracket,
    DoubleConstant,
    IntConstant,
    Plus,
    Minus,
    Multiply,
    Devide,
    Point,
    Equal,
    Assignment,
    Greater,
    Less,
    Static,
    Public,
    Private,
    Int,
    Double,
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
    Identifier,
    Comma;
    //todo create more types


    boolean isCommentOrWhiteSign(){
        return this == BlockComment || this == LineComment || this == WhiteSpace || this == Tab;
    }
}
