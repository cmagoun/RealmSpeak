package com.cjm.magic_realm.components.storyline;

import java.util.HashMap;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class StoryManager {
	private static StoryManager instance = null;
	private static HashMap<String, StoryList> stories; //characterName, StoryList
	
	
	protected StoryManager(){
		stories = new HashMap<String, StoryList>();
	}
	
	public static StoryManager getInstance(){
		if(instance == null){
			instance = new StoryManager();
		}
		
		return instance;
	}
	
	public void addCharacter(String characterName){
		stories.put(characterName, new StoryList());
	}
	
	public void addStory(String characterName, String key, Story story){
		StoryList list = getStoryList(characterName);
		if(list == null){addCharacter(characterName);}
		list.addStory(key, story);
	}
	
	public Story getStory(String characterName, String key){
		StoryList list = getStoryList(characterName);
		if(list == null){addCharacter(characterName);}
		return list.getStory(key);
	}
	
	public StoryList getStoryList(String characterName){
		return stories.get(characterName);
	}
	
	public void handleStoryEvent(String eventKey, CharacterWrapper forCharacter, Object payload){
	 	getStoryList(forCharacter.getName())
	 		.allStories()
	 		.stream()
	 		.forEach(s -> s.handleStoryEvent(eventKey, forCharacter, payload));
	}
}
