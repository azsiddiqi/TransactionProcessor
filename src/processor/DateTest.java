package processor;

import static org.junit.Assert.*;


public class DateTest {


    @org.junit.Test
    public void valid_day_and_year_but_month_greater_than_twelve() {
        //Test 1: Tests that the value for the month can't exceed 12
        Date testDate1 = new Date("13/1/2022");
        assertFalse(testDate1.isValid());
    }


    @org.junit.Test
    public void valid_day_and_year_but_month_less_than_one() {
        //Test 2: Tests that the value for the month can't be lower than one
        Date testDate2 = new Date("0/1/2022");
        assertFalse(testDate2.isValid());
    }


    @org.junit.Test
    public void valid_year_and_month_with_thirty_one_days_but_days_greater_than_thirty_one() {
        //Test 3: Tests that the months January, March, May, July, August, October, December can’t have more than 31 days
        Date testDate3 = new Date("3/32/2022");
        assertFalse(testDate3.isValid());
    }


    @org.junit.Test
    public void valid_year_and_month_with_thirty_one_days_but_days_less_than_one() {
        //Test 4: Tests that the months January, March, May, July, August, October, December can’t have a day value lower than 1
        Date testDate4 = new Date("5/0/2022");
        assertFalse(testDate4.isValid());
    }


    @org.junit.Test
    public void valid_year_and_month_with_thirty_days_but_days_greater_than_thirty_one() {
        //Test 5: Tests that the months April, June, September, November can’t have a day value exceeding 30
        Date testDate5 = new Date("4/31/2022");
        assertFalse(testDate5.isValid());
    }


    @org.junit.Test
    public void valid_year_and_month_with_thirty_days_but_days_less_than_one() {
        //Test 6: Tests that the months April, June, September, November can’t have a day value below 1
        Date testDate6 = new Date("6/0/2022");
        assertFalse(testDate6.isValid());
    }


    @org.junit.Test
    public void february_non_leap_year_with_day_greater_than_twenty_eight() {
        //Test 7: Tests that for February, the maximum value for the day is 28 in a non leap year, thus it can't exceed 28
        Date testDate7 = new Date("2/29/2022");
        assertFalse(testDate7.isValid());
    }


    @org.junit.Test
    public void february_with_day_less_than_one() {
        //Test 8: Tests that for February, the minimum value for the day is 1
        Date testDate8 = new Date("2/0/2022");
        assertFalse(testDate8.isValid());
    }


    @org.junit.Test
    public void february_leap_year_with_day_equal_to_29() {
        //Test 9: Tests that for February, the maximum value for the day is 29 during a leap year
        Date testDate9 = new Date("2/29/2024");
        assertTrue(testDate9.isValid());
    }


    @org.junit.Test
    public void february_leap_year_with_day_greater_than_twenty_nine() {
        //Test 10: Tests that for February, the maximum value for the day is 29 during a leap year, thus it can’t exceed 29.
        Date testDate10 = new Date("2/30/2024");
        assertFalse(testDate10.isValid());
    }


    @org.junit.Test
    public void valid_month_and_day_but_year_is_less_than_zero() {
        //Test 11: Tests that the year must be greater than or equal to 0
        Date testDate11 = new Date("1/1/-1");
        assertFalse(testDate11.isValid());
    }


    @org.junit.Test
    public void valid_date_and_month_and_year() {
        //Test 12: Test that a calendar date is valid
        Date testDate12 = new Date("12/29/2020");
        assertTrue(testDate12.isValid());
    }


    @org.junit.Test
    public void valid_date_and_month_and_large_year() {
        //Test 13: Tests that if the month and day is valid, but the year is large, then the date is still valid.
        Date testDate13 = new Date("10/19/202020");
        assertTrue(testDate13.isValid());
    }


    @org.junit.Test
    public void valid_date_and_month_and_small_year() {
        //Test 14: Tests that if the month and day is valid, but the year is small, then the date is still valid.
        Date testDate14 = new Date("10/19/15");
        assertTrue(testDate14.isValid());
    }
}