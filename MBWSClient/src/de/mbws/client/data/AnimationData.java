package de.mbws.client.data;

public class AnimationData {

	private float animationSpeed;
	
	private int walkStartTime;
	private int walkEndTime;
	
	private int standStartTime;
	private int standEndTime;
	
	private int fightStartTime;
	private int fightEndTime;
	
	public AnimationData() {
		super();
	}

	public float getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public int getFightEndTime() {
		return fightEndTime;
	}

	public void setFightEndTime(int fightEndTime) {
		this.fightEndTime = fightEndTime;
	}

	public int getFightStartTime() {
		return fightStartTime;
	}

	public void setFightStartTime(int fightStartTime) {
		this.fightStartTime = fightStartTime;
	}

	public int getStandEndTime() {
		return standEndTime;
	}

	public void setStandEndTime(int standEndTime) {
		this.standEndTime = standEndTime;
	}

	public int getStandStartTime() {
		return standStartTime;
	}

	public void setStandStartTime(int standStartTime) {
		this.standStartTime = standStartTime;
	}

	public int getWalkEndTime() {
		return walkEndTime;
	}

	public void setWalkEndTime(int walkEndTime) {
		this.walkEndTime = walkEndTime;
	}

	public int getWalkStartTime() {
		return walkStartTime;
	}

	public void setWalkStartTime(int walkStartTime) {
		this.walkStartTime = walkStartTime;
	}

}
