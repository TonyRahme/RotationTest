package RotationTest;
import javax.swing.*;
import java.awt.*;
public class VisualPoly extends JPanel 
{
	//global variable
	private double angle = 0;
	private int width = 500;
	private int height = 250;
	private int originX = 0;
	private int originY = 0; 
	public boolean plotPoly = false;
	public double rad = 0, radOC;
	//width&height of polygon.  origin of polygon (pivot point)
	//local variables
	int cornerPositionX, cornerPositionY, originPositionX, originPositionY, deltaX, deltaY, polyX, polyY, hypo;
	private Dimension originPosition = new Dimension(originPositionX,originPositionY);
	double deltaRad;
	int[] xPoints = new int[4], yPoints = new int[4];
	public VisualPoly()
	{
	}
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLUE);
		g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		if(!plotPoly)
		{
			g.setColor(Color.RED);
			g.fillRect((getWidth() - width)/2, (getHeight() - height)/2, width, height);
			
			g.setColor(Color.BLACK);
			g.fillOval(getWidth()/2 + originX-3 , getHeight()/2 + originY-3, 6, 6);
			
			g.setColor(Color.GREEN);
			g.drawLine(getWidth()/2 + originX, getHeight()/2 + originY, (int)((getWidth()/2 + originX) + 50*Math.cos(rad)), (int)((getHeight()/2 + originY) + 50*Math.sin(-rad)));
		}
		else
		{
			setPolyPoints();
			g.setColor(Color.RED);
			g.fillPolygon(xPoints, yPoints, 4);
			
			g.setColor(Color.BLACK);
			g.fillOval(getWidth()/2 + originX-3 , getHeight()/2 + originY-3, 6, 6);
		}
	}
	public void setPolyPoints()
	{
		cornerPositionX = (getWidth() - width)/2; cornerPositionY = (getHeight() - height)/2;
		originPositionX = getWidth()/2 + originX; originPositionY = getHeight()/2 + originY;
		originPosition.setSize(originPositionX, originPositionY);
		deltaX = originPositionX - cornerPositionX; deltaY = originPositionY - cornerPositionY;
		hypo = (int) Math.hypot((double)deltaX, (double)deltaY);//sqrt(X^2 + Y^2)
		radOC = Math.acos((double)deltaX/(double)hypo); //rad between origin point and corner point wrt x-axis
		deltaRad = Math.PI - radOC + rad;
		polyX = (originPosition.width + (int)(hypo*Math.cos(deltaRad)));//new corner position Poly to draw
		polyY = (originPosition.height + (int)(hypo*Math.sin(-deltaRad)));
		
		xPoints[0] = polyX;
		yPoints[0] = polyY;

		xPoints[1] = polyX + (int)(width*Math.cos(rad));
		yPoints[1] = polyY + (int)(width*Math.sin(-rad));

		xPoints[2] = (int) (xPoints[1] + height*Math.sin(Math.PI-rad));
		yPoints[2] = yPoints[1] + (int)(height*Math.cos(rad));

		xPoints[3] = polyX + (int)(height*Math.sin(rad));
		yPoints[3] = polyY + (int)(height*Math.cos(rad));
	}public String getPolyPoints()
	{
		return String.valueOf("\nP1("+xPoints[0]+";"+yPoints[0]+")\nP2("+xPoints[1]+";"+yPoints[1]+")\nP3("+xPoints[2]+";"+yPoints[2]+")\nP4("+xPoints[3]+";"+yPoints[3]+")");
	}
	public String getCornerPosition()
	{
		return String.valueOf("("+cornerPositionX+";"+cornerPositionY+")");
	}
	public String getOriginPosition()
	{
		return String.valueOf("("+originPositionX+";"+originPositionY+")");
	}
	public void setPolyAngle(double a)
	{
		this.angle = a%360;
		this.rad  = this.angle/180*Math.PI;
	}public double getPolyAngle(){return this.angle;}
	
	public void setPolyWidth(int w)
	{
		this.width = w;
	}public int getPolyWidth(){return this.width;}
	
	public void setPolyHeight(int h)
	{
		this.height = h;
	}public int getPolyHeight(){return this.height;}
	
	public void setOriginX(int x)
	{
		this.originX = x - this.width/2;
	}public int getOriginX(){return this.originX;}
	
	public void setOriginY(int y)
	{
		this.originY = y - this.height/2;
	}public int getOriginY(){return this.originY;}
	
}
