package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mod_residuos_basico{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgriollanura").vw.setLeft((int)((33d / 100 * width) - (views.get("imgriollanura").vw.getWidth() / 2)));
views.get("imglaguna").vw.setLeft((int)((66d / 100 * width) - (views.get("imglaguna").vw.getWidth() / 2)));
views.get("lblllanura").vw.setLeft((int)((views.get("imgriollanura").vw.getLeft() + views.get("imgriollanura").vw.getWidth()/2) - (views.get("lblllanura").vw.getWidth() / 2)));
views.get("lbllaguna").vw.setLeft((int)((views.get("imglaguna").vw.getLeft() + views.get("imglaguna").vw.getWidth()/2) - (views.get("lbllaguna").vw.getWidth() / 2)));
views.get("lblrojo").vw.setLeft((int)((views.get("imgriollanura").vw.getLeft() + views.get("imgriollanura").vw.getWidth()/2) - (views.get("lblrojo").vw.getWidth() / 2)));
views.get("scrollusos").vw.setTop((int)((views.get("lblllanura").vw.getTop() + views.get("lblllanura").vw.getHeight())+(10d * scale)));

}
}