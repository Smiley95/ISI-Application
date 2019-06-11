package android.example.myisiapplication;

class ServiceItem {
    public String title;
    public String pro_uid;
    public String tas_uid;
    //public int imgSource=R.drawable.administration;
    public ServiceItem(String title,String pro_uid,String tas_uid){

        this.title= title;
        this.pro_uid=pro_uid;
        this.tas_uid=tas_uid;
    }
    public ServiceItem(String title){

        this.title= title;
    }
}