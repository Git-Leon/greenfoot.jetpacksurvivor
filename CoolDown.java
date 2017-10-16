import greenfoot.*;
public class CoolDown {
    private String methodname;
    private double maxlife, birthTime;
    private boolean isAvailable;

    private void init(String methodname, double maxlife) {
        this.birthTime = System.currentTimeMillis();
        this.isAvailable = true;
        this.methodname = methodname;
        this.maxlife = maxlife;
    }

    public CoolDown(String methodname, double maxlife) {
        init(methodname, maxlife);
    }

    public CoolDown(String methodname) {
        init(methodname, 5);
    }

    public boolean isAvailable() {
        boolean available = elapsed() >= maxlife;
        if(available)
            setBirth(System.currentTimeMillis());
        return available;
    }

    public String getMethodName(){
        return methodname;
    }

    public void setBirth(double birthTime) {
        this.birthTime = birthTime;
    }

    private double elapsed() {
        return System.currentTimeMillis() - birthTime;
    }

    private double maxLife() {
        return this.maxlife;
    }

    public void setMaxLife(double val) {
        this.maxlife = val;
    }
}