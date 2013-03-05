package patient.service.xml.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bh.derma.images.model.IPatient;
import com.bh.derma.images.model.ISeries;
import com.bh.derma.images.model.IStudy;
import com.bh.derma.images.model.PatientFactory;
import com.bh.derma.images.model.SeriesFactory;
import com.bh.derma.images.model.StudyFactory;
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
	private static final String STUDY_ELEMENT_ATTRIBUTE_STUDY_ID = "studyID";
	

	// series element and attributes
	private static final String SERIES_ELEMENT = "series";
	private static final String SERIES_ELEMENT_ATTRIBUTE_SERIES_ID = "seriesID";
	private static final String SERIES_ELEMENT_ATTRIBUTE_NAME = "name";
	private static final String SERIES_ELEMENT_ATTRIBUTE_NOTES = "notes";
	private static final String SERIES_ELEMENT_ATTRIBUTE_PARENT_STUDY_ID = "parentStudyID";
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
			newStudyElement.setAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDY_ID, study.getStudyID());

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
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_NAME, series.getSeriesName());
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_NOTES, series.getNotes());
			newSeriesElement.setAttribute(SERIES_ELEMENT_ATTRIBUTE_PARENT_STUDY_ID, series.getParentStudyID());
			
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
			
			// XXX update number of series field in the studies xml for this series

			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public List<IPatient> searchPatients(String name, String id) throws Exception {
		String dirPath = getDirPath(PATIENTS);		
		File patientDir = new File(dirPath);
		if(!patientDir.exists()) {
			LOGGER.info("Patients dir doesn't exist : " + patientDir.getAbsolutePath());
			throw new Exception("No patients added so far. " + patientDir.getAbsolutePath() + " doesn't exist");
		}
		
		// find if patients.xml doesn't exist
		File patientsFile = new File(dirPath.concat(File.separator).concat(PATIENTS).concat(FILE_EXTENSION));
		if(! patientsFile.exists()) {
			LOGGER.info("Patients file doesn't exist : " + patientsFile.getAbsolutePath());
			throw new Exception("No patients added so far. " + patientsFile.getAbsolutePath() + " doesn't exist");			
		}
		
		Document doc = parseXMLFile(patientsFile);
		
		if(doc != null) {
			List<IPatient> searchResultsPatient = new ArrayList<IPatient>();
			
			String xmlQuery = "//patient[starts-with(@name,'" + name + "')]";
			NodeList nodes = queryXML(doc, xmlQuery);
			for(int i = 0; i < nodes.getLength(); i++) {
				Element patientElement = (Element) nodes.item(i);
				String searchResultPatientName =
						patientElement.getAttribute(PATIENT_ELEMENT_ATTRIBUTE_NAME);
				String searchResultPatientID =
						patientElement.getAttribute(PATIENT_ELEMENT_ATTRIBUTE_ID);
				IPatient patient = PatientFactory.getInstance().
						create(searchResultPatientID, searchResultPatientName, null);
				searchResultsPatient.add(patient);
			}
			
			return searchResultsPatient;
		}	
		
		return null;
	}
	
	@Override
	public List<IStudy> getStudiesForPatient(IPatient selectedPatient) throws Exception {
		String dirPath = getDirPath(STUDIES);		
		File studyDir = new File(dirPath);
		if(!studyDir.exists()) {
			LOGGER.info("Studies dir doesn't exist : " + studyDir.getAbsolutePath());
			throw new Exception("No studies added so far. " + studyDir.getAbsolutePath() + " doesn't exist");
		}
		
		// find if studies.xml doesn't exist
		File studiesFile = new File(dirPath.concat(File.separator).concat(STUDIES).concat(FILE_EXTENSION));
		if(! studiesFile.exists()) {
			LOGGER.info("Studies file doesn't exist : " + studiesFile.getAbsolutePath());
			throw new Exception("No Studies added so far. " + studiesFile.getAbsolutePath() + " doesn't exist");			
		}
		
		Document doc = parseXMLFile(studiesFile);
		
		if(doc != null) {
			List<IStudy> searchResultsStudies = new ArrayList<IStudy>();
			
			String xmlQuery = "//study[@" + STUDY_ELEMENT_ATTRIBUTE_PATIENTID + "='" + selectedPatient.getId() + "']";
			NodeList nodes = queryXML(doc, xmlQuery);
			for(int i = 0; i < nodes.getLength(); i++) {
				Element studyElement = (Element) nodes.item(i);
				
				IStudy study = StudyFactory.getInstance().create(null);
				study.setPatientID(studyElement.getAttribute(STUDY_ELEMENT_ATTRIBUTE_PATIENTID));
				study.setStudyID(studyElement.getAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDY_ID));
				study.setStudyName(studyElement.getAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDYNAME));
				study.setStudyType(studyElement.getAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDYTYPE));
				study.setNumberOfSeries(Integer.parseInt(studyElement.getAttribute(STUDY_ELEMENT_ATTRIBUTE_NUMBEROFSERIES)));
				study.setStudyDate(parseDateFomString(studyElement.getAttribute(STUDY_ELEMENT_ATTRIBUTE_STUDYDATE)));
				
				searchResultsStudies.add(study);
			}
			
			return searchResultsStudies;
		}	
		return null;
	}	

	@Override
	public List<ISeries> getSeriesForStudy(IStudy selectedStudy) throws Exception {
		String dirPath = getDirPath(SERIES);		
		File seriesDir = new File(dirPath);
		if(!seriesDir.exists()) {
			LOGGER.info("Series dir doesn't exist : " + seriesDir.getAbsolutePath());
			throw new Exception("No series added so far. " + seriesDir.getAbsolutePath() + " doesn't exist");
		}
		
		// find if studies.xml doesn't exist
		File seriesFile = new File(dirPath.concat(File.separator).concat(SERIES).concat(FILE_EXTENSION));
		if(! seriesFile.exists()) {
			LOGGER.info("Series file doesn't exist : " + seriesFile.getAbsolutePath());
			throw new Exception("No Series added so far. " + seriesFile.getAbsolutePath() + " doesn't exist");			
		}
		
		Document doc = parseXMLFile(seriesFile);
		
		if(doc != null) {
			List<ISeries> searchResultsStudies = new ArrayList<ISeries>();
			
			String xmlQuery = "//series[@" + SERIES_ELEMENT_ATTRIBUTE_PARENT_STUDY_ID + "='" + selectedStudy.getStudyID() + "']";
			NodeList nodes = queryXML(doc, xmlQuery);
			for(int i = 0; i < nodes.getLength(); i++) {
				Element seriesElement = (Element) nodes.item(i);
				
				ISeries series = SeriesFactory.getInstance().create(null);
				series.setSeriesName(seriesElement.getAttribute(SERIES_ELEMENT_ATTRIBUTE_NAME));
				series.setNotes(seriesElement.getAttribute(SERIES_ELEMENT_ATTRIBUTE_NOTES));
				series.setParentStudyID(seriesElement.getAttribute(SERIES_ELEMENT_ATTRIBUTE_PARENT_STUDY_ID));
				series.setSeriesID(seriesElement.getAttribute(SERIES_ELEMENT_ATTRIBUTE_SERIES_ID));
				series.setSeriesTime(parseDateFomString(seriesElement.getAttribute(SERIES_ELEMENT_ATTRIBUTE_SERIES_TIME)));

				// get the photos sub element
				NodeList photosNodes = seriesElement.getElementsByTagName(SERIES_SUB_ELEMENT_PHOTOS);
				if(photosNodes.getLength() > 1 || photosNodes.getLength() ==0) {
					// zero or more than one set of photos for one series. Something's gone wrong! Log
				} else {
					List<Object> photos = new ArrayList<Object>();
					
					Element photosNodeElement = (Element) photosNodes.item(0);
					Object photoObject = photosNodeElement.getAttribute(SERIES_SUB_ELEMENT_PHOTOS_SUB_ELEMENT_PHOTO_ATTRIBUTE_PATH);
					photos.add(photoObject);
					
					series.setPhotos(photos);
				}
				searchResultsStudies.add(series);
			}
			
			return searchResultsStudies;
		}	
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
	
	/**
	 * Fires xpath query on the doc with given xpath-expression and returns the
	 * result.
	 *
	 * @param doc
	 * @param xpatExpression
	 * @return
	 */
	private NodeList queryXML(Document doc, String xpatExpression) {
	        NodeList nodes = null;

	        XPathFactory factory = XPathFactory.newInstance();
	        XPath xpath = factory.newXPath();
	        try {
	                XPathExpression expr =  xpath.compile(xpatExpression);
	                Object result = expr.evaluate(doc, XPathConstants.NODESET);

	                nodes = (NodeList) result;
	        } catch (XPathExpressionException e) {
	                e.printStackTrace();
	        }

	        return nodes;
	}
	
	private Date parseDateFomString(String dateStr) {
        Date dateTime = null;
        if(!dateStr.equals("")) {
        	long dateAsLongFromString = Long.parseLong(dateStr);
        	try {
        		dateTime = new Date(dateAsLongFromString);
        	} catch (Exception e) {
        		LOGGER.info("Date couldn't be parsed");
        	}
        }
        return dateTime;
	}
}