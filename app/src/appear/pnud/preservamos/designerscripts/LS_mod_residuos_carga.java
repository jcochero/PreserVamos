package appear.pnud.preservamos.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mod_residuos_carga{

public static void LS_general(anywheresoftware.b4a.BA ba, android.view.View parent, anywheresoftware.b4a.keywords.LayoutValues lv, java.util.Map props,
java.util.Map<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) throws Exception {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btnplasticos").vw.setWidth((int)((100d*(100d / 100 * width))/320d));
views.get("btnplasticos").vw.setHeight((int)((views.get("btnplasticos").vw.getWidth())));
views.get("btnplasticos").vw.setLeft((int)((views.get("btncarton").vw.getLeft())-((5d*(100d / 100 * width))/320d) - (views.get("btnplasticos").vw.getWidth())));
views.get("btnplasticos").vw.setTop((int)((30d / 100 * height)));
views.get("btncarton").vw.setWidth((int)((100d*(100d / 100 * width))/320d));
views.get("btnmetales").vw.setWidth((int)((100d*(100d / 100 * width))/320d));
views.get("btnotros").vw.setWidth((int)((100d*(100d / 100 * width))/320d));
views.get("btnvidrios").vw.setWidth((int)((100d*(100d / 100 * width))/320d));
views.get("btnplasticos").vw.setHeight((int)((views.get("btnplasticos").vw.getWidth())));
views.get("btncarton").vw.setHeight((int)((views.get("btnplasticos").vw.getWidth())));
views.get("btnmetales").vw.setHeight((int)((views.get("btnplasticos").vw.getWidth())));
views.get("btnotros").vw.setHeight((int)((views.get("btnplasticos").vw.getWidth())));
views.get("btnvidrios").vw.setHeight((int)((views.get("btnplasticos").vw.getWidth())));
views.get("btncarton").vw.setLeft((int)((50d / 100 * width) - (views.get("btncarton").vw.getWidth() / 2)));
views.get("btnplasticos").vw.setLeft((int)((views.get("btncarton").vw.getLeft())-((5d*(100d / 100 * width))/320d) - (views.get("btnplasticos").vw.getWidth())));
views.get("btnmetales").vw.setLeft((int)((views.get("btncarton").vw.getLeft() + views.get("btncarton").vw.getWidth())+((5d*(100d / 100 * width))/320d)));
views.get("btnvidrios").vw.setLeft((int)((views.get("btncarton").vw.getLeft())-((5d*(100d / 100 * width))/320d) - (views.get("btnvidrios").vw.getWidth() / 2)));
views.get("btnotros").vw.setLeft((int)((views.get("btncarton").vw.getLeft() + views.get("btncarton").vw.getWidth())+((5d*(100d / 100 * width))/320d) - (views.get("btnotros").vw.getWidth() / 2)));
views.get("btnplasticos").vw.setTop((int)((30d / 100 * height)));
views.get("btncarton").vw.setTop((int)((30d / 100 * height)));
views.get("btnmetales").vw.setTop((int)((30d / 100 * height)));
views.get("label1").vw.setLeft((int)((views.get("btnplasticos").vw.getLeft() + views.get("btnplasticos").vw.getWidth()/2) - (views.get("label1").vw.getWidth() / 2)));
views.get("label2").vw.setLeft((int)((views.get("btncarton").vw.getLeft() + views.get("btncarton").vw.getWidth()/2) - (views.get("label2").vw.getWidth() / 2)));
views.get("label3").vw.setLeft((int)((views.get("btnmetales").vw.getLeft() + views.get("btnmetales").vw.getWidth()/2) - (views.get("label3").vw.getWidth() / 2)));
views.get("label4").vw.setLeft((int)((views.get("btnvidrios").vw.getLeft() + views.get("btnvidrios").vw.getWidth()/2) - (views.get("label4").vw.getWidth() / 2)));
views.get("label5").vw.setLeft((int)((views.get("btnotros").vw.getLeft() + views.get("btnotros").vw.getWidth()/2) - (views.get("label5").vw.getWidth() / 2)));
views.get("label1").vw.setTop((int)((views.get("btnplasticos").vw.getTop() + views.get("btnplasticos").vw.getHeight())));
views.get("label2").vw.setTop((int)((views.get("btncarton").vw.getTop() + views.get("btncarton").vw.getHeight())));
views.get("label3").vw.setTop((int)((views.get("btnmetales").vw.getTop() + views.get("btnmetales").vw.getHeight())));
views.get("btnvidrios").vw.setTop((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(5d * scale)));
views.get("btnotros").vw.setTop((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(5d * scale)));
views.get("label4").vw.setTop((int)((views.get("btnvidrios").vw.getTop() + views.get("btnvidrios").vw.getHeight())));
views.get("label5").vw.setTop((int)((views.get("btnotros").vw.getTop() + views.get("btnotros").vw.getHeight())));

}
}