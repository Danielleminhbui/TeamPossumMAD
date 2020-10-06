package com.example.mad.ui.viewEvent;

public class eventData {


    private String Title, Type, Location, Time, user;

    public eventData(String Location, String Time, String Title, String Type, String user)
    {
        this.Title = Title;
        this.Type = Type;
        this.Location = Location;
        this.Time = Time;
        this.user = user;

    }

    public eventData(){
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }




}
