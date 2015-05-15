package com.cjm.magic_realm.components.storyline;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

//
public class StoryEvent {
	public String key;
	public Object payload;
	public CharacterWrapper character;
	
	public StoryEvent(String key, CharacterWrapper character, Object payload){
		this.key = key;
		this.payload = payload;
		this.character = character;
	}
	
}
