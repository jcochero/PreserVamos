package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layabout_people{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imglogopnud").vw.setLeft((int)((50d / 100 * width) - (views.get("imglogopnud").vw.getWidth() / 2)));
views.get("imglogoappear").vw.setLeft((int)((50d / 100 * width) - (views.get("imglogoappear").vw.getWidth() / 2)));

}
}