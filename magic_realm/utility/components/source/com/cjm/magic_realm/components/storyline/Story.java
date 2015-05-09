package com.cjm.magic_realm.components.storyline;

import java.util.ArrayList;
import java.util.Optional;

import com.cjm.magic_realm.components.storyline.StoryStep.StepStatus;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public abstract class Story {
	public abstract boolean canStart(CharacterWrapper character);
	public abstract boolean isStarted();
	public abstract void start(CharacterWrapper character);
	public abstract void end(CharacterWrapper character);
	public abstract void handleStoryEvent(String eventKey, CharacterWrapper character, Object payload);
	public abstract String getDescription();
	public abstract String getName();
	
	protected ArrayList<StoryStep>steps;
	
	protected Story(){
		steps = new ArrayList<StoryStep>();
	}
	
	public ArrayList<StoryStep>getSteps(){
		return steps;
	}
	
	public void changeStepStatus(String stepName, StepStatus status){
		Optional<StoryStep> step = steps.stream()
				.filter(s -> s.Name.equalsIgnoreCase(stepName))
				.findFirst();
		
		
		//CJM -- for now I want this to throw if we don't find anything
		step.get().Status = status;
	}
	
	public void resetSteps(){
		steps.stream()
			.forEach(s -> s.Status = StepStatus.Pending);
	}
	
	public void completeSteps(){
		steps.stream()
			.forEach(s -> s.Status = StepStatus.Complete);
	}
}
