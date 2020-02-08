public class Bulb extends Space {

    public int constraining;
    public Bulb(int x, int y){
        super(x,y);
        constraining = 0;
    }


    public String getSpaceType(){
        return "b";
    }
}
