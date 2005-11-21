package de.mbws.client.state.handler;

import com.jme.input.InputHandler;
import com.jmex.bui.event.ActionEvent;
import com.jmex.bui.event.ActionListener;

import de.mbws.client.state.CharacterSelectionState;

public class CharacterSelectionStateHandler extends InputHandler implements ActionListener {

	private CharacterSelectionState state;
	
	public CharacterSelectionStateHandler() {
		super();
	}
	
	public CharacterSelectionStateHandler(CharacterSelectionState state) {
		super();
		this.state = state;
	}

	public void actionPerformed(ActionEvent event) {
		String selectedCharacter = event.getAction();
		
	}

}
