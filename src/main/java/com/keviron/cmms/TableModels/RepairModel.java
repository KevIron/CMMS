package com.keviron.cmms.TableModels;

public class RepairModel {

    String ID;
    String ClientName;
    String ClientSurname;
    String CarBrand;
    String CarName;
    String WIN;
    String Date;
    String RepairActivities;

    public RepairModel(String ID, String clientName, String clientSurname, String carBrand, String carName, String WIN, String date, String repairActivities) {
        this.ID = ID;
        ClientName = clientName;
        ClientSurname = clientSurname;
        CarBrand = carBrand;
        CarName = carName;
        this.WIN = WIN;
        Date = date;
        RepairActivities = repairActivities;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getClientSurname() {
        return ClientSurname;
    }

    public void setClientSurname(String clientSurname) {
        ClientSurname = clientSurname;
    }

    public String getCarBrand() {
        return CarBrand;
    }

    public void setCarBrand(String carBrand) {
        CarBrand = carBrand;
    }

    public String getCarName() {
        return CarName;
    }

    public void setCarName(String carName) {
        CarName = carName;
    }

    public String getWIN() {
        return WIN;
    }

    public void setWIN(String WIN) {
        this.WIN = WIN;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRepairActivities() {
        return RepairActivities;
    }

    public void setRepairActivities(String repairActivities) {
        RepairActivities = repairActivities;
    }
}
