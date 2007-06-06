package de.dynterrain;

import java.io.IOException;

import org.xml.sax.SAXException;

import com.jme.app.SimpleGame;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.CullState;


public class WorldTest extends SimpleGame {
		private DynamicWorld world;

		public WorldTest() {
			setDialogBehaviour(SimpleGame.ALWAYS_SHOW_PROPS_DIALOG);

		}

		protected void simpleInitGame() {
			world = new DynamicWorld();
			rootNode.attachChild(world);
			cam.setFrustumFar(2000);
			try {
				world.init(rootNode, cam,  display, "../../bmws/MBWSClient/data/world/world", "../../bmws/MBWSClient/data/objects");
				world.setVisibilityRadius(cam.getFrustumFar());
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			display.getRenderer().setBackgroundColor(
//					new ColorRGBA(0.8f, 0.8f, 0.8f, 1));
			DirectionalLight dr = new DirectionalLight();
			dr.setEnabled(true);
			dr.setDiffuse(new ColorRGBA(0.3f, 0.3f, 0.3f, 1.0f));
			dr.setAmbient(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
			dr.setDirection(new Vector3f(0.5f, -0.5f, 0));
			lightState.attach(dr);

			CullState cs = display.getRenderer().createCullState();
			cs.setCullMode(CullState.CS_NONE);
			cs.setEnabled(true);
			rootNode.setRenderState(cs);

			rootNode.updateWorldData(0);
			
		}

		@Override
		protected void simpleUpdate() {
//			System.err.println("height for (" + cam.getLocation().x + ","
//					+ cam.getLocation().z + ") => "
//					+ terrain.getHeight(cam.getLocation()));
			world.update(cam);
		}

		public static void main(String[] args) {
			WorldTest app = new WorldTest();
			app.start();
			
		}
}
