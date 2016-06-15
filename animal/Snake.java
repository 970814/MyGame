package animal;

import datamodel.DataModel;
import datamodel.DataUnit;
import datamodel.MapDataModel;
import mapper.ImageParser;

import javax.swing.*;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class Snake extends Animal {

    public Snake(DataModel dataModel) {
        super(dataModel);
        get(0).setValue(HEAD);
        init(9, get(0).y);
        get(0).setValue(HEAD);
    }

    @Override
    public void addNewBody() {
        addNewBody(dataModel.emptyLocation());
    }

    public void addNewBody(DataUnit unit) {
        add(unit);
        unit.setHashCode(hashCode());
        unit.setDirection(currentDirection());
        unit.setValue(BODY);
    }

    public void init(int max, int y) {
        DataUnit unit;
        while (max-- > 0 && --y > 0) {
            unit = locate(get(0).x, y);
            addNewBody(unit);
        }
        get(size() - 1).setValue(TAIL);

    }

    @Override
    public void move() {
        DataUnit headUnit = get(0);
        DataUnit tailUnit = get(size() - 1);
        int oldD = headUnit.getDirection();

        int d = currentDirection();
        /**
         * 保证蛇不能倒着走
         */
        int d0 = d;
        d0++;
        d0 %= 4;
        d0++;
        d0 %= 4;
        if (d0 == oldD) {
            d = oldD;
        }

        /**
         * mapping a new unit
         */
        int x = headUnit.x + value[d][0];
        int y = headUnit.y + value[d][1];
        DataUnit oldTailUnit = null;
        DataUnit newUnit = locate(x, y);
        if (newUnit.getValue() != FOOD && newUnit.getValue() != MOUSE) {// if not food
            tailUnit.setValue(EMPTY);
            remove(size() - 1);
            oldTailUnit = tailUnit;
        }
        boolean hasMoved = false;
        if (newUnit.getValue() == EMPTY || newUnit.getValue() == FOOD
                || newUnit.getValue() == MOUSE) {
            if (size() > 0) {
                // map[head.x][head.y] = map[i][j] == EMPTY ?
                // BODY : undigestedFood;
                if (oldD != d) {
                    headUnit.setValue(BODY$);
                    headUnit.setDirection(ImageParser.getSnakeBodyDirection(oldD, d));
                } else {
                    headUnit.setValue(BODY);
                }
            }
            // if (map[i][j] == FOOD) {
            // food--;
            // }
            if (newUnit.getValue() == MOUSE) {
                eatMouse(newUnit);
            }
            add(0, newUnit);
            hasMoved = true;
        }

        tailUnit = get(size() - 1);
        if (oldTailUnit != null) {
            if (tailUnit.getValue() == BODY$) {
                tailUnit.setDirection(ImageParser.getSnakeTailDirection(
                        oldTailUnit.getDirection(),
                        tailUnit.getDirection()));
            }
        }
        if (size() > 1) {
            tailUnit.setValue(TAIL);// tail
        }
        get(0).setValue(HEAD);// head
        if (hasMoved) {
            get(0).setDirection(d);
        }
    }

    private void eatMouse(DataUnit newUnit) {
        dataModel.kill(newUnit.animalHashCode());
    }

    @Override
    public void turn(int d) {
        super.turn(d);
    }
}
