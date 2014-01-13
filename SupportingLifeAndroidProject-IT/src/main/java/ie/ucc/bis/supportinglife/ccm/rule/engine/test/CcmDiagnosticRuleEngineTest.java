package ie.ucc.bis.supportinglife.ccm.rule.engine.test;

import ie.ucc.bis.supportinglife.activity.CcmAssessmentActivity;
import ie.ucc.bis.supportinglife.activity.SupportingLifeBaseActivity;
import ie.ucc.bis.supportinglife.assessment.model.review.ReviewItem;
import ie.ucc.bis.supportinglife.domain.Patient;

import java.util.ArrayList;

import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

/**
 * This abstract class provides base functionality for test cases related
 * to the CCM Diagnostic rule engine.
 * 
 * @author Tim O Sullivan
 *
 */
public abstract class CcmDiagnosticRuleEngineTest extends ActivityInstrumentationTestCase2<CcmAssessmentActivity> {

	protected static final String POSITIVE_SYMPTOM_RESPONSE = "YES"; 
	protected static final String LOG_TAG = "ie.ucc.bis.supportinglife.ccm.rule.engine.test";

	private SupportingLifeBaseActivity supportingLifeActivity;
	private Patient patient;
	private ArrayList<ReviewItem> reviewItems;
	private Resources resources;

	@Override
	public void setUp() {
		setPatient(new Patient("patientFirstName", "patientSurname"));
		setSupportingLifeActivity(getActivity());
		setReviewItems(new ArrayList<ReviewItem>());
		setResources(getSupportingLifeActivity().getApplicationContext().getResources());
	}

	/**
	 * Constructor
	 */
	public CcmDiagnosticRuleEngineTest() {
		super(CcmAssessmentActivity.class); 
	}

	/**
	 * Getter Method: getSupportingLifeActivity()
	 */
	public SupportingLifeBaseActivity getSupportingLifeActivity() {
		return supportingLifeActivity;
	}

	/**
	 * Setter Method: setSupportingLifeActivity()
	 */
	public void setSupportingLifeActivity(SupportingLifeBaseActivity supportingLifeActivity) {
		this.supportingLifeActivity = supportingLifeActivity;
	}

	/**
	 * Getter Method: getPatient()
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * Setter Method: setPatient()
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * Getter Method: getReviewItems()
	 */
	public ArrayList<ReviewItem> getReviewItems() {
		return reviewItems;
	}

	/**
	 * Setter Method: setReviewItems()
	 */
	public void setReviewItems(ArrayList<ReviewItem> reviewItems) {
		this.reviewItems = reviewItems;
	}

	/**
	 * Getter Method: getResources()
	 */
	public Resources getResources() {
		return resources;
	}

	/**
	 * Setter Method: setResources()
	 */
	public void setResources(Resources resources) {
		this.resources = resources;
	}
}
