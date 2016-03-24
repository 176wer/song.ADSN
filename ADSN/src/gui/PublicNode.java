package gui;

import core.DrawCurve;
import core.DrawCurve1;
import core.ZEllipse;

public class PublicNode {
    private static ZEllipse nodeBean;
    private static DrawCurve1 wenpane;
    private  static  String addr;

    public static String getAddr() {
        return addr;
    }

    public static void setAddr(String addr) {
        PublicNode.addr = addr;
    }

    public static DrawCurve1 getWenpane() {
        return wenpane;
    }

    public static void setWenpane(DrawCurve1 wenpane) {
        PublicNode.wenpane = wenpane;
    }

    public static void setNodeBean(ZEllipse node){
         nodeBean=node;
    }
    public static ZEllipse getNodeBean(){
        return  nodeBean;
    }
}
