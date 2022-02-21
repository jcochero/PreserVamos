package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layabout_panels{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imglogoabout").vw.setLeft((int)((50d / 100 * width) - (views.get("imglogoabout").vw.getWidth() / 2)));
views.get("btnproyecto").vw.setLeft((int)(0d));
views.get("btnproyecto").vw.setWidth((int)((50d / 100 * width) - (0d)));
views.get("btnequipo").vw.setLeft((int)((views.get("btnproyecto").vw.getLeft() + views.get("btnproyecto").vw.getWidth())));
views.get("btnequipo").vw.setWidth((int)((100d / 100 * width) - ((views.get("btnproyecto").vw.getLeft() + views.get("btnproyecto").vw.getWidth()))));
views.get("btnequipoicono").vw.setLeft((int)((views.get("btnequipo").vw.getLeft() + views.get("btnequipo").vw.getWidth()/2) - (views.get("btnequipoicono").vw.getWidth() / 2)));
views.get("btnproyectoicono").vw.setLeft((int)((views.get("btnproyecto").vw.getLeft() + views.get("btnproyecto").vw.getWidth()/2) - (views.get("btnproyectoicono").vw.getWidth() / 2)));
views.get("lblline3").vw.setLeft((int)((views.get("btnequipo").vw.getLeft() + views.get("btnequipo").vw.getWidth()/2) - (views.get("lblline3").vw.getWidth() / 2)));
views.get("lblline1").vw.setLeft((int)((views.get("btnproyecto").vw.getLeft() + views.get("btnproyecto").vw.getWidth()/2) - (views.get("lblline1").vw.getWidth() / 2)));

}
}