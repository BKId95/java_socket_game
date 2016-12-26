/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import gameplay.ShipLocation;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CtneWiii
 */
public class ThreadRandom implements Runnable{
    private Enemies enemies;
    private int quantity;
    private ShipLocation shipLocation;
    private Explodes explo;
    public ThreadRandom(Enemies enemies, ShipLocation shipLocation, Explodes explo)
    {
        this.enemies = enemies;
        this.shipLocation = shipLocation;
        this.explo = explo;
    }
    
    @Override
    public void run() {
        randomQuantity();
        createEnemy();
    }
    
    private int randomInt(int min,int max)
    {
        Random ran = new Random();
        return ran.nextInt(max-min+1)+min;
    }
    
    private void randomQuantity()
    {
        quantity = randomInt(1, 1);
    }
    private void createEnemy()
    {
        for(int i = 0 ;i < quantity; i++)
        {
            int x = randomInt(5,355);
            int speed = randomInt(10, 20);
            autoTranslate(x, 0, speed);
        }
    }
    
    public void autoTranslate(int x, int y, int speed)
    {
        Enemy e = new Enemy(x, 0, speed);
        enemies.add(e);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while(e.getY()<355)
                {
                    try {
                        e.translate();
                        check(e);
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadRandom.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(!e.isDead())
                    shipLocation.lost();
                enemies.remove(e);
            }
        });
        th.start();
    }
    
    private void check(Enemy e)
    {
        int x=e.getX(),y=e.getY();
        boolean b = e.isDead();
        for(int plane = 0 ; plane < 2 ; plane++)
            if(!shipLocation.isDead(plane)&&!b)
                if((y<= shipLocation.getY(plane)&& shipLocation.getY(plane)<=y+70)||(y<= shipLocation.getY(plane)+50&& shipLocation.getY(plane)<=y+20))
                    if((x<= shipLocation.getX(plane)&& shipLocation.getX(plane)<=x+70)||(x<= shipLocation.getX(plane)+50&& shipLocation.getX(plane)<=x+20))
                    {
                        System.out.println(x+" "+ shipLocation.getX(plane));
                        shipLocation.die(plane);
                        explo.add(new Explode(shipLocation.getX(plane), shipLocation.getY(plane)));
                    }
        if(shipLocation.isDead(0)&& shipLocation.isDead(1)&&!e.isDead()) shipLocation.lost();
    }
}
