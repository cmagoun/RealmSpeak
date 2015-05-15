package com.cjm.magic_realm.components.storyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

//Singleton
public class StoryManager {
	private static StoryManager instance = null;
	private static HashMap<String, StoryList> stories; //characterName, StoryList
	private static HashMap<String, ArrayList<IObserveStory>> observers; 
	private static HashMap<String, ArrayList<CustomSearch>> customSearches;
	
	protected StoryManager(){
		stories = new HashMap<String, StoryList>();
		observers = new HashMap<String, ArrayList<IObserveStory>>();
		customSearches = new HashMap<String, ArrayList<CustomSearch>>();
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
		customSearches.put(characterName, new ArrayList<CustomSearch>());
	}
	
	public void addCharacter(CharacterWrapper character){
		addCharacter(character.getName());
	}
	
	//Story methods
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
	
	public void handleStoryEvent(StoryEvent evt){
	 	getStoryList(evt.character.getName())
 		.allStories()
 		.stream()
 		.forEach(s -> s.handleStoryEvent(evt.key, evt.payload));
	}

	//Searches
	public void addSearch(CharacterWrapper character, CustomSearch search){
		ArrayList<CustomSearch> searches = customSearches.get(character.getName());
		if(searches == null){
			addCharacter(character);
			searches = customSearches.get(character.getName());
		}
		
		searches.add(search);
	}
	
	public void removeSearch(CharacterWrapper character, CustomSearch search){
		ArrayList<CustomSearch> searches = customSearches.get(character.getName());
		if(searches == null){
			addCharacter(character);
			return;
		}
		
		searches.remove(search);
	}
	
	public void removeSearch(CharacterWrapper character, String desc) {
		ArrayList<CustomSearch> searches = customSearches.get(character.getName());
		Optional<CustomSearch> toRemove = searches.stream()
				.filter(cs -> cs.getDescription().equalsIgnoreCase(desc))
				.findFirst();
		
		if(toRemove.isPresent()){searches.remove(toRemove.get());}
		
	}
	
	public ArrayList<CustomSearch> getSearches(CharacterWrapper character){
		ArrayList<CustomSearch> searches = customSearches.get(character.getName());
		if(searches == null){
			addCharacter(character);
			return null;
		}
		
		return searches;
	}
	
	
	//StoryManager acts as an event sink for the story panels
	//Observers
	public void updatedStoryFor(String characterName){
		observers.get(characterName).stream()
			.forEach(o -> o.storyChanged());
	}

	public void registerObserver(String characterName, IObserveStory o){
		ArrayList<IObserveStory>obs = observers.get(characterName);
		obs.add(o);
	}


}
