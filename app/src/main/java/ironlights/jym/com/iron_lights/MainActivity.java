package ironlights.jym.com.iron_lights;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {

    public static SurfaceView preview;
    SurfaceHolder mHolder;


    //Add Admob ads
    private AdView adView;
    InterstitialAd interstitial;

    String deviceid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Banner Ad

        String deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        Log.d("getDeviceId", "AdRequest.DEVICE_ID_EMULATOR : " + AdRequest.DEVICE_ID_EMULATOR);

        Log.d("getDeviceId", "Secure.getString.deviceid : " + deviceid);


        AdRequest adRequest = new AdRequest.Builder()
                /*.addTestDevice(deviceid)*/
                .build();
        adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(adRequest);





        //Interstitial Ad

        deviceid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        // 삽입 광고를 만듭니다.
        interstitial = new InterstitialAd(this);

        interstitial.setAdUnitId("ca-app-pub-6971886086388676/6797177344");


        //Repeatly load interstitial Ad.
        callAsynchronousTask();

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
           public void onAdLoaded() {
               // Call displayInterstitial() function
               displayInterstitial();

           }
       }
        );


    }


    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {


                        Log.d("callAsynchronousTask", "run() ");


                        // 광고 요청을 만듭니다.
                        AdRequest adRequest = new AdRequest.Builder()
                                /*.addTestDevice(deviceid)*/
                                .build();

                        // 삽입 광고 로드를 시작합니다.
                        interstitial.loadAd(adRequest);
                        //Check Ad is Loaded as Toast
//                        Toast.makeText(getBaseContext(), "interstitial.isLoaded() : " + interstitial.isLoaded(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }


    // 삽입 광고를 표시할 준비가 되면 displayInterstitial()을 호출합니다.
    public void displayInterstitial() {

        Log.d("interstitial", "displayInterstitial");

        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
