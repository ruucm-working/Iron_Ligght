package app.eth.mytsetprj;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

// J.H. Lee
// 20130129
// http://makerj.tistoy.com
// Tested in AVD, Galaxy S2
public class MainActivity extends Activity implements SurfaceHolder.Callback {
	private Camera mCamera = null;
	private int isCameraOn = 0;


    SurfaceHolder mHolder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



        SurfaceView preview = (SurfaceView) findViewById(R.id.PREVIEW);
        mHolder = preview.getHolder();
        mHolder.addCallback(this);
        mCamera = Camera.open();
//        mCamera.setPreviewDisplay(mHolder);
	}

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {}

    public void surfaceCreated(SurfaceHolder holder) {
        mHolder = holder;
//        mCamera.setPreviewDisplay(mHolder);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mHolder = null;
    }

	@Override
	protected void onPause() {
		super.onPause();
		if(mCamera != null)
		{
		mCamera.release();
        }
	}

	public void myOnClick(View v){
		// myOnClick ( activity_main.xml )
		switch(v.getId()){
		case R.id.button1://ON
			if(isCameraOn == 0)
			{
				/*mCamera = Camera.open();
				Camera.Parameters mCameraParameter = mCamera.getParameters();
				mCameraParameter.setFlashMode("torch");
				mCamera.setParameters(mCameraParameter);
				isCameraOn = 1;*/

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(params);
                mCamera.startPreview();
			}
			break;
		case R.id.button2://OFF
			if(isCameraOn == 1)
			{
				mCamera.release();
				isCameraOn = 0;
			}
			break;
		case R.id.button3://ONOFF
			if(isCameraOn == 0)
			{
				mCamera = Camera.open();
				Camera.Parameters mCameraParameter = mCamera.getParameters();
				mCameraParameter.setFlashMode("torch");
				mCamera.setParameters(mCameraParameter);			
				isCameraOn = 1;
			}
			else if(isCameraOn == 1)
			{
				mCamera.release();
				isCameraOn = 0;
			}
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
