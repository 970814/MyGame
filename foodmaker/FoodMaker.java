package foodmaker;

import controller.ControlMapping;
import datamodel.DataConstant;
import datamodel.MapDataModel;

/**
 * Created by Administrator on 2016/4/28.
 */
public class FoodMaker extends ControlMapping implements DataConstant{


    boolean inProduction = false;
    private MapDataModel dataModel;

    public FoodMaker(MapDataModel mapDataModel) {
        dataModel = mapDataModel;
    }

    public void product(int n) {
        if (inProduction) {
            return;
        }
        inProduction = true;
        new Thread(() -> {
            for (int i = 0; i < n; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dataModel.emptyLocation().setValue(FOOD);
            }
            inProduction = false;
        }).start();
    }


    @Override
    public void control(int keyCode) {
        if (keyCode == map[0]) {
            product(20);
        }
    }
}
