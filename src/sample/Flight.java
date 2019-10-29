package sample;

public class Flight {
    private String date_;
    private String departure_;
    private String arrive_;
    private Float last_;
    private Integer econom_;
    private Integer business_;
    private String time_depar;
    private String bort_;

    public Flight(String date_, String departure_, String arrive_, Float last_, Integer econom_, Integer business_, String time_depar, String bort_) {
        this.date_ = date_;
        this.departure_ = departure_;
        this.arrive_ = arrive_;
        this.last_ = last_;
        this.econom_ = econom_;
        this.business_ = business_;
        this.time_depar = time_depar;
        this.bort_ = bort_;
    }

    public Flight() {
    }

    public String getDate_() {
        return date_;
    }

    public void setDate_(String date_) {
        this.date_ = date_;
    }

    public String getDeparture_ (){
        return departure_;
    }

    public void setDeparture_ (String departure_){
        this.departure_ = departure_;
    }

    public String getArrive_ (){
        return arrive_;
    }

    public void setArrive_ (String arrive_){
        this.arrive_ = arrive_;
    }

    public Float getLast_ (){
        return last_;
    }

    public void setLast_ (Float last_){
        this.last_ = last_;
    }

    public Integer getEconom_ (){
        return econom_;
    }

    public void setEconom_ (Integer econom_){
        this.econom_ = econom_;
    }

    public Integer getBusiness_ (){
        return business_;
    }

    public void setBusiness_ (Integer business_){
        this.business_ = business_;
    }

    public String getTime_depar() {
        return time_depar;
    }

    public void setTime_depar(String time_depar) {
        this.time_depar = time_depar;
    }

    public String getBort_() {
        return bort_;
    }

    public void setBort_(String bort_) {
        this.bort_ = bort_;
    }
}
