/**
 * Draws a recursive H-style pattern in turtle
 * author: Zack Hudgins
 */
package lab01.student;
import java.util.Scanner;

import static turtle.Turtle.*;

public class HTree {
    static int MAX_SEGMENT_LENGTH = 200;

    /**
     * creates the turtle window
     * @param length    used to help set width
     * @param depth     recursive depth, used to help set title
     */
    public static void init(int length, int depth){
        Turtle.setWorldCoordinates(-length*2, -length*2, length*2, length*2);
        Turtle.title("H-Tree, depth: " + depth);
    }

    /**
     * recursively draws H-shapes
     * @param length    helps to determine the length of line segments
     * @param depth     determines how many levels of recursion the program should run
     */
    public static void drawHTree(double length, int depth){
        if (depth > 0){
            // start in center of H, move to upper right
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);

            // recurse
            drawHTree(length / 2, depth - 1);

            // move to lower right of H
            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);

            // recurse
            drawHTree(length / 2, depth - 1);

            // move to upper left of H
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.left(90);
            Turtle.forward(length);
            Turtle.right(90);
            Turtle.forward(length / 2);
            Turtle.right(90);

            // recurse
            drawHTree(length / 2, depth - 1);

            // move to lower left of H
            Turtle.right(90);
            Turtle.forward(length);
            Turtle.left(90);

            // recurse
            drawHTree(length / 2, depth - 1);

            // return to center of H
            Turtle.left(90);
            Turtle.forward(length / 2);
            Turtle.right(90);
            Turtle.forward(length / 2);
        }
    }

    /**
     * calls other functions in class to create the turtle drawing
     * @param args  reads input from the terminal
     */
    public static void main(String[] args){
        if (args.length < 2) {
            System.out.println("Usage: python3 HTree #");
            return;
        }
        int depth = Integer.parseInt(args[1]);
        if (depth < 0){
            System.out.println("The depth must be greater than or equal to 0");
        }
        //initialize turtle
        init(MAX_SEGMENT_LENGTH, depth);

        // draw the h-tree
        drawHTree(MAX_SEGMENT_LENGTH, depth);

    }
}
