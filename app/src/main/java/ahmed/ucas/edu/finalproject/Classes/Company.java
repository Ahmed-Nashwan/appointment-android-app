package ahmed.ucas.edu.finalproject.Classes;

import java.io.Serializable;

public class Company implements Serializable {

    private String docId;
    private String company_name;
    private String company_type;
    private int start_at;
    private int  end_at;
    private double lat;
    private double longt;
    private  String company_image;
    private int niOfAppoinment;

    public int getNiOfAppoinment() {
        return niOfAppoinment;
    }

    public void setNiOfAppoinment(int niOfAppoinment) {
        this.niOfAppoinment = niOfAppoinment;
    }

    public String getDocId() {
        return docId;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_type() {
        return company_type;
    }

    public int getStart_at() {
        return start_at;
    }

    public int getEnd_at() {
        return end_at;
    }



    public String getCompany_image() {
        return company_image;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }
    public void setStart_at(int start_at) {
        this.start_at = start_at;
    }
    public void setEnd_at(int end_at) {
        this.end_at = end_at;
    }
    public void setCompany_image(String company_image) {
        this.company_image = company_image;
    }


    public double getLat() {
        return lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }
}
