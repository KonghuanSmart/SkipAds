package com.konghuan.skipads.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.utils.ConfigUtil;
import com.konghuan.skipads.utils.SettingsHelper;

import java.util.List;
import java.util.Map;

public class SkipAdsService extends AccessibilityService {
    
    private final String TAG = getClass().getName() + Constants.TAG_TAIL;

    private static RuleService service;
    private static Map<String, String> rules;

    private static AppService appService;
    private static List<String> whiteList;

    private static boolean running = false;
    private static boolean showTips = false;

    public static boolean isRunningOn(){
        return running;
    }


    private void init(){
        service = new RuleService(this);
        appService = new AppService(this);
        whiteList = appService.getAllApp("White");
        rules = service.getAllRule();
        setShowTips(ConfigUtil.getSharedPreferences(this, Constants.SHARE_NAME).getBoolean("show_skip_toast", false));
    }

    public static void setShowTips(boolean b){showTips = b;}

    public static void updateRules(String name){
        rules.remove(name);
    }
    public static void updateRules(String name, String rule){
        rules.put(name, rule);
    }

    public static void addToWhiteList(String name){
        whiteList.add(name);
    }
    public static void removeFromWhiteList(String name){
        whiteList.remove(name);
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG,"开始了！");
        init();
        running = true;
        AccessibilityServiceInfo config = new AccessibilityServiceInfo();
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_VIEW_CLICKED;
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        config.notificationTimeout = 100;
        config.packageNames = SettingsHelper.getPackageNames(this);
        if (Build.VERSION.SDK_INT >= 16) {
            config.flags =
                    AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
                    | AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS
                    | AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
                    | AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY
                    | AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE;
        }
        setServiceInfo(config);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final AccessibilityNodeInfo nodeInfo = event.getSource();
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            ComponentName componentName = new ComponentName(event.getPackageName().toString(), event.getClassName().toString());
            ActivityInfo activityInfo = getActivityInfo(componentName);
            boolean isActivity = activityInfo != null;
            if (isActivity) {
                String packageName = nodeInfo.getPackageName().toString();
                Log.d(TAG, "当前前台包名" + nodeInfo.getPackageName());
                if (packageName.equals(Constants.APP_NAME) || whiteList.contains(packageName)){
                    Log.d(TAG,"白名单 -> " + packageName);
                    return;
                }
                String rule = rules.get(nodeInfo.getPackageName().toString());
                if (rule != null){
                    Log.d(TAG, "通过规则[ " + rule + " ] 跳过广告！");
                    SharedPreferences sharedPreferences = ConfigUtil.getSharedPreferences(this, Constants.RECORD_INFO);
                    int times = sharedPreferences.getInt("times", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("times", ++times);
                    editor.apply();
                    new Handler().postDelayed(() -> skipAdsByRule(nodeInfo.findAccessibilityNodeInfosByViewId(rule)), 500);
                }else {
                        List<AccessibilityNodeInfo> nodeInfoList = nodeInfo.findAccessibilityNodeInfosByText("跳过");
                        for (AccessibilityNodeInfo info : nodeInfoList) {
                            CharSequence charSequence = info.getText();
                            if (charSequence != null) {
                                String msg = charSequence.toString();
                                if (msg.contains("跳过")) {
                                    info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    String str = " PackageName: "
                                            +info.getPackageName()
                                            +"\n ClassName: "
                                            +info.getClassName()
                                            +"\n viewIdResName: "+info.getViewIdResourceName()
                                            +"\n checkable: "+info.isCheckable()
                                            +"\n visible: "+info.isVisibleToUser();
                                    String str2 = "PackageName: "
                                            +info.getPackageName()
                                            +"; ClassName: "
                                            +info.getClassName()
                                            +"; viewIdResName: "+info.getViewIdResourceName()
                                            +"; checkable: "+info.isCheckable()
                                            +"; visible: "+info.isVisibleToUser();
                                    SharedPreferences sharedPreferences = ConfigUtil.getSharedPreferences(this, Constants.RECORD_INFO);
                                    int times = sharedPreferences.getInt("times", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("value",str);
                                    editor.putInt("times", ++times);
                                    editor.putString("packageName", (String) info.getPackageName());
                                    editor.putString("id", info.getViewIdResourceName());
                                    editor.apply();
                                    Log.d(TAG, "智能识别跳过：" + str2);
                                    if (showTips){
                                        Toast.makeText(this, "跳过广告", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                }
            }
        }
    }



    private void skipAdsByRule(List<AccessibilityNodeInfo> nodeInfoList) {
        if (nodeInfoList.size() > 0) {
            nodeInfoList.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (showTips){
                Toast.makeText(getApplicationContext(), "跳过广告", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private ActivityInfo getActivityInfo(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onInterrupt() {
        running = false;
    }
}