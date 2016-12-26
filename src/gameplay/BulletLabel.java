/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

import javax.swing.JLabel;

/**
 *
 * @author CtneWiii
 */
public class BulletLabel extends JLabel {
    private int x , y;
    public BulletLabel(int x, int y)
    {
        this.x= x;
        this.y= y;
        init();
    }
    private void init()
    {
        setLocation(x, y);
        IconHelper.setIcon(this, 2);
        hidden();
    }
    public void setLocation(int x,int y)
    {
        setBounds(x,y,20,20);
    }
    public void showing()
    {
        setVisible(true);
    }
    public void hidden()
    {
        setVisible(false);
    }
}
