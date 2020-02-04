public class Bulb extends Space {

    public static final char SPACE_TYPE = 'b';

    public Bulb(int x, int y){
        super(x,y);
    }

    public String getSpaceType(){
        return "b";
    }
}
