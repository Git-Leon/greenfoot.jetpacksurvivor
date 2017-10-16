import greenfoot.*;
public class Jetpack extends SubActor {
    private String[] types = {"jetpack", "cloud"};
    private String type;
    GreenfootSound gs = new GreenfootSound("jetpack, rocket, acceleration.mp3");
    public Jetpack() {     
        this.type = "jetpack";
    }

    public void act()  {
        try { jetpack(); }
        catch(IllegalStateException ise) { }
    }

    private void jetpack() {
        if(is("jetpack")) {
            setImage("rocket.png");
            gs.play();
            shadow(getPlayer());
            if(!Util.keyDown("e")) {
                gs.stop();
                gs.setVolume(0);
                gs = null;                
                //Greenfoot.playSound("jetpack, whine, pitch, descends.mp3");
                kill(this);
                return;
            }
        }
    }    

    private boolean is(String type) {
        return Util.compare(this.type, type);
    }
}