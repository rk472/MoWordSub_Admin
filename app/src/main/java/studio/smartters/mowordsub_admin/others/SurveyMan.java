package studio.smartters.mowordsub_admin.others;

public class SurveyMan {
    String name,booth,ward,number,total,id;

    public String getId() {
        return id;
    }

    public SurveyMan(String name, String booth, String ward, String number, String total, String id) {
        this.name = name;
        this.booth = booth;
        this.ward = ward;
        this.number = number;
        this.total = total;
        this.id = id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBooth() {
        return booth;
    }

    public void setBooth(String booth) {
        this.booth = booth;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
