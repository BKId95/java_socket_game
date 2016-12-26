package UIHelper;

import javax.swing.*;
import java.awt.*;

/**
 * Created by CtneWiii
 */
public class Button extends JButton{
    public Button(String s){
        this.setText(s);
        this.setBounds(40, 395, 220, 50);
        this.setBackground(new java.awt.Color(0x5858FA));
        this.setForeground(new Color(238, 238, 238));
        this.setFont(new java.awt.Font("Verdana", 1, 15));
    }
}
