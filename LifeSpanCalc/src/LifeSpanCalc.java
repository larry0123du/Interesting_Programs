import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * LifeSpan Calculator
 * version 0.0.1
 * @author Ruihang Du, du113@purdue.edu
 * This program is to calculate how many seconds the user has lived.
 * Created by Ruihang Du on 15-9-16.
 */
public class LifeSpanCalc {
    private String birthday; //User's date of birth
    private String birthTime; //User's exact time of birth

    public LifeSpanCalc(String birthday, String birthTime) {
        this.birthday = birthday;
        this.birthTime = birthTime;
    }

    /*
    This method to calculate how many years lived till now
    @param birthday;
     */
    public int calcYearDifference(String birthday) {
        String currentTime = new SimpleDateFormat("MMddyyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        //Get current time
        int year;
        if (birthday == null) {
            return 0;
        }
        else {
            year = Integer.parseInt(birthday.substring(6, birthday.length()));
            return Integer.parseInt(currentTime.substring(4, currentTime.indexOf(" "))) - year;
        }
    }

    /*
    This method calculates how many days passed by in the current year
    @param month;
    @param day;
     */
    public int calcDays(int month, int day) {
        int totalDays; //Total days passed by in the year
        int[] calendar = new int[12]; //Setup a calender
        for (int i = 0; i < calendar.length; i++) {
            switch (i) {
                case 0:case 2:case 4:case 6:case 7:case 9:case 11:
                    calendar[i] = 31;
                    break;
                case 3:case 5:case 8:case 10:
                    calendar[i] = 30;
                    break;
                case 1:
                    calendar[i] = 28;
                    break;
            }
        }
        totalDays = day;
        for (int j = 0; j < month - 1; j++) {
            totalDays += calendar[j];
        }
        return totalDays;
    }

    /*
    This method calculates how many seconds passed by in the current day
    @param birthTime
     */
    public int calcSeconds(String birthTime) {
        int hour; //The exact hour of user's birth time
        int minute; //The exact minute of user's birth time
        int second; //The exact second of user's birth time
        if (birthTime == null) {
            hour = 0;
            minute = 0;
            second = 0;
        }
        else {
            hour = Integer.parseInt(birthTime.substring(0, 2));
            minute = Integer.parseInt(birthTime.substring(3, 5));
            second = Integer.parseInt(birthTime.substring(6, birthTime.length()));
        }
        return hour * 3600 + minute * 60 + second; //Return the exact seconds passed when the user is born
    }

    /*
    This method calculates exactly how many seconds the user lived on this world
    @param birthday
    @param birthTime
     */
    public int calcSecondsLived(String birthday, String birthTime) {
        String currentTime = new SimpleDateFormat("MMddyyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        int month; //month of birth
        int day; //day of birth
        int yearDifference; //The difference of current year and the year user is born
        int dayDifference; //The difference of the day of user's birth in the year and the current date
        int secondDifference; //The difference of current time of the day and user's time of birth
        int numLeapYear; //The number of leap years between the current year and the year of birth
        if (birthday == null) {
            return 0;
        }
        else {
            yearDifference = calcYearDifference(birthday);
            numLeapYear = yearDifference / 4;
            month = Integer.parseInt(birthday.substring(0, 2));
            day = Integer.parseInt(birthday.substring(3, 5));
            int daysSinceBirthYear = calcDays(month, day);
            int secondsSinceBirthday = calcSeconds(birthTime);
            month = Integer.parseInt(currentTime.substring(0, 2));
            day = Integer.parseInt(currentTime.substring(2, 4));
            int daysSinceThisYear = calcDays(month, day);
            int secondsSinceToday = calcSeconds(currentTime.substring(currentTime.indexOf(" ") + 1, currentTime.length()
            ));
            dayDifference = daysSinceThisYear - daysSinceBirthYear;
            secondDifference = secondsSinceToday - secondsSinceBirthday;
            if (secondDifference < 0) {
                dayDifference -= 1;
                secondDifference += 86400;
            }
            if (dayDifference < 0) {
                yearDifference -= 1;
                dayDifference += 365;
            }
            return yearDifference * 31536000 + (dayDifference + numLeapYear) * 86400 + secondDifference;
            //Return the total seconds user has lived
        }
    }

    /*
    This method checks if the input date of birth is valid
    @param birthday
     */
    public boolean checkBirthdayValid(String birthday) {
        int[] calendar = new int[12];
        for (int i = 0; i < calendar.length; i++) {
            switch (i) {
                case 0:case 2:case 4:case 6:case 7:case 9:case 11:
                    calendar[i] = 31;
                    break;
                case 3:case 5:case 8:case 10:
                    calendar[i] = 30;
                    break;
                case 1:
                    calendar[i] = 28;
                    if (Integer.parseInt(birthday.substring(6, birthday.length())) % 4 == 0) {
                        calendar[i] = 29;
                    }
                    break;
            }
        }
        if (birthday.indexOf("/") < 1) {
            return false;
        }
        int lengthOfMonth = birthday.indexOf("/");
        String withoutMonth = birthday.substring(birthday.indexOf("/") + 1, birthday.length());
        int lengthOfDate = withoutMonth.indexOf("/");
        String withoutDate = withoutMonth.substring(withoutMonth.indexOf("/") + 1, withoutMonth.length());
        int lengthOfYear = withoutDate.indexOf("/");
        if (lengthOfMonth != 2 || lengthOfDate != 2 || lengthOfYear != -1 ||
                birthday.length() != 10 || Integer.parseInt(birthday.substring(0, 2)) > calendar.length ||
                Integer.parseInt(birthday.substring(3, 5)) > calendar[Integer.parseInt(birthday.substring(0, 2))]) {
            return false;
        }
        else {
            return true;
        }
    }

    /*
    This method checks if the input time of birth is valid
    @param birthTime
     */
    public boolean checkBirthTimeValid(String birthTime) {
        if (birthTime.indexOf(":") < 1) {
            return false;
        }
        int lengthOfHour = birthTime.indexOf(":");
        String withoutHour = birthTime.substring(birthTime.indexOf(":") + 1, birthTime.length());
        int lengthOfMinute = withoutHour.indexOf(":");
        String withoutMinute = withoutHour.substring(withoutHour.indexOf(":") + 1, withoutHour.length());
        int lengthOfSecond = withoutMinute.indexOf(":");
        if (lengthOfHour != 2 || lengthOfMinute != 2 || withoutMinute.length() != 2 || lengthOfSecond != -1
                || Integer.parseInt(birthTime.substring(0, 2)) > 23 || Integer.parseInt(birthTime.substring(3, 5))
                > 59 || Integer.parseInt(birthTime.substring(6, birthTime.length())) > 59) {
            return false;
        }
        else {
            return true;
        }
    }
}
