package com.robin.magic_realm.RealmSpeak;

import java.util.ArrayList;

import com.robin.magic_realm.components.utility.Constants;

public class CurseLabel {
	public String Key;
	public String IconLocation;
	public String ToolTipText;
	
	public CurseLabel(String key, String iconLoc, String text){
		this.Key = key;
		this.IconLocation = iconLoc;
		this.ToolTipText = text;
	}
	
	public static ArrayList<CurseLabel> getAllLabels(){
		ArrayList<CurseLabel>result = new ArrayList<CurseLabel>();
		result.add(new CurseLabel(Constants.EYEMIST, "curse/eyemist", "Eyemist - Cannot SEARCH"));
		result.add(new CurseLabel(Constants.SQUEAK, "curse/squeak", "Squeak - Cannot HIDE"));
		result.add(new CurseLabel(Constants.WITHER, "curse/wither", "Wither - Cannot have active effort chits"));
		result.add(new CurseLabel(Constants.ILL_HEALTH, "curse/illhealth", "Ill Health - Cannot REST"));
		result.add(new CurseLabel(Constants.ASHES, "curse/ashes", "Ashes - GOLD is worthless"));
		result.add(new CurseLabel(Constants.DISGUST, "curse/disgust", "Disgust - FAME is worthless"));
		return result;
	}
}
