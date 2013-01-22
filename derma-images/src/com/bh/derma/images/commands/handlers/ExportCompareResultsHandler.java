package com.bh.derma.images.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.bh.derma.images.ui.CompareResultsView;
import com.bh.derma.images.ui.util.Util;

public class ExportCompareResultsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		CompareResultsView compareResultsView = Util.getCompareResultsView();
		
		if(compareResultsView == null) {
			// TODO log
			return null;
		}
		
		IStatus status = compareResultsView.exportCompareResults();
		if(status.equals(Status.OK_STATUS)) {
			// TODO log success
		} else {
			// TODO log failure
		}
		
		return null;
	}
}
