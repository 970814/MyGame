package mapper;

import java.awt.Graphics;
import java.awt.Graphics2D;
import datamodel.MapDataModel;

import javax.swing.JComponent;

/**
 * the component will paint the image by @See MapDataModel
 * 
 * @author computer
 *
 */
@SuppressWarnings("serial")
public class Painter extends JComponent{
	int wh[] = new int[] { 0, 0 };
	private int paintCount = 0;
	private final MapDataModel dataModel;

	public Painter(MapDataModel mapDataModel) {
		dataModel = mapDataModel;
	}

	@Override
	protected void paintComponent(Graphics g) {
		final Graphics2D d = (Graphics2D) g;
		int w = getWidth();
		int h = getHeight();
		wh[0] = w / (dataModel.getWidth() + 6);
		wh[1] = h / (dataModel.getHeight() + 5);
		// d.drawString(dataPoint.toString(), baseX +
		// dataPoint.x * pSize, baseY + dataPoint.y * pSize);
		dataModel.forEachData(dataUnit -> d.drawImage(ImageParser.parseOf(dataUnit), dataUnit.x
				* wh[0], dataUnit.y * wh[1], wh[0], wh[1], null));
		d.setFont(d.getFont().deriveFont(20.0f));
		d.drawString("HELLO, WORLD!" + paintCount++, w / 4, h / 4);
	}

	public MapDataModel getDataModel() {
		return dataModel;
	}
}
