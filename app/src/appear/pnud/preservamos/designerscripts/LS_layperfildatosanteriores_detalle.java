package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layperfildatosanteriores_detalle{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lbllat").vw.setWidth((int)(((views.get("lblfecha").vw.getWidth())/2d)-(10d * scale)));
views.get("lbllng").vw.setWidth((int)(((views.get("lblfecha").vw.getWidth())/2d)-(10d * scale)));
views.get("lbllng").vw.setLeft((int)((views.get("lbllat").vw.getLeft() + views.get("lbllat").vw.getWidth())+(10d * scale)));
views.get("chkenviadobar").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth()/2) - (views.get("chkenviadobar").vw.getWidth() / 2)));
views.get("chkonline").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth()/2) - (views.get("chkonline").vw.getWidth() / 2)));
views.get("chkvalidadoexpertos").vw.setLeft((int)((views.get("label13").vw.getLeft() + views.get("label13").vw.getWidth()/2) - (views.get("chkvalidadoexpertos").vw.getWidth() / 2)));
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}