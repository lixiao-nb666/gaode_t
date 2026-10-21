package com.nrmyw.gaode_t.gaode.type;

public enum GaoDeBroadCastType {

    concise_broadcast(1),
    detailed_broadcast(2),
    mute_mode(3),
    ;
    int index;
    GaoDeBroadCastType(int index){
        this.index=index;
    }

    public int getIndex() {
        return index;
    }
}
