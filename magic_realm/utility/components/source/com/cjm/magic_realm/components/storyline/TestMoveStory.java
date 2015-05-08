package com.cjm.magic_realm.components.storyline;

import com.robin.magic_realm.components.attribute.TileLocation;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class TestMoveStory extends Story {

	private enum States{None, Start, ArrivedAtDestination, ReturnedHome};
	private States state = States.None;
	

	public TestMoveStory() {
		super("Move Event Test");
	}

	@Override
	public void handleStoryEvent(String eventKey, CharacterWrapper character, Object payload) {
		switch(state){
		
		case ArrivedAtDestination:
			if(eventKey == "Move"){
				TileLocation loc = character.getCurrentLocation();
				if(loc.clearing.holdsDwelling() && loc.clearing.getDwelling().getGameObject().getName().equals("Inn")){
					state = States.ReturnedHome;
				}
			}
			
		case ReturnedHome:
			break;
			
		case Start:
			if(eventKey == "Move"){
				TileLocation loc = character.getCurrentLocation();
				if(loc.clearing.shortString().equals("Ruins 3")){
					state = States.ArrivedAtDestination;
				}
			}
		default:
			break;
		
		}
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "This is a test to see if we can handle movement events";
	}

	@Override
	public void start(CharacterWrapper character) {
		// TODO Auto-generated method stub
		state = States.Start;
	}

	@Override
	public void end(CharacterWrapper character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canStart(CharacterWrapper character) {
		return character.getCurrentLocation().clearing.holdsDwelling();
	}

}
