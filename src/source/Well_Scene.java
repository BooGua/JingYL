package source;

import java.io.FileInputStream;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Cylinder;

public class Well_Scene extends SimpleApplication {

    private double MAX_VOLUME = 25.6398456;

    private Node well_node = new Node("pivot"); // 组成井筒的结点。
    private DataQueue dataQueue = new DataQueue(MAX_VOLUME);

    private int MAX_PIECES = 2000; // 井筒由 MAX_VOLUME 块圆柱体组成，必须是偶数。
    private double time = 0; // 用于时间累加。
    private int item = 0; // 代表秒。

    private POIFSFileSystem fs = null;
    private HSSFWorkbook wb = null;

    public static void main(String[] args) {

        Well_Scene app = new Well_Scene();
        app.start();
    }

    @Override // 主逻辑。
    public void simpleInitApp() {

        flyCam.setMoveSpeed(10);
        rootNode.attachChild(well_node);
        createWell();
    }

    @Override // 重复逻辑。
    public void simpleUpdate(float deltaTime) {
        time += deltaTime;
        if (time >= 0.01) { // 时间控制，单位秒。

            dataQueue.attachDataColor(updateVariedData(item));
            dataQueue.printQ_color();

            updateColor(dataQueue);

            time = 0;
            item++;
        }
    }

    /*
     * 给井筒上色。
     */
    public void updateColor(DataQueue dataQueue) {
        int total = 0;
        int size = dataQueue.getColorQueue().size();
        Deque<VariedDataColor> queue_temp = new LinkedList<>();

        // System.out.println("颜色队列大小：" + dataQueue.getColorQueue().size());
        // System.out.println("当前体积：" + dataQueue.getCurrent_color_volume());
        // System.out.println("total:" + total);
        for (int i = 0; i < size; i++) {
            double up_line = dataQueue.getColorQueue().peek().getVolume() / MAX_VOLUME * MAX_PIECES + total;
            if (up_line > MAX_PIECES) {
                up_line = MAX_PIECES;
            }
            for (int j = total; j < up_line; j++) {
                if (dataQueue.getColorQueue().peek().getKind() == 1) {
                    Material mat_box = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                    mat_box.setColor("Color", ColorRGBA.Red);
                    well_node.getChild(j).setMaterial(mat_box);
                } else {
                    Material mat_box = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                    mat_box.setColor("Color", ColorRGBA.Blue);
                    well_node.getChild(j).setMaterial(mat_box);
                }
            }
            total = total + (int) (dataQueue.getColorQueue().peek().getVolume() / MAX_VOLUME * MAX_PIECES); // 这里有越界的危险

            queue_temp.offerFirst(dataQueue.getColorQueue().pollFirst());
        }
        // System.out.println("total:" + total);
        int size_temp = queue_temp.size();
        for (int i = 0; i < size_temp; i++) {
            dataQueue.getColorQueue().offerFirst(queue_temp.pollFirst());
        }
    }

    /*
     * 读取 Excel 数据。
     */
    public VariedData updateVariedData(int item) {
        // System.out.println(item);
        double q = 0;
        int kind = 1;
        try {
            fs = new POIFSFileSystem(
                    new FileInputStream("D:\\EclipseProject\\WellThreeD\\test_data_3D_2.xls"));
            wb = new HSSFWorkbook(fs);
            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = sheet.getRow(item + 1);
            q = row.getCell(0).getNumericCellValue();
            kind = (int) row.getCell(1).getNumericCellValue();
            VariedData variedData = new VariedData(q, kind);
            return variedData;
        } catch (Exception e) {
            // e.printStackTrace();
            return new VariedData(1, 0); // 无数据时，返回 VariedData(5, 0)。
        }
    }

    /*
     * 创建井筒基础模型。
     */
    public void createWell() {

        for (int i = 0; i < MAX_PIECES / 2; i++) { // 竖直部分。
            Spatial geo_box = new Geometry("球体", new Cylinder(10, 20, 1, (float) 0.01, true));// Cylinder(？，细致程度，半径，长度)
            geo_box.rotate(FastMath.PI / 2, 0, 0); // 圆柱体本身为水平放置，需旋转。
            geo_box.setLocalTranslation(new Vector3f(0, (float) -0.01 * i, 0));
            Material mat_box = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat_box.setColor("Color", ColorRGBA.randomColor());
            geo_box.setMaterial(mat_box);
            well_node.attachChild(geo_box);
        }

        for (int i = 0; i < MAX_PIECES / 2; i++) { // 水平部分。
            Spatial geo_box = new Geometry("球体", new Cylinder(10, 20, 1, (float) 0.01, true));// Cylinder(？，细致程度，半径，长度)
            geo_box.rotate(0, FastMath.PI / 2, 0); // 圆柱体本身为水平放置，需旋转。
            geo_box.setLocalTranslation(new Vector3f((float) 0.01 * i, -9, 0));
            Material mat_box = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            mat_box.setColor("Color", ColorRGBA.randomColor());
            geo_box.setMaterial(mat_box);
            well_node.attachChild(geo_box);
        }
    }

}
