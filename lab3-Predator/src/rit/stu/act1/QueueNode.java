package rit.stu.act1;

import rit.cs.Node;
import rit.cs.Queue;

/**
 * A queue implementation that uses a Node to represent the structure.
 * @param <T> The type of data the queue will hold
 * @author Sean Strout @ RIT CS
 * @author Zack Hudgins
 */
public class QueueNode<T> implements Queue<T> {
    private int size;
    private Node<T> front;
    private Node<T> back;
    /**
     * Create an empty queue.
     */
    public QueueNode() {
        this.size = 0;
        this.front = null;
        this.back = null;
    }

    /**
     * access data value of back node
     * @return data of back node
     */
    @Override
    public T back() {
        assert !empty();
        return this.back.getData();
    }

    /**
     * dequeues a node
     * @return node at front of queue
     */
    @Override
    public T dequeue() {
        assert !empty();
         T dq = this.front();
         this.front = this.front.getNext();
         if(empty()) {
             this.back = null;
         }
         size--;
         return dq;
    }

    /**
     * tells if queue empty
     * @return true if empty, false if not
     */
    @Override
    public boolean empty() {
        return this.front==null;
    }

    @Override
    /**
     * enqueues a node
     * @param element the new data element
     */
    public void enqueue(T element) {
        Node<T> eq = new Node<T>(element, null);
        if (empty()){
            this.front = eq;
        }else {
            this.back.setNext(eq);
        }
        this.back = eq;
        this.size++;

    }

    /**
     * access data value of front node
     * @return data of front node
     */
    @Override
    public T front() {
        assert !empty();
        return this.front.getData();
    }
}
