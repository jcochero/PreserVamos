package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layload{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("logo_load_gif").vw.setTop((int)((50d / 100 * height) - (views.get("logo_load_gif").vw.getHeight() / 2)));
views.get("logo_load_gif").vw.setLeft((int)((50d / 100 * width) - (views.get("logo_load_gif").vw.getWidth() / 2)));

}
}