package com.nyw.pets.fragment.util;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.nyw.pets.fragment.FollowFragment;
import com.nyw.pets.fragment.RecommendFragment;
import com.nyw.pets.fragment.SameCityFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用工厂模式来降低程序的耦合性   订单信息
 * @author nyw
 *
 */
public class IndexFragmentUtil {
	private static Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();
	private static final String ARG_POSITION = "position";
	private static Context context;
/**
 * 这个方法是判断当然用户操作切换到对应的Fragment加载布局展示
 * @param position 当然用户选择的postition
 * @return 返回值
 */
    public static Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = mFragments.get(position);  //在集合中取出来Fragment
        //接收值
//        WithdrawalFragments article = new WithdrawalFragments();
//		Bundle b = new Bundle();
//		b.putInt(ARG_POSITION, position);
//		article.setArguments(b);
		
        if(fragment != null) {
            mFragments.put(position, fragment);
        }
        //如果在集合中没有取出来，需要重新创建
        if(fragment == null) {
        	switch (position) {
			case 0:
                //推荐
				return new RecommendFragment();

			case 1:
                //关注
//				LoadDialog.show(fragment.getContext());
				return new FollowFragment();
                case 2:
                    //同城
                    return  new SameCityFragment();


			}

        }
        //这里返回值
         return null;
    }

}
