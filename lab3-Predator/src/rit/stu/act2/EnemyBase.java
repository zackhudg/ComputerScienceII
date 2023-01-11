package rit.stu.act2;
import rit.stu.act1.QueueNode;
import rit.stu.act1.StackNode;
/**
 * The EnemyBase contains hostages cave(stack) and guerillas queue, where soldier battles guerilla
 *
 * @author Zack Hudgins
 */
public class EnemyBase{
    /**
     * guerillas: queue that holds guerillas
     * hostages: stack that holds hostages
     * numGuerillas: number of guerillas
     * numHostages: number of hostages
     */
    private QueueNode<Guerilla> guerillas;
    private StackNode<Hostage> hostages;
    private int numGuerillas;
    private int numHostages;

    /**
     * constructs hostages stack and guerillas queue and fills them with hostages and guerillas
     * @param numHostages num hostages at start
     * @param numGuerillas num guerillas at start
     */
    public EnemyBase(int numHostages, int numGuerillas){
        this.numHostages = 0;
        this.numGuerillas = 0;
        this.hostages = new StackNode<>();
        this.guerillas = new QueueNode<>();
        for (int i = 1; i<= numHostages; i++){
            addHostage(new Hostage(i));
        }
        for (int i = 1; i<= numGuerillas; i++){
            addGuerilla(new Guerilla(i));
        }
    }

    /**
     * adds guerilla to queue
     * @param guerilla guerilla to be enqueued
     */
    private void addGuerilla(Guerilla guerilla){
        this.guerillas.enqueue(guerilla);
        this.numGuerillas++;
    }

    /**
     * adds hostage to stack
     * @param hostage hostage to be pushed
     */
    private void addHostage(Hostage hostage){
        this.hostages.push(hostage);
        this.numHostages++;
    }

    /**
     * dequeues guerilla from front of queue
     * @return guerilla from front
     */
    private Guerilla getGuerilla(){
        assert !guerillas.empty();
        this.numGuerillas--;
        return guerillas.dequeue();
    }

    /**
     * pops hostage from top of stack
     * @return hostage from top
     */
    private Hostage getHostage(){
        assert !hostages.empty();
        this.numHostages--;
        return hostages.pop();
    }

    /**
     * gets number of hostages in stack
     * @return number of hostages
     */
    public int getNumHostages(){
        return this.numHostages;
    }

    /**
     * gets number of guerillas in queue
     * @return number of guerillas
     */
    public int getNumGuerillas(){
        return this.numGuerillas;
    }

    /**
     * initiates battle sequence between soldier and guerilla to try and rescue hostage
     * @param soldier soldier that battles guerilla
     * @return hostage if battle is won by soldier, null if won by guerilla
     */
    public Hostage rescueHostage(Soldier soldier){
        System.out.println(soldier.toString() + " enters enemy base...");
        Hostage hostage = getHostage();
        if (getNumGuerillas() != 0){
            Guerilla guerilla = getGuerilla();
            int roll = Battlefield.nextInt(1,100);
            System.out.println(soldier.toString() + " battles " + guerilla.toString() + " who rolls a " + roll);
            if (roll > guerilla.CHANCE_TO_BEAT_SOLDIER){
                soldier.victory(guerilla);
                guerilla.defeat(soldier);
            }else{
                guerilla.victory(soldier);
                soldier.defeat(guerilla);
                addHostage(hostage);
                addGuerilla(guerilla);
                return null;
            }
        }
        return hostage;
    }
}