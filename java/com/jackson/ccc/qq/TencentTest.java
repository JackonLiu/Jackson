/*
package com.jackson.ccc.qq;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import com.jackson.ccc.gridview.Lost;
import com.jackson.ccc.test.R;
import com.tencent.open.SocialConstants;
import com.tencent.stat.EasyActivity;
import com.tencent.stat.MtaSDkException;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatGameUser;
import com.tencent.stat.StatService;
import com.tencent.tauth.Tencent;

import java.util.Properties;

*/
/**
 * Created by LXP on 17-4-12.
 *//*


public class TencentTest extends EasyActivity {
    private Context context;
    String open_appid = "开放平台 appid";
    StatConfig statConfig = new StatConfig();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtamain);
        // androidManifest.xml指定本activity最先启动
        // 因此，MTA的初始化工作需要在本onCreate中进行
        // 在startStatService之前调用StatConfig配置类接口，使得MTA配置及时生效

        //可以通过下面的接口关闭自动捕获异常功能
        // app的最初始代码处 StatConfig.setAutoExceptionCaught(false);
        // initMTAConfig(true);
        String appkey = " amtaandroid0";

        //用户打开手机 QQ 连续操作 10 分钟，之后按 home 键（或锁屏）退到后台，
        //超过 30 秒后再次回到 QQ，此时，SDK 会上报一次会话。
        StatConfig.setSessionTimoutMillis(30000);
       // Service.startNewSession(context );
        // Service.reportError (context, String errormsg);
        //Service.reportException (context,Throwable err);

      */
/*  try{   int retval = totalNumber / personInRoom; } catch (ArithmeticException e){ StatService.reportException(this, e); }*//*


      //MTA 会在处理完异常后，再调用原来的异常处理函数继续处理
      //Thread.setDefaultUncaughtExceptionHandler();

        // 初始化并启动MTA
        // 第三方SDK必须按以下代码初始化MTA，其中appkey为规定的格式或MTA分配的代码。
        // 其它普通的app可自行选择是否调用
        */
/*try {
            // 第三个参数必须为：com.tencent.stat.common.StatConstants.VERSION
            StatService.startStatService(this, appkey,com.tencent.stat.common.StatConstants.VERSION);  } catch (MtaSDkException e) {
            // MTA初始化失败
            Log.e("222","MTA start failed.");
            Log.e("222",e.getMessage());  }*//*



    }

    private void onClickInvite() {

        Tencent mTencent = null;
        StatConfig statConfig = new StatConfig();
        if (mTencent.isSessionValid() && mTencent.getOpenId() != null) {
            Bundle params = new Bundle();
            params.putString(SocialConstants.PARAM_APP_ICON, "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
            params.putString(SocialConstants.PARAM_APP_DESC, "AndroidSdk_1_3: invite description!");
            params.putString(SocialConstants.PARAM_APP_CUSTOM, "AndroidSdk_1_3: invite message!");
            params.putString(SocialConstants.PARAM_ACT, "进入应用");
          //  mTencent.invite(Lost.this, params, new BaseUiListener());
        }
    }

    */
/* 【次数统计】Key-Value 参数的事件
    Stat Service . trackCustom KV Event (Context c tx , String event_id,
                                         Properties properties )*//*


    //标记一次页面访问的开始
    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }
    // statConfig.setAppKey(this, "Aqc123456");

    //标记一次页面访问的结束
        @Override protected void onPause()
        {  super.onPause();  StatService.onPause(this); }

    public void onOKBtnClick(View v) {
        // 统计按钮被点击次数，统计对象：OK按钮
         Properties prop = new Properties();  prop.setProperty("name", " OK ");  StatService.trackCustomKVEvent(this, " button_click", prop); }

    public void onBackBtnClick(View v) {
        // 统计按钮被点击次数，统计对象：back按钮
        Properties prop = new Properties();  prop.setProperty("name", " back ");  StatService.trackCustomKVEvent(this, " button_click", prop);
    }

    public void onClick(View v) {
        // 统计按钮被点击次数，统计对象：OK按钮
         StatService.trackCustomEvent(this, "button_click", "OK ");
        }

    public void onClick1(View v) {
        Properties prop = new Properties();
        prop.setProperty("level", "5");
        // 统计用户通关所花时长，关卡等级： 5 // 用户通关前
        StatService.trackCustomBeginKVEvent(this, " playTime", prop);
        // 用户正在游戏中….  // …….
        // 用户通关完成时
        StatService.trackCustomEndKVEvent(this, " playTime", prop);
         }

    public void onClick2(View v) {  // 统计用户通关所花时长 // 用户通关前 StatService.trackCustomBeginEvent(this, " playTime", "level5");  // 用户正在游戏中….  // …….  // 用户通关完成时 StatService.trackCustomEndEvent(this, " playTime", " level5");
    }
    */
/*统计应用对某个外部接口（特别是网络类的接口，如连接、登陆、下载等）的调用情况。

    Stat Service . reportAppMonitorStat (
            Context ctx , StatAppMonitor monitor )*//*


    protected void someAction() {   // 获取在线参数onlineKey  String onlineValue = StatConfig.getCustomProperty("onlineKey", "off" );  if(onlineValue.equalsIgnoreCase("on")){   // do something  }else{   // do something else
    }

   */
/* app 启动时指定上报策略（默认为 APP_LAUNCH）
    void Stat Config . setStatSendStrategy (StatReportStrategy statSendStrategy)
    StatConfig.setSendPeriodMinutes(); //设置按 PERIOD 策略的时间间隔（只对
PERIOD 策略有效）

*//*


    //禁用wifi网络实时发送数据  Config . setEnableSmartReporting ( boolean isEnable )
    protected class StatGameUser1 extends StatGameUser{

     */
/*   @Override protected void userLogin() {   // 游戏用户上报 StatGameUser gameUser = new StatGameUser(); gameUser.setWorldName("world1"); gameUser.setAccount("account1"); gameUser.setLevel("100"); StatService.reportGameUser(ctx, gameUser);
        }*//*


    //Service. reportGameUser (Context ctx, StatGameUser gameUser )

        */
/**
         *  消息失败重发次数（默认 3）
         void Stat Config . setMaxSendRetryCount (int maxSendRetryCount)
         3) 用户自定义时间类型事件的最大并行数量（默认 1024）
         void Stat Config . setMaxParallelTimmingEvents (int max）

         设置统计功能开关（默认为 true）
         void Stat Config . setEnableStatService (boolean enableStatService)
         如果为false，则关闭统计功能，不会缓存或上报任何信息。
         7) 设置 session 内产生的消息数量（默认为 0，即无限制）
         void Stat Config . setMaxSessionStatReportCount ( int
         maxSessionStatReportCount )
         如果为0，则不限制；若大于0，每个session内产生的消息数量不会超过此值，若超过了，
         新产生的消息将会被丢弃。
         8) 设置每天/每个进程时间产生的会话数量（默认为 20）
         为防止开发者调用 MTA 不合理导致上报大量的会话数量（sesion）， SDK 默认每天/每个进
         Android 统计 SDK 开发者使用指南
         程时间内最多产生的会话数量，当达到此值时，SDK 不再产生并上报新的会话。当进程重启
         或跨天时，会被清 0。
         void Stat Config . setMaxDaySessionNumbers ( int maxDaySessionNumbers )
         9) 设置单个事件最大长度（默认为 4k，单位：bytes）
         为防止上报事件长度过大导致用户流量增加，SDK 默认不上报超过 4k 的单个事件；对
         于错误异常堆栈事件，异常堆栈长度不超过 100（可以超过 4k） 。
         void Stat Config . setMaxReportEventLength ( int maxReportEventLength )

         统计首次使用 APP 的设备数量。
         if(该用户是首次使用){
         StatService.trackCustomEvent(this, "first", ""); }

         //校验登陆状态
         private void doLogin() {
         IUiListener listener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
        updateLoginButton();
        }
        };
         mTencent.login(this, SCOPE, listener);
         }
         *//*


       */
/* 7.2 数据上报相关的设置接口
1) 设置最大缓存未发送消息个数（默认 1024）
        void Stat Config . setMaxStoreEventCount (int maxStoreEventCount)
        缓存消息的数量超过阈值时，最早的消息会被丢弃。
                2) （仅在发送策略为 BATCH 时有效）设置最大批量发送消息个数（默认 30）
        void Stat Config . setMaxBatchReportCount (int maxBatchReportCount)
                3) （仅在发送策略为 PERIOD 时有效）设置间隔时间（默认为 24*60，即 1 天）
        void Stat Config . setSendPeriodMinutes (int minutes)
                4) 开启 SDK LogCat 开关（默认 false）
        void Stat Config . setDebugEnable (boolean debugEnable)
                （注意：在发布产品时，请将此开关设为 false）

                *//*

    }
}
*/
