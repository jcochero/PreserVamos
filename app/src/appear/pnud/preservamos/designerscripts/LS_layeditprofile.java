package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_layeditprofile{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("lblsexo").vw.setTop((int)((views.get("txtfullname").vw.getTop() + views.get("txtfullname").vw.getHeight())+(10d * scale)));
views.get("lblfecha").vw.setTop((int)((views.get("lblsexo").vw.getTop() + views.get("lblsexo").vw.getHeight())+(10d * scale)));
views.get("lblemail").vw.setTop((int)((views.get("lblfecha").vw.getTop() + views.get("lblfecha").vw.getHeight())+(10d * scale)));
views.get("btneditsexo").vw.setTop((int)((views.get("lblsexo").vw.getTop())));
views.get("label2").vw.setTop((int)((views.get("lblsexo").vw.getTop() + views.get("lblsexo").vw.getHeight())-(2d * scale)));
views.get("lblfechadenacimiento").vw.setTop((int)((views.get("lblfecha").vw.getTop())));
views.get("label4").vw.setTop((int)((views.get("lblfecha").vw.getTop() + views.get("lblfecha").vw.getHeight())-(2d * scale)));
views.get("btneditfechadenacimiento").vw.setTop((int)((views.get("lblfechadenacimiento").vw.getTop())));
views.get("txtemail").vw.setTop((int)((views.get("lblemail").vw.getTop())));

}
}