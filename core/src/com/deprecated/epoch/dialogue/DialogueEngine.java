package com.epochgames.epoch.dialogue;

import com.badlogic.gdx.Gdx;

public class DialogueEngine {
    public boolean inDialogue = false;

    public void actDialogue(DialogueScript script) {
        if(inDialogue) {
            Gdx.app.debug("Conflicting Dialogue!", "Tried to deliver to dialogues at the same time");
            return;
        }
        inDialogue = true;
        Gdx.app.log("Interaction", "Interacted with " + script.name);
        inDialogue = false;
    }
}
