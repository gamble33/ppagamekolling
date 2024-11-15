package game.states;

import game.Game;
import game.Npc;
import game.callbacks.TextEntered;
import game.dialogue.Dialogue;
import game.dialogue.Response;
import game.sound.Sound;

import javax.swing.*;

public class TalkState implements GameState {
    private final Game game;
    private final Npc npc;
    private Dialogue dialogue;

    public TalkState(Game game, Npc npc) {
        this.game = game;
        this.npc = npc;
        this.dialogue = npc.getDialogue("start");
    }

    @Override
    public void enter() {
        game.getView().disableInputField();
        displayOptions();
    }

    @Override
    public void exit() {
        game.getView().enableInputField();
    }

    private void advanceDialogue(int choice) {
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
        String dialogueList =
                npc.getName() +
                        ": " +
                        dialogue.getMessage();
        game.getView().addText(dialogueList);
        for (int i = 0; i < dialogue.getResponses().size(); i++) {
            Response response = dialogue.getResponses().get(i);
            String responseString =
                    "[" +
                            (char) ((int) 'a' + i) +
                            "]: " +
                            response.getResponseMessage();
            JButton button = new JButton(responseString);
            int finalI = i;
            button.addActionListener(e -> {
                advanceDialogue(finalI);
            });
            game.getView().addButton(button, dialogue.getMessage());
        }
        Sound[] speechSound = new Sound[] {
                Sound.MaleSpeech1, Sound.MaleSpeech2, Sound.MaleSpeech3, Sound.MaleSpeech4, Sound.MaleSpeech5
        };
        game.getSoundPlayer().playSoundOnDifferentThread(speechSound[(int) (Math.random() * speechSound.length)]);

    }
}
