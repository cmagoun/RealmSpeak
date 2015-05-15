package com.cjm.magic_realm.components.storyline;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class MoveStoryEvent extends StoryEvent {

	public MoveStoryEvent(CharacterWrapper character) {
		super("move", character, character.getCurrentLocation());
	}

}
