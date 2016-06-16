package datamodel;

import java.awt.*;

/**
 * Created by Administrator on 2016/4/24.
 */
@SuppressWarnings("serial")
public class DataUnit extends Point {
	private static Object lock = new Object();
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	int direction;// 方向

	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * The synchronization method is to ensure that more of the animals will not be moved to a place.
	 * @return The thing.
     */
	public int getValue() {
		synchronized (lock) {
			return value;
		}
	}

	private volatile int value;// 存储的是
	int which;// 哪一条?
	int animalHashCode;

	public void setHashCode(int hashCode) {
		this.animalHashCode = hashCode;
	}

	public int animalHashCode() {
		return animalHashCode;
	}

	public DataUnit(int x, int y) {
		this(x, y,DataConstant.EMPTY);
	}

	public DataUnit(int x, int y, int value) {
		super(x, y);
		this.value = value;
	}

	@Override
	public String toString() {
		return "" + (char) value;
	}

}
