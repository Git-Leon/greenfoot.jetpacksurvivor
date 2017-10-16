import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
public class SubWorld extends World {
    Partner partner = null;
    Player player = null;
    public long birth = System.currentTimeMillis();
    //static GreenfootSound gs0 = new GreenfootSound("silence.mp3");
    static GreenfootSound gs0 = new GreenfootSound("music0.mp3");
    static GreenfootSound gs1 = new GreenfootSound("music1.mp3");
    public SubWorld() {
        super(800, 300, 1);
        mode(1);
    }

    private SubWorld(
    int shotdelay, int firerate, int magsize, int thrust,
    int fuel, int jumpstrength, int bulletspeed, int runspeed,
    int lootchance) {
        super(800, 300, 1);
        custom(shotdelay, firerate, magsize, thrust,
            fuel, jumpstrength, bulletspeed, runspeed, lootchance);
    }

    private void custom(
    int shotdelay, int firerate, int magsize, int thrust,
    int fuel, int jumpstrength, int bulletspeed, int runspeed,
    int lootchance) {
        makeGround();
        Projectile.lootchance = lootchance;	
        this.player = new Player(shotdelay, firerate, magsize, thrust,
            fuel, jumpstrength, bulletspeed, runspeed);
        addObject(player, 174, 50);
        addObject(new Jetpack(), 174, 50);
        addObject(new Menu(), getWidth()/2, getHeight()/2);

        Menu playerInfo = new Menu(true);
        addObject(playerInfo, playerInfo.getImage().getWidth()/2, playerInfo.getImage().getHeight()/2);
    }

    public void act() {
        addEnemy(0);
    }

    public Player getPlayer() {
        return this.player;
    }

    public Partner getPartner() {
        return this.partner;
    }

    private void mode(int num) {
        setPaintOrder(Player.class, Npc.class, Loot.class);
        gs1.stop();
        //gs0.playLoop();
        switch(num) {
            case 1:
            classic();
            break;

            case 2:
            break;
            case 3:
            break;
        }
    }

    private int getX(Actor actor) {
        return actor.getX();
    }

    private int getY(Actor actor) {
        return actor.getY();
    }

    private void makeGround() {
        for(int i=0; i<13; i++) {
            Platform platform = new Platform("bricks1.jpg");
            addObject(platform, i*64, getHeight());
        }	
    }

    private void classic() {
        makeGround();
        Projectile.lootchance = 66;
        this.player = new Player();
        this.partner = new Partner();
        addObject(player, 174, 50);
        addObject(partner, getX(player)-50, getY(player)+100);
        addObject(new Jetpack(), 174, 50);
        addObject(new Menu() {
                public void act() {
                    gs0.playLoop();
                    kill(this);
                }

            }, getWidth()/2, 
            getHeight()/2);

        Menu playerInfo = new Menu(true);
        addObject(playerInfo, playerInfo.getImage().

            getWidth()/2, playerInfo.getImage().

            getHeight()/2);
    }

    private Platform[] ladder() {
        Platform[] ladder = new Platform[5];
        for(int i=0; i<5; i++) {
            Platform rung = new Platform();
            ladder[i] = rung;
            addObject(rung, getWidth(), getHeight()- (i*80) );
        }
        return ladder;
    }

    private void addLadder(int freq) {
        if( Util.chance(freq) )
            ladder();
    }

    private void rain(int frequency) {
        if(Util.chance(frequency)) {
            int offset = Util.plusOrMinus() * 70;
            addObject(new Loot(), Util.ran(getWidth()), 40);
        }
    }

    private void addEnemy(int num) {
        boolean b = count(Enemy.class) < (Menu.score/10)+1;
        if(b)
            addObject(new Enemy(), getWidth()-50, 50);
    }

    public SubWorld getCurrentWorld() {
        return this;
    }

    public int count(Class cls) {
        return getObjects(cls).size();
    }
}