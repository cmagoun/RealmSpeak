package com.cjm.magic_realm.components.storyline;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.robin.general.swing.AggressiveDialog;

@SuppressWarnings("serial")
public class StoryOptionsFrame extends AggressiveDialog {

	private final int width = 450;
	private final int height = 400;
	
	private JPanel contentPane;
	private JPanel textPane;
	private JPanel buttonPane;
	private JLabel textLabel;
	private ArrayList<StoryOption> options;
	
	public StoryOptionsFrame() {
		super(StoryManager.getInstance().getFrame());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setAlwaysOnTop(true);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(width,height);
		setLocation(dim.width/2-width/2, dim.height/2-height/2);

		options = new ArrayList<StoryOption>();
		
		initializeControls();
	}
	
	private void initializeControls(){
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		textPane = new JPanel();
		textPane.setPreferredSize(new Dimension(width-10,(height/2)-10));
		textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textPane.setBackground(Color.WHITE);
		contentPane.add(textPane);
		
		textLabel = new JLabel();
		textLabel.setPreferredSize(new Dimension(width-10, (height/2)-10));
		textLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		textLabel.setVerticalAlignment(SwingConstants.TOP);
		textPane.add(textLabel);
		
		buttonPane = new JPanel();
		buttonPane.setPreferredSize(new Dimension(width-10, (height/2)-10));
		contentPane.add(buttonPane);
	}
	
	public void setTitle(String title){
		super.setTitle(title);
	}
	
	public void setText(String text){
		textLabel.setText(
			"<html>"
			+ "<body style='width:99%;'>"
			+ text 
			+ "</body>"
			+ "</html>");
	}
	
	public void setOptions(ArrayList<StoryOption>options){
		this.options = options;
		createOptionButtons();
	}
	
	public void addOptions(StoryOption option){
		options.add(option);
		createOptionButtons();
	}
	
	private void createOptionButtons(){
		buttonPane.removeAll();
		
		options.stream()
			.forEach(o -> {
				JButton btn = new JButton();
				btn.setPreferredSize(new Dimension(width-40, 40));
				btn.setText(o.Text);
				btn.addActionListener(o.Action);
				buttonPane.add(btn);
			});
		
	}

}
