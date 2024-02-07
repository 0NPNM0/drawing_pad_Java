package ex4;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.File;

public class Draw {
	
	//window object
	private Frame frame = new Frame("drawing programme");
	
	//window width and height
	private final int WINDOW_HEIGHT = 700;
	private final int WINDOW_WIDTH = 700;
	
	//menu for choosing color
	private PopupMenu colorMenu = new PopupMenu();
	private MenuItem redItem = new MenuItem("my_red");
	private MenuItem blackItem = new MenuItem("my_black");
	private MenuItem orangeItem = new MenuItem("my_orange");
	private MenuItem yellowItem = new MenuItem("my_yellow");
	private MenuItem greenItem = new MenuItem("my_green");
	private MenuItem save = new MenuItem("save as");
	
	//current pen color
	private Color currentColor = Color.BLACK;
	
	//create BufferedImage object
	BufferedImage image = new BufferedImage(WINDOW_HEIGHT,WINDOW_WIDTH,BufferedImage.TYPE_INT_RGB);
	
	//Graphics object
	Graphics g = image.getGraphics();
	
	//set line width
	Graphics2D g2d = (Graphics2D) g;
	float strokeWidth = 7.0f; 
	
	//self-define canvas
	private class MyCanvas extends Canvas{
		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
	}
	
	//canvas object
	MyCanvas draw = new MyCanvas();
	
	//last location
	private int lastX = -1;
	private int lastY = -1;
	
	
	public void init() {
		
		//choose color
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String actionCommand = e.getActionCommand();
				switch(actionCommand) {
				case "my_red":
					currentColor = new Color(200,83,83);
					break;
			    case "my_black":
			    	currentColor = Color.BLACK;
			    	break;
			    case "my_orange":
			    	currentColor = new Color(223,142,0);
			    	break;
			    case "my_yellow":
			    	currentColor = new Color(226,198,2);
			    	break;
			    case "my_green":
			    	currentColor = new Color(0,132,84);
			    	break;
				}
			}
		};
		
		//save action
		save.addActionListener(e->{
			
			//file dialog
			FileDialog fileDialog = new FileDialog(frame,"save image",FileDialog.SAVE);
			fileDialog.setVisible(true);
			
			//get the save path and the file name
			String dir = fileDialog.getDirectory();
			String fileName = fileDialog.getFile();
			
			try {
				ImageIO.write(image, "JPEG", new File(dir,fileName));
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		});
		
		//set color item listener
		redItem.addActionListener(listener);
		blackItem.addActionListener(listener);
		orangeItem.addActionListener(listener);
		yellowItem.addActionListener(listener);
		greenItem.addActionListener(listener);
		
		//add color item to menu
		colorMenu.add(redItem);
		colorMenu.add(blackItem);
		colorMenu.add(orangeItem);
		colorMenu.add(yellowItem);
		colorMenu.add(greenItem);
		colorMenu.add(save);
		
		//put color menu into canvas
		draw.add(colorMenu);
		
		//set listener of mouse right click on canvas
		draw.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				boolean popupTrigger = e.isPopupTrigger();
				if(popupTrigger) {
					colorMenu.show(draw, e.getX(), e.getY());
				}
				
				lastX = -1;
				lastY = -1;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//set background color
		g.setColor(new Color(250,246,217));
		g.fillRect(0,0,WINDOW_HEIGHT,WINDOW_WIDTH);
		
		//set listener on mouse move(for painting)
		draw.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(lastX>0 && lastY>0) {
					g.setColor(currentColor);
					
					//set line width
					g2d.setStroke(new BasicStroke(strokeWidth));
					g2d.drawLine(lastX, lastY, e.getX(), e.getY());
				}
				
				lastX = e.getX();
				lastY = e.getY();
				
				draw.repaint();
			}
		});
		
		draw.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		frame.add(draw);
		
		//set window listener
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//set the frame size according to the window and component layout 
		frame.pack();
		frame.setVisible(true);
	}
}
