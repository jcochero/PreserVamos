package ilpla.appear.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymainreportar{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnreportar").vw).setTextSize((float)((50d * scale)));
views.get("btnreportar").vw.setLeft((int)((50d / 100 * width)-(views.get("btnreportar").vw.getWidth())/2d));
views.get("lblanalizar").vw.setLeft((int)((50d / 100 * width)-(views.get("lblanalizar").vw.getWidth())/2d));

}
}