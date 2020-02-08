public class PriorityQueue {
    private PriorityNode top;
    private int length;

    public PriorityQueue() {
        top = null;
        length = 0;
    }

    public void add(Space b, int heurVal) {
        PriorityNode newNode;
        PriorityNode currNode = top;
        PriorityNode prev = null;
        if (top == null){
            top = new PriorityNode(b, heurVal, null);
            length++;
        }else {
            if (heurVal > top.getPriority()) {
                newNode = new PriorityNode(b, heurVal, top);
                top = newNode;
                length++;
            } else {
                while (currNode != null && currNode.getPriority() >= heurVal) {
                    prev = currNode;
                    currNode = currNode.getNext();
                }
                if (prev != null) {
                    newNode = new PriorityNode(b, heurVal, currNode);
                    prev.setNext(newNode);
                    length++;
                }
            }
        }
    }

    public int getLength() {
        return length;
    }

    public PriorityNode getTop() {
        return top;
    }

    public PriorityNode pop(){
        PriorityNode temp = top;
        if (temp!=null) {
            top = top.getNext();
            length--;
        }
        return temp;
    }

    public String toString(){
        return "Size: "+length;
    }
}

