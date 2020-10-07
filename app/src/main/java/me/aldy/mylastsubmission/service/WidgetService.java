package me.aldy.mylastsubmission.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import me.aldy.mylastsubmission.widget.MyViewsFactory;

public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyViewsFactory(getApplicationContext(), intent);
    }
}
