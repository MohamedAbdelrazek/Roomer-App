package mohamedabdelrazek.com.roomer.GuestsData;

import java.io.Serializable;

/**
 * Created by Mohamed on 9/10/2016.
 */
public class GuestModel  implements Serializable{

    private String  name;
    private String  Address;
    private String  Phone;
    private String  E_mail;
    private String national_id;
    private String   id;
    private int gender;
    private String  age;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getE_mail() {
        return E_mail;
    }

    public void setE_mail(String e_mail) {
        E_mail = e_mail;
    }

    public String  getAge() {
        return age;
    }

    public void setAge(String  age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }
}