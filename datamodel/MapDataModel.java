package datamodel;

import java.util.*;

import animal.Animal;
import consumer.Consumer;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MapDataModel extends DataModel {
	/**
	 * listList is the map model.
	 */
	private Random random = new Random();
	private List<List<DataUnit>> listList = null;


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
	 * @return If generate success returns TRUE, otherwise it returns FALSE
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

	private void productRock() {
		int rocks = 0;
		while (rocks < rockCount) {
			if (randomRock()) {
				rocks++;
			}
		}
	}
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

	public void forEach(Consumer<DataUnit> consumer) {
		for (List<DataUnit> list : listList) {
			list.forEach(consumer::accept);
		}
	}
}
