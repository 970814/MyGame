package systemcontrol;

import controller.ControlMapping;
import controller.MainThing;
import datamodel.MapDataModel;

/**
 * 用于控制一些系统操作;
 */
public class SystemController extends ControlMapping {
    MainThing mainThing = null;
    MapDataModel dataModel = null;

    public void setDataModel(MapDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public SystemController(int... keys) {
        super(keys);
    }

    public SystemController(MainThing mainThing, int... keys) {
        super(keys);
        this.mainThing = mainThing;
    }

    @Override
    public void control(int keyCode) {
        for (int i = 0; i < map.length; i++) {
            if (keyCode == map[i]) {
                doMainThing();
                return;
            }
        }
    }

    public void doMainThing() {
        if (mainThing != null) {
            mainThing.doMainThing();
        }
    }
}
