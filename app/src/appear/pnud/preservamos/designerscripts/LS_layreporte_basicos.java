package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layreporte_basicos{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgriollanura").vw.setLeft((int)((50d / 100 * width)-((views.get("imgriollanura").vw.getWidth())/2d)-(5d * scale) - (views.get("imgriollanura").vw.getWidth() / 2)));
views.get("imglaguna").vw.setLeft((int)((50d / 100 * width)+((views.get("imglaguna").vw.getWidth())/2d)+(5d * scale) - (views.get("imglaguna").vw.getWidth() / 2)));
views.get("lbllaguna").vw.setLeft((int)((views.get("imglaguna").vw.getLeft() + views.get("imglaguna").vw.getWidth()/2) - (views.get("lbllaguna").vw.getWidth() / 2)));
views.get("lblllanura").vw.setLeft((int)((views.get("imgriollanura").vw.getLeft() + views.get("imgriollanura").vw.getWidth()/2) - (views.get("lblllanura").vw.getWidth() / 2)));

}
}