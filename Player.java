import greenfoot.*;
import java.util.ArrayList;
import java.util.List;
public class Player extends SubActor {
    public int shotdelay, firerate, magsize, thrust, fuel,
    jumpstrength, bulletspeed, runspeed, kills = 0;
    private ArrayList<CoolDown> coolDowns = new ArrayList<CoolDown>();
    private void init(
    int shotdelay, int firerate, int magsize, int thrust,
    int fuel, int jumpstrength, int bulletspeed, int runspeed) {
        this.shotdelay = shotdelay;
        this.firerate = firerate;
        this.magsize = magsize;
        this.thrust = thrust;
        this.fuel = fuel;
        this.jumpstrength = jumpstrength;
        this.bulletspeed = bulletspeed;
        this.runspeed = runspeed;
    }

    public Player(int shotdelay, int firerate, int magsize, int thrust,
    int fuel, int jumpstrength, int bulletspeed, int runspeed) {
        init(shotdelay, firerate, magsize, thrust, fuel, jumpstrength, bulletspeed, runspeed);
    }

    public Player() {
        super("player/walk/walk", ".png", 6);
        for(GreenfootImage image : getImages())
            image.mirrorHorizontally();
        init(1000, 1, 1, 2, 15, 10, 5, 3);
        addCoolDown("shoot", shotdelay);
        addCoolDown("jetpack", fuel);
        addCoolDown("invincibility", 1000);
    }

    public int getShotDelay() {
        return this.shotdelay;
    }

    public int setShotDelay(int val) {
        this.shotdelay = val;
        this.firerate = 1001 - shotdelay;
        return val;
    }

    public boolean invincible() {
        return isAvailable("invincibility");
    }

    public CoolDown addCoolDown(String methodName, double maxlife) {
        CoolDown c = new CoolDown(methodName, maxlife);
        this.coolDowns.add(c);
        return c;
    }

    public void act()  {
        checkFall();
        checkKeys();
        endGame();
    }

    private void endGame() {
        SubActor enemy = (SubActor)getOneIntersectingObject(Enemy.class);
        if(enemy != null){
            boolean a = getOneObjectAtOffset(0,0, Enemy.class) != null;
            boolean b = enemy.getObjectAtOffset(0,0, Player.class) != null;
            if(a || b)
                die();
        }

    }

    private void die() {        
        Menu finalScore = new Menu("FINAL SCORE:   " + Menu.score()) {
                public void act() {
                    Greenfoot.delay(10);
                }
            }; addObject(finalScore, getX(), getY());

        Menu reset = new Menu("Press Space to Continue") {
                public void act() {
                    if(Util.keyDown("space")) {
                        Greenfoot.setWorld(new SubWorld());
                    }
                }
            }; addObject(reset, getX(), getY()-finalScore.getImage().getHeight());

        ((SubWorld)getWorld()).gs0.stop();
        ((SubWorld)getWorld()).gs1.playLoop();
        kill(this);
    }

    public void handleCollision() {

    }

    public boolean hitPlatform() {
        try {
            for(Object platform : getIntersectingObjects(Platform.class))
                if(((Platform)platform).getY() < getY()) // not on top platform
                    return true;
            return false;
        } catch(NullPointerException npe) {
            return false;
        }
    }

    private boolean v_flipped = false, h_flipped = false;
    private void checkKeys() {
        if(!hitPlatform()) {
            if(Util.keysDown("left", "a")) {
                if(v_flipped == false){
                    setRotation(180);
                    v_flipImages();
                    v_flipped = true;
                }
                if(h_flipped) {
                }
                setLocation(getX() - runspeed, getY() );
                animate( 15-runspeed >= 1 ? 1-runspeed : 2);
            }

            if(Util.keysDown("right", "d")) {
                if(v_flipped == true) {
                    setRotation(0);
                    v_flipImages();
                    v_flipped = false;
                }
                setLocation(getX() + runspeed, getY() );
                animate( 15-runspeed >= 1 ? 1-runspeed : 2);
            }
        }

        if(Util.keysDown("up", "w") && onGround())
            jump(10);

        if(Util.keysDown("down", "x", "s")){
            fire(this.bulletspeed);
        }

        boolean jetpack_exists = count(Jetpack.class) > 0;
        if(Util.keyDown("e"))
            if(jetpack_exists)
                jump(Util.ran(thrust));
            else
                addObject(new Jetpack(), getX(), getY());

        if(Util.keyDown("p")) {
            addObject(new Menu(), worldWidth()/2, worldHeight()/2);
            while(Util.keyDown("p"))
                Greenfoot.delay(1);
        }
    }

    public boolean isAvailable(String methodname) {
        return getCoolDown(methodname).isAvailable();
    }

    public CoolDown getCoolDown(String methodName) {
        for(CoolDown c : coolDowns)
            if(c.getMethodName().equals(methodName))
                return c;
        return null;
    }

    public Projectile fire(int speed) {
        if(count(Projectile.class) < magsize)
            if(isAvailable("shoot") ) {
                setImage("player/shoot/shoot3.png");
                if(getRotation() == 180) {
                    getImage().mirrorVertically();
                }
                else{
                    getImage().mirrorHorizontally();
                }
                Greenfoot.playSound("shot1.mp3");
                return shoot(bulletspeed);
        }
        return null;
    }

    private void fall() {
        fall(1, 7);
    }
}