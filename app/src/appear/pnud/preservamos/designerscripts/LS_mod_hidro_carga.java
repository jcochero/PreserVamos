package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mod_hidro_carga{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgalturamanual").vw.setTop((int)((50d / 100 * height) - (views.get("imgalturamanual").vw.getHeight() / 2)));
views.get("panelseeks").vw.setHeight((int)((50d / 100 * height)));
views.get("panelseeks").vw.setTop((int)((50d / 100 * height) - (views.get("panelseeks").vw.getHeight() / 2)));
views.get("lblalturamanualtitle").vw.setTop((int)((views.get("imgalturamanual").vw.getTop() + views.get("imgalturamanual").vw.getHeight())+(10d * scale)));

}
}