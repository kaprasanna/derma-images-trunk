package com.bh.derma.images.platform;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        
        Rectangle size = Display.getDefault().getBounds();
        configurer.setInitialSize(new Point(size.width, size.height));
        
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setTitle("Derma Images"); //$NON-NLS-1$
        
        PlatformUI.getPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS, 
        		false); 
        
    }
    
    @Override
    public void postWindowCreate() {
    	Display display = getWindowConfigurer().getWindow().getWorkbench().getDisplay();
    	if(display.getMonitors().length > 0) {
    		Shell shell = getWindowConfigurer().getWindow().getShell();
    		
    		Monitor[] monitors = display.getMonitors();
    		for(Monitor monitor : monitors) {
    			if(!monitor.equals(display.getPrimaryMonitor())) {
    				Rectangle bounds = monitor.getBounds();
    				shell.setSize(bounds.width, bounds.height);
    				shell.setLocation(bounds.x, bounds.y);
    				shell.setMaximized(true);
    			}
    		}
    	} else {
    		super.postWindowCreate();
    	}    	
    }
}
