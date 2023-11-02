package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mod_hidro_container{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lblcirculopos2").vw.setLeft((int)((50d / 100 * width)-(5d * scale) - (views.get("lblcirculopos2").vw.getWidth())));
views.get("lblcirculopos1").vw.setLeft((int)((views.get("lblcirculopos2").vw.getLeft())-(5d * scale) - (views.get("lblcirculopos1").vw.getWidth())));
views.get("lblcirculopos3").vw.setLeft((int)((views.get("lblcirculopos2").vw.getLeft() + views.get("lblcirculopos2").vw.getWidth())+(5d * scale)));
views.get("lblcirculopos4").vw.setLeft((int)((views.get("lblcirculopos3").vw.getLeft() + views.get("lblcirculopos3").vw.getWidth())+(5d * scale)));

}
}