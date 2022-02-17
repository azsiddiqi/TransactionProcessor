package processor;

public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    @Override
    public String toString() {
        return this.fname + this.lname + dob.toString();
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
