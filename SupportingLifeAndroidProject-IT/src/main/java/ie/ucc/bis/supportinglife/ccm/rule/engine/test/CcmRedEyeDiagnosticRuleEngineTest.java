package ie.ucc.bis.supportinglife.ccm.rule.engine.test;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.assessment.ccm.model.review.RedEyesDurationCcmReviewItem;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRuleEngine;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

public class CcmRedEyeDiagnosticRuleEngineTest extends CcmDiagnosticRuleEngineTest {
	
    public CcmRedEyeDiagnosticRuleEngineTest() {
        super(); 
    }
    
    @Override
    public void setUp() {
    	super.setUp();

    	// red eyes
    	String reviewItemLabel = getResources().getString(R.string.ccm_ask_secondary_assessment_review_red_eyes);
    	String reviewItemValue = "YES";
    	String reviewItemSymptomId = getResources().getString(R.string.ccm_ask_secondary_assessment_red_eyes_symptom_id);
    	getReviewItems().add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, null, -1));

    	// red eyes duration
    	reviewItemLabel = getResources().getString(R.string.ccm_ask_secondary_assessment_review_red_eyes_duration);
    	reviewItemSymptomId = getResources().getString(R.string.ccm_ask_initial_assessment_red_eyes_duration_four_days_symptom_id);
    	getReviewItems().add(new RedEyesDurationCcmReviewItem(reviewItemLabel, "14", reviewItemSymptomId, null, -1));
    	
    }

    public void testRedEyeClassification() {
    	
        ClassificationRuleEngine classificationRuleEngine = new ClassificationRuleEngine();
        classificationRuleEngine.readCcmClassificationRules(getSupportingLifeActivity());
        classificationRuleEngine.determinePatientClassifications(getSupportingLifeActivity(), getReviewItems(), getPatient(), classificationRuleEngine.getSystemCcmClassifications());
        
        TreatmentRuleEngine treatmentRuleEngine = new TreatmentRuleEngine();
        treatmentRuleEngine.readCcmTreatmentRules(getSupportingLifeActivity());
        treatmentRuleEngine.determineCcmTreatments(getSupportingLifeActivity(), getReviewItems(), getPatient());
        
        StringBuilder debugOutput = new StringBuilder();
        for (Diagnostic diagnostic : getPatient().getDiagnostics()) {
        	// obtain classification details
        	debugOutput = debugOutput.append(diagnostic.getClassification().debugOutput() + "\n");
        	// obtain treatment details pertaining to the classification
        	for (String recommendedTreatment : diagnostic.getTreatmentRecommendations()) {
        		debugOutput.append(recommendedTreatment + "\n");
        	}	
        }  
        LoggerUtils.i(LOG_TAG, debugOutput);
    }
}

