package ie.ucc.bis.supportinglife.test;

import ie.ucc.bis.supportinglife.activity.HomeActivity;
import android.test.ActivityInstrumentationTestCase2;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    public HelloAndroidActivityTest() {
        super(HomeActivity.class); 
    }

    public void testActivity() {
    	assertNotNull("activity should be launched successfully", getActivity());
    	
    //	FragmentActivity activity = getActivity();
     //   assertNotNull(activity);
    }
}

