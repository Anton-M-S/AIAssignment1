public class Bulb extends Space {

    public static final char SPACE_TYPE = 'b';

    public Bulb(int x, int y){
        super(x,y);
    }

    public int getConstr(){
        return 0;
    }

    public String getSpaceType(){
        return "b";
    }
}
