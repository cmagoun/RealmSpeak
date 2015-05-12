package com.cjm.magic_realm.components.storyline;

import com.cjm.magic_realm.components.storyline.StoryStep.StepStatus;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class TestStory extends Story {

	private enum States{None, One, Two, Three, Done};
	private States state = States.One;
	
	public TestStory() {
		//CJM: I think the way we are managing steps is a little clunky
		steps.add(new StoryStep("Step One"));
		steps.add(new StoryStep("Step Two"));
		steps.add(new StoryStep("Step Three"));
		changeStepStatus("step one", StepStatus.Current);
	}

	@Override
	public void handleStoryEvent(String eventKey, Object payload) {
		switch(state){
		case One:
			if(eventKey == "two"){
				state = States.Two;
				setComplete("step one");
				setCurrent("step two");
			}
		case Two:
			if(eventKey == "three"){
				state = States.Three;
				setComplete("step two");
				setCurrent("step three");
			}
		case Three:
			if(eventKey == "done"){
				state = States.Done;
				setComplete("step three");
			} else {
				if(eventKey == "reset"){
					state = States.One;
					resetSteps();
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
	
	@Override
	public boolean isStarted() {
		return state != States.None;
	}
	
	@Override
	public String getName() {
		return "Test Story";
	}

	@Override
	public String getStartInstructions() {
		// TODO Auto-generated method stub
		return null;
	}

}
