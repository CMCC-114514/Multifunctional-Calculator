package unitsConversion;

public class AuxFunctions {
    private AuxFunctions(){}

    public static double getAreaStd(byte choose, double num) {
        double std = 0;    //标准单位（平方米）
        switch (choose) {               //将对应单位转换成平方米
            case 1 -> std = num / 1e6;              //平方毫米
            case 2 -> std = num / 1e4;              //平方厘米
            case 3 -> std = num;                    //平方米
            case 4 -> std = num / 100;              //平方分米
            case 5 -> std = num * 1e6;              //平方公里
            case 6 -> std = num * (2000.0 / 3.0);   //市亩
            case 7 -> std = num / 1550;             //平方英寸
            case 8 -> std = num / 10.7639;          //平方英尺
            case 9 -> std = num * 2589988.1;        //平方英里
            case 10 -> std = num / 1.196;           //平方码
            case 11 -> std = num * 4046.94;         //英亩
            case 12 -> std = num * 1e4;             //公顷
        }
        return std;
    }

    public static double getLengthStd(byte choose, double num) {
        double std = 0;    //标准单位（米）
        switch (choose) {               //将对应单位转换成米
            case 1 -> std = num / 1e3;      //毫米
            case 2 -> std = num / 1e2;      //厘米
            case 3 -> std = num;            //米
            case 4 -> std = num * 1e3;      //公里
            case 5 -> std = num / 39.37;    //英寸
            case 6 -> std = num / 3.281;    //英尺
            case 7 -> std = num * 1609.347; //英里
            case 8 -> std = num / 300;      //分
            case 9 -> std = num / 30;       //寸
            case 10 -> std = num / 3;       //尺
            case 11 -> std = num * 500;     //里
        }
        return std;
    }

    public static double getVolumeStd(byte choose, double num) {
        double std = 0;    //标准单位（立方米）
        switch (choose) {               //将对应单位转换成立方米
            case 1 -> std = num / 1e6;          //毫升
            case 2 -> std = num / 1e3;          //升
            case 3 -> std = num;                //立方米
            case 4 -> std = num / 264.172;      //加仑
            case 5 -> std = num / 33818.06;     //盎司
        }
        return std;
    }

    public static double getMassStd(byte choose, double num) {
        double std = 0;    //标准单位（千克）
        switch (choose) {               //将对应单位转换成千克
            case 1 -> std = num / 1e3;          //克
            case 2 -> std = num;                //千克
            case 3 -> std = num * 1e3;          //吨
            case 4 -> std = num / 20;           //两
            case 5 -> std = num / 2;            //斤
            case 6 -> std = num / 2.2046;       //磅
            case 7 -> std = num / 35.274;       //盎司
        }
        return std;
    }

    public static int getNumSystemStd(byte choose, String num) {
        int std = 0;
        switch (choose) {
            case 1 -> std = Integer.parseInt(num, 2);
            case 2 -> std = Integer.parseInt(num, 8);
            case 3 -> std = Integer.parseInt(num);
            case 4 -> std = Integer.parseInt(num, 16);
        }
        return std;
    }
}
