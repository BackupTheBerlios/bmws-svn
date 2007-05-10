package org.kerim.client;

import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jmex.editors.swing.settings.GameSettingsPanel;
import com.jmex.game.StandardGame;
import com.jmex.game.state.GameStateManager;
import com.jmex.game.state.load.LoadingGameState;

/**
 * <code>TestStandardGame</code> is meant to be an example replacement of
 * <code>jmetest.base.TestSimpleGame</code> using the StandardGame
 * implementation instead of SimpleGame.
 * 
 * @author Matthew D. Hicks
 */
public class Island {
  private MyDebugGameState state;
  private LoadingGameState lstate;

  public Island() throws Exception {
    // Instantiate StandardGame
    StandardGame game = new StandardGame("Island");
    // Show settings screen
    GameSettingsPanel.prompt(game.getSettings());
    // Start StandardGame, it will block until it has initialized successfully,
    // then return
    game.start();
  }

  public void initGameStates() {
    // Create a DebugGameState - has all the built-in features that SimpleGame
    // provides
    // NOTE: for a distributable game implementation you'll want to use
    // something like
    // BasicGameState instead and provide control features yourself.
    lstate = new LoadingGameState();
    GameStateManager.getInstance().attachChild(lstate);
    lstate.setActive(true);
    lstate.setProgress(0.0f, "loading gamestate");
    state = new MyDebugGameState();
    GameStateManager.getInstance().attachChild(state);
    lstate.setProgress(0.3f, "loading objects");
  }

  public void loadBox() {
    // Put our box in it
    Box box = new Box("my box", new Vector3f(0, 0, 0), 2, 2, 2);
    box.setModelBound(new BoundingSphere());
    box.updateModelBound();
    // We had to add the following line because the render thread is already
    // running
    // Anytime we add content we need to updateRenderState or we get funky
    // effects
    box.updateRenderState();
    state.getRootNode().attachChild(box);
    lstate.setActive(false);
    state.setActive(true);
  }

  public static void main(String[] args) throws Exception {
    Island i = new Island();
    i.initGameStates();
    i.loadBox();

  }
}
