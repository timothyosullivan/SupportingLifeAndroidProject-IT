package ie.ucc.bis.supportinglife.ccm.rule.engine.test;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.ccm.model.review.RedEyesDurationCcmReviewItem;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.ccm.rule.engine.utilities.CcmRuleEngineUtilities;
import ie.ucc.bis.supportinglife.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRuleEngine;

/**
 * This test case evaluates the correctness of the CCM Classification and 
 * Treatment rule engines in assessing the condition: 
 * 
 * 		-> 'Red Eye for 4 Days or more' 
 * 
 * The test cases establishes the following patient criteria to fulfil 
 * this condition:
 * 
 * 		-> Red Eye: YES
 * 		-> Red Eye Duration: 14 Days
 * 
 * The classification returned by the CCM Classification rule engine should
 * be:
 * 
 * 		-> 'Red Eye for 4 Days or more'
 * 
 * The treatments returned by the CCM Treatment rule engine should be:
 * 
 * 		-> Apply antibiotic eye ointment
 * 
 * @author Tim O Sullivan
 *
 */
public class CcmRedEyeDangerSignDiagnosticTest extends CcmDiagnosticRuleEngineTest {
	
	private static final String RED_EYE_DURATION = "14";
	
    public CcmRedEyeDangerSignDiagnosticTest() {
        super(); 
    }
    
    @Override
    public void setUp() {
    	super.setUp();

    	// CONFIGURE THE PATIENT SYMPTOMS   	
    	// 1. red eyes = YES
    	String reviewItemLabel = getResources().getString(R.string.ccm_ask_secondary_assessment_review_red_eyes);
    	String reviewItemSymptomId = getResources().getString(R.string.ccm_ask_secondary_assessment_red_eyes_symptom_id);
    	getReviewItems().add(new ReviewItem(reviewItemLabel, POSITIVE_SYMPTOM_RESPONSE, reviewItemSymptomId, null, -1));

    	// 2. red eyes duration = 14 days
    	reviewItemLabel = getResources().getString(R.string.ccm_ask_secondary_assessment_review_red_eyes_duration);
    	reviewItemSymptomId = getResources().getString(R.string.ccm_ask_initial_assessment_red_eyes_duration_four_days_symptom_id);
    	getReviewItems().add(new RedEyesDurationCcmReviewItem(reviewItemLabel, RED_EYE_DURATION, reviewItemSymptomId, null, -1));	
    }

    /**
     * Test case to check the classification and treatment of 
     * the danger sign: 'Red Eye for 4 Days or more'
     * 
     */
    public void testRedEyeDangerSign() {
    	
    	// 1. Execute the Classification rule engine to determine patient classifications
        ClassificationRuleEngine classificationRuleEngine = new ClassificationRuleEngine();
        classificationRuleEngine.readCcmClassificationRules(getSupportingLifeActivity());
        classificationRuleEngine.determinePatientClassifications(getSupportingLifeActivity(), getReviewItems(), getPatient(), classificationRuleEngine.getSystemCcmClassifications());
        
        // 2. Execute the Treatment rule engine to determine patient treatments
        TreatmentRuleEngine treatmentRuleEngine = new TreatmentRuleEngine();
        treatmentRuleEngine.readCcmTreatmentRules(getSupportingLifeActivity());
        treatmentRuleEngine.determineCcmTreatments(getSupportingLifeActivity(), getReviewItems(), getPatient());
        
        // 3. Has the correct number of classifications been determined?
       assertEquals("the actual number of patient classifications does not match the expected number",
    		   1, CcmRuleEngineUtilities.calculateStandardClassificationNumber(getPatient().getDiagnostics()));
        
        // 4. Has the correct classification been determined?
        assertEquals("incorrect classification assessed", true, CcmRuleEngineUtilities.classificationPresent(getPatient().getDiagnostics(), "Red Eye for 4 Days or more"));
         
        // 5. Has the correct treatment been determined?
        assertEquals("incorrect treatment assessed", true, CcmRuleEngineUtilities.treatmentPresent(getPatient().getDiagnostics(), "Apply antibiotic eye ointment"));
    }
} // end of class