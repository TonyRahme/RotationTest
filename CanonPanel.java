package RotationTest;
import javax.swing.*;
import java.awt.*;
public class CanonPanel extends JPanel {
	private int rectH, rectW, rectX, rectY;
	public CanonPanel(double h)
	{
		
		rectH = 22;
		rectW = 100;
		rectX = 50;
		rectY = (int) h - 100;
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(rectX,rectY,rectW,rectH);
		g.setColor(Color.ORANGE);
		g.fillPolygon(new int[]{rectX+10,rectX+25,rectX+40},new int[]{rectY+40,rectY+5,rectY+40},3);
	}
}
