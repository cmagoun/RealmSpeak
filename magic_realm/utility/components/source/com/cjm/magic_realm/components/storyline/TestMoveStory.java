package com.cjm.magic_realm.components.storyline;

import com.cjm.magic_realm.components.storyline.StoryStep.StepStatus;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class TestMoveStory extends Story {

	private String GO_TO = "Move to Ruins 3";
	private String RETURN_TO_INN = "Return to Inn";
	
	public enum States{None, Started, ArrivedAtDestination, ReturnedHome};
	private States state = States.None;
	

	public TestMoveStory(){
		steps.add(new StoryStep(GO_TO));
		steps.add(new StoryStep(RETURN_TO_INN));
	}
	
	@Override
	public void handleStoryEvent(String eventKey, CharacterWrapper character, Object payload) {
		switch(state){
		
		case ArrivedAtDestination:
			if(eventKey.equalsIgnoreCase("move") && StoryRequirements.isInDwelling(character, "inn")){
				state = States.ReturnedHome;
				completeSteps();
			}
			
		case ReturnedHome:
			break;
			
		case Started:
			if(eventKey.equalsIgnoreCase("move")  && StoryRequirements.isInClearing(character, "ruins 3")){
				state = States.ArrivedAtDestination;
				setComplete(GO_TO);
				setCurrent(RETURN_TO_INN);
				
			}
		default:
			break;
		
		}
	}

	@Override
	public String getDescription() {
		return "This is a test to see if we can handle movement events. Head to Ruins 3, then return to the Inn.";
	}

	@Override
	public void start(CharacterWrapper character) {
		state = States.Started;
		setCurrent(GO_TO);
	}

	@Override
	public void end(CharacterWrapper character) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canStart(CharacterWrapper character) {
		return StoryRequirements.isInDwelling(character);
	}

	@Override
	public boolean isStarted() {
		return state != States.None;
	}

	@Override
	public String getName() {
		return "Test Move Story";
	}

	@Override
	public String getStartInstructions() {
		return "You may start this quest in any dwelling";
	}

}
