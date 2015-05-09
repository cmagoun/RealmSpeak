package com.cjm.magic_realm.components.storyline;

public class StoryStep {
	public enum StepStatus{Pending, Current, Complete, Irrelevant}
	
	public String Name;
	public StepStatus Status;
	
	public StoryStep(String name){
		this.Name = name;
		this.Status = StepStatus.Pending;
	}
}
