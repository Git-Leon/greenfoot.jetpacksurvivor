import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Partner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Partner extends Npc {
    public Partner() {
        setAnimation("npc/partner1/", ".png", 7);
    }

    public void act() {
        animate(6);
        try {
            Player p = getPlayer();        
            setLocation(getX(p)-50, getY(p)-75);
            if(Util.keyDown("q") )
                shot(p.bulletspeed * 3);
        } catch(NullPointerException npe) {} catch(IllegalStateException ise) {}
    }

    private void followPlayer() {
        Player p = getPlayer();
        setLocation(getX(p)-50, getY(p)-75);
    }

    public void shot(int speed) {
        Player p = getPlayer();
        Projectile bullet = p.fire(speed).setSpeed(speed);
        bullet.setLocation(getX(), getY());
        java.util.List list = getObjects(Enemy.class);
        int index = Util.ran(2) == 1 ? list.size()-1 : 0;
        bullet.faceObject(list.get(index));
    }

}