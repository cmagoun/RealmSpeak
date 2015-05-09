package com.cjm.magic_realm.components.storyline;

public class StoryStep {
	public enum StepStatus{Pending, Current, Complete, Irrelevant}
	
	public String Name;
	public String Key;
	public StepStatus Status;
	
	public StoryStep(String key, String name){
		this.Name = name;
		this.Key = key;
		this.Status = StepStatus.Pending;
	}
	
	public StoryStep(String name){
		this.Name = name;
		this.Key = name;
		this.Status = StepStatus.Pending;
	}
}
