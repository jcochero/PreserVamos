package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layregister{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lblcirculopos2").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcirculopos2").vw.getWidth() / 2)));
views.get("lblcirculopos1").vw.setLeft((int)((views.get("lblcirculopos2").vw.getLeft() + views.get("lblcirculopos2").vw.getWidth()/2)-((views.get("lblcirculopos2").vw.getWidth()))-(5d * scale) - (views.get("lblcirculopos1").vw.getWidth() / 2)));
views.get("lblcirculopos3").vw.setLeft((int)((views.get("lblcirculopos2").vw.getLeft() + views.get("lblcirculopos2").vw.getWidth()/2)+((views.get("lblcirculopos2").vw.getWidth()))+(5d * scale) - (views.get("lblcirculopos3").vw.getWidth() / 2)));

}
}