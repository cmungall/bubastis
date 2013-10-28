package yourpackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;

import uk.ac.ebi.efo.bubastis.CompareOntologies;
import uk.ac.ebi.efo.bubastis.OWLClassAxiomsInfo;
import uk.ac.ebi.efo.bubastis.OntologyChangesBean;



/**
 * This class shows an example of how to interact with the bubastis core library
 * The example below shows how to run a diff and write the result in xml to a file
 * It also shows how to work directly with the results objects in memory using the
 * OntologyChangesBean class which holds information on each set of changed classes,
 * deleted classes and new classes. Within these three types of change set you can also
 * access the specific diff on the axioms - which ones are added and deleted and label 
 * annotation on the class as well as the URI
 * 
 * 
 * @author malone
 *
 */



public class DiffTest {

	
	public static void main(String[] args) {
		
		//specify location of the two ontologies - this can also be done using URLs
		String ontology1 = "H:\\yourdir\\ontologyname1.obo";
		String ontology2 = "H:\\yourdir\\ontologyname2.obo";
		
		//create corresponding File objects
		File ontologyFile1 = new File(ontology1);
		File ontologyFile2 = new File(ontology2);
		
		//create CompareOntologies object
		CompareOntologies bubastis = new CompareOntologies();

		//perform the diff - you can also use URLs or a combination of one from a file and one from a URL
		bubastis.doFindAllChanges(ontologyFile1, ontologyFile2);
		
		//write the diff reports to a file specified in the path below
		bubastis.writeDiffAsXMLFile("H:\\bubastis_diff_report.xml");

		//alternatively you can also write the diff reports as xml including an xslt file location
		//the default xslt is included in the xslt folder - remember the path is relative to current location of xml file
		bubastis.writeDiffAsXMLFile("H:\\bubastis_diff_report.xml", "xslt/bubastis_style_info.xslt");

		
		//you can also work directly with the in memory bean to access the diff results
		OntologyChangesBean changesBean = bubastis.getOntologyChangesBean();

		//get the total number of changed classes
		int numberOfChangedClasses = changesBean.getNumChangedClasses();

		//or get the set of objects which constitute the set of changes to classes, 
		//stored in an OWLClassAxiomsInfo objects
		ArrayList<OWLClassAxiomsInfo> allClassChanges = changesBean.getClassesWithDifferences();

		//looking at the first class axiom
		OWLClassAxiomsInfo firstClassChanges = allClassChanges.get(0);
		//get the labels on this class
		firstClassChanges.getLabelsAsString();
		//and the uri
		IRI classURI = firstClassChanges.getIRI();
		//and perhaps the set of class axioms that are new to this class (if any) as textual labels (not URIs)
		Set<String> newAxioms = firstClassChanges.getNewClassAxiomsAsLabels();
		
		
	}
	

}