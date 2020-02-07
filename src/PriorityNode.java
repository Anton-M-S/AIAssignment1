public class PriorityNode {
    private Bulb bulb;
    private int priority;
    private PriorityNode next;

    public PriorityNode(Bulb b, int p, PriorityNode n){
        this.bulb = b;
        this.priority = p;
        this.next = n;
    }
    public Bulb getBulb() {
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
}
