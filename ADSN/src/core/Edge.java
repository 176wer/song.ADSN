/**
 * Project Name:ADSN
 * File Name:Edge.java
 * Package Name:core
 * Date:2016年3月3日上午8:40:55
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package core;

/**
 * ClassName:Edge <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年3月3日 上午8:40:55 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
class Edge {
    //true 为线的开始端，false为线的结束端
    public boolean flag = true;
    private ZEllipse startNode;
    private ZEllipse endNode;

    public Edge(ZEllipse startNode, ZEllipse endNode) {

        this.startNode = startNode;
        this.endNode = endNode;


    }

    public float getStartX() {
    	 
    		 double StartRadius = (double) startNode.width / 2;
    	      
    	        double EndRadius = (double) endNode.width / 2;
    	        double c0x = startNode.getX() + StartRadius;
    	        double c1x = endNode.getX() + EndRadius;
    	        double c0y = startNode.getY() + StartRadius;
    	        double c1y = endNode.getY() + EndRadius;

    	        double distance = Math.sqrt((c0x - c1x) * (c0x - c1x) + (c0y - c1y) * (c0y - c1y));
    	        double c0ratio = StartRadius / distance;

    	        return (float) (c0x + (c1x - c0x) * c0ratio);




        // return (float)startNode.getX()+(float)startNode.width/2;


    }

    public float getStartY() {
        double StartRadius = (double) startNode.width / 2;
        double EndRadius = (double) endNode.width / 2;
        double c0x = startNode.getX() + StartRadius;
        double c1x = endNode.getX() + EndRadius;
        double c0y = startNode.getY() + StartRadius;
        double c1y = endNode.getY() + EndRadius;

        double distance = Math.sqrt((c0x - c1x) * (c0x - c1x) + (c0y - c1y) * (c0y - c1y));
        double c0ratio = StartRadius / distance;
        return (float) (c0y + (c1y - c0y) * c0ratio);
    }

    public float getEndX() {
        double StartRadius = (double) startNode.width / 2;
        double EndRadius = (double) endNode.width / 2;
        double c0x = startNode.getX() + StartRadius;
        double c1x = endNode.getX() + EndRadius;
        double c0y = startNode.getY() + StartRadius;
        double c1y = endNode.getY() + EndRadius;

        double distance = Math.sqrt((c0x - c1x) * (c0x - c1x) + (c0y - c1y) * (c0y - c1y));
        double c1ratio = EndRadius / distance;
        return (float) (c1x - (c1x - c0x) * c1ratio);
    }

    public float getEndY() {
        double StartRadius = (double) startNode.width / 2;
        double EndRadius = (double) endNode.width / 2;
        double c0x = startNode.getX() + StartRadius;
        double c1x = endNode.getX() + EndRadius;
        double c0y = startNode.getY() + StartRadius;
        double c1y = endNode.getY() + EndRadius;

        double distance = Math.sqrt((c0x - c1x) * (c0x - c1x) + (c0y - c1y) * (c0y - c1y));
        double c1ratio = EndRadius / distance;
        return (float) (c1y - (c1y - c0y) * c1ratio);
    }
}


