package game.dialogue;

public class Response {
    private final String responseMessage;
    private final String nextDialogue;

    public Response(String responseMessage, String nextDialogue) {
        this.responseMessage = responseMessage;
        this.nextDialogue = nextDialogue;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getNextDialogue() {
        return nextDialogue;
    }
}
