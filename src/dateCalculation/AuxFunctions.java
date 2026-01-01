package dateCalculation;

//Helper类：存放辅助函数
public class AuxFunctions {
    private AuxFunctions(){}

    //辅助函数1：平年闰年判定
    //平年返回false，闰年返回true
    public static boolean yearCheck(int year) {

        //闰年两种形式：
        //1.能被4整除的非世纪年
        //2.能被400整除的世纪年
        return ((year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0));
    }

    //辅助函数2：返回某月的天数
    public static int getDayOfMonth(int month, int thisYear) {
        return switch (month) {
            //大月
            case 1, 3, 5, 7, 8, 10, 12 -> 31;

            //小月
            case 4, 6, 9, 11 -> 30;

            //二月
            //闰年返回29天，平年返回28天
            case 2 -> yearCheck(thisYear) ? 29 : 28;

            //输入了错误的月份就输出-1，表示错误
            default -> -1;
        };
    }

    //辅助函数3：计算当前年份过去的天数
    public static int getDayCount(Date date) {
        int dayPassed = 0;   //当前年份过去的天数

        //加上每个月的天数
        for (int i = 1; i < date.month; i++) {
            dayPassed += getDayOfMonth(i, date.year);
        }

        //date.day表示的这一天还没过完，所以要减一
        dayPassed += date.day - 1;
        return dayPassed;
    }

    //辅助函数4：计算同一年中两个月份之间的天数
    //比如计算6月到9月需要多少天
    public static int getDayCount(Date startDate, Date endDate) {
        int numOfDay = 0;
        for (int i = startDate.month; i < endDate.month; i++) {
            numOfDay += getDayOfMonth(i, endDate.year);
        }
        return numOfDay;
    }

    //辅助函数5：调整日期
    //处理月份和天数溢出
    public static void adjustDate(Date date) {

        // 调整月份溢出到年份
        while (date.month > 12) {
            date.month -= 12;
            date.year++;
        }

        // 调整负数天数（向前借位）
        while (date.day < 1) {
            date.month--;
            if (date.month < 1) {
                date.month = 12;
                date.year--;
            }
            date.day += getDayOfMonth(date.month, date.year);
        }
    }
}
