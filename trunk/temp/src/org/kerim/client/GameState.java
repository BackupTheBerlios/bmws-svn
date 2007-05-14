/*
 * Copyright (c) 2003-2006 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.kerim.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

import jmetest.renderer.TestEnvMap;
import jmetest.renderer.TestSkybox;
import jmetest.renderer.loader.TestMilkJmeWrite;
import jmetest.terrain.TestProceduralSplatTexture;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.light.PointLight;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Controller;
import com.jme.scene.Node;
import com.jme.scene.Skybox;
import com.jme.scene.shape.Box;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.WireframeState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureKey;
import com.jme.util.TextureManager;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.geom.Debugger;
import com.jmex.game.state.StatisticsGameState;
import com.jmex.model.XMLparser.Converters.MilkToJme;
import com.jmex.model.animation.JointController;
import com.jmex.model.animation.KeyframeController;
import com.jmex.terrain.TerrainBlock;
import com.jmex.terrain.util.ImageBasedHeightMap;
import com.jmex.terrain.util.ProceduralSplatTextureGenerator;

/**
 * <code>TestGameState</code> provides an extremely basic gamestate with
 * various testing features pre-implemented. The preferred way to utilize this
 * is to instantiate your game, instantiate the TestGameState, register it with
 * <code>GameStateManager</code>, and then use the getRootNode() method on
 * TestGameState to get the root node, or simply extend this class to create
 * your own test scenario.
 * 
 * @author Matthew D. Hicks
 */
public class GameState extends StatisticsGameState {
  protected InputHandler input;
  protected WireframeState wireState;
  protected LightState lightState;
  protected boolean pause;
  protected boolean showBounds = false;
  protected boolean showDepth = false;
  protected boolean showNormals = false;
  private TerrainBlock tb;

  public GameState() {
    this(true);
  }

  public GameState(boolean handleInput) {
    init(handleInput);
  }

  private void init(boolean handleInput) {
    rootNode = new Node("RootNode");

    // Create a wirestate to toggle on and off. Starts disabled with default
    // width of 1 pixel.
    wireState = DisplaySystem.getDisplaySystem().getRenderer()
        .createWireframeState();
    wireState.setEnabled(false);
    rootNode.setRenderState(wireState);

    // Create ZBuffer for depth
    ZBufferState zbs = DisplaySystem.getDisplaySystem().getRenderer()
        .createZBufferState();
    zbs.setEnabled(true);
    zbs.setFunction(ZBufferState.CF_LEQUAL);
    rootNode.setRenderState(zbs);

    // Lighting
    /** Set up a basic, default light. */
    PointLight light = new PointLight();
    light.setDiffuse(new ColorRGBA(0.75f, 0.75f, 0.75f, 0.75f));
    light.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
    light.setLocation(new Vector3f(100, 100, 100));
    light.setEnabled(true);

    /** Attach the light to a lightState and the lightState to rootNode. */
    lightState = DisplaySystem.getDisplaySystem().getRenderer()
        .createLightState();
    lightState.setEnabled(true);
    lightState.attach(light);
    rootNode.setRenderState(lightState);

    // Terrain
    initTerrain();
    // SkyBox
    initSkyBox();
    // Player
    initPlayer();

    // Initial InputHandler
    if (handleInput) {
      input = new FirstPersonHandler(DisplaySystem.getDisplaySystem()
          .getRenderer().getCamera(), 15.0f, 0.5f);
      initKeyBindings();
    }

    // Signal to the renderer that it should keep track of rendering
    // information.
    DisplaySystem.getDisplaySystem().getRenderer().enableStatistics(true);

    // Finish up
    rootNode.updateRenderState();
    rootNode.updateWorldBound();
    rootNode.updateGeometricState(0.0f, true);
    // rootNode.setRenderQueueMode(Renderer.QUEUE_OPAQUE);
  }

  private void initTerrain() {
    // MidPointHeightMap heightMap = new MidPointHeightMap(128, 1.9f);
    ImageBasedHeightMap heightMap = new ImageBasedHeightMap(new ImageIcon(
        GameState.class.getClassLoader().getResource(
            "jmetest/data/IslandExport/heightmap.png")).getImage());
    // ImageBasedHeightMap heightMap = new ImageBasedHeightMap(new ImageIcon(
    // GameState.class.getClassLoader().getResource("jmetest/data/IslandExport/island.gif")).getImage());

    Vector3f terrainScale = new Vector3f(0.5f, 0.001f, 0.5f);
    // System.out.println(heightMap.getSize());
    tb = new TerrainBlock("Terrain", heightMap.getSize(), terrainScale,
        heightMap.getHeightMap(), new Vector3f(0, 0, 0), false);
    // tb.setTrisPerPixel( 0.5f);
    // tb.setDistanceTolerance( 1.0f);
    tb.setDetailTexture(1, 16);
    tb.setModelBound(new BoundingBox());
    tb.updateModelBound();
    tb.setLocalTranslation(new Vector3f(0, -10, 0));
    rootNode.attachChild(tb);
    ProceduralSplatTextureGenerator pst = new ProceduralSplatTextureGenerator(
        heightMap);
    pst.addTexture(new
        ImageIcon(TestProceduralSplatTexture.class.getClassLoader().getResource(
        "jmetest/data/texture/grassb.png")), -128, 0, 128);
//    pst
//        .addSplatTexture(
//            new ImageIcon(
//                GameState.class
//                    .getClassLoader()
//                    .getResource(
//                        "jmetest/data/IslandExport/alphamap_MaPZone[Dirty_ground_diffuse].PNG")),
//            new ImageIcon(GameState.class.getClassLoader().getResource(
//                "jmetest/data/IslandExport/MaPZone[Dirty_ground_diffuse].PNG")));
    pst
    .addSplatTexture(
        new ImageIcon(
            GameState.class
                .getClassLoader()
                .getResource(
                    "jmetest/data/IslandExport/alphamap_MaPZone[stone_01_diffuse].png")),
        new ImageIcon(GameState.class.getClassLoader().getResource(
            "jmetest/data/IslandExport/MaPZone[stone_01_diffuse].png")));
    
    // pst.addTexture(new
    // ImageIcon(TestProceduralSplatTexture.class.getClassLoader().getResource(
    // "jmetest/data/texture/dirt.jpg")), 0, 128, 255);
    // pst.addTexture(new
    // ImageIcon(TestProceduralSplatTexture.class.getClassLoader().getResource(
    // "jmetest/data/texture/highest.jpg")), 128, 255, 384);
    //
    // pst.addSplatTexture(new
    // ImageIcon(TestProceduralSplatTexture.class.getClassLoader().getResource(
    // "jmetest/data/texture/terrainTex.png")), new
    // ImageIcon(TestProceduralSplatTexture.class.getClassLoader().getResource(
    // "jmetest/data/texture/water.png")));

    pst.createTexture(512);

    TextureState ts = DisplaySystem.getDisplaySystem().getRenderer()
        .createTextureState();
    ts.setEnabled(true);
    Texture t1 = TextureManager.loadTexture(pst.getImageIcon().getImage(),
        Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true);
    ts.setTexture(t1, 0);

    Texture t2 = TextureManager.loadTexture(TestProceduralSplatTexture.class
        .getClassLoader().getResource("jmetest/data/texture/Detail.jpg"),
        Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);

    ts.setTexture(t2, 1);
    t2.setWrap(Texture.WM_WRAP_S_WRAP_T);

    t1.setApply(Texture.AM_COMBINE);
    t1.setCombineFuncRGB(Texture.ACF_MODULATE);
    t1.setCombineSrc0RGB(Texture.ACS_TEXTURE);
    t1.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
    t1.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
    t1.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
    t1.setCombineScaleRGB(1.0f);

    t2.setApply(Texture.AM_COMBINE);
    t2.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
    t2.setCombineSrc0RGB(Texture.ACS_TEXTURE);
    t2.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
    t2.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
    t2.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
    t2.setCombineScaleRGB(1.0f);
    rootNode.setRenderState(ts);
  }

  private void initSkyBox() {
    // Create a skybox
    Skybox m_skybox = new Skybox("skybox", 256, 256, 256);

    Texture north = TextureManager.loadTexture(TestSkybox.class
        .getClassLoader().getResource("jmetest/data/texture/north.jpg"),
        Texture.MM_LINEAR, Texture.FM_LINEAR);
    Texture south = TextureManager.loadTexture(TestSkybox.class
        .getClassLoader().getResource("jmetest/data/texture/south.jpg"),
        Texture.MM_LINEAR, Texture.FM_LINEAR);
    Texture east = TextureManager.loadTexture(TestSkybox.class.getClassLoader()
        .getResource("jmetest/data/texture/east.jpg"), Texture.MM_LINEAR,
        Texture.FM_LINEAR);
    Texture west = TextureManager.loadTexture(TestSkybox.class.getClassLoader()
        .getResource("jmetest/data/texture/west.jpg"), Texture.MM_LINEAR,
        Texture.FM_LINEAR);
    Texture up = TextureManager.loadTexture(TestSkybox.class.getClassLoader()
        .getResource("jmetest/data/texture/top.jpg"), Texture.MM_LINEAR,
        Texture.FM_LINEAR);
    Texture down = TextureManager.loadTexture(TestSkybox.class.getClassLoader()
        .getResource("jmetest/data/texture/bottom.jpg"), Texture.MM_LINEAR,
        Texture.FM_LINEAR);

    m_skybox.setTexture(Skybox.NORTH, north);
    m_skybox.setTexture(Skybox.WEST, west);
    m_skybox.setTexture(Skybox.SOUTH, south);
    m_skybox.setTexture(Skybox.EAST, east);
    m_skybox.setTexture(Skybox.UP, up);
    m_skybox.setTexture(Skybox.DOWN, down);
    m_skybox.preloadTextures();
    rootNode.attachChild(m_skybox);

  }

  private void initPlayer() {
    // // Put our box in it
    // Box box = new Box("player", new Vector3f(0, 0, 0), 1, 1.8f, 1);
    // box.setModelBound(new BoundingBox());
    // box.updateModelBound();
    // box.setLocalTranslation(new Vector3f(250,tb.getHeight(new
    // Vector2f(250,250)),250));
    // box.updateRenderState();
    // TextureState ts =
    // DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
    // //Base texture, not environmental map.
    // Texture t0 = TextureManager.loadTexture(
    // TestEnvMap.class.getClassLoader().getResource(
    // "jmetest/data/images/Monkey.jpg"),
    // Texture.MM_LINEAR_LINEAR,
    // Texture.FM_LINEAR);
    // t0.setWrap(Texture.WM_WRAP_S_WRAP_T);
    // ts.setTexture(t0);
    // box.setRenderState(ts);
    // box.getBatch(0).scaleTextureCoordinates(0, 5);
    // rootNode.attachChild(box);

    MilkToJme converter = new MilkToJme();
    URL MSFile = TestMilkJmeWrite.class.getClassLoader().getResource(
        "jmetest/data/dwarf/dwarf1.ms3d");
    ByteArrayOutputStream BO = new ByteArrayOutputStream();

    try {
      converter.convert(MSFile.openStream(), BO);
    } catch (IOException e) {
      System.out.println("IO problem writting the file!!!");
      System.out.println(e.getMessage());
      System.exit(0);
    }

    URL TEXdir = TestMilkJmeWrite.class.getClassLoader().getResource(
        "jmetest/data/dwarf/");
    TextureKey.setOverridingLocation(TEXdir);
    // older comment jbr.setProperty("texurl",TEXdir);
    Node i = null;
    try {
      // TODO: ANIMATION ALLWAYS WORKING ?
      i = (Node) BinaryImporter.getInstance().load(
          new ByteArrayInputStream(BO.toByteArray()));
      i.setLocalTranslation(new Vector3f(0, tb
          .getHeight(new Vector2f(250, 250)) - 10f, 0));
      System.out.println(i.getControllers().size());
      Controller c = i.getController(0);// getChild(0).getController(0);
      if (c instanceof KeyframeController) {
        System.out.println("key");
        c.setSpeed(0);
        c.setActive(false);
        ((KeyframeController) c).setNewAnimationTimes(0, 0);
      } else if (c instanceof JointController) {
        c.setSpeed(0);
        // object.getJointController().setTimes(
        // object.getAnimationData().getStandStartTime(),
        // object.getAnimationData().getStandEndTime());
      }

    } catch (IOException e) {
      System.out.println("darn exceptions:" + e.getMessage());
    }
    i.setLocalScale(.1f);
    rootNode.attachChild(i);

  }

  private void initKeyBindings() {
    /** Assign key P to action "toggle_pause". */
    KeyBindingManager.getKeyBindingManager()
        .set("toggle_pause", KeyInput.KEY_P);
    /** Assign key T to action "toggle_wire". */
    KeyBindingManager.getKeyBindingManager().set("toggle_wire", KeyInput.KEY_T);
    /** Assign key L to action "toggle_lights". */
    KeyBindingManager.getKeyBindingManager().set("toggle_lights",
        KeyInput.KEY_L);
    /** Assign key B to action "toggle_bounds". */
    KeyBindingManager.getKeyBindingManager().set("toggle_bounds",
        KeyInput.KEY_B);
    /** Assign key N to action "toggle_normals". */
    KeyBindingManager.getKeyBindingManager().set("toggle_normals",
        KeyInput.KEY_N);
    /** Assign key C to action "camera_out". */
    KeyBindingManager.getKeyBindingManager().set("camera_out", KeyInput.KEY_C);
    KeyBindingManager.getKeyBindingManager()
        .set("screen_shot", KeyInput.KEY_F1);
    KeyBindingManager.getKeyBindingManager().set("exit", KeyInput.KEY_ESCAPE);
    KeyBindingManager.getKeyBindingManager().set("parallel_projection",
        KeyInput.KEY_F2);
    KeyBindingManager.getKeyBindingManager().set("toggle_depth",
        KeyInput.KEY_F3);
    KeyBindingManager.getKeyBindingManager().set("mem_report", KeyInput.KEY_R);
    KeyBindingManager.getKeyBindingManager()
        .set("toggle_mouse", KeyInput.KEY_M);
  }

  public void update(float tpf) {
    super.update(tpf);
    // Update the InputHandler
    if (input != null) {
      input.update(tpf);

      /** If toggle_pause is a valid command (via key p), change pause. */
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_pause", false)) {
        pause = !pause;
      }

      if (pause)
        return;
    }

    // Update the geometric state of the rootNode
    rootNode.updateGeometricState(tpf, true);

    if (input != null) {
      /** If toggle_wire is a valid command (via key T), change wirestates. */
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_wire", false)) {
        wireState.setEnabled(!wireState.isEnabled());
        rootNode.updateRenderState();
      }
      /** If toggle_lights is a valid command (via key L), change lightstate. */
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_lights", false)) {
        lightState.setEnabled(!lightState.isEnabled());
        rootNode.updateRenderState();
      }
      /** If toggle_bounds is a valid command (via key B), change bounds. */
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_bounds", false)) {
        showBounds = !showBounds;
      }
      /** If toggle_depth is a valid command (via key F3), change depth. */
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_depth", false)) {
        showDepth = !showDepth;
      }

      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_normals", false)) {
        showNormals = !showNormals;
      }
      /** If camera_out is a valid command (via key C), show camera location. */
      if (KeyBindingManager.getKeyBindingManager().isValidCommand("camera_out",
          false)) {
        System.err.println("Camera at: "
            + DisplaySystem.getDisplaySystem().getRenderer().getCamera()
                .getLocation());
      }
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "screen_shot", false)) {
        DisplaySystem.getDisplaySystem().getRenderer().takeScreenShot(
            "SimpleGameScreenShot");
      }
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "parallel_projection", false)) {
        if (DisplaySystem.getDisplaySystem().getRenderer().getCamera()
            .isParallelProjection()) {
          cameraPerspective();
        } else {
          cameraParallel();
        }
      }
      if (KeyBindingManager.getKeyBindingManager().isValidCommand("mem_report",
          false)) {
        long totMem = Runtime.getRuntime().totalMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        long maxMem = Runtime.getRuntime().maxMemory();

        System.err.println("|*|*|  Memory Stats  |*|*|");
        System.err.println("Total memory: " + (totMem >> 10) + " kb");
        System.err.println("Free memory: " + (freeMem >> 10) + " kb");
        System.err.println("Max memory: " + (maxMem >> 10) + " kb");
      }
      if (KeyBindingManager.getKeyBindingManager().isValidCommand(
          "toggle_mouse", false)) {
        MouseInput.get().setCursorVisible(!MouseInput.get().isCursorVisible());
        System.out.println("Cursor Visibility set to "
            + MouseInput.get().isCursorVisible());
      }

      if (KeyBindingManager.getKeyBindingManager()
          .isValidCommand("exit", false)) {
        System.exit(0);
      }
    }
  }

  protected void cameraPerspective() {
    DisplaySystem display = DisplaySystem.getDisplaySystem();
    Camera cam = display.getRenderer().getCamera();
    cam.setFrustumPerspective(45.0f, (float) display.getWidth()
        / (float) display.getHeight(), 1, 1000);
    cam.setParallelProjection(false);
    cam.update();
  }

  protected void cameraParallel() {
    DisplaySystem display = DisplaySystem.getDisplaySystem();
    Camera cam = display.getRenderer().getCamera();
    cam.setParallelProjection(true);
    float aspect = (float) display.getWidth() / display.getHeight();
    cam.setFrustum(-100.0f, 1000.0f, -50.0f * aspect, 50.0f * aspect, -50.0f,
        50.0f);
    cam.update();
  }

  public void render(float tpf) {
    super.render(tpf);

    // Render the rootNode
    DisplaySystem.getDisplaySystem().getRenderer().draw(rootNode);

    if (showBounds) {
      Debugger.drawBounds(rootNode, DisplaySystem.getDisplaySystem()
          .getRenderer(), true);
    }

    if (showNormals) {
      Debugger.drawNormals(rootNode, DisplaySystem.getDisplaySystem()
          .getRenderer());
    }

    if (showDepth) {
      DisplaySystem.getDisplaySystem().getRenderer().renderQueue();
      Debugger.drawBuffer(Texture.RTT_SOURCE_DEPTH, Debugger.NORTHEAST,
          DisplaySystem.getDisplaySystem().getRenderer());
    }
  }

  public void cleanup() {
  }
}
