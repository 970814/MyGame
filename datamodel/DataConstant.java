package datamodel;

/**
 * Created by Administrator on 2016/4/25.
 */
public interface DataConstant {
	char EMPTY = ' ';
	char ROCK = '▇';
	char TRACE = '~';
	char TAIL = '✿';
	char FOOD = '❤';
	char BULLET = '●';
	char BODY = '❂';// ▩⊕♨❂ ❁
	char BODY$ = '@';
	char undigestedFood = '♥';
	char HEAD = 'D';
	char MOUSE = 'M';

	int EAST = 102;
	int SOUTHEAST = 99;
	int SOUTH = 98;
	int SOUTHWEST = 97;
	int WEST = 100;
	int NORTHWEST = 103;
	int NORTH = 104;
	int NORTHEAST = 105;
	int[] key = { 102, 99, 98, 97, 100, 103, 104, 105 };

	int LEFT = 37;
	int UP = 38;
	int RIGHT = 39;
	int DOWN = 40;

	char[] DS = { '→', '↓', '←', '↑', };
	/**
	 * mapping the x and y
	 */

	int[][] value = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, 1}, {1, 1}, {1, -1}, {-1, -1},};
}
