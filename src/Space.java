public class Space {

    private int x;
    private int y;
    public static final char SPACE_TYPE = ' ';

    public Space(){
        x = -1;
        y = -1;
    }

    public Space(int x2, int y2){
        this.x = x2;
        this.y = y2;
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

    public boolean equals(Space s){
        return s.x == this.x && s.y == this.y;
    }

    public String toString(){
        return x +","+y;
    }

    public String getSpaceType(){
        return " ";
    }
}