	package com.nyw.pets.util;

import android.content.Context;

    /**
* 项目名称：translate
* 实现功能：  显示设置相关工具类
* 类名称：DisplayUtil
* 类描述：(该类的主要功能)
* 创建人：
* E-mail:
* 创建时间：2014年10月21日 下午9:45:18
* 修改人：
* 修改时间：
* 修改备注：
* @version
*/
public class DisplayUtil {
    /**
        * @方法名称: px2dip
        * @描述: 将px值转换为dip或dp值
        * @param   @param context
        * @param   @param pxValue
        * @param   @return
        * @return int
        * @throws
        * @author
        * 2014年10月24日 下午9:59:08
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     *
        * @方法名称: dip2px
        * @描述: 将dip或dp值转换为px值
        * @param   @param context
        * @param   @param dipValue
        * @param   @return
        * @return int
        * @throws
        * @author
        * 2014年10月24日 下午9:59:53
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     *
        * @方法名称: px2sp
        * @描述: 将px值转换为sp值
        * @param   @param context
        * @param   @param pxValue
        * @param   @return
        * @return int
        * @throws
        * @author
        * 2014年10月24日 下午10:00:36
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     *
        * @方法名称: sp2px
        * @描述: 将sp值转换为px值
        * @param   @param context
        * @param   @param spValue
        * @param   @return
        * @return int
        * @throws
        * @author
        * 2014年10月24日 下午10:01:01
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
