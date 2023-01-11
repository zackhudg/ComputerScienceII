/**
 * The bunker queue is where the soldiers are stored
 *
 * @author Zack Hudgins
 */

package rit.stu.act2;

import rit.stu.act1.QueueNode;

public class Bunker{
    /**
     * bunker: queue to store soldiers
     * numSoldiers: number of soldiers in bunker
     */
    private QueueNode<Soldier> bunker;
    private int numSoldiers;

    /**
     * constructs new bunker, queued with created soldiers
     * @param numSoldiers number of soldiers to enqueue
     */
    public Bunker(int numSoldiers){
        this.bunker = new QueueNode<>();
        for (int i = 1; i<= numSoldiers; i++){
            Soldier soldier = new Soldier(i);
            this.fortifySoldiers(soldier);
        }
    }

    /**
     * tells if soldiers in bunker
     * @return true if queue empty, false if populated
     */
    public boolean hasSoldiers(){
        return !bunker.empty();
    }

    /**
     * gets number of soldiers
     * @return number of soldiers
     */
    public int getNumSoldiers(){
        return this.numSoldiers;
    }

    /**
     * dequeues soldier from bunker
     * @return soldier from front of queue
     */
    public Soldier deployNextSoldier(){
        assert !bunker.empty();
        this.numSoldiers--;
        return bunker.dequeue();
    }

    /**
     * enqueues soldier into bunker
     * @param soldier soldier to be enqueued into bunker
     */
    public void fortifySoldiers(Soldier soldier){
        bunker.enqueue(soldier);
        this.numSoldiers++;
    }
}