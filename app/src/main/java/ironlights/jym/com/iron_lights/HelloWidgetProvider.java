package ironlights.jym.com.iron_lights;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.IOException;
import java.util.Hashtable;

/**
 * 위젯의 상태를 주기적으로 갱신하는 예제
 *
 * @author Eye
 * @since 2011.04.01
 */
public class HelloWidgetProvider extends AppWidgetProvider
{



    static Camera mCamera;

    static Context ctx;
    private Hashtable<Integer, MediaPlayer> soundPlayers = new Hashtable<Integer, MediaPlayer>();



    //Custom Action
    public static String INTENT_ON_CLICK_FORMAT = "com.jym.iron_light.pendingAction";



    ImageView lights ;
    static boolean light_switch = false ;
//    private static Camera mCamera = Camera.open() ;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        // RemoteViews 인스턴트 생성
        RemoteViews rv = new RemoteViews(context.getPackageName(),  R.layout.hellowidget_layout);
        updateClickIntent(rv, context);
        String action = intent.getAction();

        Log.d("switch","onReceive_action : "+action);


        ctx = context;





        // Click
        if (action.startsWith("com.jym.iron_light")) {





            if(!light_switch) {


//                rv.setImageViewResource(R.id.imgButton, R.drawable.iron_light_anim);
                rv.setImageViewResource(R.id.imgButton, R.drawable.pica_light_anim);
                light_switch = !light_switch;

/*

                try {
                    Thread.sleep(1000);


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
*/

                Toast.makeText(context,"POW!",Toast.LENGTH_SHORT).show();


                mCamera = Camera.open();


                try {
                    mCamera.setPreviewTexture(new SurfaceTexture(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                mCamera.setPreviewDisplay(preview.getCameraHolder());
                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(params);
                mCamera.startPreview();


                final MediaPlayer snd  = getSoundPlayer(R.raw.pikapi_open);
                snd.seekTo(0);
                snd.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    public void onSeekComplete(MediaPlayer mp) {
                        snd.start();
                    }
                });


            }

                else {


                Log.d("off_bugfix", "setImageViewResource");

//                rv.setImageViewResource(R.id.imgButton, R.drawable.skeleton_animation_0);
                rv.setImageViewResource(R.id.imgButton, R.drawable.skeleton_lightening_0);

                light_switch = !light_switch;


                Log.d("off_bugfix"," try {");


                try {
                    mCamera.setPreviewTexture(new SurfaceTexture(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }



                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                mCamera.stopPreview();
                mCamera.release();

                Log.d("off_bugfix","getSoundPlayer");

                final MediaPlayer snd  = getSoundPlayer(R.raw.pika_close);
                snd.seekTo(0);
                snd.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    public void onSeekComplete(MediaPlayer mp) {
                        snd.start();
                    }
                });

            }


        }


//        rv.setImageViewResource(R.id.imgButton,R.drawable.skeleton_animation_10);



        // 위젯 화면 갱신
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName cpName = new ComponentName(context, HelloWidgetProvider.class);
        appWidgetManager.updateAppWidget(cpName, rv);


    }

    // 호출한 객체에 PendingIntent를 부여
    private PendingIntent getPendingIntent(Context context, int id) {
        Intent intent = new Intent(context, HelloWidgetProvider.class);
        intent.setAction(INTENT_ON_CLICK_FORMAT);
        intent.putExtra("viewId", id);

        // 중요!!! getBroadcast를 이용할 때 동일한 Action명을 이용할 경우 서로 다른 request ID를 이용해야함
        // 아래와 같이 동일한 request ID를 주면 서로 다른 값을 putExtra()하더라도 제일 처음 값만 반환됨
        // return PendingIntent.getBroadcast(context, 0, intent, 0);
        return PendingIntent.getBroadcast(context, id, intent, 0);
    }

    /* (non-Javadoc)
     * @see android.appwidget.AppWidgetProvider#onUpdate(android.content.Context, android.appwidget.AppWidgetManager, int[])
     */
    /*@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // 현재 클래스로 등록된 모든 위젯의 리스트를 가져옴
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        final int N = appWidgetIds.length;
        for(int i = 0 ; i < N ; i++)
        {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Toast.makeText(context, "onUpdate(): [" + String.valueOf(i) + "] " + String.valueOf(appWidgetId), Toast.LENGTH_SHORT).show();
        }
    }

    *//**
     * 위젯의 형태를 업데이트합니다.
     *
     * @param context 컨텍스트
     * @param appWidgetManager 위젯 메니저
     * @param appWidgetId 업데이트할 위젯 아이디
     *//*
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
      *//*  Date now = new Date();
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.hellowidget_layout);
        updateViews.setTextViewText(R.id.widgettext, "[" + String.valueOf(appWidgetId) + "]" + now.toLocaleString());
        appWidgetManager.updateAppWidget(appWidgetId, updateViews);*//*
    }

    *//**
     * 예약되어있는 알람을 취소합니다.
     *//*
    public void removePreviousAlarm()
    {
        if(mManager != null && mSender != null)
        {
            mSender.cancel();
            mManager.cancel(mSender);
        }
    }
*/


    private void updateClickIntent(RemoteViews rviews, Context context)
    {


        Log.d("onclick","updateClickIntent");

       /* Intent intent = new Intent(String.format(INTENT_ON_CLICK_FORMAT, 33));
        intent.setClass(context, HelloWidgetProvider.class);
        intent.putExtra("widgetId", R.id.imgButton);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);*/

/*
        Intent intent = new Intent(context, HelloWidgetProvider.class);
        intent.setAction(INTENT_ON_CLICK_FORMAT);
        intent.putExtra("viewId", R.id.imgButton);


        PendingIntent pi =  PendingIntent.getBroadcast(context, R.id.imgButton, intent, 0);

        rviews.setOnClickPendingIntent(R.id.imgButton, pi);*/


        Intent intent = new Intent(String.format(INTENT_ON_CLICK_FORMAT, 33));
        intent.setClass(context, HelloWidgetProvider.class);
        intent.putExtra("widgetId", 33);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rviews.setOnClickPendingIntent(R.id.imgButton, pi);
    }


    public MediaPlayer getSoundPlayer(int resId) {
        MediaPlayer output = soundPlayers.get(resId);
        if (output == null) {
            try {
                output = CreateSoundPlayer(resId);
                soundPlayers.put(resId, output);
            } catch (Exception e) {
                output = null;
            }
        }
        return output;
    }


    private MediaPlayer CreateSoundPlayer(int resId) {
        return MediaPlayer.create(ctx, resId);
    }

}