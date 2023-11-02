package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_alertas_tipo_alertas{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgpeces").vw.setLeft((int)((50d / 100 * width)-((views.get("imgpeces").vw.getWidth())/2d)-(10d * scale) - (views.get("imgpeces").vw.getWidth() / 2)));
views.get("imgderrame").vw.setLeft((int)((50d / 100 * width)+((views.get("imgderrame").vw.getWidth())/2d)+(10d * scale) - (views.get("imgderrame").vw.getWidth() / 2)));
views.get("lblderrame").vw.setLeft((int)((views.get("imgderrame").vw.getLeft() + views.get("imgderrame").vw.getWidth()/2) - (views.get("lblderrame").vw.getWidth() / 2)));
views.get("lblpeces").vw.setLeft((int)((views.get("imgpeces").vw.getLeft() + views.get("imgpeces").vw.getWidth()/2) - (views.get("lblpeces").vw.getWidth() / 2)));
views.get("imgalgas").vw.setLeft((int)((views.get("imgpeces").vw.getLeft() + views.get("imgpeces").vw.getWidth()/2) - (views.get("imgalgas").vw.getWidth() / 2)));
views.get("lblalgas").vw.setLeft((int)((views.get("lblpeces").vw.getLeft() + views.get("lblpeces").vw.getWidth()/2) - (views.get("lblalgas").vw.getWidth() / 2)));
views.get("imgcaza").vw.setLeft((int)((views.get("imgderrame").vw.getLeft() + views.get("imgderrame").vw.getWidth()/2) - (views.get("imgcaza").vw.getWidth() / 2)));
views.get("lblcaza").vw.setLeft((int)((views.get("lblderrame").vw.getLeft() + views.get("lblderrame").vw.getWidth()/2) - (views.get("lblcaza").vw.getWidth() / 2)));
views.get("imgdidymo").vw.setLeft((int)((views.get("imgalgas").vw.getLeft() + views.get("imgalgas").vw.getWidth()/2) - (views.get("imgdidymo").vw.getWidth() / 2)));
views.get("lbldidymo").vw.setLeft((int)((views.get("imgalgas").vw.getLeft() + views.get("imgalgas").vw.getWidth()/2) - (views.get("lbldidymo").vw.getWidth() / 2)));
views.get("imgfumigaciones").vw.setLeft((int)((views.get("imgalgas").vw.getLeft() + views.get("imgalgas").vw.getWidth()/2) - (views.get("imgfumigaciones").vw.getWidth() / 2)));
views.get("lblfumigaciones").vw.setLeft((int)((views.get("lblalgas").vw.getLeft() + views.get("lblalgas").vw.getWidth()/2) - (views.get("lblfumigaciones").vw.getWidth() / 2)));
views.get("imgtomasilegales").vw.setLeft((int)((views.get("imgcaza").vw.getLeft() + views.get("imgcaza").vw.getWidth()/2) - (views.get("imgtomasilegales").vw.getWidth() / 2)));
views.get("lbltomasilegales").vw.setLeft((int)((views.get("lblcaza").vw.getLeft() + views.get("lblcaza").vw.getWidth()/2) - (views.get("lbltomasilegales").vw.getWidth() / 2)));
views.get("imgotraalerta").vw.setLeft((int)((views.get("imgcaza").vw.getLeft() + views.get("imgcaza").vw.getWidth()/2) - (views.get("imgotraalerta").vw.getWidth() / 2)));
views.get("lblotraalerta").vw.setLeft((int)((views.get("imgcaza").vw.getLeft() + views.get("imgcaza").vw.getWidth()/2) - (views.get("lblotraalerta").vw.getWidth() / 2)));

}
}