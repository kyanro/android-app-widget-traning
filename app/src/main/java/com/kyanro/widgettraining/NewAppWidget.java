package com.kyanro.widgettraining;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewAppWidgetConfigureActivity NewAppWidgetConfigureActivity}
 */
public class NewAppWidget extends AppWidgetProvider {

    public static final String ACTION_UP_CLICK = "com.kyanro.widgettraining.ACTION_UP_CLICK";
    public static final String ACTION_DOWN_CLICK = "com.kyanro.widgettraining.ACTION_DOWN_CLICK";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        CharSequence widgetText = NewAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // click event 送信
        Log.d("mydevlog", "update log");

        Toast.makeText(context, "test", Toast.LENGTH_LONG).show();

        Intent upIntent = new Intent();
        upIntent.setAction(ACTION_UP_CLICK);
        PendingIntent upPendingIntent = PendingIntent.getBroadcast(context, 0, upIntent, 0);
        views.setOnClickPendingIntent(R.id.up_text, upPendingIntent);

        Intent downIntent = new Intent();
        downIntent.setAction(ACTION_DOWN_CLICK);
        PendingIntent downPendingIntent = PendingIntent.getBroadcast(context, 0, downIntent, 0);
        views.setOnClickPendingIntent(R.id.down_text, downPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            NewAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.d("mydevlog", "intent comming");

        String action = intent.getAction();
        if (ACTION_UP_CLICK.equals(action)) {
            Log.d("mydevlog", "button up clicked");
        }else if (ACTION_DOWN_CLICK.equals(action)) {
            Log.d("mydevlog", "button down clicked");
        }
    }
}

