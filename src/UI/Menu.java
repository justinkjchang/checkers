package UI;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

import Controllers.Handler;
public class Menu extends JFrame{

    private JButton sound;
    private JPanel panel;
    private URL url = Menu.class.getResource("Intro 4 quite.wav");
    private AudioClip clip = Applet.newAudioClip(url);
	private JRadioButton Single;
	private JRadioButton Double;
	private JButton start;
	private JButton load;
	private JButton picture;
	
	final private Icon pic = new ImageIcon(getClass().getResource("Checkers Title.png"));
	final private Icon red = new ImageIcon(getClass().getResource("Checkers Title Red Jump.png"));
	final private Icon grey = new ImageIcon(getClass().getResource("Checkers Title Grey Jump.png"));
	
	final private Icon startPic = new ImageIcon(getClass().getResource("Start Button.png"));
	final private Icon loadPic = new ImageIcon(getClass().getResource("Load Button.png"));
	final private Border loweredBorder = BorderFactory.createLoweredBevelBorder();
	public Menu(Handler handler){
		super("Checkers Menu");
		setLayout(new FlowLayout());
		start = new JButton();
		picture = new JButton();
        picture.setIcon(pic);
        picture.setBorder(loweredBorder);
		add(picture);
		add(start);
		panel = new JPanel();
		Single = new JRadioButton("Single Player");
		Double = new JRadioButton("Two Player");
		sound = new JButton("Sound Off");
		load = new JButton();
		Single.setFont(Single.getFont().deriveFont(9.0f));
		Double.setFont(Double.getFont().deriveFont(9.0f));
		sound.setFont(sound.getFont().deriveFont(9.0f));
		add(load);
		panel.add(Single);
		panel.add(Double);
		add(panel);
		add(sound);
		load.setPreferredSize(new Dimension(105, 35));
		start.setPreferredSize(new Dimension(105, 35));
		load.setIcon(loadPic);
		start.setIcon(startPic);
		
		sound.addActionListener(handler);
		Single.addActionListener(handler);
		Double.addActionListener(handler);
		start.addActionListener(handler);
		load.addActionListener(handler);
		Single.setSelected(true);
		
	}
	

	
	public void pic(){
		picture.setIcon(pic);
	}
	
	public void red(){
		picture.setIcon(red);
	}
	
	public void grey(){
		picture.setIcon(grey);
	}
	
	public JButton music(){
		return sound;
	}
	
	public AudioClip clip(){
	    return clip;
	}

	public JButton start(){
		return start;
	}
	public JRadioButton Single(){
		return Single;
	}
	public JRadioButton Double(){
		return Double;
	}
	
	public JButton load(){
		return load;
	}
	
}
