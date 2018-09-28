package com.codingblocks.suraksha;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_IDS;

/**
 * Implementation of App Widget functionality.
 */
public class PanicWidget extends AppWidgetProvider {
    private static final String MyOnClick = "myOnClickTag";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.panic_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.panic_widget);

            Intent intent = new Intent();
            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            //Pass the IDs of the current widget to the onUpdate method
            intent.putExtra(EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pi = PendingIntent.getBroadcast(context, 420, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//            remoteView.setOnClickPendingIntent(R.id.parent, pi);
            remoteView.setOnClickPendingIntent(R.id.btnPanicWidget, getPendingSelfIntent(context, MyOnClick));

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyOnClick.equals(intent.getAction())) {

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
}

