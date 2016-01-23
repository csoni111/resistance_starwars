package com.droidwars.starwars.resistance.ListAdapter;

/**
 * Created by Chirag on 18-01-2016.
 */
public class ListModel {

    private int id;
    private  String WarriorName = "";
    private  String WarriorImage = "";
    private  String side = "";
    private String gender = "";


    /*********** Set Methods ******************/

    public void setId(int i) {
        this.id = i;
    }

    public void setWarriorName(String WarriorName)
    {
        this.WarriorName = WarriorName;
    }

    public void setWarriorImage(String Image)
    {
        this.WarriorImage = Image;
    }

    public void setSide(String s)
    {
        this.side = s;
    }

    public void setGender(String g) {
        this.gender = g;
    }


    /*********** Get Methods ****************/

    public int getId() {
        return id;
    }

    public String getWarriorName()
    {
        return this.WarriorName;
    }

    public String getWarriorImage()
    {
        return this.WarriorImage;
    }

    public String getSide()
    {
        return this.side;
    }

    public String getGender() {
        return this.gender;
    }

}
