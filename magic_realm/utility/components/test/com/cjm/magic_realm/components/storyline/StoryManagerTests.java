package com.cjm.magic_realm.components.storyline;

import static org.junit.Assert.*;

import org.junit.Test;

import com.robin.magic_realm.components.TestBaseWithLoader;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class StoryManagerTests extends TestBaseWithLoader {

	@Test
	public void canCreateStoryManager() {
		StoryManager manager = StoryManager.getInstance();
		assertNotNull(manager);
	}
	
	@Test
	public void canAddCharacterToStoryManager(){
		StoryManager manager = StoryManager.getInstance();
		manager.addCharacter("Black Knight");
		assertNotNull(manager.getStoryList("Black Knight"));
	}
	
	@Test
	public void canAddStory(){
		StoryManager manager = StoryManager.getInstance();
		manager.addCharacter("Amazon");
		manager.addStory("Amazon", new TestStory());
		
		assertEquals(manager.getStory("Amazon", "Test Story").getDescription(), "This is a test");
	}

	@Test
	public void whatHappensWhenWeAddTheSameStoryTwice(){
		StoryManager manager = StoryManager.getInstance();
		manager.addCharacter("Amazon");
		manager.addStory("Amazon", new TestStory());
		manager.addStory("Amazon", new TestStory());
		
		assertEquals(manager.getStoryList("Amazon").count(), 1);
	}
	
	@Test
	public void canSwitchStates(){
		StoryManager manager = StoryManager.getInstance();
		manager.addCharacter("Amazon");
		manager.addStory("Amazon", new TestStory());
		
		CharacterWrapper amazon = new CharacterWrapper(findGameObject("Amazon"));
		manager.handleStoryEvent("two", amazon, null);
		
		TestStory result = (TestStory)manager.getStory("Amazon", "Test Story");
		assertEquals(result.report(), "two");
	}
	
	@Test
	public void canSwitchStatesWhenTwoCharactersHaveStories(){
		StoryManager manager = StoryManager.getInstance();
		manager.addCharacter("Amazon");
		manager.addCharacter("Black Knight");
		manager.addStory("Amazon", new TestStory());
		manager.addStory("Black Knight", new TestStory());
		
		CharacterWrapper amazon = new CharacterWrapper(findGameObject("Amazon"));
		manager.handleStoryEvent("two", amazon, null);
		
		TestStory resultAmazon = (TestStory)manager.getStory("Amazon", "Test Story");
		TestStory resultBK = (TestStory)manager.getStory("Black Knight", "Test Story");
		assertEquals(resultAmazon.report(), "two"); //story should have advanced
		assertEquals(resultBK.report(), "one"); //story should be unaffected
	}
}
