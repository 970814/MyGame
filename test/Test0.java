package test;

import java.awt.EventQueue;

import animal.Mouse;
import animal.Snake;
import foodmaker.FoodMaker;
import parasite.ControlSet;
import controller.ControllerSet;
import datamodel.MapDataModel;
import mapper.Monitor;
import mapper.Painter;
import systemcontrol.SystemController;

public class Test0 {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            final ControllerSet controllerSet = new ControllerSet();
            ControlSet set0 = new ControlSet();
            set0.mapping(40, 39, 38, 37);// down right up left
            ControlSet set1 = new ControlSet();
            set1.mapping(83, 68, 87, 65);// S F W A
            ControlSet set2 = new ControlSet();
            set2.mapping(98, 102, 104, 100, 97, 99, 105, 103);// num. 2 6 8 4 1 3 9 7
            controllerSet.add(set0);
            controllerSet.add(set1);
            controllerSet.add(set2);


            MapDataModel dataModel = new MapDataModel(50, 25, 0.0);
//            FoodMaker foodMaker = new FoodMaker(dataModel);
//            foodMaker.mapping(49);

            SystemController sleepAll = new SystemController(122) {//F11
                private boolean isSleep = false;

                @Override
                public void doMainThing() {
                    if (isSleep) {
                        dataModel.workAll();
                    } else {
                        dataModel.sleepAll();
                    }
                    isSleep = !isSleep;
                }
            };
            SystemController newSnake = new SystemController(112) {//F1
                @Override
                public void doMainThing() {
                    dataModel.addWithStart(new Snake(dataModel) {
                        {
                            parasitic(set2);
                        }
                    });
                }
            };

            controllerSet.add(changeStates);
            controllerSet.add(sleepAll);
            controllerSet.add(newSnake);

            controllerSet.add(foodMaker);

            Painter painter = new Painter(dataModel);
            Monitor monitor = new Monitor(painter);
            SystemController changeStates = new SystemController(123) {//F12
                @Override
                public void doMainThing() {
                    monitor.is
                }
            };

            monitor.addControllerListener(controllerSet);
            monitor.setVisible(true);
            dataModel.add(new Mouse(dataModel) {
                {
                    parasitic(set0);
                }
            });
            dataModel.add(new Mouse(dataModel) {
                {
                    parasitic(set1);
                }
            });
            dataModel.add(new Mouse(dataModel) {

            });
//            dataModel.add(new Mouse(dataModel) {
//                {
//                    parasitic(set2);
//                }
//            });
//            dataModel.add(new Snake(dataModel) {
//                {
//                    parasitic(set0);
//                    speed(150);
//                }
//            });
            dataModel.sleepAll();
            dataModel.startAll();
        });
    }
}
//            new Thread(() -> {
//                for (int i = 0; i < 10; i++) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    new Mouse(dataModel) {
//                        {
//                            parasitic(controller);
//                            start();
//                            new Thread(()->{
//                                try {
//                                    sleep(10000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                die();
//                            }).start();
//                        }
//                    };
//                }
//
//
//            }).start();
