package dateCalculation;

public class LunarSolarConverter {

    /* ================= 农历数据（1900-2099） ================= */
    private static final long[] LUNAR_INFO = new long[]{
            0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,
            0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
            0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
            0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
            0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,
            0x0b557,0x06ca0,0x0b550,0x15355,0x04da0,0x0a5b0,0x14573,0x052b0,0x0a9a8,
            0x0e950,0x06aa0,0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,
            0x0d950,0x05b57,0x056a0,0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,
            0x0d558,0x0b540,0x0b5a0,0x195a6,0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,
            0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,0x04af5,0x04970,0x064b0,0x074a3,
            0x0ea50,0x06b58,0x05ac0,0x0ab60,0x096d5,0x092e0,0x0c960,0x0d954,0x0d4a0,
            0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,0x0a950,0x0b4a0,
            0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,0x07954,
            0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,
            0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,
            0x0dd45,0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,
            0x06d20,0x0ada0
    };

    /* ================= 中文相关 ================= */
    private static final String[] GAN = {"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
    private static final String[] ZHI = {"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
    private static final String[] ANIMALS = {"鼠","牛","虎","兔","龙","蛇","马","羊","猴","鸡","狗","猪"};
    private static final String[] MONTH_CN = {"","正","二","三","四","五","六","七","八","九","十","冬","腊"};
    private static final String[] DAY_CN = {
            "","初一","初二","初三","初四","初五","初六","初七","初八","初九","初十",
            "十一","十二","十三","十四","十五","十六","十七","十八","十九","二十",
            "廿一","廿二","廿三","廿四","廿五","廿六","廿七","廿八","廿九","三十"
    };

    /* ================= 公历 → 农历 ================= */
    public static LunarDate solarToLunar(Date date) {
        int offset = daysFromBase(date);

        int year;
        for (year = 1900; year < 2100 && offset > 0; year++) {
            offset -= yearDays(year);
        }
        if (offset < 0) {
            offset += yearDays(--year);
        }

        int leap = leapMonth(year);
        boolean isLeap = false;
        int month;

        for (month = 1; month <= 12 && offset > 0; month++) {
            int days;
            if (isLeap) {
                days = leapDays(year);
                isLeap = false;
            } else {
                days = monthDays(year, month);
            }
            offset -= days;
            if (month == leap && !isLeap) {
                isLeap = true;
                month--;
            }
        }

        if (offset < 0) {
            offset += monthDays(year, month - 1);
            month--;
        }

        return new LunarDate(year, month, offset + 1, isLeap);
    }

    /* ================= 农历日期类 ================= */
    public static class LunarDate {
        int year, month, day;
        boolean isLeap;

        public LunarDate(int y, int m, int d, boolean l) {
            year = y;
            month = m;
            day = d;
            isLeap = l;
        }

        public String toChineseString() {
            String ganZhi = GAN[(year - 4) % 10] + ZHI[(year - 4) % 12];
            String animal = ANIMALS[(year - 4) % 12];
            return "农历：" + ganZhi + "年（" + animal + "）"
                    + (isLeap ? "闰" : "")
                    + MONTH_CN[month] + "月"
                    + DAY_CN[day];
        }
    }

    /* ================= 工具方法 ================= */
    private static int daysFromBase(Date d) {
        int days = 0;
        for (int y = 1900; y < d.year; y++) {
            days += isLeapYear(y) ? 366 : 365;
        }
        int[] md = {31,28,31,30,31,30,31,31,30,31,30,31};
        if (isLeapYear(d.year)) md[1] = 29;
        for (int m = 1; m < d.month; m++) {
            days += md[m - 1];
        }
        return days + d.day - 31;
    }

    private static boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    private static int yearDays(int y) {
        int sum = 348;
        long info = LUNAR_INFO[y - 1900];
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            if ((info & i) != 0) sum++;
        }
        return sum + leapDays(y);
    }

    private static int leapMonth(int y) {
        return (int)(LUNAR_INFO[y - 1900] & 0xf);
    }

    private static int leapDays(int y) {
        return leapMonth(y) != 0 ?
                ((LUNAR_INFO[y - 1900] & 0x10000) != 0 ? 30 : 29) : 0;
    }

    private static int monthDays(int y, int m) {
        return ((LUNAR_INFO[y - 1900] & (0x10000 >> m)) != 0) ? 30 : 29;
    }
}
