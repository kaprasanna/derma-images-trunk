package com.bh.derma.images.platform;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import com.bh.derma.images.internal.Activator;
import com.bh.derma.images.ui.util.StatusLineContribution;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

    private MenuManager showViewMenuMgr;
	private IContributionItem showViewItem;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	showViewMenuMgr = new MenuManager("Show View", "showView");
    	showViewItem = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    	MenuManager windowMenu = new MenuManager(
    						"&Window", IWorkbenchActionConstants.M_WINDOW);
    	showViewMenuMgr.add(showViewItem); 
    	windowMenu.add(showViewMenuMgr);
    	
//    	menuBar.add(windowMenu);
    }
    
    @Override
    protected void fillStatusLine(IStatusLineManager statusLine) {
    	IContributionItem statusItem =
    			new StatusLineContribution("some line");
//    					new StatusLineContributionItem("LoggedInStatus");
    	statusLine.add(statusItem);    	
    	Activator.getDefault().setStatusItem(statusItem);
    }
}
