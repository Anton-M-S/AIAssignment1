public class UnlitSpace extends Space {
    public static final char SPACE_TYPE = '_';

    public UnlitSpace(int x, int y){
        super(x,y);
    }

    public String getSpaceType(){
        return "_";
    }
}
