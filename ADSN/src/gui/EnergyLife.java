/**
 * Project Name:ADSN
 * File Name:EnergyLife.java
 * Package Name:gui
 * Date:2015年12月24日下午4:42:19
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package gui;

import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * ClassName:EnergyLife <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年12月24日 下午4:42:19 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class EnergyLife extends JPanel {

	/**
	 * Create the panel.
	 */
	public EnergyLife() {
		setLayout(new GridLayout(2,0));
		
		JPanel panel = new JPanel();
		add(panel);
		
		JPanel panel_1 = new JPanel();
		add(panel_1);

	}

}

