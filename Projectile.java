import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Projectile extends SubActor {
    private int speed;
    private Class enemy;
    public static int lootchance;

    private void init(int speed, Class cls) {
        this.speed = speed;
        this.enemy = cls;
    }

    public Projectile(int speed, Class enemy) {
        init(speed, enemy);
    }

    public Projectile(int speed) {
        init(speed, Enemy.class);
    }

    public Projectile(Class cls) {
        init(50, cls);
    }

    public Projectile() {
        this.speed = 50;
        this.enemy = Enemy.class;
    }

    public void act()  {
        move(speed);      
        hit(0, Enemy.class);
    }    

    public Projectile setSpeed(int val) {
        this.speed = val;
        return this;
    }

    private void hit(int dmg, Class cls) {
        Actor actor = getOneIntersectingObject(cls);
        if(atWorldEdge(3)) {
            die();
        }
        else if(actor != null) {
            kill(actor);
            addLoot(100);
            getPlayer().kills++;
            die();
        }
    }

    private void addLoot(int percentchance) {
        if(Util.chance(percentchance)) //&& Menu.score() < 100)for(int i=0; i<100; i++)
            addObject(new Loot(), getX(), getY());
    }

    private void die() {
        addObject(new SubActor("explosion/", ".png", 14) {
                public void act() {
                    animate(2);
                    if(getCurImage() >= getImages().length-1) {
                        kill(this);
                    }
                }
            }, getX(), getY());
        Greenfoot.playSound("vaporize.mp3");
        kill(this);
    }
}