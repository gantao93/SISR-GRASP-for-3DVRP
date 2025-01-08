package util;

public class TimeProcess {
    public static String getDuration(long milliseconds){
        long totalSeconds = milliseconds/1000;
        long day = totalSeconds/(24*3600);
        long hour = (totalSeconds-day*24*3600)/3600;
        long minute = (totalSeconds-day*24*3600-hour*3600)/60;
        long second = totalSeconds - day*24*3600-hour*3600-minute*60;
        return "" + day +" day " + hour + " hours " + minute + " minutes " + second + " seconds";
    }
}
