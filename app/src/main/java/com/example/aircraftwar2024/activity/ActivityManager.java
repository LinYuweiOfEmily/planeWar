package com.example.aircraftwar2024.activity;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    public ActivityManager() {
    }

    public static ActivityManager getActivityManager(){
        if(instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        if(activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity(){
        for(int i = 0,size = activityStack.size();i<size;i++){
            if(activityStack.get(i) != null){
                activityStack.get(i).finish();;
            }
        }
        activityStack.clear();
    }

    public void exitApp (Context context){
        try{
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }catch(Exception ex){
        }
    }
}
