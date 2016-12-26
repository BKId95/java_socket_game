/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import gameplay.IconHelper;
import gameplay.GamePanel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author CtneWiii
 */
public class Explodes extends ArrayList<Explode>{
    public Explodes()
    {
    }
    
    public void animations(GamePanel g)
    {
        if(this.size()>0)
            for(Explode e : this)
                animation(e, g);
    }
    private void animation(Explode e, GamePanel g)
    {
        Thread x = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JLabel label = new JLabel();
                    g.add(label);
                    for(int i = 0; i < 25 ; i+=5)
                    {
                        label.setBounds(e.x-i, e.y-i, 50+2*i, 50+2*i);
                        IconHelper.setIcon(label, 4);
                        g.repaint();
                        if(i==20) 
                            Thread.sleep(40);
                        Thread.sleep(20);
                    }
                    g.remove(label);
                    g.repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Explodes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        x.start();
    }
}
