package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layreporte_basicos{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgrio").vw.setLeft((int)((50d / 100 * width)-((views.get("imgrio").vw.getWidth())/2d)-(5d * scale) - (views.get("imgrio").vw.getWidth() / 2)));
views.get("imglaguna").vw.setLeft((int)((50d / 100 * width)+((views.get("imglaguna").vw.getWidth())/2d)+(5d * scale) - (views.get("imglaguna").vw.getWidth() / 2)));
views.get("lbllaguna").vw.setLeft((int)((views.get("imglaguna").vw.getLeft() + views.get("imglaguna").vw.getWidth()/2) - (views.get("lbllaguna").vw.getWidth() / 2)));
views.get("lblrio").vw.setLeft((int)((views.get("imgrio").vw.getLeft() + views.get("imgrio").vw.getWidth()/2) - (views.get("lblrio").vw.getWidth() / 2)));
views.get("imgllanura").vw.setLeft((int)((views.get("imgrio").vw.getLeft() + views.get("imgrio").vw.getWidth()/2) - (views.get("imgllanura").vw.getWidth() / 2)));
views.get("imgsierra").vw.setLeft((int)((views.get("imglaguna").vw.getLeft() + views.get("imglaguna").vw.getWidth()/2) - (views.get("imgsierra").vw.getWidth() / 2)));
views.get("lblllanura").vw.setLeft((int)((views.get("imgrio").vw.getLeft() + views.get("imgrio").vw.getWidth()/2) - (views.get("lblllanura").vw.getWidth() / 2)));
views.get("lblsierra").vw.setLeft((int)((views.get("imgsierra").vw.getLeft() + views.get("imgsierra").vw.getWidth()/2) - (views.get("lblsierra").vw.getWidth() / 2)));

}
}