public class PriorityNode {
    private Space bulb;
    private int priority;
    private PriorityNode next;

    public PriorityNode(Space b, int p, PriorityNode next){
        this.bulb = b;
        this.priority = p;
        this.next = next;
    }
    public Space getBulb() {
        return bulb;
    }

    public int getPriority() {
        return priority;
    }

    public PriorityNode getNext() {
        return next;
    }

    public void setNext(PriorityNode next) {
        this.next = next;
    }

    public String toString(){
        return priority + ", ";
    }
}
