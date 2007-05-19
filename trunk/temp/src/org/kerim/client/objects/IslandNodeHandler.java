package org.kerim.client.objects;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.action.KeyNodeBackwardAction;
import com.jme.input.action.KeyNodeForwardAction;
import com.jme.input.action.KeyNodeRotateLeftAction;
import com.jme.input.action.KeyNodeRotateRightAction;
import com.jme.input.action.KeyNodeStrafeLeftAction;
import com.jme.input.action.KeyNodeStrafeRightAction;
import com.jme.scene.Spatial;

public class IslandNodeHandler extends InputHandler{


  /**
   * <code>IslandNodeHandler</code> is a specific implementation for NodeHandling. Since the methods in NodeHandler are private we must define an own class.
   * @author Kerim Mansour
   * 
   */
  

      /**
       * Constructor instantiates a new <code>NodeHandler</code> object. The
       * application is set for the use of the exit action. The node is set to
       * control, while the api defines which input api is to be used.
       * @param node the node to control.
       */
      public IslandNodeHandler(Spatial node) { 
          setKeyBindings();
          setActions(node, 4f, 2f );
      }

      /**
       * Constructor instantiates a new <code>NodeHandler</code> object. The
       * application is set for the use of the exit action. The node is set to
       * control, while the api defines which input api is to be used.
       * @param node the node to control.
       * @param keySpeed action speed for key actions (move)
       */
      public IslandNodeHandler(Spatial node, float keySpeed, float turnSpeed) {
          setKeyBindings();
          setActions(node, keySpeed, turnSpeed );
      }

      /**
       *
       * <code>setKeyBindings</code> binds the keys to use for the actions.
       */
      private void setKeyBindings() {
          KeyBindingManager keyboard = KeyBindingManager.getKeyBindingManager();

          keyboard.set("forward_node", KeyInput.KEY_U);
          keyboard.set("backward_node", KeyInput.KEY_J);
          keyboard.set("strafeLeft_node", KeyInput.KEY_M);
          keyboard.set("strafeRight_node", KeyInput.KEY_N);
          keyboard.set("turnRight_node", KeyInput.KEY_K);
          keyboard.set("turnLeft_node", KeyInput.KEY_H);
      }

     

      /**
       *
       * <code>setActions</code> sets the keyboard actions with the corresponding
       * key command.
       * @param node the node to control.
       * @param moveSpeed
       * @param turnSpeed
       */
      private void setActions( Spatial node, float moveSpeed, float turnSpeed ) {
          addAction( new KeyNodeForwardAction( node, moveSpeed ), "forward_node", true );
          addAction( new KeyNodeBackwardAction( node, moveSpeed ), "backward_node", true );
//          addAction( new KeyNodeStrafeLeftAction( node, moveSpeed ), "strafeLeft_node", true );
//          addAction( new KeyNodeStrafeRightAction( node, moveSpeed ), "strafeRight_node", true );
          KeyNodeRotateRightAction rotateRight = new KeyNodeRotateRightAction( node, turnSpeed );
          rotateRight.setLockAxis( node.getLocalRotation().getRotationColumn( 1 ) );
          addAction( rotateRight, "turnRight_node", true );
          KeyNodeRotateLeftAction rotateLeft = new KeyNodeRotateLeftAction( node, turnSpeed );
          rotateLeft.setLockAxis( node.getLocalRotation().getRotationColumn( 1 ) );
          addAction( rotateLeft, "turnLeft_node", true );
      }
  }

