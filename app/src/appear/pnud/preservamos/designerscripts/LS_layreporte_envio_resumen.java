package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layreporte_envio_resumen{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lblfinalizado1").vw.setLeft((int)((50d / 100 * width) - (views.get("lblfinalizado1").vw.getWidth() / 2)));
views.get("imglisto").vw.setLeft((int)((50d / 100 * width) - (views.get("imglisto").vw.getWidth() / 2)));
views.get("imglisto").vw.setTop((int)((50d / 100 * height) - (views.get("imglisto").vw.getHeight() / 2)));
views.get("lblfinalizado1").vw.setTop((int)((views.get("imglisto").vw.getTop() + views.get("imglisto").vw.getHeight())+(10d * scale)));

}
}