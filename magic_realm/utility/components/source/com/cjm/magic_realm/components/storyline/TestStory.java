package com.cjm.magic_realm.components.storyline;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class TestStory extends Story {

	private enum States{One, Two, Three, Done};
	private States state = States.One;
	
	public TestStory() {super("Test");}

	@Override
	public void handleStoryEvent(String eventKey, CharacterWrapper forCharacter, Object payload) {
		switch(state){
		case One:
			if(eventKey == "two"){
				state = States.Two;
			}
		case Two:
			if(eventKey == "three"){
				state = States.Three;
			}
		case Three:
			if(eventKey == "done"){
				state = States.Done;
			} else {
				if(eventKey == "reset"){
					state = States.One;
				}
			}
		default:
			break;
		
		}
	}

	@Override
	public String getDescription() {
		return "This is a test";
	}
	
	public String report(){
		switch(state){
		case Done:
			return "done";
		case One:
			return "one";
		case Three:
			return "three";
		case Two:
			return "two";
		default:
			break;
		}
		return "???";
	}

	@Override
	public boolean canStart(CharacterWrapper character) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void start(CharacterWrapper character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(CharacterWrapper character) {
		// TODO Auto-generated method stub
		
	}

}
