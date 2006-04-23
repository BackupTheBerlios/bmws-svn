package de.mbws.client.gui.ingame;

public class CharacterLifeInfo {

	private String name;
	private int maxHitPoints;
	private int currentHitPoints;
	private int maxMana;
	private int currentMana;
	private int maxAggression;
	private int currentAggression;
	private int maxEndurance;
	private int currentEndurance;
	
	public CharacterLifeInfo() {
		super();
	}

	public int getCurrentAggression() {
		return currentAggression;
	}

	public void setCurrentAggression(int currentAggression) {
		this.currentAggression = currentAggression;
	}

	public int getCurrentEndurance() {
		return currentEndurance;
	}

	public void setCurrentEndurance(int currentEndurance) {
		this.currentEndurance = currentEndurance;
	}

	public int getCurrentHitPoints() {
		return currentHitPoints;
	}

	public void setCurrentHitPoints(int currentHitPoints) {
		this.currentHitPoints = currentHitPoints;
	}

	public int getCurrentMana() {
		return currentMana;
	}

	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}

	public int getMaxAggression() {
		return maxAggression;
	}

	public void setMaxAggression(int maxAggression) {
		this.maxAggression = maxAggression;
	}

	public int getMaxEndurance() {
		return maxEndurance;
	}

	public void setMaxEndurance(int maxEndurance) {
		this.maxEndurance = maxEndurance;
	}

	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	public void setMaxHitPoints(int maxHitPoints) {
		this.maxHitPoints = maxHitPoints;
	}

	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
