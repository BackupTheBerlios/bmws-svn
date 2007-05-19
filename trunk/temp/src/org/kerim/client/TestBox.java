package org.kerim.client;

import com.jme.app.SimplePassGame;
import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.pass.RenderPass;
import com.jme.scene.shape.Box;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.CullState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

public class TestBox extends SimplePassGame {
    public static void main(String[] args) {
        TestBox app = new TestBox();
        app.setDialogBehaviour(ALWAYS_SHOW_PROPS_DIALOG);
        app.start();
    }

    protected void simpleInitGame() {
        display.setTitle("Repeating Texture");
        display.getRenderer().setBackgroundColor(ColorRGBA.gray);

        CullState cs = display.getRenderer().createCullState();
        cs.setCullMode(CullState.CS_BACK);
        rootNode.setRenderState(cs);

        Box floor = new Box("Floor", new Vector3f(), 10, 10, 10);
        floor.setModelBound(new BoundingBox());
        floor.updateModelBound();
        floor.copyTextureCoords(0, 0, 1);

        TextureState ts1 = createSplatTextureState(
                "jmetest/data/texture/clouds.png", null);

        TextureState ts2 = createSplatTextureState(
                "jmetest/data/images/Monkey.jpg",
                "jmetest/data/images/checkdown.png");

        TextureState ts3 = createSplatTextureState(
                "jmetest/data/texture/wall.jpg",
                "jmetest/data/cursor/cursor1.png");

        AlphaState as1 = display.getRenderer().createAlphaState();
        as1.setBlendEnabled(true);
        as1.setSrcFunction(AlphaState.SB_SRC_ALPHA);
        as1.setDstFunction(AlphaState.DB_ONE_MINUS_SRC_ALPHA);
        as1.setTestEnabled(true);
        as1.setTestFunction(AlphaState.TF_GREATER);
        as1.setEnabled(true);

        floor.setRenderState(as1);

        rootNode.attachChild(floor);

        //Setup renderpasses
        RenderPass firstPass = new RenderPass();
        firstPass.add(rootNode);
        firstPass.setPassState(ts1);
        pManager.add(firstPass);

        RenderPass secondPass = new RenderPass();
        secondPass.add(rootNode);
        secondPass.setPassState(ts2);
        pManager.add(secondPass);

        RenderPass thirdPass = new RenderPass();
        thirdPass.add(rootNode);
        thirdPass.setPassState(ts3);
        pManager.add(thirdPass);

        RenderPass fpsPass = new RenderPass();
        fpsPass.add(fpsNode);
        pManager.add(fpsPass);
    }

    private TextureState createSplatTextureState(String texture, String alpha) {
        TextureState ts = display.getRenderer().createTextureState();
        ts.setEnabled(true);

        Texture t0 = TextureManager.loadTexture(
                TestBox.class.getClassLoader().getResource(texture),
                Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
        t0.setWrap(Texture.WM_WRAP_S_WRAP_T);
        t0.setApply(Texture.AM_MODULATE);
        ts.setTexture(t0, 0);

        if (alpha != null) {
            Texture t1 = TextureManager.loadTexture(
                    TestBox.class.getClassLoader().getResource(alpha),
                    Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
            t1.setWrap(Texture.WM_WRAP_S_WRAP_T);
            t1.setApply(Texture.AM_COMBINE);
            t1.setCombineFuncRGB(Texture.ACF_REPLACE);
            t1.setCombineSrc0RGB(Texture.ACS_PREVIOUS);
            t1.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
            t1.setCombineFuncAlpha(Texture.ACF_REPLACE);
            ts.setTexture(t1, 1);
        }

        return ts;
    }
}