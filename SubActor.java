import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * adds functionality to Actor class
 */
public class SubActor<T> extends Actor
{
    private long birth = System.currentTimeMillis();
    public int health = 1;

    private int lifetime;
    private GreenfootImage[] images;
    private int currentImage = 0;

    private int speed = 7;
    private int vSpeed = 0;
    private int gravity = 2;
    private int jumpStrength = 5;
    
    public int getSpeed() {
        return speed;
    }

    public long age() {
        return (System.currentTimeMillis() - birth)/1000;
    }

    public int worldWidth() {
        return getWorld().getWidth();
    }

    public int worldHeight() {
        return getWorld().getHeight();
    }

    private void init(String basename, String suffix, int noOfImages) {
        images = new GreenfootImage[noOfImages];
        for(int i=0; i < noOfImages; i++)
            images[i] = new GreenfootImage(basename + i + suffix);
        setImage(images[currentImage]);
    }

    public void setAnimation(String basename, String suffix, int noOfImages) {
        init(basename, suffix, noOfImages);
    }

    public SubActor(String basename, String suffix, int noOfImages) {
        super();
        init(basename, suffix, noOfImages);
    }

    public SubActor() {
        super();
    }

    public void animate(int frequency)  {
        if(lifetime%frequency == 1) {
            animate();
        }
        lifetime++;
    }

    public void animate()  {
        if(images != null) {
            currentImage++;
            currentImage %= images.length;
            setImage(images[currentImage]);
        }
    }

    /** return the Image that we have */
    public int getCurImage() {
        return currentImage;
    }

    public GreenfootImage[] getImages() {
        return images;
    }

    public void flipImages() {    
        for(GreenfootImage image : getImages()) {
            image.mirrorHorizontally();
            image.mirrorVertically();
        }
    }

    public Player getPlayer() {
        return ((SubWorld)getWorld()).getPlayer();
    }

    public Partner getPartner() {
        return ((SubWorld)getWorld()).getCurrentWorld().getPartner();
    }

    public Actor damage(Class cls, int dmg) {
        SubActor actor = (SubActor)getOneIntersectingObject(cls);
        return damage(actor, dmg);
    }

    public Actor damage(Actor actor, int dmg) {
        return actor != null ? ((SubActor)actor).reduceHealth(dmg) : null;
    }

    private Actor reduceHealth(int dmg) {
        this.health -= dmg;
        System.out.println(this + ": has taken " + dmg + " points of damage");
        System.out.println(this + ": has " + this.health + " hit points");
        return this;
    }

    public void faceObject(Object object) {
        if(object != null) {
            Actor obj = (Actor)object;
            int deltaX = obj.getX() - getX();
            int deltaY = obj.getY() - getY();
            setRotation((int) (180 * Math.atan2(deltaY, deltaX) / Math.PI));
        } else {
            return;
        }
    }

    public void kill(Class cls) {
        Actor actor = getOneIntersectingObject(cls);
        kill(actor);
    }

    public void kill(Actor actor) {
        if(actor != null) {
            removeObject(actor);
        }
    }

    public int getAge() {
        return (int)((System.currentTimeMillis() - birth)/1000);
    }

    public long getLifeTime() {
        return System.currentTimeMillis() - birth;
    }

    public boolean rot(int sec) {
        if(sec < getAge()) {
            getWorld().removeObject(this);
            return true;
        }
        return false;
    }

    public void randomTurn(int max, int min) {
        if( Util.chance(5) ) {
            turn( Util.ran(90) + min ); // turn at a random degree up to 90, subtracted by 65. (25 Max)
        }
    }

    public boolean atWorldEdge(int offset) {
        return atWorldEdge(offset, offset);
    }

    public boolean atWorldEdge(int x, int y) {
        if(getX() < x || getX() > getWorld().getWidth() - x)
            return true;
        if(getY() < y || getY() > getWorld().getHeight() - y)
            return true;
        else
            return false;
    }

    public void turnAtEdge(int edgeOffset, int degree) {
        if(atWorldEdge(edgeOffset)) {
            turn(degree);
        }
    }

    public void disappearAtEdge() {
        disappearAtEdge(10);
    }

    public void disappearAtEdge(int offset) {
        disappearAtEdge(offset, offset);
    }

    public void disappearAtEdge(int x, int y) {
        if(atWorldEdge(x,y)) getWorld().removeObject(this);
    }

    public Projectile shoot(int velocity) {       
        return (Projectile)addObject(new Projectile(velocity), getX(), getY());
    }

    public Projectile shoot(int velocity, Actor actor) {
        Projectile projectile = (Projectile)addObject(shoot(velocity), getX(), getY());
        projectile.faceObject(actor);
        return projectile;
    }

    public void shootInRange(Class cls, int radius) {
        Actor actor = null;
        actor = faceNearest(cls, radius);
        if(actor != null) shoot(50, actor);
    }

    private void shot() {
        shot(1);
    }

    private void shot(int firePower) {
        for(int i=0; i<=Util.ran(firePower); i++) {
            shoot(50).setRotation(getRotation());
        }
    }

    public Actor faceNearest(Class cls, int radius) {
        Actor actor = getNearest(cls, radius);
        faceObject(actor);
        return actor;
    }

    public Actor getNearest(Class cls, int radius) {
        List<Actor> nearestActors = getWorld().getObjects(cls);
        Actor nearestActor = null;
        Double nearestDistance = null;
        double distance;
        int size = nearestActors.size();
        for(int i=0; i < size; i++) {
            distance = getDistance(nearestActors.get(i));
            if(i==0) {
                nearestDistance = distance;
            }
            if (distance < nearestDistance) {
                nearestActor = nearestActors.get(i);
                nearestDistance = distance;
            }  
        }
        return nearestActor;  
    }        

    public List<Object> getObjectsInRange(Class cls, int radius) {
        return getObjectsInRange(radius, cls);
    }

    public double getDistance(Actor actor) {  
        return Math.hypot(actor.getX() - getX(), actor.getY() - getY());  
    }

    public void fall(int velocity) {
        setLocation(getX(), getY()+velocity);
    }

    public Object getObject(Class cls, int n) { //return nth object
        try {
            return getWorld().getObjects(cls).get(n);
        } catch(Exception e) {
            return null;
        }
    }

    public int indexOf(Object obj) {
        return getWorld().getObjects(obj.getClass()).indexOf(obj);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch(CloneNotSupportedException cnse) {
            return null;
        }
    }

    public World getThisWorld() {
        return (SubWorld)getWorld();
    }

    public Actor addObject(Actor actor, int x, int y) {
        getWorld().addObject(actor, x, y);
        return actor;
    }

    public void removeObject(Actor actor) {
        getWorld().removeObject(actor);
    }

    public void shadow(Actor actor) {
        setLocation(actor.getX(), actor.getY());
    }

    public long getBirth() {
        return birth;
    }

    public int count(Class cls) {
        return getWorld().getObjects(cls).size();
    }

    public void rescale(double xRatio, double yRatio, GreenfootImage... images) { //rescale image sizes
        for(GreenfootImage image : images) {
            double width = image.getWidth();
            double height = image.getHeight();

            double x = width*xRatio;
            double y = height*yRatio;

            int xInt = (int) Math.floor(x);
            int yInt = (int) Math.floor(y);
            image.scale(xInt, yInt);
        }
    }

    public void h_flipImages() {
        for(GreenfootImage image : getImages())
            image.mirrorHorizontally();
    }

    public void v_flipImages() {
        for(GreenfootImage image : getImages())
            image.mirrorVertically();       
    }

    public void flipImages(boolean horizontal, boolean vertical) {
        if(horizontal)
            h_flipImages();        
        if(vertical)
            v_flipImages();
    }

    public void jump(int strength) {
        vSpeed = -strength;
        fall(1, 7);
    }

    public void fall(int gravity, int vTerminal) {
        setLocation(getX(), getY() + vSpeed);
        int maxSpeed = vSpeed < vTerminal ? vSpeed += gravity : vSpeed;
    }

    public boolean onGround() {
        int height = getImage().getHeight();
        Actor ground = null;
        for(int i=0; i<height; i++) {
            ground = getOneObjectAtOffset ( 0, i / 2, Platform.class);
            if(ground != null)
                break;
        }
        return ground != null;
    }

    public void checkFall() {
        if(onGround()) {
            vSpeed = 0;
        } else if(!Util.keyDown("`")) {
            fall(1, 7);
        }
    }

    public Actor getOneObjectAtOffset(int x, int y, Class... classes) {
        for(Class cls : classes)
            return getOneObjectAtOffset(x, y, cls);
        return null;
    }

    public boolean noseContact(Class cls) {
        return touchingNose(cls) != null;
    }

    public boolean noseContact(Class cls, int xOffset, int yOffset) {
        return touchingNose(cls, xOffset, yOffset) != null;
    }

    public Actor touchingNose(Class cls) {
        int imageWidth = (this.getImage().getWidth());
        int imageHeight = (this.getImage().getHeight());
        int offset = imageWidth > imageHeight ? imageWidth : imageHeight;

        return touchingNose(cls, offset/2);
    }

    public Actor touchingNose(Class cls, int offset) {
        return touchingNose(cls, offset, offset);
    }

    public Actor touchingNose(Class cls, int xOffset, int yOffset) {        
        int deltaX = (int)Math.round((Math.cos(getRotation()*Math.PI/180)));
        int deltaY = (int)Math.round((Math.sin(getRotation()*Math.PI/180)));
        int x = xOffset * deltaX;
        int y = yOffset * deltaY;
        Actor actor = getOneObjectAtOffset(x, y, cls);

        return actor;
    }

    public Actor getObjectAtOffset(int x, int y, Class cls) {
        return getOneObjectAtOffset(x,y,cls);
    }

    public List getObjects(Class cls) {
        return getWorld().getObjects(cls);
    }

    public int getX(Actor a) {
        return a.getX();
    }

    public int getY(Actor a) {
        return a.getY();
    }

    private Actor getObjectAtOffset(int offset, int degrees) {
        turn(degrees);
        int x = offset * (int)Math.round((Math.cos(getRotation()*Math.PI/180)));
        int y = offset * (int)Math.round((Math.sin(getRotation()*Math.PI/180)));
        Actor actor = getOneObjectAtOffset(x, y, Actor.class);
        turn(360-degrees);
        return actor;
    }   
}