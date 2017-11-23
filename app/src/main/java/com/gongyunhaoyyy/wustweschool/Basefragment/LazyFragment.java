package com.gongyunhaoyyy.wustweschool.Basefragment;

import android.support.v4.app.Fragment;

/**
 * Created by 99460 on 2017/11/8.
 */

public abstract class LazyFragment extends Fragment {

    protected  boolean isVisble;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisble = true;
            onVisible();
        } else {
            isVisble = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}

}
