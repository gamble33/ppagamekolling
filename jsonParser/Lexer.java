package jsonParser;

import static jsonParser.TokenKind.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Lexer {
    private static final Map<String, TokenKind> keywords = Map.of(
            "true", LITERAL_TRUE,
            "false", LITERAL_FALSE,
            "null", LITERAL_NULL);

    private String source;
    private List<Token> tokens;
    private int current = 0;
    private int start = 0;

    public Lexer(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
    }

    public List<Token> scanTokens() {
        while (!isAtEnd()) {
            try {
                scanToken();
            } catch (ParseException exception) {
                System.err.println("Lexical Analysis Exception: " + exception.getLocalizedMessage());
            }
        }
        addToken(EOF);
        return tokens;
    }

    private void scanToken() throws ParseException {
        start = current;
        char c = advance();
        switch (c) {
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case '[':
                addToken(LEFT_BRACKET);
                break;
            case ']':
                addToken(RIGHT_BRACKET);
                break;
            case ':':
                addToken(COLON);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '"':
                scanString();
                break;

            // Ignore whitespace
            case ' ':
            case '\r':
            case '\t':
            case '\n':
                break;

            default:
                if (Character.isDigit(c)) {
                    scanNumber();
                } else if (Character.isAlphabetic(c)) {
                    scanKeyword();
                } else {
                    throw new ParseException("Couldn't scan literal `" + c + "`.", current);
                }
                break;
        }
    }

    private void scanKeyword() throws ParseException {
        while (Character.isAlphabetic(peek()))
            advance();

        String value = source.substring(start, current);
        if (!keywords.keySet().contains(value)) {
            throw new ParseException("`" + value + "` is not a valid keyword.", current);
        }

        addToken(keywords.get(value));
    }

    private void scanNumber() {
        while (Character.isDigit(peek())) {
            advance();
        }

        if (peek() == '.') {
            advance();
            // TODO: make sure `1.` doesn't throw an error.
            while (Character.isDigit(peek()))
                advance();
        }

        String stringValue = source.substring(start, current);
        float value = Float.parseFloat(stringValue);
        addToken(LITERAL_NUMBER, value);
    }

    private void scanString() throws ParseException {
        while (peek() != '"' && !isAtEnd()) {
            advance();
        }

        if (isAtEnd()) {
            throw new ParseException("Unterminated String Literal.", current);
        }
        advance();
        String value = source.substring(start + 1, current - 1);
        addToken(LITERAL_STRING, value);
    }

    private void addToken(TokenKind kind) {
        Token token = new Token(kind, null);
        tokens.add(token);
    }

    private void addToken(TokenKind kind, Object literal) {
        Token token = new Token(kind, null);
        tokens.add(token);
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

}
