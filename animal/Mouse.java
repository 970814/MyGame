package animal;

import datamodel.DataModel;
import datamodel.DataUnit;
import datamodel.MapDataModel;

public class Mouse extends Animal {

    public Mouse(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void addNewBody() {
        addNewBody(dataModel.emptyLocation());
    }

    public void addNewBody(DataUnit unit) {
        add(unit);
        unit.setHashCode(hashCode());
        unit.setDirection(currentDirection());
        unit.setValue(MOUSE);
    }
    /**
     * robot
     */
    @Override
    public void move() {
        DataUnit head = get(0);
        int d = currentDirection();
        int x = head.x + value[d][0];
        int y = head.y + value[d][1];
        DataUnit newP = locate(x, y);
        if (newP.getValue() == EMPTY || newP.getValue() == FOOD) {
            head.setValue(EMPTY);//clear
            remove(head);
            addNewBody(newP);
        }
        /**
         * if the mouse is not parasitic,
         */
        if (isParasitic()) {
            super.turn(d);
        } else {
            turn(d);
        }
    }

    /**
     * Mouse can go diagonally
     * @param d the next direction of mouse
     */
    @Override
    public void turn(int d) {
        super.turn(random.nextInt(8));
    }
}
