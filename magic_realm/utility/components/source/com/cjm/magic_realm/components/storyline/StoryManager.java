package com.cjm.magic_realm.components.storyline;

import java.util.ArrayList;
import java.util.HashMap;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

//Singleton
public class StoryManager {
	private static StoryManager instance = null;
	private static HashMap<String, StoryList> stories; //characterName, StoryList
	private static HashMap<String, ArrayList<IObserveStory>> observers; 
	
	protected StoryManager(){
		stories = new HashMap<String, StoryList>();
		observers = new HashMap<String, ArrayList<IObserveStory>>();
	}
	
	public static StoryManager getInstance(){
		if(instance == null){
			instance = new StoryManager();
		}
		
		return instance;
	}
	
	public void addCharacter(String characterName){
		stories.put(characterName, new StoryList());
		observers.put(characterName, new ArrayList<IObserveStory>());
	}
	
	public void addCharacter(CharacterWrapper character){
		addCharacter(character.getName());
	}
	
	public void addStory(CharacterWrapper character, Story story){
		StoryList list = getStoryList(character.getName());
		if(list == null){addCharacter(character.getName());}
		list.addStory(story);
		story.setCharacter(character);
	}
	
	public void removeStory(String characterName, String storyName){
		StoryList list = getStoryList(characterName);
		if(list == null) {
			addCharacter(characterName); 
			return;
		}
		
		list.removeStory(storyName);
	}
	
	public void removeStory(CharacterWrapper character, String storyName){
		removeStory(character.getName(), storyName);
	}
	
	public Story getStory(String characterName, String key){
		StoryList list = getStoryList(characterName);
		if(list == null){addCharacter(characterName);}
		return list.getStory(key);
	}
	
	public Story getStory(CharacterWrapper character, String key){
		return getStory(character.getName(), key);
	}
	
	public StoryList getStoryList(String characterName){
		return stories.get(characterName);
	}
	
	public StoryList getStoryList(CharacterWrapper character){
		return getStoryList(character.getName());
	}
	
	public void handleStoryEvent(String eventKey, CharacterWrapper forCharacter, Object payload){
	 	getStoryList(forCharacter.getName())
	 		.allStories()
	 		.stream()
	 		.forEach(s -> s.handleStoryEvent(eventKey, payload));
	}
	
	//StoryManager acts as an event sink for the story panels
	public void updatedStoryFor(String characterName){
		observers.get(characterName).stream()
			.forEach(o -> o.storyChanged());
	}

	public void registerObserver(String characterName, IObserveStory o){
		ArrayList<IObserveStory>obs = observers.get(characterName);
		obs.add(o);
	}
}
