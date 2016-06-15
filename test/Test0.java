package test;

import java.awt.EventQueue;

import animal.Mouse;
import animal.Snake;
import foodmaker.FoodMaker;
import parasite.ControlSet;
import controller.Controller;
import datamodel.MapDataModel;
import mapper.Monitor;
import mapper.Painter;

public class Test0 {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            final Controller controller = new Controller();
            ControlSet set0 = new ControlSet();
            set0.mapping(40, 39, 38, 37);
            ControlSet set1 = new ControlSet();
            set1.mapping(83, 68, 87, 65);
            ControlSet set2 = new ControlSet();
            set2.mapping(98, 102, 104, 100, 97, 99, 105, 103);
            controller.add(set0);
            controller.add(set1);
            controller.add(set2);


            MapDataModel dataModel = new MapDataModel(35, 40, 0.0);
            FoodMaker foodMaker = new FoodMaker(dataModel);
            foodMaker.mapping(49);
            controller.add(foodMaker);
            Painter painter = new Painter(dataModel);
            Monitor monitor = new Monitor(painter);
            monitor.addControllerListener(controller);
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
            dataModel.add(new Mouse(dataModel) {
                {
                    parasitic(set2);
                }
            });
            dataModel.add(new Snake(dataModel) {
                {
                    parasitic(set0);
                    speed(150);
                }
            });
            dataModel.add(new Snake(dataModel) {
                {
//                    parasitic(set0);
//                    speed(150);
                }
            });
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
