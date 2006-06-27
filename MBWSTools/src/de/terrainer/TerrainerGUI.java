package de.terrainer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

import org.apache.log4j.BasicConfigurator;

import de.terrainer.generators.PerlinNoise;
import de.terrainer.generators.RandomMidpointDisplacement;
import de.terrainer.gui.HeightMapComponent;
import de.terrainer.gui.PropertyPanel;

public class TerrainerGUI extends JFrame {
	public static Random random = new Random(System.currentTimeMillis());

	protected HeightMapComponent heightMapComp = new HeightMapComponent();
	protected WorldMap world;
	protected HeightMapCache currentHeightMap;
	protected PropertyPanel propPanel;

	TerrainerGUI() {
		super("Terrainer");
		BasicConfigurator.configure();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
		propPanel = new PropertyPanel();
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createHeightMapPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.NORTH);
		getContentPane().add(propPanel, BorderLayout.EAST);

		setJMenuBar(createMenu());

		setResizable(false);
		pack();

	}

	private JMenuBar createMenu() {
		JMenuBar mb = new JMenuBar();
		JMenu fileMen = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		newItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewTerrain();
			}
		});
		fileMen.add(newItem);
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		fileMen.add(saveItem);
		fileMen.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		fileMen.add(exitItem);
		mb.add(fileMen);
		return mb;
	}

	protected void createNewTerrain() {
		world = new WorldMap();
		WorldMapWizard wiz = new WorldMapWizard(this, world);
		wiz.setVisible(true);
		if (!wiz.isCancelled()) {
			// calculate power or 2 bigger than resolution
			int max = Math.max(world.getHeight(), world.getWidth());
			int size = calculateNextPowerOf2(max);
			int detailsize = (int) Math.pow(2, world.getSectionResolution());
			currentHeightMap = new HeightMapCache(size, detailsize);
			System.out.println("size: " + size);
			System.out.println("detailsize: "+ detailsize);
			heightMapComp.setHeightMap(currentHeightMap);
		}
	}

	private int calculateNextPowerOf2(int val) {
		int log2 = 0;
		int inc = 0;
		// first we shift to the right until we find the last bit set
		while (val > 1) {
			// find out if one of the lesser bit is set (meaning val is not
			// exactly a power of 2)
			if ((val & 1) != 0)
				inc = 1;
			val >>= 1;
			log2++;
		}
		int size = 1;
		// now we calculate a power of two
		// if val was not a power of two we increment to the next power of two
		for (int i = 0; i < log2 + inc; i++) {
			size <<= 1;
		}
		return size;
	}

	protected void save() {
		currentHeightMap.flush();
	}
	
	protected void load() {
		//HeightMap.load();
	}

	private JPanel createHeightMapPanel() {
		JPanel pan = new JPanel(new BorderLayout());
		pan.setBorder(BorderFactory.createTitledBorder(BorderFactory
				.createLoweredBevelBorder(), "Section"));
		pan.add(heightMapComp, BorderLayout.CENTER);
		return pan;
	}

	private JToolBar createButtonPanel() {
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		JButton worldModeButton = new JButton(new ImageIcon(ClassLoader
				.getSystemResource("de/terrainer/resource/arrow.gif")));
		worldModeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TerrainerGUI.this.createNewTerrain();
			}
		});
		toolbar.add(worldModeButton);
		toolbar.addSeparator();

		AbstractGenerator rmd = new RandomMidpointDisplacement(heightMapComp);
		JButton rmdButton = createGeneratorButton(rmd);
		toolbar.add(rmdButton);

        AbstractGenerator perlin = new PerlinNoise(heightMapComp);
        JButton perlinButton = createGeneratorButton(perlin);
        toolbar.add(perlinButton);
		// viewButton
		toolbar.addSeparator();
		ImageIcon viewIcon = new ImageIcon(ClassLoader
				.getSystemResource("de/terrainer/resource/view.png"));
		JButton viewButton = new JButton(viewIcon);
		viewButton.setToolTipText("view in 3D window");
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				(new Viewer()).showViewer();
			}
		});
		toolbar.add(viewButton);
		return toolbar;
	}

	private JButton createGeneratorButton(final AbstractGenerator generator) {
		JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				heightMapComp.repaint();
			}
		});
		button.addMouseListener(new MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				propPanel.update(generator);
				pack();
			}
		});
		button.setAction(generator.getAction());
		Icon icon = generator.getIcon();
		button.setIcon(icon);
		button.setToolTipText(generator.getName());
		return button;
	}

	protected void close() {
		System.exit(0);
	}

	public static void main(String[] args) {
		(new TerrainerGUI()).setVisible(true);
	}
}
