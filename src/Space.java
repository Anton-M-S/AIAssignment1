
public class Space{
    int x;
    int y;
    Space(int inX, int inY){
        x = inX;
        y = inY;
    }

    public Space deepClone(){
        return new Space(x,y);
    }

    public int getX(){
        return  x;
    }

    public int getY() {
        return y;
    }
}