package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymainexplorar{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btnabrirmapa").vw.setLeft((int)((50d / 100 * width) - (views.get("btnabrirmapa").vw.getWidth() / 2)));
views.get("btnabririnat").vw.setLeft((int)((50d / 100 * width) - (views.get("btnabririnat").vw.getWidth() / 2)));
views.get("lblbackgreen").vw.setHeight((int)((50d / 100 * height)));
views.get("lblbackblue").vw.setHeight((int)((50d / 100 * height)));
views.get("lblbackblue").vw.setTop((int)((50d / 100 * height)));
views.get("btnabririnat").vw.setTop((int)((75d / 100 * height) - (views.get("btnabririnat").vw.getHeight() / 2)));
views.get("btnabrirmapa").vw.setTop((int)((25d / 100 * height) - (views.get("btnabrirmapa").vw.getHeight() / 2)));
views.get("lblabririnattitle").vw.setTop((int)((views.get("lblbackblue").vw.getTop())+(10d * scale)));
views.get("lblabrirmapatitle").vw.setTop((int)((views.get("lblbackgreen").vw.getTop())+(10d * scale)));

}
}