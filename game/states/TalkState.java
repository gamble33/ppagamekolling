package game.states;

import game.Game;
import game.Npc;
import game.callbacks.TextEntered;
import game.dialogue.Dialogue;
import game.dialogue.Response;

public class TalkState implements GameState {
    private final Game game;
    private final Npc npc;
    private Dialogue dialogue;

    private final TextEntered entered = (this::processDialogue);

    public TalkState(Game game, Npc npc) {
        this.game = game;
        this.npc = npc;
        this.dialogue = npc.getDialogue("start");
    }

    @Override
    public void enter() {
        displayOptions();
        game.getView().addUIListener(entered);
    }

    @Override
    public void exit() {
        game.getView().removeUIListener(entered);
    }

    private void processDialogue(String input) {
        int choice = processUserChoice(input);
        if (choice == -1) return;

        Response response = dialogue.getResponses().get(choice);
        game.getView().addText("Me: " + response.getResponseMessage());
        String nextDialogueId = response.getNextDialogue();

        if (nextDialogueId.equals("end")) {
            game.changeState(new CommandState(game));
            return;
        }

        dialogue = npc.getDialogue(nextDialogueId);
        displayOptions();
    }

    private void displayOptions() {
        StringBuilder dialogueList = new StringBuilder();
        dialogueList
                .append(npc.getName())
                .append(": ")
                .append(dialogue.getMessage())
                .append("\n");
        for (int i = 0; i < dialogue.getResponses().size(); i++) {
            Response response = dialogue.getResponses().get(i);
            dialogueList
                    .append("[")
                    .append((char) ((int) 'a' + i))
                    .append("]: ")
                    .append(response.getResponseMessage())
                    .append("\r\n");
        }
        game.getView().addText(dialogueList.toString());

    }

    private int processUserChoice(String userChoice) {
        int choice = -1;

        if (userChoice.split(" ").length > 1) {
            game.getView().addText("Please just enter a single letter corresponding to a response.");
            return -1;
        }

        userChoice = userChoice.toLowerCase().trim();
        if (userChoice.length() != 1) {
            game.getView().addText("Please just enter a single letter corresponding to a response.");
            return -1;
        }

        choice = userChoice.charAt(0) - 'a';
        if (choice < 0 || choice >= dialogue.getResponses().size()) {
            game.getView().addText(userChoice + " is not a valid choice.");
            return -1;
        }
        return choice;
    }
}
