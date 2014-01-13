package ie.ucc.bis.supportinglife.ccm.rule.engine.utilities;

import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;

import java.util.List;

/**
 * This abstract class provides utility functions to all CCM-related 
 * test cases.
 * 
 * @author Tim O Sullivan
 *
 */
public class CcmRuleEngineUtilities {

	
	/**
	 * Utility method to determine whether a classification is present in
	 * the diagnostics associated with a patient. 
	 * 
	 * @param patientDiagnostics
	 * @param classificationToCheck
	 * 
	 * @return boolean - whether classification is present
	 */
	public static boolean classificationPresent(List<Diagnostic> patientDiagnostics, String classificationToCheck) {
		boolean classificationPresent = false;
		
        for (Diagnostic diagnostic : patientDiagnostics) {
        	if (diagnostic.getClassification().getName().equalsIgnoreCase(classificationToCheck)) {
        		classificationPresent = true;
        	}
		}
		return classificationPresent;
	}

	/**
	 * Utility method to determine whether a treatment is present in
	 * the diagnostics associated with a patient. 
	 * 
	 * @param patientDiagnostics
	 * @param treatmentToCheck
	 * 
	 * @return boolean - whether treatment is present
	 */
	public static Object treatmentPresent(List<Diagnostic> patientDiagnostics, String treatmentToCheck) {
		boolean treatmentPresent = false;
		
        for (Diagnostic diagnostic : patientDiagnostics) {
        	for (String recommendedTreatment : diagnostic.getTreatmentRecommendations()) {
        		// need to remove all newline and tab characters
        		recommendedTreatment = recommendedTreatment.replace("\t", "").replace("\n", "").replace("\\n", "");
        		
            	if (recommendedTreatment.equals(treatmentToCheck)) {
            		treatmentPresent = true;
            	}
        	}	
		}
		return treatmentPresent;
	}

	/**
	 * Utility method to determine whether a treatment is present in
	 * the diagnostics associated with a patient. 
	 * 
	 * @param patientDiagnostics
	 * 
	 * @return int - number of standard classifications
	 */
	public static int calculateStandardClassificationNumber(List<Diagnostic> patientDiagnostics) {
		int classificationCount = 0;
		
        for (Diagnostic diagnostic : patientDiagnostics) {
        	// need to ignore non-standard classifications
        	// i.e. only include those classifications where the CCM Treatment Display Name is not null
        	if (diagnostic.getClassification().getCcmTreatmentDisplayName() != null) {
        		classificationCount++;
        	}
		}		
		
		return classificationCount;
	}
	
} // end of class