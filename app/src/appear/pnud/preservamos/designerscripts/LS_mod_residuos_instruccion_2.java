package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mod_residuos_instruccion_2{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imginstruccion2").vw.setLeft((int)((50d / 100 * width) - (views.get("imginstruccion2").vw.getWidth() / 2)));
views.get("imginstruccion2a").vw.setLeft((int)((views.get("imginstruccion2").vw.getLeft() + views.get("imginstruccion2").vw.getWidth()/2)+((views.get("imginstruccion2").vw.getWidth())/4d) - (views.get("imginstruccion2a").vw.getWidth() / 2)));
views.get("imginstruccion2b").vw.setLeft((int)((views.get("imginstruccion2").vw.getLeft() + views.get("imginstruccion2").vw.getWidth()/2) - (views.get("imginstruccion2b").vw.getWidth())));

}
}