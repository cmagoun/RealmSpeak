package com.cjm.magic_realm.components.storyline;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.event.ChangeListener;

import com.robin.magic_realm.components.table.RealmTable;

public class CustomSearch {
	private String description;
	private ImageIcon icon;
	private RealmTable table;
	private String clearing;
	
	public CustomSearch(String desc, String clearing,  ImageIcon icon, RealmTable table){
		this.description = desc;
		this.icon = icon;
		this.table = table;
		this.clearing = clearing;
	}
	
	public String getDescription(){
		return description;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public RealmTable getTable(){
		return table;
	}
	
	public RealmTable getTable(JFrame frame, ChangeListener listener){
		table.setFrame(frame);
		table.setListener(listener);
		return table;
	}
	
	public String getClearing(){
		return clearing;
	}
}
