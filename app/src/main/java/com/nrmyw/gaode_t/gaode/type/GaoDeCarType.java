package com.nrmyw.gaode_t.gaode.type;

public enum GaoDeCarType {
    //0-燃油客车，1-燃油货车，2-纯电动客车，3-纯电动货车，4-插电式混动汽车，5-插电式混动货车，11-摩托车
    //0-Fuel passenger car, 1-Fuel truck, 2-Pure electric passenger car, 3-Pure electric truck, 4-Plug-in hybrid car, 5-Plug-in hybrid truck, 11-Motorcycle
    Fuel_passenger_car(0),
    Fuel_truck(1),
    Pure_electric_passenger_car(2),
    Pure_electric_truck(3),
    Plug_in_hybrid_car(4),
    Plug_in_hybrid_truck(5),
    Motorcycle(6),
    ;
    int index;

    GaoDeCarType(int index){
        this.index=index;
    }

    public int getIndex() {
        return index;
    }


    //是否是火车
    public boolean isTruck(){
        if (this==Pure_electric_truck||this==Plug_in_hybrid_truck){
            return true;
        }
        return false;
    }

}
