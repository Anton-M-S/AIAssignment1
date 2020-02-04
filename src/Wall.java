public class Wall extends Space {
    public static final char SPACE_TYPE = 'W';
    public int wallNum;

    public Wall(int x, int y, char num){
        super(x,y);
        wallNum = num-48;
    }

    public Wall(int x, int y, int num){
        super(x,y);
        wallNum = num;
    }

    public int getWallNum() {
        return wallNum;
    }


    public boolean equals(Wall s) {
        boolean result;
        result =  super.equals(s);
        return result && this.getWallNum()==s.getWallNum();
    }

    public String getSpaceType(){
        return ""+wallNum;
    }
}
