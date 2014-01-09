package ie.ucc.bis.supportinglife.ccm.rule.engine.test;

import ie.ucc.bis.supportinglife.R;
import ie.ucc.bis.supportinglife.activity.CcmAssessmentActivity;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.ccm.model.review.RedEyesDurationCcmReviewItem;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.domain.Patient;
import ie.ucc.bis.supportinglife.rule.engine.ClassificationRuleEngine;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;
import ie.ucc.bis.supportinglife.rule.engine.TreatmentRuleEngine;
import ie.ucc.bis.supportinglife.ui.utilities.LoggerUtils;

import java.util.ArrayList;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

public class CcmRedEyeClassificationRuleEngineTest extends ActivityInstrumentationTestCase2<CcmAssessmentActivity> {
	
    private static final String LOG_TAG = "ie.ucc.bis.supportinglife.ccm.rule.engine.test";
    
	private SupportingLifeBaseActivity activity;
	private Patient patient;
	private ArrayList<ReviewItem> reviewItems;
	
    public CcmRedEyeClassificationRuleEngineTest() {
        super(CcmAssessmentActivity.class); 
    }
    
    @Override
    public void setUp() {
    	patient = new Patient("patientFirstName", "patientSurname");
    	activity = getActivity();
    	reviewItems = new ArrayList<ReviewItem>();
    	
    	Resources resources = activity.getApplicationContext().getResources();
    	// red eyes
    	String reviewItemLabel = resources.getString(R.string.ccm_ask_secondary_assessment_review_red_eyes);
    	String reviewItemValue = "YES";
    	String reviewItemSymptomId = resources.getString(R.string.ccm_ask_secondary_assessment_red_eyes_symptom_id);
    	reviewItems.add(new ReviewItem(reviewItemLabel, reviewItemValue, reviewItemSymptomId, null, -1));

    	// red eyes duration
    	reviewItemLabel = resources.getString(R.string.ccm_ask_secondary_assessment_review_red_eyes_duration);
    	reviewItemSymptomId = resources.getString(R.string.ccm_ask_initial_assessment_red_eyes_duration_four_days_symptom_id);
    	reviewItems.add(new RedEyesDurationCcmReviewItem(reviewItemLabel, "14", reviewItemSymptomId, null, -1));
    	
    }

    public void testRedEyeClassification() {
    	
        ClassificationRuleEngine classificationRuleEngine = new ClassificationRuleEngine();
        classificationRuleEngine.readCcmClassificationRules(activity);
        classificationRuleEngine.determinePatientClassifications(activity, reviewItems, patient, classificationRuleEngine.getSystemCcmClassifications());
        
        TreatmentRuleEngine treatmentRuleEngine = new TreatmentRuleEngine();
        treatmentRuleEngine.readCcmTreatmentRules(activity);
        treatmentRuleEngine.determineCcmTreatments(activity, reviewItems, patient);
        
        StringBuilder debugOutput = new StringBuilder();
        for (Diagnostic diagnostic : patient.getDiagnostics()) {
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

