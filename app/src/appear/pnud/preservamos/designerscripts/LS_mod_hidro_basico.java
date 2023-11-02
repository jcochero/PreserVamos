package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mod_hidro_basico{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imglimnimetro").vw.setLeft((int)((50d / 100 * width)-(views.get("imglimnimetro").vw.getWidth())/2d-(5d * scale) - (views.get("imglimnimetro").vw.getWidth() / 2)));
views.get("imgnolimnimetro").vw.setLeft((int)((50d / 100 * width)+(views.get("imglimnimetro").vw.getWidth())/2d+(5d * scale) - (views.get("imgnolimnimetro").vw.getWidth() / 2)));
views.get("lbllimnimetro").vw.setLeft((int)((views.get("imglimnimetro").vw.getLeft() + views.get("imglimnimetro").vw.getWidth()/2) - (views.get("lbllimnimetro").vw.getWidth() / 2)));
views.get("lblnolimnimetro").vw.setLeft((int)((views.get("imgnolimnimetro").vw.getLeft() + views.get("imgnolimnimetro").vw.getWidth()/2) - (views.get("lblnolimnimetro").vw.getWidth() / 2)));
views.get("lblrojo").vw.setLeft((int)((views.get("imglimnimetro").vw.getLeft() + views.get("imglimnimetro").vw.getWidth()/2) - (views.get("lblrojo").vw.getWidth() / 2)));
views.get("imgejlimnimetro").vw.setLeft((int)((50d / 100 * width) - (views.get("imgejlimnimetro").vw.getWidth() / 2)));

}
}