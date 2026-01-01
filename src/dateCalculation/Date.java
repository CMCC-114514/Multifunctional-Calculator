package dateCalculation;

//日期类
public class Date {

    int year;
    int month;
    int day;

    public Date() {
        this.year = this.month = this.day = 0;
    }

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void showDate() {
        System.out.print(year + "年" + month + "月" + day + "日");
    }

    public void showNumOfYears() {
        System.out.print(year + "年" + month + "个月" + day + "天");
    }
}
