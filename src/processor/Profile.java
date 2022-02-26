package processor;

/**
 This class creates a Profile object based off of a users first name, last name, and date of birth. Contains a toString
 method which returns all information as a string and an equals method which compares two Profile objects to determine
 if they are the same or not.
 @author Azaan Siddiqi, Karan Patel
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     Converts profile information into a string and returns said string.
     @return a string containing the Profile's first name, last name, and date of birth, all in that order.
     */
    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + dob.toString();
    }

    /**
     Compares the first name, last name, and date of birth of two profiles in order to determine whether they are the
     same or not.
     @param obj Profile object being compared to the original Profile object.
     @return
     */
    public boolean equals(Object obj){
        if (obj instanceof Profile){
            Profile secondProfile = (Profile) obj;
            if (this.fname.toUpperCase().equals(secondProfile.fname.toUpperCase()) &&
                    this.lname.toUpperCase().equals(secondProfile.lname.toUpperCase()) &&
                    this.dob.compareTo(secondProfile.dob) == 0){
                return true;
            }
        }
        return false;
    }
}
