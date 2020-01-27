public class Space {

    public int x;
    public int y;

    public Space(int x2, int y2){
        this.x = x2;
        this.y = y2;
    }

    public Space deepClone(){
        return new Space(x,y);
    }
}
