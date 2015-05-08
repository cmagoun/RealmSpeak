package com.cjm.magic_realm.components.storyline;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public abstract class Story {
	public String Name;
	
	public abstract boolean canStart(CharacterWrapper character);
	public abstract void start(CharacterWrapper character);
	public abstract void end(CharacterWrapper character);
	public abstract void handleStoryEvent(String eventKey, CharacterWrapper character, Object payload);
	public abstract String getDescription();
	//public abstract boolean canAppear();

	protected Story(String name){
		Name = name;
	}
}
