package app.eth.mytsetprj;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

// J.H. Lee
// 20130129
// http://makerj.tistoy.com
// Tested in AVD, Galaxy S2
public class MainActivity extends Activity {
	private Camera mCamera = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mCamera = Camera.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mCamera != null) {
			mCamera.release();
		}
	}

	public void myOnClick(View v) {
		// myOnClick ( activity_main.xml )
		switch (v.getId()) {
		case R.id.button1:// ON
			flashOn();
			break;
		case R.id.button2:// OFF
			flashOff();
			break;
		case R.id.button3:// ONOFF
			flashOnOff();
			break;
		}
	}

	public void flashOn() {
		Camera.Parameters mCameraParameter = mCamera.getParameters();
		if (mCameraParameter.getFlashMode().equals("off"))
		{
			mCameraParameter.setFlashMode("torch");
			mCamera.setParameters(mCameraParameter);
		}
	}
	public void flashOff() {
		Camera.Parameters mCameraParameter = mCamera.getParameters();
		if (mCameraParameter.getFlashMode().equals("torch")){
			mCameraParameter.setFlashMode("off");
			mCamera.setParameters(mCameraParameter);
		}
	}
	
	public void flashOnOff(){
		Camera.Parameters mCameraParameter = mCamera.getParameters();
		String state = mCameraParameter.getFlashMode();
		if(state.equals("off")){
			mCameraParameter.setFlashMode("torch");
			mCamera.setParameters(mCameraParameter);
		}
		else if(state.equals("torch")){
			mCameraParameter.setFlashMode("off");
			mCamera.setParameters(mCameraParameter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
