/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplay;

/**
 *
 * @author CtneWiii
 */
public class ShipLocation {
    private int planesLocation[][];
    private int lost;
    private int die[];
    
    public ShipLocation()
    {
        planesLocation = new int[2][2];
        setLocation(0, 118, 395);
        setLocation(1, 261, 395);
        lost = 0;
        die = new int[2];
        die[0]=0;die[1]=0;
    }
    public void setLocation(int plane,int x, int y)
    {
        planesLocation[plane][0]=x;
        planesLocation[plane][1]=y;
    }
    public int[] getLocation(int plane)
    {
        return planesLocation[plane];
    }
    public void setX(int plane, int x)
    {
        planesLocation[plane][0]=x;
    }
    public void setY(int plane, int y)
    {
        planesLocation[plane][1]=y;
    }
    public int getX(int plane)
    {
        return planesLocation[plane][0];
    }
    public int getY(int plane)
    {
        return planesLocation[plane][1];
    }
    
    public boolean isLost()
    {
        return lost == 1;
    }
    
    public void lost()
    {
        lost = 1;
    }
    
    public void die(int plane)
    {
        die[plane] = 1;
    }
    
    public boolean isDead(int plane)
    {
        return die[plane]==1;
    }
}
