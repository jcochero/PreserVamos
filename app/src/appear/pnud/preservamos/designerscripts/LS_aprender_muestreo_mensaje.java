package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_aprender_muestreo_mensaje{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgelementos").vw.setLeft((int)((50d / 100 * width) - (views.get("imgelementos").vw.getWidth() / 2)));
views.get("imgelementos2").vw.setLeft((int)((50d / 100 * width) - (views.get("imgelementos2").vw.getWidth() / 2)));
views.get("lbltitulo").vw.setTop((int)((views.get("imgelementos").vw.getTop() + views.get("imgelementos").vw.getHeight())+(5d * scale)));
views.get("lbltexto1").vw.setTop((int)((views.get("lbltitulo").vw.getTop() + views.get("lbltitulo").vw.getHeight())+(5d * scale)));
views.get("imgelementos2").vw.setTop((int)((views.get("lbltexto1").vw.getTop() + views.get("lbltexto1").vw.getHeight())+(5d * scale)));
views.get("lbltitulo2").vw.setTop((int)((views.get("imgelementos2").vw.getTop() + views.get("imgelementos2").vw.getHeight())+(5d * scale)));
views.get("lbltexto2").vw.setTop((int)((views.get("lbltitulo2").vw.getTop() + views.get("lbltitulo2").vw.getHeight())+(5d * scale)));

}
}