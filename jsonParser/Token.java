package jsonParser;

public class Token {
    private TokenKind kind;
    private Object literal;

    public Token(TokenKind kind, Object literal) {
        this.kind = kind;
        this.literal = literal;
    }

    @Override
    public String toString() {
        return kind + ": " + String.valueOf(literal);
    }
}