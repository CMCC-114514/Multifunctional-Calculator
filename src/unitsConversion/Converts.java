package unitsConversion;

public class Converts {
    private Converts(){}

    // 长度单位名称数组
    public static final String[] LENGTH_UNITS = {
            "毫米(mm)", "厘米(cm)", "米(m)", "千米(km)",
            "英寸(in)", "英尺(ft)", "英里(mi)",
            "分(fen)", "寸(cun)", "尺(chi)", "里(li)",
            "海里(n mi)", "码(yd)"
    };

    // 面积单位名称数组
    public static final String[] AREA_UNITS = {
            "平方毫米(mm²)", "平方厘米(cm²)", "平方米(m²)",
            "公顷(ha)", "平方千米(km²)", "亩(mu)",
            "平方英寸(in²)", "平方英尺(ft²)", "平方英里(mi²)",
            "平方码(yd²)", "英亩(acre)", "公亩(a)"
    };

    // 体积单位名称数组
    public static final String[] VOLUME_UNITS = {
            "毫升(ml)", "升(L)", "立方米(m³)",
            "加仑(gal)", "液量盎司(fl oz)"
    };

    // 质量单位名称数组
    public static final String[] MASS_UNITS = {
            "克(g)", "千克(kg)", "吨(t)",
            "两(liang)", "斤(jin)", "磅(lb)", "盎司(oz)"
    };

    // 进制单位名称数组
    public static final String[] NUM_SYSTEM_UNITS = {
            "二进制", "八进制", "十进制", "十六进制"
    };

    // 速度单位名称数组
    public static final String[] SPEED_UNITS = {
            "米/秒(m/s)", "千米/小时(km/h)", "英里/小时(mph)", "节(knots)"
    };

    // 温度单位名称数组
    public static final String[] TEMPERATURE_UNITS = {
            "摄氏度(C)", "华氏度(F)", "开尔文(K)"
    };

    //换算方法1：长度换算
    public static double[] length(byte choose, double num) {

        //将选择的单位转化为标准单位
        double std = AuxFunctions.getLengthStd(choose, num);

        //计算结果
        return new double[]{std*1e3, std*1e2, std, std/1e3, std*39.37, std*3.281, std/1609.347, std*300, std*30, std*3, std/500, std/0.9144, std/1852};
    }

    //换算方法2：面积换算
    public static double[] area(byte choose, double num) {

        //将选择的单位转化为标准单位
        double std = AuxFunctions.getAreaStd(choose, num);

        //计算结果
        return new double[]{std*1e6, std*1e4, std, std*100, std/1e6, std/(2000.0 / 3.0), std*1550, std*10.7639, std/2589988.1, std*1.196, std/4046.94, std/1e4};
    }

    //换算方法3：体积换算
    public static double[] volume(byte choose, double num) {

        //将选择的单位转化为标准单位
        double std = AuxFunctions.getVolumeStd(choose, num);

        //计算结果
        return new double[]{std*1e6, std*1e3, std, std*264.172, std*33818.06};
    }

    //换算方法4：质量换算
    public static double[] mass(byte choose, double num) {
        //将选择的单位转化为标准单位
        double std = AuxFunctions.getMassStd(choose, num);

        //计算结果
        return new double[]{std*1e3, std, std/1e3, std*20, std*2, std*2.2046, std*35.274};
    }

    //换算方法5：进制换算
    public static String[] numSystem(byte choose, String num) {
        //将选择的进制转化为标准进制
        int std = AuxFunctions.getNumSystemStd(choose, num);

        //计算结果
        return new String[]{Integer.toBinaryString(std), Integer.toOctalString(std), Integer.toString(std), Integer.toHexString(std)};
    }

    //换算方法6：速度换算
    public static double[] speed(byte choose, double num) {
        //将选择的单位转化为标准单位
        double std = AuxFunctions.getSpeedStd(choose, num);

        //计算结果
        return new double[]{std, std*3.6, std/0.44704, std/0.514444};
    }

    //换算方法7：温度换算
    public static double[] temperature(byte choose, double num) {
        //将选择的单位转化为标准单位
        double std = AuxFunctions.getTemperatureStd(choose, num);

        //计算结果
        return new double[]{std, std*1.8+32, std+273.15};
    }
}
