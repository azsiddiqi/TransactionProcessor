package processor;

public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return this.fname + " " + this.lname + " " + dob.toString();
    }

    public boolean equals(Object obj){
        if (obj instanceof Profile){
            Profile secondProfile = (Profile) obj;
            if (this.fname.equals(secondProfile.fname) && this.lname.equals(secondProfile.lname)
                && this.dob.compareTo(secondProfile.dob) == 0){
                return true;
            }
        }
        return false;
    }
}
