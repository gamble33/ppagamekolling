package jsonParser;

class Token {
    private TokenKind kind;
    private Object literal;

    public Token(TokenKind kind, Object literal) {
        this.kind = kind;
        this.literal = literal;
    }

    public TokenKind getKind() {
        return kind;
    }

    public Object getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return kind + ": " + String.valueOf(literal);
    }
}