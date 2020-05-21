package RotationTest;
import Physics.*;
import javax.swing.*;
import java.awt.*;
public class Main {
	public static void main(String[] args)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		RotateGame game = new RotateGame("Rotation");
		game.setVisible(true);
	}
}
