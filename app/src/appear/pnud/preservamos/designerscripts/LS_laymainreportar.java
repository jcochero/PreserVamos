package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymainreportar{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btnreportar").vw.setLeft((int)((50d / 100 * width) - (views.get("btnreportar").vw.getWidth() / 2)));
views.get("btnreportar").vw.setTop((int)((40d / 100 * height) - (views.get("btnreportar").vw.getHeight() / 2)));
views.get("lblanalizar").vw.setTop((int)((views.get("btnreportar").vw.getTop() + views.get("btnreportar").vw.getHeight())+(10d * scale)));
views.get("lblanalizar").vw.setLeft((int)((50d / 100 * width) - (views.get("lblanalizar").vw.getWidth() / 2)));
views.get("lblcomofunciona2").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcomofunciona2").vw.getWidth() / 2)));
views.get("lblcomofuncionaanalizar").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcomofuncionaanalizar").vw.getWidth() / 2)));

}
}