package com.cjm.magic_realm.components.storyline;

import java.util.ArrayList;
import java.util.HashMap;

public class StoryList {
	private HashMap<String, Story>stories; //storyKey, Story
	
	public StoryList(){
		stories = new HashMap<String, Story>();
	}
	
	public Story getStory(String key){
		return stories.get(key);
	}
	
	public void addStory(Story story){
		stories.put(story.getName(), story);
	}
	
	public void removeStory(String storyName){
		stories.remove(storyName);
	}
	
	public ArrayList<Story>allStories(){
		ArrayList<Story>result = new ArrayList<Story>();
		stories.forEach((k,v) -> result.add(v));
		return result;
	}
	
	public int count(){
		return stories.size();
	}
}
