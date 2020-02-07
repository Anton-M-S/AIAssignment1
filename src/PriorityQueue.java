public class PriorityQueue {
    private PriorityNode top;
    private int length;

    public PriorityQueue() {
        top = null;
        length = 0;
    }

    public void add(Bulb b) {
        PriorityNode newNode;
        PriorityNode currNode = top;
        PriorityNode prev = null;
        if (b.getConstr() > top.getPriority()) {
            newNode = new PriorityNode(b, b.getConstr(), top);
            top = newNode;
        } else {
            while (currNode != null && currNode.getPriority() >= b.getConstr()) {
                prev = currNode;
                currNode = currNode.getNext();
            }
            if (prev != null) {
                newNode = new PriorityNode(b, b.getConstr(), currNode);
                prev.setNext(newNode);
                length++;
            }
        }
    }

    public int getLength() {
        return length;
    }

    public PriorityNode getTop() {
        return top;
    }
}
