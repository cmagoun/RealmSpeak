package com.cjm.magic_realm.components.storyline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class TestMoveStory extends Story {

	private String GO_TO = "Move to ";
	private String RETURN_TO_INN = "Return to Inn";
	private String destination = "ruins 3";
	
	public enum States{None, Started, ArrivedAtDestination, ReturnedHome};
	private States state = States.None;
	

	public TestMoveStory(){
		steps.add(new StoryStep(GO_TO, GO_TO + destination));
		steps.add(new StoryStep(RETURN_TO_INN));
		destination = "ruins 3";
	}
	
	@Override
	public void handleStoryEvent(String eventKey, Object payload) {
		if(eventKey.equalsIgnoreCase("dest")){	
			Optional<StoryStep> step = steps.stream().filter(s -> s.Key.equals(GO_TO)).findFirst();
					
			String newLocation = (String)payload;
			destination = newLocation;
			
			step.get().changeDescription(GO_TO + destination);
			StoryManager.getInstance().updatedStoryFor(character.getName());
			return;
		}
		
		switch(state){
		
		case ArrivedAtDestination:
			if(eventKey.equalsIgnoreCase("move") && StoryRequirements.isInDwelling(character, "inn")){
				state = States.ReturnedHome;
				completeSteps();
			}
			
		case ReturnedHome:
			break;
			
		case Started:
			if(eventKey.equalsIgnoreCase("move")  && StoryRequirements.isInClearing(character, destination)){
				state = States.ArrivedAtDestination;
				setComplete(GO_TO);
				setCurrent(RETURN_TO_INN);
				
				showArrived();
				
			}
		default:
			break;
		
		}
	}

	@Override
	public String getDescription() {
		return "This is a test to see if we can handle movement events. Head to " + destination + ", then return to the Inn.";
	}

	@Override
	public void start(CharacterWrapper character) {
		state = States.Started;
		setCurrent(GO_TO);
		showIntro();
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
	
	private void showIntro(){
		StoryOptionsFrame frame = new StoryOptionsFrame();

		frame.setTitle("This is a Test!");
		frame.setText("You are sitting quietly in the Inn when suddenly, someone comes to you and tells you to get your butt to " + destination + ". Like NOW!!!");
		
		StoryOption ok = new StoryOption("OK", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				frame.setVisible(false);
			}
		});
		
		StoryOption cancel = new StoryOption("On second thought...", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				state = States.None;
				resetSteps();
				frame.setVisible(false);
			}
		});
		
		frame.addOptions(ok);
		frame.addOptions(cancel);
		frame.setVisible(true);	
	}
	
	private void showArrived(){
		StoryOptionsFrame frame = new StoryOptionsFrame();

		frame.setTitle("Are We There Yet?");
		frame.setText("You have arrived at " + destination + ", but alas there is nothing here. Return to the Inn to lament how boring this quest is. You gain 1 Fame for just being able to bear the tedium.");
		
		StoryOption ok = new StoryOption("OK", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				character.addFame(1);
				frame.setVisible(false);
			}
		});
		
		frame.addOptions(ok);
		frame.setVisible(true);	
	}

}
