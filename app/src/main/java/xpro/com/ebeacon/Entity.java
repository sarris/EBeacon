package xpro.com.ebeacon;

/**
 * Created by houyang on 2017/7/28.
 */
public class Entity {
    private String name;
    private String address;

    private String buscode;

    public String getBuscode() {
        return buscode;
    }

    public void setBuscode(String buscode) {
        this.buscode = buscode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
