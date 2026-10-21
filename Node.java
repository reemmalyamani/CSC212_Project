public class Node<T> {
    public T data;
    public Node<T> next;

    public Node() {
        data = null;
        next = null;
    }

    public Node(T val) {
        data = val;
        next = null;
    }
}
