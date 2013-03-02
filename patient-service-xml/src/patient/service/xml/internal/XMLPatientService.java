package patient.service.xml.internal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.bh.derma.images.model.IPatient;
import com.bh.derma.images.model.ISeries;
import com.bh.derma.images.model.IStudy;
import com.bh.derma.images.service.IPatientService;

public class XMLPatientService implements IPatientService {

	private static final String FILE_EXTENSION = ".xml";
	private static final String PATIENTS = "patients";
	private static final String STUDIES = "studies";
	private static final String SERIES = "series";
	
	// patient element and attributes
	private static final String PATIENT_ELEMENT = "patient";
	private static final String PATIENT_ELEMENT_ATTRIBUTE_NAME = "name";
	private static final String PATIENT_ELEMENT_ATTRIBUTE_ID = "id";
	
	// study element and attributes
	private static final String STUDY_ELEMENT = "study";
	private static final String STUDY_ELEMENT_ATTRIBUTE_NUMBEROFSERIES = "numberOfSeries";
	private static final String STUDY_ELEMENT_ATTRIBUTE_STUDYNAME = "studyName";
	private static final String STUDY_ELEMENT_ATTRIBUTE_STUDYDATE = "studyDate";
	private static final String STUDY_ELEMENT_ATTRIBUTE_STUDYTYPE = "studyType";
	private static final String STUDY_ELEMENT_ATTRIBUTE_PATIENTID = "patientID";
	

	// series element and attributes
	private static final String SERIES_ELEMENT = "series";
	private static final String SERIES_ELEMENT_ATTRIBUTE_SERIES_ID = "seriesID";
	private static final String SERIES_ELEMENT_ATTRIBUTE_NAME = "name";
	private static final String SERIES_ELEMENT_ATTRIBUTE_NOTES = "notes";
	private static final String SERIES_ELEMENT_ATTRIBUTE_PARENT_STUDY_ID = "parentStudy";
	private static final String SERIES_ELEMENT_ATTRIBUTE_SERIES_TIME = "seriesTime";
	private static final String SERIES_SUB_ELEMENT_PHOTOS = "photos";
	private static final String SERIES_SUB_ELEMENT_PHOTOS_SUB_ELEMENT_PHOTO = "photo";
	private static final String SERIES_SUB_ELEMENT_PHOTOS_SUB_ELEMENT_PHOTO_ATTRIBUTE_PATH = "path";
	
	private final Log LOGGER = LogFactory.getLog(this.getClass().getName());
	
	private static String tmpDir = System.getProperty("java.io.tmpdir");
	public static final String OSGI_INSTANCE_AREA = System.getProperty("osgi.instance.area", tmpDir).replace("file:/", "");
	
	@Override
	public IStatus saveNewPatient(IPatient patient) throws IOException {
		// locate patients.xml and add this patient's entry into it.
		String dirPath = getDirPath(PATIENTS);
		
		
		// create the dir if it isn't there
		File patientDir = new File(dirPath);
		if(!patientDir.exists()) {
			boolean dirCreation = patientDir.mkdir();
			if(!dirCreation) {
				LOGGER.fatal("Couldn't create patients dir : " + patientDir.getAbsolutePath());
				throw new IOException("Couldn't create patients dir : " + patientDir.getAbsolutePath());
			}
		}
		
		Document doc = null;
		
		// if patients.xml doesn't exist create new. also create new DOM document
		File patientsFile = new File(dirPath.concat(File.separator).concat(PATIENTS).concat(FILE_EXTENSION));
		if(! patientsFile.exists()) {
			boolean fileCreation = patientsFile.createNewFile();
			if(!fileCreation) {
				LOGGER.fatal("Couldn't create patients file : " + patientDir.getAbsolutePath());
				throw new IOException("Couldn't create patients file : " + patientDir.getAbsolutePath());
			}
			try {
				doc = createNewXMLDoc(PATIENTS);
			} catch (ParserConfigurationException e) {
				throw new RuntimeException("parse configuration exception", e);
			}
		} else {
			// read the existing XML into a DOM
			doc = parseXMLFile(patientsFile);
		}
		
		// add new element to the XML doc
		if(doc != null) {			
			// create element for new patient			
			Element newPatientElement = doc.createElement(PATIENT_ELEMENT);
			newPatientElement.setAttribute(PATIENT_ELEMENT_ATTRIBUTE_NAME, patient.getName());
			newPatientElement.setAttribute(PATIENT_ELEMENT_ATTRIBUTE_ID, patient.getId());
			
			// add the new patient element to the root
			Element rootPatientElement = doc.getDocumentElement();
			rootPatientElement.appendChild(newPatientElement);
			
			
			persist(doc, patientsFile);
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;		
	}

	@Override
	public IStatus saveEditedPatient(IPatient patient) {
		return null;
	}

	@Override
	public IStatus saveNewStudy(IStudy study) throws IOException {
		// locate studies.xml and add this patient's entry into it.
		String studiesDirPath = getDirPath(STUDIES);

		// create the dir if it isn't there
		File studiesDir = new File(studiesDirPath);
		if(!studiesDir.exists()) {
			boolean dirCreation = studiesDir.mkdir();
			if(!dirCreation) {
				LOGGER.fatal("Couldn't create studies dir : " + studiesDir.getAbsolutePath());
				throw new IOException("Couldn't create studies dir : " + studiesDir.getAbsolutePath());
			}
		}
		
		Document doc = null;

		// if studies.xml doesn't exist create new. also create new DOM document
		File studiesFile = new File(studiesDirPath.concat(File.separator).concat(STUDIES).concat(FILE_EXTENSION));
		if(! studiesFile.exists()) {
			boolean fileCreation = studiesFile.createNewFile();
			if(!fileCreation) {
				LOGGER.fatal("Couldn't create studies file : " + studiesDir.getAbsolutePath());
				throw new IOException("Couldn't create studies file : " + studiesDir.getAbsolutePath());
			}
			try {
				doc = createNewXMLDoc(STUDIES);
			} catch (ParserConfigurationException e) {
				throw new RuntimeException("parse configuration exception", e);
			}
		} else {
			// read the existing XML into a DOM
			doc = parseXMLFile(studiesFile);
		}

		// add new element to the XML doc
		if(doc != null) {			
			// create element for new patient			
			Element newStudyElement = doc.createElement(STUDY_ELEMENT);
			newStudyElement.setAttribute(STUDY_ELEMENT_ATTRIBUTE_NUMBEROFSERIES, String.valueOf(study.getNumberOfSeries()));
			newStudyElement.setAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDYNAME, study.getStudyName());
			newStudyElement.setAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDYTYPE, study.getStudyType());
			newStudyElement.setAttribute(STUDY_ELEMENT_ATTRIBUTE_PATIENTID, study.getPatientID());

			// store date as a long.
			long dateAsLong = study.getStudyDate().getTime();
			String studyDate = String.valueOf(dateAsLong);
			newStudyElement.setAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDYDATE, studyDate);

			// add the new study element to the root
			Element rootPatientElement = doc.getDocumentElement();
			rootPatientElement.appendChild(newStudyElement);

			persist(doc, studiesFile);

			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public IStatus saveNewSeries(ISeries series) throws IOException {
		// locate series.xml and add this patient's entry into it.
		String seriesDirPath = getDirPath(SERIES);

		// create the dir if it isn't there
		File seriesDir = new File(seriesDirPath);
		if(!seriesDir.exists()) {
			boolean dirCreation = seriesDir.mkdir();
			if(!dirCreation) {
				LOGGER.fatal("Couldn't create studies dir : " + seriesDir.getAbsolutePath());
				throw new IOException("Couldn't create studies dir : " + seriesDir.getAbsolutePath());
			}
		}
		
		Document doc = null;

		// if series.xml doesn't exist create new. also create new DOM document
		File seriesFile = new File(seriesDirPath.concat(File.separator).concat(SERIES).concat(FILE_EXTENSION));
		if(! seriesFile.exists()) {
			boolean fileCreation = seriesFile.createNewFile();
			if(!fileCreation) {
				LOGGER.fatal("Couldn't create series file : " + seriesDir.getAbsolutePath());
				throw new IOException("Couldn't create series file : " + seriesDir.getAbsolutePath());
			}
			try {
				doc = createNewXMLDoc(SERIES);
			} catch (ParserConfigurationException e) {
				throw new RuntimeException("parse configuration exception", e);
			}
		} else {
			// read the existing XML into a DOM
			doc = parseXMLFile(seriesFile);
		}

		// add new element to the XML doc
		if(doc != null) {			
			// create element for new patient			
			Element newSeriesElement = doc.createElement(SERIES_ELEMENT);
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_SERIES_ID, series.getSeriesID());
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_NAME, series.getName());
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_NOTES, series.getNotes());
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_PARENT_STUDY_ID, series.getParentStudy().getStudyID());
			
			Element newSeriesPhotosElement = doc.createElement(SERIES_SUB_ELEMENT_PHOTOS);
			
			for(Object photoPath : series.getPhotos()) {
				String path = (String) photoPath;
				Element newSeriesPhotosPhotoElement = doc.createElement(SERIES_SUB_ELEMENT_PHOTOS_SUB_ELEMENT_PHOTO);
				newSeriesPhotosPhotoElement.setAttribute(SERIES_SUB_ELEMENT_PHOTOS_SUB_ELEMENT_PHOTO_ATTRIBUTE_PATH, path);
				
				newSeriesPhotosElement.appendChild(newSeriesPhotosPhotoElement);
			}
			
			newSeriesElement.appendChild(newSeriesPhotosElement);

			// store date as a long.
			long dateAsLong = series.getSeriesTime().getTime();
			String seriesTime = String.valueOf(dateAsLong);
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_SERIES_TIME, seriesTime); 

			// add the new series element to the root
			Element rootPatientElement = doc.getDocumentElement();
			rootPatientElement.appendChild(newSeriesElement);

			persist(doc, seriesFile);

			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public IPatient[] searchPatients(String name, String id) {
		return null;
	}

	private String getDirPath(String subDirName) {
		return OSGI_INSTANCE_AREA.concat(File.separator).concat(subDirName);
	}
	
	private Document createNewXMLDoc(String rootName) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();
		Document doc = impl.createDocument(null, null, null);

		Element root = doc.createElement(rootName);
		doc.appendChild(root);
		return doc;
	}
	
	/**
	 * Serializes the given document to the given file.
	 *
	 * @param doc
	 * @param xmlFile
	 */
	private IStatus persist(Document doc, File xmlFile) {
		DOMSource domSource = new DOMSource(doc);
		Result result = new StreamResult(xmlFile);

		try {
			Transformer xformer =
					TransformerFactory.newInstance().newTransformer();
			xformer.setOutputProperty(OutputKeys.INDENT, "yes");
			xformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			xformer.transform(domSource, result);
			return Status.OK_STATUS;
		} catch (TransformerConfigurationException e) {
			LOGGER.fatal(e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			LOGGER.fatal(e.getMessage());
		} catch (TransformerException e) {
			LOGGER.fatal(e.getMessage());
		}
		return Status.CANCEL_STATUS;
	}
	
	/**
	 * A helper method that parses the given xml file and returns the
	 * org.w3c.dom.Document associated with it.
	 * @param icasexmlFile
	 * @return
	 */
	private Document parseXMLFile(File icasexmlFile) {
		Document doc = null;
		try {
			DocumentBuilderFactory factory =
					DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(icasexmlFile);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}
}