import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
public class Cluster extends Npc {
    public void act() {
        followTheLeader();
        behavior();
    }

    private void behavior() {
        if(indexOf(this) == 0) { //head behavior
            if(onGround() && Util.chance(10))
                jump(15);
            checkFall();
            setImage("smiley4.png");
            move(1);
        } else { //body behavior
            if( ((Npc)getObject(this.getClass(), 0)).onGround() )  {//head on ground
                checkFall();        
            } else { //head in air
                for(int i=0; i<5 && !noseContact(this.getClass()); i++)
                    move(1);
            }
        }
    }

    private void followTheLeader() {
        if(indexOf(this) == 0) {
            faceObject(getPlayer());
        } else
            try { faceObject( getObject(this.getClass(), 0) ); }
            catch(IndexOutOfBoundsException ioobe) { return; }
    }
}