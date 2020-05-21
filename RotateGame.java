package RotationTest;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class RotateGame extends JFrame implements ActionListener{
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	JButton plotB;//Rotate the block
	JTextField angleT, widthT, heightT, originX_T, originY_T;
	JLabel angleL, widthL, heightL, originX_L1, originX_L2, originY_L1, originY_L2;
	JLabel errorAngle, errorWidth, errorHeight, errorOriginX, errorOriginY;
	JPanel cards, localP, globalP;//Used to make an application with paging
	final static String localS = "Local";//to identify each card; Local Panel
	final static String globalS = "Global";//to identify each card; Global Panel
	//plotP contains rotateTest object
	JPanel inputP, mainP, angleP, widthP, heightP, originXP, originYP;//mainmenu split to two sub-panels: inputP and visualP
	VisualPoly poly = new VisualPoly();//RigidBody placed in the visual panel
	//Parameter to set the origin with respect to the visualP panel and wrt the poly itself

	//Local card part
	JTextArea stats;
	public RotateGame(String title)
	{
		super(title);
		setSize(1000,600);
		//Global variables
		angleL = new JLabel("Angle °");angleT = new JTextField(3);angleT.setText("0");
		errorAngle = new JLabel("Must be an integer geater than or equal to 0");
		widthL = new JLabel("Width");widthT = new JTextField(3);
		widthT.setText(String.valueOf(poly.getPolyWidth()));widthT.setToolTipText("Type in integer number only");
		errorWidth = new JLabel("Must be an integer between 1 and 500");errorWidth.setVisible(false);
		heightL = new JLabel("Height");heightT = new JTextField(3);
		heightT.setText(String.valueOf(poly.getPolyHeight()));heightT.setToolTipText("Type in integer number only");
		errorHeight = new JLabel("Must be an integer between 1 and 500"); errorHeight.setVisible(false);
		originX_L1 = new JLabel("X Origin position: 0 <");originX_L2 = new JLabel("< 500");originX_T = new JTextField(3);
		originX_T.setText(String.valueOf(poly.getPolyWidth()/2));errorOriginX = new JLabel("Must be within width boundary"); errorOriginX.setVisible(false);
		originY_L1 = new JLabel ("Y Origin position: 0 <");originY_L2 = new JLabel("< 250");originY_T = new JTextField(3);
		originY_T.setText(String.valueOf(poly.getPolyHeight()/2));errorOriginY = new JLabel("Must be within height boundary"); errorOriginY.setVisible(false);
		plotB = new JButton("Local");
		
		originX_T.setToolTipText("Type in integer number only");
		originY_T.setToolTipText("Type in integer number only");
		
		angleP = new JPanel();angleP.add(angleL);angleP.add(angleT);
		widthP = new JPanel();widthP.add(widthL);widthP.add(widthT);
		heightP = new JPanel();heightP.add(heightL);heightP.add(heightT);
		originXP = new JPanel();originXP.add(originX_L1);originXP.add(originX_T);originXP.add(originX_L2);
		originYP = new JPanel();originYP.add(originY_L1);originYP.add(originY_T);originYP.add(originY_L2);
		
		globalP = new JPanel();globalP.setLayout(new BoxLayout(globalP, BoxLayout.Y_AXIS));
		globalP.add(angleP);globalP.add(errorAngle);globalP.add(widthP);globalP.add(errorWidth);
		globalP.add(heightP);globalP.add(errorHeight);globalP.add(originXP);globalP.add(errorOriginX);
		globalP.add(originYP);globalP.add(errorOriginY);
		
		//local variables

		stats = new JTextArea(50, 30);
		stats.setEditable(false);
		localP = new JPanel();localP.setLayout(new BoxLayout(localP, BoxLayout.Y_AXIS));
		localP.add(stats);
		cards = new JPanel(new CardLayout());
		cards.add(globalP,globalS);
		cards.add(localP, localS);

		inputP = new JPanel();inputP.setLayout(new BoxLayout(inputP, BoxLayout.PAGE_AXIS));
		inputP.add(cards);inputP.add(plotB);
		
		mainP = new JPanel();mainP.setLayout(new BorderLayout());
		mainP.add(inputP, BorderLayout.WEST);
		mainP.add(poly, BorderLayout.CENTER);
		add(mainP);
		
		angleT.addActionListener(this);
		widthT.addActionListener(this);
		heightT.addActionListener(this);
		originX_T.addActionListener(this);
		originY_T.addActionListener(this);
		plotB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CardLayout cl = (CardLayout)(cards.getLayout());
				if(!poly.plotPoly)
				{
					plotB.setText("Global");
					cl.show(cards, localS);
					poly.plotPoly = true;
					poly.setPolyPoints();
					stats.setText("Origin: "+poly.getOriginPosition());
					stats.setText(stats.getText()+"\n\nCorner: "+poly.getCornerPosition());
					stats.setText(stats.getText()+"\n\nPolygon Points: "+poly.getPolyPoints());
					stats.setText(stats.getText()+"\n\nAngle: "+String.valueOf(poly.getPolyAngle())+"\nRad: "+String.valueOf(poly.rad));
					stats.setText(stats.getText()+"\nRadOC: "+String.valueOf(poly.radOC)+"\nAngleOC: "+String.valueOf(poly.radOC*180/Math.PI));
					stats.setText(stats.getText()+"\nDelta Angle(Angle+RadOC): "+String.valueOf(poly.deltaRad));
					stats.setText(stats.getText()+"\n\nOrigin-Corner distances:\nDeltaX: "+poly.deltaX+"\nDeltaY: "+String.valueOf(poly.deltaY)+"\nHypotenuse: "+String.valueOf(poly.hypo));
					
					poly.repaint();
				}
				else
				{
					plotB.setText("Local");
					cl.show(cards, globalS);
					poly.plotPoly = false;
					poly.repaint();
				}
			}
		});
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation((dim.width - getWidth())/2, (dim.height - getHeight())/2);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{

		if(isInputNumber((JTextField) e.getSource()))
		{
			int a = Integer.parseInt(((JTextField) e.getSource()).getText());
			int tmp;
			if(e.getSource() == angleT)
			{
				errorAngle.setVisible(false);
				poly.setPolyAngle((double)a);
				angleT.setText(String.valueOf((int)poly.getPolyAngle()));
			}
			else if(e.getSource() == widthT)
			{
				if (a > 1 && a <= 500)
				{
					tmp = poly.getPolyWidth();
					errorWidth.setVisible(false);
					originX_L2.setText("< "+String.valueOf(a));
					poly.setPolyWidth(a);
					poly.setOriginX(proportionOrigin(Integer.parseInt(originX_T.getText()), tmp, widthT));
				}
				else errorWidth.setVisible(true);
			}
			else if (e.getSource() == heightT)
			{
				if (a > 0 && a <= 500)
				{
					tmp = poly.getPolyHeight();
					errorHeight.setVisible(false);
					originY_L2.setText("< "+String.valueOf(a));
					poly.setPolyHeight(a);
					poly.setOriginY(proportionOrigin(Integer.parseInt(originY_T.getText()), tmp, heightT));
				}
				else errorHeight.setVisible(true);
			}	
			else if (e.getSource() == originX_T)
			{
				if (a >= 0 && a <= poly.getPolyWidth() && isInputNumber(widthT))
				{
					errorOriginX.setVisible(false);
					poly.setOriginX(a);
				}
				else errorOriginX.setVisible(true);
			}
			else
			{
				if (a >= 0 && a <= poly.getPolyHeight() && isInputNumber(heightT))
				{
					errorOriginY.setVisible(false);
					poly.setOriginY(a);
				}
				else errorOriginY.setVisible(true);
			}
			poly.repaint();
		}
		else if(e.getSource() == angleT)errorAngle.setVisible(true);
		else if(e.getSource() == widthT)errorWidth.setVisible(true);
		else if(e.getSource() == heightT)errorHeight.setVisible(true);
		else if(e.getSource() == originX_T)errorOriginX.setVisible(true);
		else errorOriginY.setVisible(true);
	}
	
	public boolean isInputNumber(JTextField input)
	{
		String s = String.valueOf(input.getText());
		for(int i = 0; i < s.length(); i++)
		{
			if(Character.isDigit(s.indexOf(i)))
			return false;
		}
		return true;
	}
	public int proportionOrigin(int x, int temp, JTextField txt)
	{
		int parameter = Integer.parseInt(txt.getText());
		int newX = x*parameter/temp;
		if(txt == widthT) originX_T.setText(String.valueOf(newX));
		else originY_T.setText(String.valueOf(newX));
		return newX;
	}
}
