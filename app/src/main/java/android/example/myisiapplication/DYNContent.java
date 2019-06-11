package android.example.myisiapplication;

public class DYNContent {
    private String dyn_uid;
    private String dyn_title;
    private String  dyn_description;
    private String dyn_type;
    private String dyn_content;

    public DYNContent(String dyn_uid, String dyn_title, String dyn_description, String dyn_type, String dyn_content) {
        this.dyn_uid = dyn_uid;
        this.dyn_title = dyn_title;
        this.dyn_description = dyn_description;
        this.dyn_type = dyn_type;
        this.dyn_content = dyn_content;
    }

    public String getDyn_uid() {
        return dyn_uid;
    }

    public void setDyn_uid(String dyn_uid) {
        this.dyn_uid = dyn_uid;
    }

    public String getDyn_title() {
        return dyn_title;
    }

    public void setDyn_title(String dyn_title) {
        this.dyn_title = dyn_title;
    }

    public String getDyn_description() {
        return dyn_description;
    }

    public void setDyn_description(String dyn_description) {
        this.dyn_description = dyn_description;
    }

    public String getDyn_type() {
        return dyn_type;
    }

    public void setDyn_type(String dyn_type) {
        this.dyn_type = dyn_type;
    }

    public String getDyn_content() {
        return dyn_content;
    }

    public void setDyn_content(String dyn_content) {
        this.dyn_content = dyn_content;
    }
}
