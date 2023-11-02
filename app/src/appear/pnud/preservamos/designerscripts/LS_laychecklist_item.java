package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laychecklist_item{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("lblnombrecomun").vw.setLeft((int)((views.get("imageview1").vw.getLeft() + views.get("imageview1").vw.getWidth())));
views.get("lblnombrecomun").vw.setWidth((int)((100d / 100 * width) - ((views.get("imageview1").vw.getLeft() + views.get("imageview1").vw.getWidth()))));
views.get("lblnombrecientifico").vw.setLeft((int)((views.get("imageview1").vw.getLeft() + views.get("imageview1").vw.getWidth())));
views.get("lblnombrecientifico").vw.setWidth((int)((100d / 100 * width) - ((views.get("imageview1").vw.getLeft() + views.get("imageview1").vw.getWidth()))));

}
}