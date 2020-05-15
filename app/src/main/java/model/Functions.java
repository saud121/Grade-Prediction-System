package model;

import com.google.gson.annotations.SerializedName;

public class Functions {
    @SerializedName("marks")
    private String marks;
    @SerializedName("Name")
private String Name;
    @SerializedName("CMSID")
private String CMSID;
private String Batch;
private String Department;
@SerializedName("Course_id")
private String Course_id;
@SerializedName("Course_Name")
private String Course_Name;
//@SerializedName("Teacher_Name")
//private String Teacher_Name;


    public Functions(String course_id, String course_Name, Integer credit_hours) {
        Course_id = course_id;
        Course_Name = course_Name;
        Credit_hours = credit_hours;
    }

    private Integer Credit_hours;
private String Password;

    public String getCourse_id() {
        return Course_id;
    }

    public String getCourse_Name() {
        return Course_Name;
    }

    public Integer getCredit_hours() {
        return Credit_hours;
    }

    public String getPassword() {
        return Password;
    }

    public String getDesignation() {
        return Designation;
    }

    private String Designation;



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCMSID() {
        return CMSID;
    }

    public void setCMSID(String CMSID) {
        this.CMSID = CMSID;
    }

    public String getBatch() {
        return Batch;
    }

    public void setBatch(String batch) {
        Batch = batch;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public Functions(String name, String CMSID, String batch, String department) {
        this.Name = name;
        this.CMSID = CMSID;
        this.Batch = batch;
        this.Department = department;
    }
}
