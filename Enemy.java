import greenfoot.*;
public class Enemy extends Npc {
    private String[] types = {
            "mummy", "orc_rider", "bat",
            "zombie_gunner", "zombie_crawler",
            "fiend_crawler"};
    private String prefix, suffix, type;
    private int numberOfImages;
    private boolean h_flip = true, v_flip = true;

    private void init(String prefix, String suffix, int numberOfImages) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.numberOfImages = numberOfImages;
        setAnimation(prefix, suffix, numberOfImages);
    }

    private void constructor() {
        if(is("zombie_gunner")) { //Zombie Gunner Constructor
            init("npc/zombie_gunner/", ".png", 13);
            flipImages(h_flip, !v_flip);
        }
        else if( is("orc_rider") ) { //Orc Rider Constructor
            init("npc/orc_rider/", ".png", 2);
            flipImages(!h_flip, !v_flip);
        }
        else if( is("bat") ) { //Bat Constructor
            init("npc/bat/", ".png", 10);
            flipImages(h_flip, v_flip);
        }
        else if( is("mummy") ) {
            init("npc/mummy/", ".png", 17);
            flipImages(h_flip, !v_flip);
        }
        else if( is("zombie_crawler")) {
            init("npc/zombie_crawler/", ".png", 12);      
            flipImages(h_flip, !v_flip);
        }
        else if( is("fiend_crawler")) {
            init("npc/fiend_crawler/", ".png", 5);            
        }
    }

    public Enemy() {
        this.type = types[ Util.ran(types.length) ];
        constructor();
    }

    public Enemy(int mob_num) {
        this.type = types[ mob_num ];
        constructor();
    }

    public void act() {
        try {
            //facePlayer(100);
            if(is("orc_rider")) {
                touchGround();
                act(5, 10, 1);
            } else if(is("mummy")) {
                touchGround();
                act(Util.ran(2), Util.ran(3)+1, 1);
            } else if(is("zombie_gunner")) {
                checkFall();
                if(onGround() && Util.chance(1))
                    jump(15);
                act(1, 3, 1);
            } else if(is("bat")) {
                facePlayer(20);
                act(-5, 3, 1);
            } else if(is("zombie_crawler")) {
                touchGround();
                act(1,3,1);
            } else if(is("fiend_crawler")) {
                touchGround();
                act(4, 2, 1);
            }
        } catch(IllegalStateException ise) {}
        catch(NullPointerException npe) {}
    }

    private void act(int moveSpeed, int frequency, int damage) {
        move(-moveSpeed);
        animate(frequency);
        //damagePlayer(damage);
        try { disappearAtEdge(); }
        catch(IllegalStateException ise) { }
    }

    private void damagePlayer(int damage) {
        Player p = (Player)getOneObjectAtOffset(0,0,Player.class);
        boolean isTouching = p != null;
        if(isTouching) {

            boolean vulnerable = p.invincible();
            boolean canhurt = isTouching && vulnerable;
            System.out.println("touching: " + isTouching + "\n" + "vulnerable: " + vulnerable);
            if(canhurt)
                damage(Player.class, damage);
        }
    }

    private boolean is(String type) {
        return Util.compare(this.type, type);
    }
}