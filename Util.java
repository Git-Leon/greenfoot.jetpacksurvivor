import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;
import greenfoot.GreenfootImage;
import java.awt.Color;
import java.lang.reflect.Field;
public class Util {
    public static boolean isInRange(int testVal, int min, int max) {
        boolean inRange = false;
        if(min > max) 
            min = max;
        return (testVal >= min) && (testVal <= max);
    }

    public static int ran(int maxVal) {
        if(maxVal < 0)
            return -1 * Greenfoot.getRandomNumber(Math.abs(maxVal));
        return Greenfoot.getRandomNumber(maxVal);
    }

    public static boolean chance(int percent) {
        return ran(100) <= percent;
    }

    public static boolean mouseClicked() {
        return mouseClicked(null);
    }

    public static boolean mouseClicked(Object obj) {
        return Greenfoot.mouseClicked(obj);
    }

    public static boolean keysDown(String... keys) {
        for(String key : keys)
            if(keyDown(key))
                return true;
        return false;
    }

    public static boolean keyDown(String key) {
        return Greenfoot.isKeyDown(key);
    }

    public static boolean hasLifted(String key) {
        return true;	
    }

    public static boolean XOR(boolean x, boolean y) {
        return (x || y) && !(x && y);
    }

    public static int plusOrMinus() {
        return Util.ran(1) == 0 ? 1 : -1;
    }

    public static <T extends Comparable> T[] sorted(T[] table) {
        int pass = 1;
        boolean exchanges = false;
        do {
            exchanges = false;
            for(int i = 0; i < table.length - pass; i++) {
                if(table[i].compareTo(table[i+1]) > 0) {
                    T temp = table[i];
                    table[i] = table[i+1];
                    table[i+1] = temp;
                    exchanges = true;
                }
            }
            pass++;
        } while(exchanges);
        return table;
    }

    public static boolean compare(String text, String text0) {
        return text.toLowerCase().equals(text0);
    }

    public static String longString(char delimeter, String... strings) {
        StringBuilder sb = new StringBuilder();
        for(String string : strings)
            sb.append(string+delimeter);
        return sb.toString();
    }

    public static String longString(String... strings) {
        return longString('\0', strings);
    }

    public static String[] varsAndVals(Object obj) {
        try {
            Field[] fields = getFields(obj);
            String[] varsAndVals= new String[fields.length];            
            for(int i=0; i<fields.length; i++) {
                String name = fields[i].getName();
                Object value = fields[i].get(obj);
                varsAndVals[i] = name +": "+ value;
            }
            return sorted(varsAndVals);
        } catch(IllegalAccessException e) {return null;}
    }

    public static Field[] getFields(Object obj) {
        return obj.getClass().getFields();
    }

    public static Object[] fieldvals(Object obj) {
        try {
            Field[] fields = getFields(obj);
            Object[] vals = new Object[fields.length];
            for(int i=0; i<vals.length; i++)
                vals[i] = fields[i].get(obj);
            return vals;
        } catch(IllegalAccessException iae) {return null;}
    }

    public static String[] fieldnames(Object obj) {
        Field[] fields = obj.getClass().getFields();
        String[] names = new String[fields.length];
        for(int i=0; i<fields.length; i++)
            names[i] = fields[i].getName();
        return sorted(names);
    }

    public static String[] privatefields(Object p) {        
        try{
            Field privateStringField = p.getClass().getDeclaredField("privateString");
            privateStringField.setAccessible(true);
            return (String[]) privateStringField.get(p);
        }catch(NoSuchFieldException nsfe) { return null; }
        catch(IllegalAccessException iae) {return null; }
    }

    public static GreenfootSound playSound(String str) {
        Greenfoot.playSound(str);
        return new GreenfootSound(str);
    }

    public static class Text {
        public static GreenfootImage getText(String text) {
            return getText(text, 20);
        }

        public static GreenfootImage getText(String text, int size) {
            return getText(text, size, new Color(Util.ran(255), Util.ran(255), Util.ran(255), 255));
        }

        public static GreenfootImage getText(String text, int size, Color foreground) {
            return getText(text, size, foreground, new Color(0, 0, 0, 255));
        }

        public static GreenfootImage getText(String text, int size, Color foreground, Color background) {
            return new GreenfootImage(text, size, foreground, background);
        }
    }
}