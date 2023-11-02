package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_camera_new{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btnrecord").vw.setLeft((int)((50d / 100 * width) - (views.get("btnrecord").vw.getWidth() / 2)));
views.get("lblcomofuncionaback").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcomofuncionaback").vw.getWidth() / 2)));
views.get("btninfocianosemaforo").vw.setLeft((int)((50d / 100 * width) - (views.get("btninfocianosemaforo").vw.getWidth() / 2)));

}
}