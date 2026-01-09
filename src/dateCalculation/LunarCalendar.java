package dateCalculation;

public class LunarCalendar {

    // 农历数据表（1900-2099）
    private static final int[] LUNAR_INFO = {
            0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,
            0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
            0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
            0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
            0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,
            0x0b557,0x06ca0,0x0b550,0x15355,0x04da0,0x0a5b0,0x14573,0x052b0,0x0a9a8,
            0x0e950,0x06aa0,0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,
            0x0d950,0x05b57,0x056a0,0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,
            0x0d558,0x0b540,0x0b6a0,0x195a6,0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,
            0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,0x04af5,0x04970,0x064b0,0x074a3,
            0x0ea50,0x06b58,0x05ac0,0x0ab60,0x096d5,0x092e0,0x0c960,0x0d954,0x0d4a0,
            0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,0x0a950,0x0b4a0,
            0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,0x07954,
            0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,
            0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,
            0x0dd45,0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,
            0x06d20,0x0ada0
    };

    /** 农历年总天数 */
    private static int lunarYearDays(int year) {
        int sum = 348;
        int info = LUNAR_INFO[year - 1900];
        for (int i = 0x8000; i > 0x8; i >>= 1) {
            if ((info & i) != 0) sum++;
        }
        return sum + leapMonthDays(year);
    }

    /** 闰月 */
    private static int leapMonth(int year) {
        return LUNAR_INFO[year - 1900] & 0xf;
    }

    /** 闰月天数 */
    private static int leapMonthDays(int year) {
        if (leapMonth(year) != 0) {
            return ((LUNAR_INFO[year - 1900] & 0x10000) != 0) ? 30 : 29;
        }
        return 0;
    }

    /** 农历月天数 */
    private static int monthDays(int year, int month) {
        return ((LUNAR_INFO[year - 1900] & (0x10000 >> month)) != 0) ? 30 : 29;
    }

    /** 公历 → 农历 */
    public static LunarDate solarToLunar(Date date) {

        // 1900-01-31
        int offset = daysFromBase(date);

        int year, month;
        boolean isLeap = false;

        for (year = 1900; year < 2100 && offset > 0; year++) {
            int days = lunarYearDays(year);
            if (offset < days) break;
            offset -= days;
        }

        int leap = leapMonth(year);

        for (month = 1; month <= 12 && offset > 0; month++) {
            int days;
            if (leap > 0 && month == leap + 1 && !isLeap) {
                month--;
                days = leapMonthDays(year);
                isLeap = true;
            } else {
                days = monthDays(year, month);
            }

            if (offset < days) break;
            offset -= days;

            if (isLeap && month == leap + 1) isLeap = false;
        }

        return new LunarDate(year, month, offset + 1, isLeap);
    }

    /** 计算距 1900-01-31 的天数 */
    private static int daysFromBase(Date date) {
        int[] solarMonth = {31,28,31,30,31,30,31,31,30,31,30,31};
        int days = 0;

        for (int y = 1900; y < date.year; y++) {
            days += isLeapYear(y) ? 366 : 365;
        }

        for (int m = 1; m < date.month; m++) {
            days += solarMonth[m - 1];
            if (m == 2 && isLeapYear(date.year)) days++;
        }

        days += date.day - 31;
        return days;
    }

    private static boolean isLeapYear(int y) {
        return (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
    }

    public static void main(String[] args) {
        Date d = new Date(2025, 6, 30);
        LunarDate ld = solarToLunar(d);
        System.out.println(ld);
    }
}

