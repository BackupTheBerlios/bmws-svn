package org.kerim.client;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;
import com.jmex.editors.swing.settings.GameSettingsPanel;
import com.jmex.game.StandardGame;
import com.jmex.game.state.GameStateManager;
import com.jmex.game.state.load.LoadingGameState;

public class Island {
  private IslandGameState state;
  private LoadingGameState lstate;
  private StandardGame game;

  public Island() throws Exception {
    // Instantiate StandardGame
    game = new StandardGame("Island");
    // Show settings screen
    GameSettingsPanel.prompt(game.getSettings());
    // Start StandardGame, it will block until it has initialized successfully,
    // then return
    game.start();
  }

  /**
   * initGameStates will load the two gamestates (loadinggamestate and islandgamestate).
   * To solve the problem of OPENGLThreading we use the GameTaskQueue. 
   */
  public void initGameStates() {
    try {
      //LoadingGameState
      Callable<?> loadState = new Callable() {
        public Object call() {
          lstate = new LoadingGameState();
          return lstate;
        }
      };
      Future f1 = GameTaskQueueManager.getManager().getQueue(
          GameTaskQueue.UPDATE).enqueue(loadState);
      f1.get();
      GameStateManager.getInstance().attachChild(lstate);
      lstate.setActive(true);
      lstate.setProgress(0.0f, "loading gamestate");
      
      //IslandGameState
      Callable<?> gameState = new Callable() {
        public Object call() {
          state = new IslandGameState();
          return state;
        }
      };
      Future f2 = GameTaskQueueManager.getManager().getQueue(
          GameTaskQueue.UPDATE).enqueue(gameState);
      f2.get();
      GameStateManager.getInstance().attachChild(state);
      lstate.setProgress(0.3f, "loaded gamestate");
      
      
    } catch (Exception e1) {
      e1.printStackTrace();
      System.exit(1);
    }

    
    
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void loadPlayer() {
    // // Put our box in it
    // Box box = new Box("player", new Vector3f(0, 0, 0), 1, 1.8f, 1);
    // box.setModelBound(new BoundingBox());
    // box.updateModelBound();
    // box.setLocalTranslation(new Vector3f(250,50,250));
    // // We had to add the following line because the render thread is already
    // // running
    // // Anytime we add content we need to updateRenderState or we get funky
    // // effects
    // box.updateRenderState();
    // state.getRootNode().attachChild(box);
    lstate.setActive(false);
    state.setActive(true);
  }

  public void setCS() {
    // state.getRootNode.setRenderState(cs);
  }

  public static void main(String[] args) throws Exception {
    Island i = new Island();
    i.initGameStates();

    i.loadPlayer();

  }
}
