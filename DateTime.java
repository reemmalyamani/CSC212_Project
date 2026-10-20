public class DateTime implements IDateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    // Constructor
    public DateTime(int year, int month, int day, int hour, int minute) {
        if (year < 1 || month < 1 || month > 12 || hour < 0 || hour > 23 || minute < 0 || minute > 59)
            throw new IllegalArgumentException("invalid date or time");

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (month == 2 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)))
            daysInMonth[1] = 29;

        if (day < 1 || day > daysInMonth[month - 1])
            throw new IllegalArgumentException("invalid day");

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    // Parse date and time
    public static DateTime parse(String str) {
        String[] parts = str.trim().split("\\s+");

        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");

        int month = Integer.parseInt(dateParts[0].trim());
        int day = Integer.parseInt(dateParts[1].trim());
        int year = Integer.parseInt(dateParts[2].trim());

        int hour = Integer.parseInt(timeParts[0].trim());
        int minute = Integer.parseInt(timeParts[1].trim());

        return new DateTime(year, month, day, hour, minute);
    }

    // Getters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d",
                month, day, year, hour, minute);
    }

    public int compareTo(IDateTime other) {
        if (year != other.getYear())
            return year < other.getYear() ? -1 : 1;

        if (month != other.getMonth())
            return month < other.getMonth() ? -1 : 1;

        if (day != other.getDay())
            return day < other.getDay() ? -1 : 1;

        if (hour != other.getHour())
            return hour < other.getHour() ? -1 : 1;

        if (minute != other.getMinute())
            return minute < other.getMinute() ? -1 : 1;

        return 0;
    }

    public String toString() {
        return format();
    }
}
