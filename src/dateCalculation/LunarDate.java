package dateCalculation;

public class LunarDate {

    // ========= 基本数值 =========
    public int year;
    public int month;
    public int day;
    public boolean isLeap;

    // ========= 农历名称数组 =========
    private static final String[] TIAN_GAN = {
            "甲","乙","丙","丁","戊","己","庚","辛","壬","癸"
    };

    private static final String[] DI_ZHI = {
            "子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"
    };

    private static final String[] SHENG_XIAO = {
            "鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"
    };

    private static final String[] MONTH_NAME = {
            "正月","二月","三月","四月","五月","六月",
            "七月","八月","九月","十月","冬月","腊月"
    };

    private static final String[] DAY_NAME = {
            "初一","初二","初三","初四","初五","初六","初七","初八","初九","初十",
            "十一","十二","十三","十四","十五","十六","十七","十八","十九","二十",
            "廿一","廿二","廿三","廿四","廿五","廿六","廿七","廿八","廿九","三十"
    };

    // ========= 构造 =========
    public LunarDate(int year, int month, int day, boolean isLeap) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.isLeap = isLeap;
    }

    // ========= 中文年（干支） =========
    public String getGanZhiYear() {
        int index = year - 1900 + 36; // 1900 是 庚子年
        return TIAN_GAN[index % 10] + DI_ZHI[index % 12];
    }

    public String getShengXiao() {
        return SHENG_XIAO[(year - 4) % 12];
    }

    // ========= 中文月 / 日 =========
    public String getMonthName() {
        return (isLeap ? "闰" : "") + MONTH_NAME[month - 1];
    }

    public String getDayName() {
        return DAY_NAME[day - 1];
    }

    // ========= 完整中文表示 =========
    @Override
    public String toString() {
        return getGanZhiYear() + "（" + getShengXiao() + "）年 "
                + getMonthName() + getDayName();
    }

//    @Override
//    public String toString() {
//        return year + "年" + (isLeap ? "闰" : "") + month + "月" + day + "日";
//    }
}


