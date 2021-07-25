package ahmed.ucas.edu.finalproject.Classes;

import java.io.Serializable;

public class Appointment implements Serializable {

    private String docId;
    private String notice;
    private String company_name;
    private String company_id;
    private int day;
    private int year;
    private int month;
    private int hour;
    private int minut;
    private long time;


    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setTime(long time) {
        this.time = time;

    }

    public long getTime() {
        return time;
    }

    public int getMinut() {
        return minut;
    }

    public void setMinut(int minut) {
        this.minut = minut;
    }


    public String getDocId() {
        return docId;
    }


    public String getNotice() {
        return notice;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }



    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }


    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

}
