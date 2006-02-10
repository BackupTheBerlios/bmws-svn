package de.mbws.client.state.handler;

import com.jme.app.GameState;
import com.jme.app.GameStateManager;
import com.jme.input.InputHandler;

import de.mbws.client.state.*;

/**
 * Description:
 * 
 * @author Azarai
 */
public class BaseInputHandler extends InputHandler {

    protected BaseGameState state;

    private int switchState = 0;

    public final static int GAMESTATE_MAINMENU = 1;

    public final static int GAMESTATE_CHARACTER_SELECTION = 2;

    public final static int GAMESTATE_CHARACTER_CREATION = 3;

    public final static int GAMESTATE_INGAME = 4;

    public BaseInputHandler(BaseGameState state) {
        super();
        this.state = state;
    }

    private void startCharacterCreationState() {
        GameState gameState = new CharacterCreationState("characterCreation");
        switchState(gameState);
    }

    private void startMainGameState() {
        GameState gameState = new OutdoorGameState("ingame");
        switchState(gameState);
    }

    private void startCharacterSelectionState() {
        GameState ingame = new CharacterSelectionState("characterSelection");
        switchState(ingame);
    }

    private void startMainMenuState() {
        GameState mainMenu = new MainMenuState("menu");
        switchState(mainMenu);
    }

    private void switchState(GameState newState) {
        state.setActive(false);
        GameStateManager.getInstance().cleanup();
        state = null;
        GameStateManager.getInstance().attachChild(newState);
        newState.setActive(true);
    }

    public void requestStateSwitch(int newState) {
        switchState = newState;
    }

    /**
     * overrides update from super, checks if we can start the game
     */
    public void update(float time) {
        if (switchState != 0) {
            switch (switchState) {
                case GAMESTATE_MAINMENU:
                    startMainMenuState();
                    break;
                case GAMESTATE_CHARACTER_SELECTION:
                    startCharacterSelectionState();
                    break;
                case GAMESTATE_CHARACTER_CREATION:
                    startCharacterCreationState();
                    break;
                case GAMESTATE_INGAME:
                    startMainGameState();
                    break;
                default:
                    break;
            }
            switchState = 0;
        }
        super.update(time);
    }
}