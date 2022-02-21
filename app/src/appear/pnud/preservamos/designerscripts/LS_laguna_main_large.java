package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laguna_main_large{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btnterminar").vw.setLeft((int)((50d / 100 * width)-(views.get("btnterminar").vw.getWidth())/2d));
views.get("btnpregunta13").vw.setLeft((int)((50d / 100 * width)-(views.get("btnpregunta13").vw.getWidth())/2d));

}
}