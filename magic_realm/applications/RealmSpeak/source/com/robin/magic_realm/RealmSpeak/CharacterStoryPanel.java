package com.robin.magic_realm.RealmSpeak;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cjm.magic_realm.components.storyline.Story;
import com.cjm.magic_realm.components.storyline.StoryManager;
import com.cjm.magic_realm.components.storyline.StoryStep;
import com.cjm.magic_realm.components.storyline.StoryStep.StepStatus;



public class CharacterStoryPanel extends CharacterFramePanel {

	JList<String> storyList;
	JPanel leftPanel;
	JPanel rightPanel;
	JPanel mainPanel;
	JLabel descriptionLabel;
	
	ListSelectionListener questSelected = new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent evt){
			setDescriptionLabelText();
		}
	};

	protected CharacterStoryPanel(CharacterFrame parent) {
		super(parent);
		initComponents();
	}
	
	private void setDescriptionLabelText(){
		int index = storyList.getSelectedIndex();
		if(index == -1){
			descriptionLabel.setText("");
			return;
		}
		
		String storyName = storyList.getModel().getElementAt(index);
		Story story = getStoryWithName(storyName);	
		descriptionLabel.setText(getDescriptionLabelHtml(story));
	}
	
	private String getDescriptionLabelHtml(Story story){
		return "<html>"
				+ "<body style='width:99%;'>"
				+ "<h2>" + story.getName() + "</h2>"
				+ "<p><b>Description: </b>" + story.getDescription() + "</p><br/>"
				+ "<hr><br/>"
				+ "<p><b>Quest Steps: </b>"
				+ "<ul>"
				+ getStepsHtml(story.getSteps())
				+ "</p>"
				+ "</body>"
				+ "</html>";
	}
	
	private String getStepsHtml(ArrayList<StoryStep>steps){
		HashMap<StepStatus, String>styles = new HashMap<StepStatus, String>();
		styles.put(StepStatus.Pending, "");
		styles.put(StepStatus.Current, "color:#CCCC00;font-weight:bold;");
		styles.put(StepStatus.Irrelevant, "text-decoration: line-through;");
		styles.put(StepStatus.Complete, "color:green;");
		
		return steps.stream()
			.map(s -> "<li><span style='" + styles.get(s.Status) + "'>" + s.Name + "</span>")
			.collect(Collectors.joining(""));
	}
	
	private void initComponents() {	
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(200,400));
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		mainPanel.add(leftPanel);
		mainPanel.add(Box.createHorizontalStrut(20));
		

		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(400,400));
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		descriptionLabel = new JLabel();
		descriptionLabel.setPreferredSize(new Dimension(380,380));
		descriptionLabel.setFont(new Font("arial", Font.PLAIN, 12));
		descriptionLabel.setVerticalTextPosition(SwingConstants.TOP);
		descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
		
		rightPanel.add(descriptionLabel);
		
		
		mainPanel.add(rightPanel);
		
		add(mainPanel);
		
		createStoryListBox();
		updateControls();
	}

	private void updateControls() {	

	}
	
	private void createStoryListBox(){
		storyList = new JList<String>();
		storyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		storyList.setLayoutOrientation(JList.VERTICAL);
		storyList.setVisibleRowCount(20);

		storyList.setBackground(Color.WHITE);
		storyList.setFont(new Font("arial", Font.PLAIN, 12));
		
		storyList.addListSelectionListener(questSelected);
		
		leftPanel.add(storyList);
	}
	
	@Override
	public void updatePanel() {
		DefaultListModel<String> storyModel = new DefaultListModel<String>();

		getStories().stream()
			.forEach(s -> storyModel.addElement(s.getName()));
		
		storyList.setModel(storyModel);
		setDescriptionLabelText();
	}

	private ArrayList<Story>getStories(){
		StoryManager mgr = StoryManager.getInstance();
		return mgr.getStoryList(getCharacter().getName()).allStories();
	}
	
	private Story getStoryWithName(String storyName){
		StoryManager mgr = StoryManager.getInstance();
		return mgr.getStory(getCharacter().getName(), storyName);
	}
}
