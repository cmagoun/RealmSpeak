package com.cjm.magic_realm.components.stories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import com.cjm.magic_realm.components.storyline.CustomSearch;
import com.cjm.magic_realm.components.storyline.Story;
import com.cjm.magic_realm.components.storyline.StoryManager;
import com.cjm.magic_realm.components.storyline.StoryOption;
import com.cjm.magic_realm.components.storyline.StoryOptionsFrame;
import com.cjm.magic_realm.components.storyline.StoryRequirements;
import com.cjm.magic_realm.components.storyline.StoryStep;
import com.robin.game.objects.GameData;
import com.robin.game.objects.GameObject;
import com.robin.magic_realm.components.utility.MonsterCreator;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class TestMoveStory extends Story {

	private String GO_TO = "Move to ";
	private String RETURN_TO_INN = "Return to Inn";
	private String FIND_WIDGET = "Find the Widget";
	private String KILL_GUARD = "Kill the Guardian";
	private String destination = "ruins 3";
	
	public enum States{None, Started, ArrivedAtDestination, FoundWidget, Guardian, ReturnedHome};
	private States state = States.None;
	
	public TestMoveStory(){
		steps.add(new StoryStep(GO_TO, GO_TO + destination));
		steps.add(new StoryStep(FIND_WIDGET));
		steps.add(new StoryStep(KILL_GUARD));
		steps.add(new StoryStep(RETURN_TO_INN));
		destination = "ruins 3";
	}
	
	@Override
	public void handleStoryEvent(String eventKey, Object payload) {
		//CHEATS -- change destination for ease of testing
		if(eventKey.equalsIgnoreCase("dest")){	
			Optional<StoryStep> step = steps.stream().filter(s -> s.Key.equals(GO_TO)).findFirst();
					
			String newLocation = (String)payload;
			destination = newLocation;
			
			step.get().changeDescription(GO_TO + destination);
			StoryManager.getInstance().updatedStoryFor(character.getName());
			return;
		}
	
		//NON-CHEATS
		switch(state){
		
		case Started:
			//have you gotten to your destination yet?
			if(eventKey.equalsIgnoreCase("move")  && StoryRequirements.isInClearing(character, destination)){
				state = States.ArrivedAtDestination;
				advanceStep();
				
				StoryManager.getInstance().addSearch(character, new CustomSearch("Find Widget", destination, character.getIcon(), new FindWidgetTable()));
				
				showArrived();		
			} 	
			break;
		
		case ArrivedAtDestination:
			if(eventKey.equalsIgnoreCase("search")){
				String searchResult = (String)payload;
				
				//have you found the widget yet?
				if(StoryRequirements.hasFound(searchResult, FindWidgetTable.FOUND)){
					state = States.Guardian;
					character.addFame(1);
					showGuardian();
					//createGuardianEncounter();
					advanceStep();
					StoryManager.getInstance().removeSearch(character, "Find Widget");
				}
			}
			break;
			
		case Guardian:
			if(eventKey.equalsIgnoreCase("kill")){
				GameObject killed = (GameObject)payload;
				
				if(StoryRequirements.hasKilledName(killed, "widget guard")){
					state = States.FoundWidget;
					advanceStep();
				}
			}
			break;
			
		case FoundWidget:
			//have you returned home yet?
			if(eventKey.equalsIgnoreCase("move") && StoryRequirements.isInDwelling(character, "inn")){
				state = States.ReturnedHome;
				completeSteps();
			}
			break;
			
		case ReturnedHome:
			break;
			
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
		advanceStep();
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
		frame.setText("You have arrived at " + destination + ", (+1 FAME) but you have yet to find the fabled Widget. SEARCH for the Widget and bring it back to the inn.");
		
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

	private void showGuardian(){
		StoryOptionsFrame frame = new StoryOptionsFrame();

		frame.setTitle("Guaridan Appears");
		frame.setText("As you pick up the Widget and start to leave the clearing, a fell beast appears!");
		
		StoryOption ok = new StoryOption("OK", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				frame.setVisible(false);
				createGuardianEncounter();
			}
		});
		
		frame.addOptions(ok);
		frame.setVisible(true);	
	}
	
	private void createGuardianEncounter(){
		GameData data = character.getGameObject().getGameData();
		MonsterCreator creator = new MonsterCreator("widget_guardian");
		GameObject summon = creator.createOrReuseMonster(data);
		
		creator.setupGameObject(summon, "Widget Guard", "imp", "monsters", "L", false, false);
		creator.setupSide(summon, "light", null, 0, 0, 0, 6, "lightgreen");
		creator.setupSide(summon, "dark", null, 0, 0, 0, 6, "forestgreen");
		
		character.getCurrentLocation().clearing.add(summon,null);
		character.setHidden(false);
	}
}
