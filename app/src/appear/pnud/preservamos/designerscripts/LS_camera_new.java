package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_camera_new{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("btnrecord").vw.setLeft((int)((50d / 100 * width) - (views.get("btnrecord").vw.getWidth() / 2)));
views.get("logopreservamos").vw.setLeft((int)((50d / 100 * width) - (views.get("logopreservamos").vw.getWidth() / 2)));
views.get("lblcomofuncionaback").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcomofuncionaback").vw.getWidth() / 2)));

}
}