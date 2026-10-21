package com.edc;//package com.edc;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.tmapmobility.tmap.tmapsdk.edc.EDCConst;
//import com.tmapmobility.tmap.tmapsdk.edc.TmapEDCSDK;
//
//public class EdcUtil {
//
//
//    public EdcUtil(){
//        TmapEDCSDK  edcAuthData = new TmapEDCSDK.Companion.EDCAuthData(CLIENT_ID, API_KEY, this.getPackageName(), USER_KEY, DEVICE_KEY);
//
//
//        //티맵이 자동 실행
//        TmapEDCSDK.Companion.initialize(this, edcAuthData, new TmapEDCSDK.Companion.EDCWorkListener() {
//            @Override
//            public void onInitSuccessEDC() {
//
//            }
//
//            @Override
//            public void onFinishedEDC() {
//
//            }
//
//            @Override
//            public void onHostAppStarted() {
//
//            }
//
//            @Override
//            public void onResult(@NonNull EDCConst.CommandState commandState, @NonNull Object o) {
//
//                if (commandState.equals(EDCConst.CommandState.COMMAND_GET_INFO)) {
//                    //운행 중 데이터 획득은 Bundle로 받아온다.
//                    Bundle data = (Bundle)o;
//                }
//
//            }
//
//            @Override
//            public void onFail(@NonNull EDCConst.CommandState commandState, int i, @Nullable String s) {
//
//            }
//        }, false);
//
//        //마지막 매개변수로 티맵 실행 여부를 판단
//        TmapEDCSDK.Companion.initializeWithRunHostOption(this, edcAuthData, new TmapEDCSDK.Companion.EDCWorkListener() {
//            @Override
//            public void onInitSuccessEDC() {
//                //초기화 성공
//            }
//
//            @Override
//            public void onFinishedEDC() {
//                //EDC 종료
//            }
//
//            @Override
//            public void onHostAppStarted() {
//
//            }
//
//            @Override
//            public void onResult(@NonNull EDCConst.CommandState commandState, @NonNull Object o) {
//
//            }
//
//            @Override
//            public void onFail(@NonNull EDCConst.CommandState commandState, int i, @Nullable String s) {
//
//            }
//        },false,true);
//    }
//
//
//}
