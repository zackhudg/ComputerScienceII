package rit.stu.act1;

import rit.cs.Stack;
import rit.cs.Node;

/**
 * A stack implementation that uses a Node to represent the structure.
 * @param <T> The type of data the stack will hold
 * @author Sean Strout @ RIT CS
 * @author Zack Hudgins
 */
public class StackNode<T> implements Stack<T> {
    private int size;
    private Node<T> top;
    /**
     * Create an empty stack.
     */
    public StackNode() {
        this.size = 0;
        this.top = null;
    }

    /**
     * tells if stack empty
     * @return true if empty, false if not
     */
    @Override
    public boolean empty() {
        return this.top == null;
    }

    /**
     * returns and removes top of stack
     * @return top of stack
     */
    @Override
    public T pop() {
        assert !empty();
        T popped = this.top();
        this.top = this.top.getNext();
        this.size--;
        return popped;
    }

    /**
     * adds node to top of stack
     * @param element The new data element
     */
    @Override
    public void push(T element) {
        Node<T> element_node = new Node<T>(element, null);
        if(!this.empty()){
            element_node.setNext(this.top);
        }
        this.top = element_node;
        this.size++;
    }

    /**
     * returns value of top node
     * @return value of top node
     */
    @Override
    public T top() {
        assert !empty();
        return this.top.getData();
    }
}
