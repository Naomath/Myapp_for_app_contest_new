package com.lifeistech.naoto.myapplication_app_contest;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by naoto on 2017/05/22.
 */

public class SugarOrmControl extends SugarApp {

    @Override
    public void onCreate(){
        super.onCreate();
        SugarContext.init(this);
    }
}
