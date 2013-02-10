package patient.service.xml.internal;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.bh.derma.images.model.IPatient;
import com.bh.derma.images.model.ISeries;
import com.bh.derma.images.model.IStudy;
import com.bh.derma.images.service.IPatientService;

public class XMLPatientService implements IPatientService {

	@Override
	public IStatus saveNewPatient(IPatient patient) {
		System.out.println("Saved New patient");
		return Status.OK_STATUS;
	}

	@Override
	public IStatus saveEditedPatient(IPatient patient) {
		return null;
	}

	@Override
	public IStatus saveNewStudy(IStudy study, IPatient patient) {
		return null;
	}

	@Override
	public IStatus saveNewSeries(ISeries series, IStudy study, IPatient patient) {
		return null;
	}

	@Override
	public IPatient[] searchPatients(String name, String id) {
		return null;
	}

}
