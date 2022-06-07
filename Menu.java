
import greenfoot.*;
public class Menu extends SubActor {
    private Color color = new Color(255, 255, 255, 255);
    private String name = null;
    public Menu(boolean a) {
        this.name = "default";
    }

    public Menu() {
        this.name = "pause menu";
        setImage("menu.png");
    }

    public Menu(String text) {
        this.name = text;
        setImage(Util.Text.getText(text, 20, this.color));    
    }

    public void act() {
        if(Util.compare(this.name, "pause menu"))
            kill(this);
        else if(Util.compare(this.name, "default"))
            update();
    }

    private void update() {
        setLocation(getImage().getWidth()/2, getImage().getHeight()/2);
        setImage(Util.Text.getText(getPlayerInfo(), 20, this.color));
    }

    private String getPlayerInfo() {
        this.score = getScore();
        return Util.longString('\n', Util.varsAndVals(getPlayer())) + "SCORE: " +getScore();
    }

    private int startScore = 1038;
    public static int score;
    public int getScore() {
        int score = 0;
        Player p = getPlayer();
        Object[] values = Util.fieldvals(getPlayer());
        for(Object val : values)
            try {
                score += ((Integer)val).intValue();
            } catch(Exception e) {}
        return score-startScore;
    }

    public static int score() {
        return new Menu().score; 
    }
}