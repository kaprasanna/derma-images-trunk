package com.bh.derma.images.internal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "derma-images"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static IViewPart getView(IWorkbenchWindow window, String viewId) {
	    IViewReference[] refs = window.getActivePage().getViewReferences();
	    for (IViewReference viewReference : refs) {
	        if (viewReference.getId().equals(viewId)) {
	            return viewReference.getView(true);
	        }
	    }
	    return null;
	}
	
	/**
	 * IWorkbenchPage page = 
Workbench.getInstance().getActiveWorkbenchWindow().getActivePage()

Perspective perspective = page.getPerspective();

String viewId = "myViewId"; //defined by you

//get the reference for your viewId
IViewReference ref = page.findViewReference(viewId);

//release the view
perspective.getViewFactory.releaseView(ref);
	 */
}
