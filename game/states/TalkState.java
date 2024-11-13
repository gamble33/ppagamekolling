package game.states;

import game.Game;
import game.Npc;
import game.dialogue.Dialogue;
import game.dialogue.Response;

import java.util.Scanner;

public class TalkState implements GameState {
    private final Game game;
    private final Npc npc;
    private final Scanner reader;
    private Dialogue dialogue;

    public TalkState(Game game, Npc npc) {
        this.game = game;
        this.npc = npc;
        this.dialogue = npc.getDialogue("start");
        this.reader = new Scanner(System.in);
    }

    @Override
    public void update() {
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
                    .append(response.getResponseMessage());
        }
        game.getView().addText(dialogueList.toString());
        int choice = processUserChoice();
        Response response = dialogue.getResponses().get(choice);
        game.getView().addText("Me: " + response.getResponseMessage());
        String nextDialogueId = response.getNextDialogue();

        if (nextDialogueId.equals("end")) {
            game.changeState(new CommandState(game));
        }

        dialogue = npc.getDialogue(nextDialogueId);
    }

    private int processUserChoice() {
        int choice = -1;

        for (;;) {
            System.out.print("> ");
            String userChoice = reader.nextLine();
            System.out.println();

            if (userChoice.split(" ").length > 1) {
                System.out.println("Please just enter a single letter corresponding to a response.");
                continue;
            }
            userChoice = userChoice.toLowerCase().trim();
            if (userChoice.length() != 1) {
                System.out.println("Please just enter a single letter corresponding to a response.");
                continue;
            }
            choice = userChoice.charAt(0) - 'a';
            if (choice < 0 || choice >= dialogue.getResponses().size()) {
                System.out.println(userChoice + " is not a valid choice.");
                continue;
            }
            break;
        }
        return choice;
    }
}
