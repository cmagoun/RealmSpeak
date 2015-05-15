package com.cjm.magic_realm.components.storyline;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class SearchStoryEvent extends StoryEvent{
	public SearchStoryEvent(CharacterWrapper character, String searchResult) {
		super("search", character, searchResult);
	}
}
