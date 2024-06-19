package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymain{

public static void LS_800x1280_1(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnmapa").vw).setTextSize((float)(16d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btninformacion").vw).setTextSize((float)(16d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnanalizar").vw).setTextSize((float)(16d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnperfil").vw).setTextSize((float)(16d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnalerta").vw).setTextSize((float)(16d));

}
public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgicon").vw.setLeft((int)((50d / 100 * width) - (views.get("imgicon").vw.getWidth() / 2)));
views.get("btnanalizar").vw.setLeft((int)((50d / 100 * width) - (views.get("btnanalizar").vw.getWidth() / 2)));
views.get("btninformacion").vw.setLeft((int)((views.get("btnanalizar").vw.getLeft()) - (views.get("btninformacion").vw.getWidth())));
views.get("btnmapa").vw.setLeft((int)((views.get("btninformacion").vw.getLeft()) - (views.get("btnmapa").vw.getWidth())));
views.get("btnperfil").vw.setLeft((int)((views.get("btnanalizar").vw.getLeft() + views.get("btnanalizar").vw.getWidth())));
views.get("btnalerta").vw.setLeft((int)((views.get("btnperfil").vw.getLeft() + views.get("btnperfil").vw.getWidth())));
views.get("icomapa").vw.setLeft((int)((views.get("btnmapa").vw.getLeft() + views.get("btnmapa").vw.getWidth()/2) - (views.get("icomapa").vw.getWidth() / 2)));
views.get("icoperfil").vw.setLeft((int)((views.get("btnperfil").vw.getLeft() + views.get("btnperfil").vw.getWidth()/2) - (views.get("icoperfil").vw.getWidth() / 2)));
views.get("icoinformacion").vw.setLeft((int)((views.get("btninformacion").vw.getLeft() + views.get("btninformacion").vw.getWidth()/2) - (views.get("icoinformacion").vw.getWidth() / 2)));
views.get("icoanalizar").vw.setLeft((int)((views.get("btnanalizar").vw.getLeft() + views.get("btnanalizar").vw.getWidth()/2) - (views.get("icoanalizar").vw.getWidth() / 2)));
views.get("icoalerta").vw.setLeft((int)((views.get("btnalerta").vw.getLeft() + views.get("btnalerta").vw.getWidth()/2) - (views.get("icoalerta").vw.getWidth() / 2)));
views.get("btncirculo").vw.setLeft((int)((views.get("btnanalizar").vw.getLeft() + views.get("btnanalizar").vw.getWidth()/2) - (views.get("btncirculo").vw.getWidth() / 2)));
views.get("logo_load_gif").vw.setTop((int)((50d / 100 * height) - (views.get("logo_load_gif").vw.getHeight() / 2)));
views.get("logo_load_gif").vw.setLeft((int)((50d / 100 * width) - (views.get("logo_load_gif").vw.getWidth() / 2)));

}
}