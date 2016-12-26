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
public class EnemyLabel extends JLabel{
    private int x, y;
    public EnemyLabel(int x, int y)
    {
        this.x = x;
        this.y = y;
        init();
    }
    private void init()
    {
        setLocation(x, y);
        IconHelper.setIcon(this, 3);
        setVisible(false);
    }
    
    public void setLocation(int x, int y)
    {
        setBounds(x, y, 70, 70);
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
