package com.cjm.magic_realm.components.stories;

import com.robin.magic_realm.components.table.RealmTable;
import com.robin.magic_realm.components.wrapper.CharacterWrapper;

public class FindWidgetTable extends RealmTable {
	public static final String FOUND = "You discovered the Widget (+1 Fame)";
		
	protected FindWidgetTable() {
		super(null, null);
		//CJM -- I don't think this will work
	}

	@Override
	public String getTableName(boolean longDescription) {
		return "Find Widget (1-4)";
	}

	@Override
	public String getTableKey() {
		return "findWidget";
	}

	@Override
	public String applyOne(CharacterWrapper character) {
		return FOUND;
	}

	@Override
	public String applyTwo(CharacterWrapper character) {
		return FOUND;
	}

	@Override
	public String applyThree(CharacterWrapper character) {
		return FOUND;
	}

	@Override
	public String applyFour(CharacterWrapper character) {
		return FOUND;
	}

	@Override
	public String applyFive(CharacterWrapper character) {
		return "Nothing";
	}

	@Override
	public String applySix(CharacterWrapper character) {
		return "Nothing";
	}

}
