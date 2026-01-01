package dateCalculation;

//日期计算器
public class Calculators {
    private Calculators(){}

    //计算器1：天数转年份
    //需要考虑起始年份是不是闰年
    //默认情况下，第一年是非闰年
    public static Date Conversion(int numOfDays, int startYear) {

        //如果numOfDays大于365，算整年份
        int year = 0;
        while (numOfDays >= 365) {
            int numOfYear = AuxFunctions.yearCheck(startYear) ? 366 : 365;
            numOfDays -= numOfYear;
            startYear++;
            year++;
        }

        //再算月数，需要考虑大小月日期不同
        //大月31天，小月30天，二月28天（考虑闰年）
        int month = 0;  //表示几个月（月数-1）
        while (numOfDays > 30) {

            //大小月判定
            numOfDays -= AuxFunctions.getDayOfMonth(month + 1, startYear);

            //计算一次月数加一
            month++;
        }

        //输出并返回日期
        return new Date(year, month, numOfDays);
    }

    ///////////////////////////////////////////////////////

    //计算器2：日期推算
    //直接在传入的日期上操作
    //需要考虑闰年和平年
    public static Date Calculation(Date date, int numOfDays) {

        //向前推算：输入的天数小于0
        if (numOfDays < 0) {

            //天数调正
            numOfDays = -numOfDays;

            //预处理1：将日期调整为整年
            //将日期调整至当前年份的12月31日
            int dayOfYear = AuxFunctions.yearCheck(date.year) ? 366 : 365;  //一年的天数

            int dayCount = AuxFunctions.getDayCount(date);   //当前年份已经过去的天数

            int dayLeft = dayOfYear - dayCount;  //当前年份剩余的天数

            numOfDays += dayLeft;     //天数加上剩余天数，表示调整至12月31日

            date.month = 12;    //调整日期
            date.day = 31;

            //如果天数大于365，将天数减到小于365
            while (numOfDays >= 365) {
                dayOfYear = AuxFunctions.yearCheck(date.year) ? 366 : 365;
                numOfDays -= dayOfYear;
                date.year--;
            }

            //然后对days进行年份换算
            Date addend = Conversion(numOfDays,date.year);

            //日期直接相减
            date.year -= addend.year;
            date.month -= addend.month;
            date.day -= addend.day;

        //向后推算：输入的天数大于0
        } else {

            //预处理：将日期调整为整年
            //将日期调整至下一年的1月1日
            while (numOfDays >= 365) {

                //第一步：将days减去当前年份的剩余天数
                int dayOfYear = AuxFunctions.yearCheck(date.year) ? 366 : 365;  //一年的天数

                int dayCount = AuxFunctions.getDayCount(date);   //当前年份已经过去的天数

                int dayLeft = dayOfYear - dayCount;  //当前年份剩余的天数

                numOfDays -= dayLeft;   //将天数减去当前年份剩余的天数，表示跳过这一年

                //第二步：将日期重置到下年的1月1日
                date.year++;
                date.month = date.day = 1;
            }

            //然后对days进行年份换算
            Date addend = Conversion(numOfDays,date.year);

            //日期直接相加
            date.year += addend.year;
            date.month += addend.month;
            date.day += addend.day;
        }

        //处理日期溢出
        AuxFunctions.adjustDate(date);

        //返回日期
        date.showDate();
        return date;
    }

    //////////////////////////////////////////////////////

    //计算器3：计算日期间隔
    //输入起始日期和结束日期，输出间隔天数
    //输出-1表示错误
    public static int Interval(Date startDate, Date endDate) {
        int dateInterval = 0;

        if (startDate.year > endDate.year ||
                (startDate.year == endDate.year && startDate.month > endDate.month) ||
                (startDate.year == endDate.year && startDate.month == endDate.month
                        && startDate.day > endDate.day)) {
            return -1;
        }

        //将起始日期调到和结束日期相同
        //调整日
        //如果结束日期的天数大于起始日期，就将整月天数减去两日期之间间隔的天数
        if (startDate.day > endDate.day) {
            dateInterval += AuxFunctions.getDayOfMonth(startDate.month, startDate.year)
                    - (startDate.day - endDate.day);
            startDate.month++;
        } else {
            dateInterval += endDate.day - startDate.day;
        }
        startDate.day = endDate.day;

        //调整月份a，与调整日类似
        if (startDate.month > endDate.month) {
            dateInterval += AuxFunctions.yearCheck(startDate.year + 1) ? 366 : 365
                    - AuxFunctions.getDayCount(endDate, startDate);
            startDate.year++;
        } else {
            dateInterval += AuxFunctions.getDayCount(startDate, endDate);
        }
        startDate.month += endDate.month;

        //最后计算整年的天数
        for (int i = startDate.year; i < endDate.year; i++) {
            dateInterval += AuxFunctions.yearCheck(i) ? 366 : 365;
        }

        return dateInterval;
    }
}
