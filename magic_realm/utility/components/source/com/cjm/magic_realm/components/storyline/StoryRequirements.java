package com.cjm.magic_realm.components.storyline;

import com.robin.game.objects.GameObject;
import com.robin.magic_realm.components.attribute.TileLocation;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class StoryRequirements {
	
	public static boolean isInDwelling(CharacterWrapper character, String dwellingName){
		TileLocation loc = character.getCurrentLocation();
		
		return loc.clearing.holdsDwelling() 
			&& loc.clearing.getDwelling().toString().equalsIgnoreCase(dwellingName);
	}
	
	public static boolean isInDwelling(CharacterWrapper character){
		TileLocation loc = character.getCurrentLocation();
		return loc.clearing.holdsDwelling();
	}
	
	public static boolean isInClearing(CharacterWrapper character, String clearingName){
		TileLocation loc = character.getCurrentLocation();
		return loc.clearing.shortString().equalsIgnoreCase(clearingName);
	}
	
	public static boolean isInTile(CharacterWrapper character, String tileName){
		TileLocation loc = character.getCurrentLocation();
		return loc.tile.getName().equalsIgnoreCase(tileName);
	}

	public static boolean hasFound(String searchResult, String needed) {
		return searchResult.toLowerCase().endsWith(needed.toLowerCase());
	}

	public static boolean hasKilledName(GameObject killed, String needed) {
		return killed.getName().equalsIgnoreCase(needed);
	}
	
	
	
}
