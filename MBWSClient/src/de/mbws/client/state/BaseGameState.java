package de.mbws.client.state;

import com.jme.app.BasicGameState;
import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.LightState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jmex.awt.swingui.JMEDesktop;

/**
 * BaseGameState for MBWS; We add a second node for the gui system (using JMEDesktop),
 * which is drawn separatly after rootNode! 
 * 
 * @author Azarai
 */
public abstract class BaseGameState extends BasicGameState {

    protected DisplaySystem display;

    protected JMEDesktop jmeDesktop;

    private Node desktopNode;

    private Node guiRootNode;

    protected InputHandler input;

    protected Camera cam;

    public BaseGameState(String name) {
        super(name);
        guiRootNode = new Node("state guiRootNode");
        guiRootNode.setCullMode(Spatial.CULL_NEVER);
        display = DisplaySystem.getDisplaySystem();
        initCamera();
        initJMEDesktop();
        fullScreen();
        initInputHandler();

        initZBuffer();
        // Update geometric and rendering information for the rootNode.
        rootNode.updateGeometricState(0.0f, true);
        rootNode.updateRenderState();
        guiRootNode.updateGeometricState(0.0f, true);
        guiRootNode.updateRenderState();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (jmeDesktop.getFocusOwner() == null || jmeDesktop.getFocusOwner() == jmeDesktop.getJDesktop()) {
            input.setEnabled(true);
        } else {
            input.setEnabled(false);
        }
        guiRootNode.updateGeometricState(tpf, true);
    }

    private void initJMEDesktop() {
        jmeDesktop = new JMEDesktop(name);
        jmeDesktop.setup(display.getWidth(), display.getHeight(), false);
        jmeDesktop.setLightCombineMode(LightState.OFF);
        desktopNode = new Node("desktop node");
        desktopNode.attachChild(jmeDesktop);
        guiRootNode.attachChild(desktopNode);
    }

    protected void fullScreen() {
        final DisplaySystem display = DisplaySystem.getDisplaySystem();
        desktopNode.getLocalRotation().set(0, 0, 0, 1);
        desktopNode.getLocalTranslation().set(display.getWidth() / 2, display.getHeight() / 2, 0);
        desktopNode.getLocalScale().set(1, 1, 1);
        desktopNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
    }

    /**
     * camera configuration from SimpleGame; avoid unexpected behaviour.
     */
    protected void initCamera() {
        cam = display.getRenderer().createCamera(display.getWidth(), display.getHeight());
        cam.setFrustumPerspective(45.0f, (float) display.getWidth() / (float) display.getHeight(), 1, 1000);
        cam.setParallelProjection(false);
        cam.update();
        Vector3f loc = new Vector3f(0.0f, 0.0f, 25.0f);
        Vector3f left = new Vector3f(-1.0f, 0.0f, 0.0f);
        Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
        Vector3f dir = new Vector3f(0.0f, 0f, -1.0f);
        cam.setFrame(loc, left, up, dir);
        cam.update();
    }

    protected void initZBuffer() {
        ZBufferState buf = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
        buf.setEnabled(true);
        buf.setFunction(ZBufferState.CF_LEQUAL);
        rootNode.setRenderState(buf);
    }

    protected abstract void initInputHandler();

    public void setActive(boolean active) {
        if (active)
            onActivate();
        super.setActive(active);
    }

    @Override
    public void render(float tpf) {
        super.render(tpf);
        DisplaySystem.getDisplaySystem().getRenderer().draw(guiRootNode);
    }

    protected void onActivate() {
        display.getRenderer().setCamera(cam);
    }
}