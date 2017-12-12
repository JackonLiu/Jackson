package com.jackson.ccc.viewpage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by LXP on 17-3-12.
 */

public class MyfragmentPageAdapter  extends FragmentStatePagerAdapter{


    private List<Fragment>fragList;
    private List<String> titleList;


    public MyfragmentPageAdapter(FragmentManager fm, List<Fragment> fragList ,List<String> titleList) {
        super(fm);
        this.fragList = fragList;
        this.titleList=titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        /*String[] strings = {"schoolCard", "phone", "money", "card", "book"};
        for (String s : strings) {
            titleList.add(s);
        }*/
        return titleList.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //你想查看这个titleList是否有空指针异常，就可以这样打印信息
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
