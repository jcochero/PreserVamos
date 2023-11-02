package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_rio_sierras_main_large{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btnterminar").vw.setLeft((int)((50d / 100 * width)-(views.get("btnterminar").vw.getWidth())/2d));
views.get("btnpregunta1").vw.setLeft((int)((50d / 100 * width) - (views.get("btnpregunta1").vw.getWidth() / 2)));
views.get("lblpregunta1").vw.setLeft((int)((50d / 100 * width) - (views.get("lblpregunta1").vw.getWidth() / 2)));
views.get("btnpregunta2").vw.setLeft((int)((50d / 100 * width) - (views.get("btnpregunta2").vw.getWidth() / 2)));
views.get("lblpregunta2").vw.setLeft((int)((50d / 100 * width) - (views.get("lblpregunta2").vw.getWidth() / 2)));
views.get("btnpregunta3").vw.setLeft((int)((50d / 100 * width) - (views.get("btnpregunta3").vw.getWidth() / 2)));
views.get("lblpregunta3").vw.setLeft((int)((50d / 100 * width) - (views.get("lblpregunta3").vw.getWidth() / 2)));
views.get("btnpregunta7").vw.setLeft((int)((50d / 100 * width) - (views.get("btnpregunta7").vw.getWidth() / 2)));
views.get("lblpregunta7").vw.setLeft((int)((50d / 100 * width) - (views.get("lblpregunta7").vw.getWidth() / 2)));
views.get("txtnotas").vw.setTop((int)((views.get("btnmasdetalle").vw.getTop() + views.get("btnmasdetalle").vw.getHeight())+(5d * scale)));
views.get("txtnotas").vw.setTop((int)((views.get("btnpregunta13").vw.getTop())-(5d * scale) - (views.get("txtnotas").vw.getHeight())));
views.get("label1").vw.setTop((int)((views.get("txtnotas").vw.getTop())));
views.get("label1").vw.setTop((int)((views.get("txtnotas").vw.getTop() + views.get("txtnotas").vw.getHeight()) - (views.get("label1").vw.getHeight())));

}
}