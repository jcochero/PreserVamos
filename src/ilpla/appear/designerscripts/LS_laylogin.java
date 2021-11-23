package ilpla.appear.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laylogin{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnregister").vw).setTextSize((float)(16d));
views.get("imglogo").vw.setLeft((int)((50d / 100 * width)-(views.get("imglogo").vw.getWidth())/2d));
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}