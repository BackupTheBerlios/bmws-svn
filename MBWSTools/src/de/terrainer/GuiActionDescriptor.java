package de.terrainer;

import javax.swing.Action;
import javax.swing.ImageIcon;

public interface GuiActionDescriptor {
	
	public MetaInfo[] getMetaInfo();

	public Action getAction();
	
	public ImageIcon getIcon();

}
