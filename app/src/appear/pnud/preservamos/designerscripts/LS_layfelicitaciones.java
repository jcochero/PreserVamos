package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layfelicitaciones{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgdatosenviados").vw.setLeft((int)((50d / 100 * width) - (views.get("imgdatosenviados").vw.getWidth() / 2)));
views.get("lbldatosenviados").vw.setTop((int)((10d * scale)));
views.get("logopreservamos").vw.setLeft((int)((50d / 100 * width) - (views.get("logopreservamos").vw.getWidth() / 2)));
views.get("logopreservamos").vw.setTop((int)((views.get("lbldatosenviados").vw.getTop() + views.get("lbldatosenviados").vw.getHeight())+(5d * scale)));
views.get("imgdatosenviados").vw.setTop((int)((views.get("logopreservamos").vw.getTop() + views.get("logopreservamos").vw.getHeight())+(50d * scale)));

}
}