package com.robin.magic_realm.RealmSpeak;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
	JPanel buttonPanel;
	JPanel mainPanel;
	JPanel descriptionPanel;
	JLabel descriptionLabel;
	JButton startButton;
	JButton abandonButton;
	int lastIndex;
	CharacterStoryPanel thePanel;
	
	ListSelectionListener questSelected = new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent evt){
			Story story = getSelectedStory();
			if(story != null){story.setPanel(thePanel);}
			setDescriptionLabelText(story);
		}
	};
	
	ActionListener startPressed = new ActionListener(){
		public void actionPerformed(ActionEvent evt){
			Story story = getSelectedStory();
			if(story == null){return;}
			
			story.start(getCharacter());
			story.setPanel(thePanel);
			
			lastIndex = storyList.getSelectedIndex();
			updatePanel();
		}
	};
	
	ActionListener abandonPressed = new ActionListener(){
		public void actionPerformed(ActionEvent evt){
			Story story = getSelectedStory();
			if(story == null){return;}
			
			StoryManager.getInstance().removeStory(getCharacter().getName(), story.getName());	
			updatePanel();
		}
	};

	protected CharacterStoryPanel(CharacterFrame parent) {
		super(parent);
		thePanel = this;
		initComponents();
	}
	
	private Story getSelectedStory(){
		int index = storyList.getSelectedIndex();
		if(index == -1) {return null;}
		
		String storyName = storyList.getModel().getElementAt(index);
		return getStoryWithName(storyName);	
	}
	
	private void setDescriptionLabelText(Story story){
		if(story != null){
			descriptionLabel.setText(getDescriptionLabelHtml(story));
		} else	{
			descriptionLabel.setText("");
		}		
	}
	
	private String getDescriptionLabelHtml(Story story){
		return "<html>"
				+ "<body style='width:99%;'>"
				+ "<h2>" + story.getName() + "</h2>"
				+ "<p><b>Description: </b>" + story.getDescription() + "</p><br/>"
				+ "<hr><br/>"
				+ "<p><b>To Start: </b>" + story.getStartInstructions() + "</p></br></br>"
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
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setPreferredSize(new Dimension(200,400));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));	
		
		mainPanel.add(leftPanel);
		mainPanel.add(Box.createHorizontalStrut(20));
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setPreferredSize(new Dimension(400,400));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		descriptionPanel = new JPanel();
		descriptionPanel.setBackground(Color.WHITE);
		descriptionPanel.setPreferredSize(new Dimension(380,330));
		rightPanel.add(descriptionPanel);
		mainPanel.add(rightPanel);
		
		descriptionLabel = new JLabel();
		descriptionLabel.setPreferredSize(new Dimension(380,330));
		descriptionLabel.setFont(new Font("arial", Font.PLAIN, 12));
		descriptionLabel.setVerticalTextPosition(SwingConstants.TOP);
		descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
		descriptionPanel.add(descriptionLabel);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setPreferredSize(new Dimension(400, 50));
		rightPanel.add(buttonPanel);
		
		startButton = new JButton();
		startButton.setPreferredSize(new Dimension(100,40));
		startButton.setText("Start");
		startButton.addActionListener(startPressed);
		buttonPanel.add(startButton);
		buttonPanel.add(Box.createHorizontalStrut(20));
		
		abandonButton = new JButton();
		abandonButton.setPreferredSize(new Dimension(100,40));
		abandonButton.setText("Abandon");
		abandonButton.addActionListener(abandonPressed);
		buttonPanel.add(abandonButton);
			
		add(mainPanel);
		
		createStoryListBox();
		updateControls();
	}

	private void updateControls() {	
		//TODO: enable/disable buttons properly
	}
	
	private void createStoryListBox(){
		storyList = new JList<String>();
		storyList.setBackground(Color.WHITE);
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
		
		storyList.setSelectedIndex(lastIndex);
		setDescriptionLabelText(getSelectedStory());
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
