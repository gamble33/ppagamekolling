package jsonParser;

import static jsonParser.TokenKind.*;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonParser {
    private List<Token> tokens;
    private int current = 0;

    public JsonParser(String source) {
        this.tokens = new Lexer(source).scanTokens();
    }

    public JSONObject parse() {
        try {
            consume(LEFT_BRACE);
            JSONObject jsonObject = parseObject();
            consume(EOF);
            return jsonObject;
        } catch (ParseException exception) {
            System.err.println(exception.getLocalizedMessage());
        }
        return null;
    }

    private JSONObject parseObject() throws ParseException {
        Map<String, Object> properties = new HashMap<>();

        // Case of an empty object (closing brace is met straight away).
        if (peek() == RIGHT_BRACE) return new JSONObject(properties);

        do {
            Entry<String, Object> property = parseProperty();
            properties.put(property.getKey(), property.getValue());
            if (peek() != RIGHT_BRACE) consume(COMMA);
        } while (peek() != RIGHT_BRACE && !isAtEnd());

        consume(RIGHT_BRACE);

        return new JSONObject(properties);
    }

    private Entry<String, Object> parseProperty() throws ParseException {
        String propertyName = (String) consume(LITERAL_STRING).getLiteral();
        consume(COLON);
        Object value = parseValue();
        return new AbstractMap.SimpleEntry(propertyName, value);
    }

    private Object parseArray() {
        // TODO.
        return null;
    }

    private Object parseValue() throws ParseException {
        Token token = advance();
        switch (token.getKind()) {
            case LEFT_BRACE:
                return parseObject();
            case LEFT_BRACKET:
                return parseArray();
            case LITERAL_TRUE:
            case LITERAL_FALSE:
            case LITERAL_NULL:
            case LITERAL_NUMBER:
            case LITERAL_STRING:
                return token.getLiteral();
            default:
                throw new ParseException("Expected value, got: " + token.getKind(), current);
        }
    }

    private Token consume(TokenKind kind) throws ParseException {
        if (current().getKind() != kind) {
            throw new ParseException("Expected " + kind + ", got: " + current().getKind(), 0);
        }
        return advance();
    }

    private Token advance() {
        return tokens.get(current++);
    }

    private Token current() {
        return tokens.get(current);
    }

    private TokenKind peek() {
        return tokens.get(current).getKind();
    }

    private boolean isAtEnd() {
        return current >= tokens.size();
    }
}
