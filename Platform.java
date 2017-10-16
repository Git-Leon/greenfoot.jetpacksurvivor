import greenfoot.Actor;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Platform extends SubActor {
    public Platform() {
    }

    public Platform(String image) {
        setImage(image);
    }

    public void act() {
        //setLocation(getX()-1, getY());
        repeat();
    }

    private void repeat() {
        if(getX() < 30)
            setLocation(13*64, 784);
    }

    public void touchingplatform() {
    }
}