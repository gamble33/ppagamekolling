package game.dialogue;

import java.util.List;

public class Dialogue {
    private final String message;
    private final List<Response> responses;

    public Dialogue(String message, List<Response> responses) {
        this.message = message;
        this.responses = responses;
    }

    public String getMessage() {
        return message;
    }

    public List<Response> getResponses() {
        return responses;
    }
}
