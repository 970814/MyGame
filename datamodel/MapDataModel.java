package datamodel;

import java.util.*;
import java.util.function.Consumer;

import animal.Animal;
import systemcontrol.SystemController;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MapDataModel extends DataModel {
	/**
	 * listList is the map model.
	 */
	private Random random = new Random();
	private List<List<DataUnit>> listList = null;

	/**
	 * 定位到地图的某个有x,y指定的位置,并返回这个对象
     */
	public DataUnit locate(int x, int y) {
		if (listList == null) {
			throw new NullPointerException("listList == null");
		}
		return listList.get(y).get(x);
	}

	@SuppressWarnings("serial")
	private void new_List_Map() {
		listList = new LinkedList<>();
		for (int y = 0; y < height + 2; y++) {
			final int y0 = y;
			listList.add(new LinkedList<DataUnit>() {
				{
					for (int x = 0; x < width + 2; x++) {
						add(new DataUnit(x, y0));
					}
				}
			});
		}
		for (int y = 0; y < height + 2; y++) {
			locate(0, y).setValue(ROCK);
			locate(width + 1, y).setValue(ROCK);
		}
		for (int x = 1; x < width + 1; x++) {
			locate(x, 0).setValue(ROCK);
			locate(x, height + 1).setValue(ROCK);
		}
	}

	/**
	 * 随机选地图某个位置设置石头,如果该位置不为空则设置为石头,返回真,否则返回假
	 */
	public boolean randomRock() {
		int x = random.nextInt(width) + 1;
		int y = random.nextInt(height) + 1;
		if (locate(x, y).getValue() == EMPTY) {
			locate(x, y).setValue(ROCK);
			return true;
		}
		return false;
	}

	/**
	 * 产生石头
	 */
	private void productRock() {
		int rocks = 0;
		while (rocks < rockCount) {
			if (randomRock()) {
				rocks++;
			}
		}
	}

	/**
	 * 杀掉一个进程;
     */
	@Override
	public void kill(int hashCode) {
		super.forEach(runnable -> {
			if (runnable instanceof Animal) {
				Animal animal = (Animal) runnable;
				if (animal.hashCode() == hashCode) {
					animal.die();
					return;
				}
			}
		});
	}

	public MapDataModel(int width, int height, double density) {
		super(width, height, density);
		new_List_Map();
		productRock();
	}

	/**
	 * 返回在地图上的一个空位置
     */
	@Override
	public DataUnit emptyLocation() {
		int x;
		int y;
		do {
			x = random.nextInt(width) + 1;
			y = random.nextInt(height) + 1;
		} while (locate(x, y).getValue() != EMPTY);
		return locate(x, y);
	}

	public void forEachData(Consumer<DataUnit> consumer) {
		for (List<DataUnit> list : listList) {
			list.forEach(consumer);
		}
	}

	public void workAll() {
		super.forEach(runnable -> ((Animal) runnable).wakeUp());
	}

	public void sleepAll(){
		super.forEach(runnable -> ((Animal) runnable).sleep());
	}

	public void addSystemController(SystemController systemController) {
		systemController.setDataModel(this);
	}

}
