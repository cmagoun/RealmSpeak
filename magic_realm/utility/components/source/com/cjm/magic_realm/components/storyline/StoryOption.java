package com.cjm.magic_realm.components.storyline;

import java.awt.event.ActionListener;

public class StoryOption {
	public String Text;
	public ActionListener Action; 
	
	public StoryOption(String text, ActionListener action){
		this.Text = text;
		this.Action = action;
	}
}
