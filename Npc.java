import greenfoot.*;
public class Npc extends SubActor {
    public Npc() {
    }

    public Npc(String prefix, String suffix, int numOfImages) {
        super(prefix, suffix, numOfImages);
    }

    public void facePlayer(int percentChance) {
        if(Util.chance(percentChance)) {
            Player player = getPlayer();
            if(player != null)
                faceObject(player);
        } 
    }

    public void touchGround() {
        while(!onGround() && !atWorldEdge(10, 30))
            checkFall();
    }
}