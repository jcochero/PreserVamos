package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layregister_tab1{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("logopreservamos_register").vw.setLeft((int)((50d / 100 * width) - (views.get("logopreservamos_register").vw.getWidth() / 2)));
anywheresoftware.b4a.keywords.LayoutBuilder.scaleView(views.get("lbltitle_tab1"));
anywheresoftware.b4a.keywords.LayoutBuilder.scaleView(views.get("lbldes_tab1"));

}
}