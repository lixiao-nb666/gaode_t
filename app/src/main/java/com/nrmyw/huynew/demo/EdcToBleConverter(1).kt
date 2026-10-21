//package com.infratek.t850.repository
//
//import android.util.Log
//import com.infratek.core.util.RGConstant
//import com.infratek.core.edc.AllHighWayTbtModel
//import com.infratek.t850.model.BleCommandType
//import com.infratek.t850.model.BleData
//import com.infratek.core.edc.LaneBubbleModel
//import com.infratek.t850.repository.EDCManagerImpl.DRIVEMODE
//import com.nrmyw.hud_data_lib.bean.HudLaneCountBean
//import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean
//import com.nrmyw.hud_data_lib.type.lane.HudLaneType
//import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType
//import com.nrmyw.hud_data_lib.type.notification.HudNotificationIconType
//import com.nrmyw.hud_data_lib.type.reach.HudReachType
//import com.nrmyw.hud_data_lib.type.set.HudGpsStatuType
//import com.nrmyw.hud_data_lib.type.speed.HudSpeedingShowBJType
//import com.nrmyw.hud_data_lib.type.speed.HudSpeedingTextType
//import com.nrmyw.hud_data_lib.type.speed.SpeedType
//import com.nrmyw.hud_data_lib.type.turn.HudTurnType
//import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType
//import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType
//import com.nrmyw.hud_manager_lib.HudManager
//import timber.log.Timber
//import java.time.LocalTime
//import java.util.Arrays
//import javax.inject.Inject
//import javax.inject.Singleton
//
//interface EdcToBleConverter {
//    fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    )
//}
//
//@Singleton
//class RearCameraConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//
//    var lastFirst = HudYellowStatuBjType.HIDE
//    var lastSecond = HudYellowStatuBjType.HIDE
//
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//
//        val shouldUpdate =
//            bleDataOld.rearCamera != bleData.rearCamera ||
//                    bleDataOld.rearCamera2 != bleData.rearCamera2 ||
//                    BleCommandType.SEND_REAR_CAMERA in failStatus
//
//        if (!shouldUpdate) return
//
//        val newFirst = if (bleData.rearCamera) HudYellowStatuBjType.STYLE_T700 else HudYellowStatuBjType.HIDE
//        val newSecond = if (bleData.rearCamera2) HudYellowStatuBjType.STYLE_T700 else HudYellowStatuBjType.HIDE
//
//        if (lastFirst == newFirst && lastSecond == newSecond &&
//            BleCommandType.SEND_REAR_CAMERA !in failStatus
//        ) return
//
//        lastFirst = newFirst
//        lastSecond = newSecond
//
//        HudManager.getInstance().getHudEvent().setYellowStatu(newFirst, newSecond)
//
//        return
//    }
//
//    companion object {
//        private const val CONFIG_NAME= "RearCamera"
//        const val REAR_CAMERA_CMD = 0x12.toByte()
//    }
//}
//
///**
// * 구간 단속
// */
//@Singleton
//class SectionInfoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//    fun convertTurnOff(){
//        HudManager.getInstance().getHudEvent().hideIntervalSpeed()
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if(bleDataOld.nSdiBlockSection && !bleData.nSdiBlockSection){
//            // 구간단속 종료시, 지우기 명령 보내야함
//            HudManager.getInstance().getHudEvent().hideIntervalSpeed()
//            return
//        }
//        if (bleData.nSdiBlockSection) {
//            val minute = bleData.sectionTime / 60
//            val second = bleData.sectionTime % 60
//            // 구간단속 정보 보낼때는 구간단속제외한SDI 보내면 안됨
//            HudManager.getInstance().getHudEvent().sendIntervalSpeed(
//                bleData.speedLimit,
//                bleData.sdiDistance,
//                bleData.speedAverage,
//                minute,
//                second
//            )
//            return
//        }
//        return
//    }
//    companion object{
//        private const val CONFIG_NAME= "SectionInfo"
//        private const val HIDE_INTERVAL_SPEED = 0x66.toByte()
//    }
//}
//
///**
// * SDI 정보
// */
//@Singleton
//class SdiInfoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//    private var lastSdiSend = 0
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if(bleData.nSdiBlockSection) return  // 구간 단속일때, 안보냄
//        val camType = if (bleData.cameraType == 0x27) {
//            if (bleData.speedLimit == 0) 0x26 else 0x06
//        } else {
//            bleData.cameraType
//        }
//        if (bleDataOld.cameraType != bleData.cameraType || bleDataOld.sdiDistance != bleData.sdiDistance || bleDataOld.cameraType2 != bleData.cameraType2 || bleDataOld.sdiDistance2 != bleData.sdiDistance2 || failStatus.contains(
//                BleCommandType.SEND_SDI)) {
//            // 이전에 2개를 보냈다면, 지우는거도 2개로 지워야함
//            if(camType == 0){
//                HudManager.getInstance().getHudEvent().hideBigTurnType()
//                HudManager.getInstance().getHudEvent().hideAllWarningPoint()
//                HudManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.HIDE,
//                    HudYellowStatuBjType.HIDE)
////                HudManager.getInstance().getHudEvent().sendWarningPoint(
////                    HudWarningPointType.none,
////                    0,
////                    HudWarningPointType.none,
////                    0,
////                )
//                return
////                return if(lastSdiSend == 2){
////                    HudManager.getInstance().getHudEvent().sendWarningPoint(
////                        HudWarningPointType.none,
////                        0,
////                        HudWarningPointType.none,
////                        0,
////                    )
////                }else{
////                    HudManager.getInstance().getHudEvent().hideBigTurnType()
////                }
//
//                HudManager.getInstance().getHudEvent().sendLaneHiPass()
//
//            // SDI정보
//            if(bleData.cameraType2 > 0){
//                lastSdiSend = 2
//                HudManager.getInstance().getHudEvent().sendWarningPoint(
//                    HudWarningPointType.entries.find { it.type == camType.toByte() }
//                        ?: HudWarningPointType.none,
//                    bleData.sdiDistance,
//                    HudWarningPointType.entries.find { it.type == bleData.cameraType2.toByte() }
//                        ?: HudWarningPointType.none ,
//                    bleData.sdiDistance2,
//                )
//                return
//            }else{
//                lastSdiSend = 1
//                HudManager.getInstance().getHudEvent().sendBigWarningPoint(
//                    HudWarningPointType.entries.find { it.type == camType.toByte() } ?: HudWarningPointType.none,
//                    bleData.sdiDistance
//                )
//                return
//            }
//        }
//        return
//    }
//    companion object{
//        private const val CONFIG_NAME= "SdiInfo"
//    }
//}
//
///**
// * 현재속도와 제한속도(SDI에서의 정보)
// */
//@Singleton
//class SpeedLimitTwoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//    private fun speedChanged(bleDataOld: BleData, bleData: BleData): Boolean {
//        return bleData.speed != bleDataOld.speed || bleData.speedLimit != bleDataOld.speedLimit || bleData.speedLimit2 != bleDataOld.speedLimit2
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if (speedChanged(bleDataOld, bleData)
//            || bleData.imgCrossSize != 0L
//            || failStatus.contains(BleCommandType.SEND_SPEED)
//        ) {
//            HudManager.getInstance().getHudEvent().sendWarningPointLimitedSpeed(
//                bleData.speedLimit,
//                bleData.speedLimit2
//            )
//            return
//        }
//        return
//    }
//    companion object{
//        private const val CMD = 0x69.toByte()
//        private const val CONFIG_NAME= "SpeedTwo"
//    }
//}
//
///**
// * 현재 속도
// */
//@Singleton
//class SpeedCurrentConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//    private val edcManger: EdcManager,
//) : EdcToBleConverter {
//
//    fun convertTurnOff(){
//        HudManager.getInstance().getHudEvent().sendNowSpeed(0, SpeedType.NO_DESTINATION)
//    }
//    private fun speedChanged(bleDataOld: BleData, bleData: BleData): Boolean {
//        return bleData.speed != bleDataOld.speed || bleData.speedLimit != bleDataOld.speedLimit || bleData.speedLimit2 != bleDataOld.speedLimit2
//    }
//
//    /**
//     * 0x01 리턴시, tbt와 겹침
//     */
//    private fun withoutDestination(): Byte{
//        return if(edcManger.driveMode == DRIVEMODE.SIMUL || edcManger.driveMode == DRIVEMODE.ROUTE){
//            0x00.toByte()
//        }else{
//            0x01.toByte()
//        }
//    }
//
//    private var lastWithoutDestination = 0x02.toByte()
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        val currWithoutDestination = withoutDestination()
//        if (speedChanged(bleDataOld, bleData)
//            || bleData.imgCrossSize != 0L
//            || failStatus.contains(BleCommandType.SEND_SPEED) || currWithoutDestination != lastWithoutDestination
//        ) {
//            lastWithoutDestination = currWithoutDestination
//            if(currWithoutDestination == 0x00.toByte()){
//                HudManager.getInstance().getHudEvent().sendNowSpeed(bleData.speed, SpeedType.DEF)
//                return
//            }else{
//                HudManager.getInstance().getHudEvent().sendNowSpeed(bleData.speed, SpeedType.NO_DESTINATION)
//                return
//            }
//        }
//        lastWithoutDestination = currWithoutDestination
//        return
//    }
//    companion object{
//        private const val CMD=0x68.toByte()
//        private const val CONFIG_NAME= "SpeedCurrent"
//    }
//}
//
//
//
///**
// * GPS 상태 on/off
// */
//@Singleton
//class GpsConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//
//    fun convertTurnOff() {
//        HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.CLOSE)
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if (!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if ((bleData.gpsLight != bleDataOld.gpsLight) || failStatus.contains(BleCommandType.SEND_GPS)) {
//            if (bleData.gpsLight){
//                HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.OPEN)
//                return
//            } else{
//                HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.CLOSE)
//                return
//            }
//
//        }
//        return
//    }
//
//    companion object {
//        private const val CONFIG_NAME= "Gps"
//        const val GPS_CMD = 0x09.toByte()
//    }
//}
//
///**
// * 현재 시각
// */
//@Singleton
//class TimeInfoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//
//    fun convertTurnOff(){
//        HudManager.getInstance().getHudEvent().sendTime()
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        // 시간정보
//        if (
//            (bleDataOld.lastTime == null ||bleData.lastTime == null || failStatus.contains(
//                BleCommandType.SEND_TIME))
//            || (bleData.lastTime.minute != bleDataOld.lastTime.minute)
//        ) {
//            HudManager.getInstance().getHudEvent().sendTime()
//            return
//        }
//        return
//    }
//    companion object{
//        private const val CONFIG_NAME= "TimeInfo"
//    }
//}
//
///**
// * TBT 아이콘 타입과 거리
// */
//@Singleton
//class TbtInfoConverter @Inject constructor(
//    private val edcManager: EdcManager,
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//
//    private fun convertInner(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if (bleDataOld.tbtType != bleData.tbtType || bleDataOld.tbtDistance != bleData.tbtDistance || bleDataOld.tbtType2 != bleData.tbtType2 || bleDataOld.tbtDistance2 != bleData.tbtDistance2 || failStatus.contains(
//                BleCommandType.SEND_TBT)) {
////            // tbt타입과 거리
////            if(bleData.tbtType == 0){
////                HudManager.getInstance().getHudEvent().sendTurnType(
////                    HudTurnType.none,
////                    0,
////                    HudTurnType.none,
////                    0,
////                )
////                return
////            }
//            HudManager.getInstance().getHudEvent().sendTurnType(
//                HudTurnType.entries.find { it.type == bleData.tbtType.toByte() },
//                bleData.tbtDistance,
//                HudTurnType.entries.find { it.type == bleData.tbtType2.toByte() },
//                bleData.tbtDistance2
//            )
//
//            return
//        }
//        return
//    }
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        return convertInner(bleDataOld, bleData, failStatus).also {
//            if (edcManager.driveMode != DRIVEMODE.SIMUL && edcManager.driveMode != DRIVEMODE.ROUTE) {
//                bleDataOld.tbtText = ""
//            }
//        }
//    }
//    companion object{
//        private const val CONFIG_NAME= "TbtInfo"
//    }
//}
//
//
//
///**
// * 특정 SDI에 따른 과속상태
// */
//@Singleton
//class OverSpeedConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//): EdcToBleConverter {
//
//    fun convertTurnOff() {
//        HudManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.WRITE, HudSpeedingShowBJType.NONE)
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if ((bleDataOld.isOver != bleData.isOver) || failStatus.contains(BleCommandType.SEND_OVERSPEED)) {
//            Log.e("TAG", "Send Over Speed :" + bleData.isOver)
//            if(bleData.isOver == 1){
//                HudManager.getInstance().getHudEvent().sendSpeeding(
//                    HudSpeedingTextType.RED,
//                    HudSpeedingShowBJType.RED_80
//                )
//            }else{
//                HudManager.getInstance().getHudEvent().sendSpeeding(
//                    HudSpeedingTextType.WRITE,
//                    HudSpeedingShowBJType.NONE,
//                )
//            }
//            return
//        }
//        return
//    }
//
//    companion object {
//        private const val CONFIG_NAME= "OverSpeed"
//        const val OVERSPEED_CMD = 0x1B.toByte()
//    }
//
//}
//
///**
// * 중앙화면 TBT 아이콘 깜빡임
// */
//@Singleton
//class TbtBlinkConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter {
//    private var lastBlink :Boolean = false
//
//    private fun isHighwayTurnCode(turnCode: Int): Boolean {
//        return when(turnCode){
//            RGConstant.TbtCode.EXP_OUT_LEFT,
//            RGConstant.TbtCode.EXP_IN_LEFT,
//            RGConstant.TbtCode.EXP_OUT_RIGHT,
//            RGConstant.TbtCode.EXP_IN_RIGHT,
//            RGConstant.TbtCode.SAEXP_OUT_LEFT,
//            RGConstant.TbtCode.SAEXP_IN_LEFT,
//            RGConstant.TbtCode.SAEXP_OUT_RIGHT,
//            RGConstant.TbtCode.SAEXP_IN_RIGHT,
//            RGConstant.TbtCode.ROAD_LEFT,
//            RGConstant.TbtCode.ROAD_RIGHT,
//                -> true
//            else-> false
//        }
//    }
//
//    private fun shouldBlink(bleData: BleData): Boolean {
//        if(bleData.tbtDistance == 0){
//            return false
//        }
//        if(bleData.tbtDistance < 300){
//            return true
//        }
//
//        if(bleData.tbt1ToolFee > 0){
//            return true
//        }
//        // highway
//        if(isHighwayTurnCode(bleData.tbt1TurnCode) && bleData.isHighway && bleData.tbtDistance < 2000){
//            return true
//        }
//        return false
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if(shouldBlink(bleData).also { Log.d("TbtBlinkConverter", "shouldBlink: $it") }){
//            if(lastBlink){
//                return
//            }
//            lastBlink = true
//            HudManager.getInstance().getHudEvent().iconFlicherOpen()
//            Log.d("TbtBlinkConverter", "call blink")
//            return
//        }
//
//        if(lastBlink){
//            lastBlink = false
//            HudManager.getInstance().getHudEvent().iconFlicherClose()
//            Log.d("TbtBlinkConverter", "call blink close")
//            return
//        }
//        return
//    }
//
//    companion object {
//        private const val CONFIG_NAME= "TbtBlink"
//        const val TBT_BLINK_CMD = 0x13.toByte()
//    }
//
//}
//
//@Singleton
//class TbtColorConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//): EdcToBleConverter {
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        // TBT 아이콘 색상 변경 (주간/야간)
////        val type = 0x00.toByte()
////        return HudBleDataTools.createCommand(CMD, type)
//        return
//    }
//    companion object{
//        private const val CONFIG_NAME= "TbtColor"
//        const val CMD = 0x71.toByte()
//    }
//}
//
//@Singleton
//class LaneInfoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter{
//    private fun sendHipass(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        if(!checkCanSendByRemoteConfigUseCase("HipassLaneInfo")){
//            return
//        }
//        if ((!bleDataOld.hipassLaneInfo.contentEquals(bleData.hipassLaneInfo)) || failStatus.contains(BleCommandType.SEND_LANE)) {
//            HudManager.getInstance().getHudEvent().sendLaneHiPass(HudLaneHiPassCountBean().apply {
//                for (i in 0..9){
//                    add(bleData.hipassLaneInfo[i])
//                }
//            })
//            return
//        }
//        return
//    }
//
//    private fun sendLane(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        if(!checkCanSendByRemoteConfigUseCase("LaneInfo")){
//            return
//        }
//        if ((!bleDataOld.byteLane.contentEquals(bleData.byteLane)) || failStatus.contains(BleCommandType.SEND_LANE)) {
//            val bean = HudLaneCountBean().apply {
//                bleData.byteLane.forEach { lane ->
//                    if(lane != 0x00.toByte()){
//                        val type = HudLaneType.entries.find { it.type == lane } ?: HudLaneType.F_N // fallback 직진 비활성화. 히스토리를 모르겠음
//                        add(type)
//                    }
//                }
//
//                leftIndex = bleData.pocketLeft
//                rightIndex = bleData.pocketRight
//            }
//            HudManager.getInstance().getHudEvent().sendLaneInformation(bean)
//            return
//        }
//        return
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if (bleData.hipassLaneCount > 0) {
//            sendHipass(bleDataOld, bleData, failStatus)
//            return
//        } else if (bleData.laneShow) {
//            sendLane(bleDataOld, bleData, failStatus)
//            return
//        } else {
//            Arrays.fill(bleData.hipassLaneInfo, 0)
//            Arrays.fill(bleData.byteLane, 0.toByte())
//            if ((bleDataOld.hipassLaneCount != bleData.hipassLaneCount)
//                || (bleDataOld.laneShow != bleData.laneShow)
//                || failStatus.contains(BleCommandType.SEND_LANE)
//            ) {
//                HudManager.getInstance().getHudEvent().sendLaneHide()
//                return
//            }
//
//        }
//        return
//    }
//
//}
//
//@Singleton
//class LaneTurnInfoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter{
//
//
//    private fun sendLane(
//        laneBubbleModel: LaneBubbleModel,
//    ) {
//        if(!checkCanSendByRemoteConfigUseCase("LaneTurnInfo")){
//            return
//        }
//        val bean = HudLaneCountBean().apply {
//            laneBubbleModel.createByteLane().forEach { lane ->
//                if(lane != 0x00.toByte()){
//                    val type = HudLaneType.entries.find { it.type == lane } ?: HudLaneType.F_N // fallback 직진 비활성화. 히스토리를 모르겠음
//                    add(type)
//                }
//            }
//
//            val (left, right) = laneBubbleModel.pocketCounts()
//            leftIndex = left
//            rightIndex = right
//        }
//        HudManager.getInstance().getHudEvent().sendTurnLaneInformation(bean)
//    }
//
//    private fun eqFirst(
//        o1: List<LaneBubbleModel>,
//        o2: List<LaneBubbleModel>
//    ): Boolean {
//        val a = o1.firstOrNull()
//        val b = o2.firstOrNull()
//
//        // 둘 다 없으면 같다고 봄
//        if (a == null && b == null) return true
//
//        // 하나만 있으면 다름
//        if (a == null || b == null) return false
//
//        // 둘 다 있으면 값 비교
//        return a.latitude == b.latitude && a.longitude == b.longitude
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        val curr = bleData.laneBubbleModels
//        val old = bleDataOld.laneBubbleModels
//
//        if(eqFirst(curr, old)) return
//
//        if(curr.isEmpty()){
//            HudManager.getInstance().getHudEvent().sendTurnLaneHide()
//        }else {
//            sendLane(bleData.laneBubbleModels[0])
//        }
//    }
//
//}
//
///**
// * 도로명 전송
// */
//@Singleton
//class RoadNameConverter @Inject constructor(
//    private val settingRepository: SettingRepository,
//    private val edcManager: EdcManager,
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//): EdcToBleConverter{
//    private var mLaneInfoToggleCnt = 0
//
//    fun convertTurnOff() {
//        HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.none,"none")
//    }
//
//    private fun showLocationIfAndo(bleData: BleData){
//        val laneName = bleData.laneCurrentName
//        if(laneName?.isEmpty() != true){
//            HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.location,laneName)
//            return
//        }else{
//            HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.none,"none")
//            return
//        }
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        if(bleData.hipassLaneCount > 0 || bleData.laneShow){
//            return
//        }
//        val useBottomInfo = settingRepository.getSettingValues().useBottomInfo
//        if(useBottomInfo && (edcManager.driveMode == DRIVEMODE.ANDO)){
//            showLocationIfAndo(bleData)
//            return
//        }
//
//        if (useBottomInfo && ((edcManager.driveMode == DRIVEMODE.SIMUL) || (edcManager.driveMode == DRIVEMODE.ROUTE))) {
//            if (mLaneInfoToggleCnt > 15) mLaneInfoToggleCnt = 0
//
//            if ((mLaneInfoToggleCnt / 5 == 1) && (bleData.destinationName != null) && bleData.destinationName != bleDataOld.laneText)  // && !bleData.destinationName.equals(bleDataOld.laneText))
//            {
//                bleData.laneText = bleData.destinationName
//                if (bleData.laneText == "") bleDataOld.laneSeq = 0
//                else bleDataOld.laneSeq = 1
//                HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.entries.find { it.type == bleDataOld.laneSeq.toByte() },bleData.laneText)
//                return
//            } else if ((mLaneInfoToggleCnt / 5 == 2) && (bleData.laneCurrentName != null) && bleData.laneCurrentName != bleDataOld.laneText)  // && !bleData.laneCurrentName.equals(bleDataOld.laneText))
//            {
//                bleData.laneText = bleData.laneCurrentName
//                if (bleData.laneText == "") bleDataOld.laneSeq = 0
//                else bleDataOld.laneSeq = 2
//                HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.entries.find { it.type == bleDataOld.laneSeq.toByte() },bleData.laneText)
//                return
//            } else if ((mLaneInfoToggleCnt / 5 == 3) && (bleData.laneRoadName != null) && bleData.laneRoadName != bleDataOld.laneText)  // && !bleData.laneRoadName.equals(bleDataOld.laneText))
//            {
//                bleData.laneText = bleData.laneRoadName
//                if (bleData.laneText == "") bleDataOld.laneSeq = 0
//                else bleDataOld.laneSeq = 3
//                mLaneInfoToggleCnt = 0
//                HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.entries.find { it.type == bleDataOld.laneSeq.toByte() },bleData.laneText)
//                return
//            } else {
//                bleData.laneText = bleDataOld.laneText
//                if (failStatus.contains(BleCommandType.SEND_BOTTOM_INFO)){
//                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.entries.find { it.type == bleDataOld.laneSeq.toByte() },bleDataOld.laneText)
//                    return
//                }
//            }
//            mLaneInfoToggleCnt++
//        } else {
//            if (bleDataOld.laneText != "" || failStatus.contains(BleCommandType.SEND_BOTTOM_INFO)) {
//                HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.none,"none")
//                return
//            }
//        }
//        return
//    }
//
//    companion object{
//        const val CONFIG_NAME= "RoadName"
//    }
//}
//
///**
// * 남은시간 or 도착예정시간
// */
//@Singleton
//class DestinationDistanceInfoConverter @Inject constructor(
//    private val settingRepository: SettingRepository,
//    private val edcManager: EdcManager,
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//): EdcToBleConverter{
//
//    /** 마지막으로 전송한 시점의 useRemainingTime 설정값 */
//    private var lastUseRemainingTime: Boolean? = null
//
//    fun convertTurnOff(){
//        if(!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)){
//            return
//        }
//        HudManager.getInstance().getHudEvent().hideReach()
//    }
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        val isGuiding = edcManager.driveMode == DRIVEMODE.SIMUL || edcManager.driveMode == DRIVEMODE.ROUTE
//
//        // 원격설정으로 전송이 막혀도 잔여 거리/시간은 초기화해야 하므로 canSend 검사보다 먼저 처리한다
//        if (!isGuiding) {
//            bleData.remainedDist = 0
//            bleData.remainedTime = 0
//        }
//
//        if (!checkCanSendByRemoteConfigUseCase(CONFIG_NAME)) return
//
//        if (isGuiding) {
//            sendReachInfoIfNeed(bleDataOld, bleData, failStatus)
//        } else if (isChanged(bleDataOld, bleData, failStatus)) {
//            HudManager.getInstance().getHudEvent().hideReach()
//        }
//    }
//
//    private fun sendReachInfoIfNeed(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ) {
//        val useRemainingTime = settingRepository.getSettingValues().useRemainingTime
//        // 설정이 바뀌면 거리/시간 변화가 없어도 즉시 다시 보내야 표시 형식이 갱신된다
//        val settingChanged = lastUseRemainingTime != useRemainingTime
//
//        if (!settingChanged) {
//            if (!isChanged(bleDataOld, bleData, failStatus)) return
//            if (!isEnoughChanged(bleDataOld, bleData, failStatus)) return
//        }
//        lastUseRemainingTime = useRemainingTime
//
//        val hudEvent = HudManager.getInstance().getHudEvent()
//        if (useRemainingTime) {
//            val (hour, minute) = bleData.remainedTime.toHourMinute()
//            hudEvent.sendReachInfo(bleData.remainedDist, hour, minute, HudReachType.ARRIVAL)
//        } else {
//            val arrivalTime = LocalTime.now().plusSeconds(bleData.remainedTime.toLong())
//            val type = if (arrivalTime.hour in 0..11) HudReachType.AM else HudReachType.PM
//            hudEvent.sendReachInfo(bleData.remainedDist, arrivalTime.hour, arrivalTime.minute, type)
//        }
//    }
//
//    private fun isChanged(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ): Boolean = bleDataOld.remainedDist != bleData.remainedDist ||
//            bleDataOld.remainedTime != bleData.remainedTime ||
//            failStatus.contains(BleCommandType.SEND_DEST_DIST_TIME)
//
//    /** 1km 초과 구간은 100m 단위나 분 단위가 바뀔 때만 전송한다 (잦은 갱신 방지) */
//    private fun isEnoughChanged(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ): Boolean {
//        if (bleData.remainedDist <= 1000) return true
//        return bleDataOld.remainedDist / 100 != bleData.remainedDist / 100 ||
//                bleDataOld.remainedTime.toHourMinute() != bleData.remainedTime.toHourMinute() ||
//                failStatus.contains(BleCommandType.SEND_DEST_DIST_TIME)
//    }
//
//    /** 초 단위 잔여시간을 (시, 분)으로 변환 */
//    private fun Int.toHourMinute(): Pair<Int, Int> = (this / 60) / 60 to (this / 60) % 60
//
//    companion object{
//        const val CONFIG_NAME= "DestinationDistanceInfo"
//    }
//
//}
//
//
//@Singleton
//class HighwayInfoConverter @Inject constructor(
//    private val checkCanSendByRemoteConfigUseCase: CheckCanSendByRemoteConfigUseCase,
//) : EdcToBleConverter{
//
//    data class HighwayInfoConverterUiModel(
//        val first: HighwayModel?,
//        val last: HighwayModel?,
//    ){
//        fun eqIcon(other: HighwayInfoConverterUiModel): Boolean {
//            return first?.iconType == other.first?.iconType &&
//                    last?.iconType == other.last?.iconType
//        }
//
//        fun eqContent(other: HighwayInfoConverterUiModel): Boolean {
//            return first == other.first &&
//                    last == other.last
//        }
//
//
//        companion object{
//            fun from(bleData: BleData): HighwayInfoConverterUiModel{
//                val models = bleData.allHighWayTbtModels
//
//                if(models.isEmpty()){
//                    return HighwayInfoConverterUiModel(null, null)
//                }
//
//                val lastModel = models.last()
//                val saModel = models.firstOrNull { it.tbtType == 2 } ?: lastModel
//
//                if(lastModel == saModel){
//                    return HighwayInfoConverterUiModel(
//                        first = HighwayModel.from(lastModel),
//                        last = null
//                    )
//                }
//
//                return HighwayInfoConverterUiModel(
//                    first = HighwayModel.from(saModel),
//                    last = HighwayModel.from(lastModel),
//                )
//            }
//        }
//    }
//
//    data class HighwayModel(
//        val title:String,
//        val remainDistance:Int,
//        val iconType: IconType,
//    ){
//
//        enum class IconType{
//            HIDE,
//            EXIT,
//            E1,
//            EX,
//            GS,
//            HD,
//            NH,
//            OK,
//            SK,
//            SOIL,
//            STATION;
////            NONE, IC, JC, TG,  SA;
//            fun toIcon(): HudNotificationIconType{
//                return when(this){
//                    HIDE -> HudNotificationIconType.HIDE
//                    EXIT -> HudNotificationIconType.EXIT
//                    E1 -> HudNotificationIconType.E1
//                    EX -> HudNotificationIconType.EX
//                    GS -> HudNotificationIconType.GS
//                    HD -> HudNotificationIconType.HD
//                    NH -> HudNotificationIconType.NH
//                    OK -> HudNotificationIconType.OK
//                    SK -> HudNotificationIconType.SK
//                    SOIL -> HudNotificationIconType.SOIL
//                    STATION -> HudNotificationIconType.STATION
//                }
//            }
//            companion object{
//                private fun fromBrand(brand: Int): IconType? {
//                    return when(brand){
//                        1, 12 -> SK
//                        2, 13 -> GS
//                        3, 14 -> HD
//                        4, 15 -> SOIL
//                        8, 19 -> NH
//                        10 -> OK
//                        16 -> E1
//                        22, 23 -> EX
//                        else -> null
//                    }
//                }
//
//                fun fromSa(oilBrand: Int, gasBrand: Int): IconType{
//                    return fromBrand(oilBrand)
//                        ?: fromBrand(gasBrand)
//                        ?: STATION
//                }
//            }
//        }
//        companion object{
////            /**
////             * 주유소 브랜드 (RG data)
////             **/
////            public static final class FuelBrandCode {
////                public static final int OIL_NONE = 0;
////                /**
////                 * SK 주유소
////                 */
////                public static final int OIL_SK = 1;
////                /**
////                 * GS칼텍스 주유소
////                 */
////                public static final int OIL_GS = 2;
////                /**
////                 * 현대오일 주유소
////                 */
////                public static final int OIL_HYUNDAE = 3;
////                /**
////                 * S-Oil 주유소
////                 */
////                public static final int OIL_SOIL = 4;
////                /**
////                 * TigerOil 주유소
////                 */
////                public static final int OIL_TIGER = 5;
////                /**
////                 * 인천정유 주유소
////                 */
////                public static final int OIL_INCHEON = 6;
////                /**
////                 * NC-Oil 주유소
////                 */
////                public static final int OIL_NC = 7;
////                /**
////                 * NH-Oil 주유소
////                 */
////                public static final int OIL_NH = 8;
////                /**
////                 * SevenD-Oil 주유소
////                 */
////                public static final int OIL_SEVEND = 9;
////                /**
////                 * 알뜰 주유소
////                 */
////                public static final int OIL_ECONOMIC = 10;
////                /**
////                 * 기타 주유소
////                 */
////                public static final int OIL_ETC = 11;
////                /**
////                 * SK 충전소
////                 */
////                public static final int GAS_SK = 12;
////                /**
////                 * GS칼텍스 충전소
////                 */
////                public static final int GAS_GS = 13;
////                /**
////                 * 현대오일 충전소
////                 */
////                public static final int GAS_HYUNDAE = 14;
////                /**
////                 * S-Oil 충전소
////                 */
////                public static final int GAS_SOIL = 15;
////                /**
////                 * E1 충전소
////                 */
////                public static final int GAS_E1 = 16;
////                /**
////                 * 인천정유 주유소
////                 */
////                public static final int GAS_INCHEON = 17;
////                /**
////                 * NC-Oil 충전소
////                 */
////                public static final int GAS_NC = 18;
////                /**
////                 * NH-Oil 충전소
////                 */
////                public static final int GAS_NH = 19;
////                /**
////                 * SevenD-Oil 충전소
////                 */
////                public static final int GAS_SEVEND = 20;
////                /**
////                 * 기타 충전소
////                 */
////                public static final int GAS_ETC = 21;
////                /**
////                 * EX 충전소
////                 */
////                public static final int GAS_EX = 22;
////                /**
////                 * EX 주유소
////                 */
////                public static final int OIL_EX = 23;
////            }
//
////            private fun getOilIconImg(brand: Int): Int {
////                when (brand) {
////                    RGConstant.FuelBrandCode.OIL_SK -> return R.drawable.logo_station_sk
////                    RGConstant.FuelBrandCode.OIL_GS -> return R.drawable.logo_station_gs
////                    RGConstant.FuelBrandCode.OIL_HYUNDAE -> return R.drawable.logo_station_hd
////                    RGConstant.FuelBrandCode.OIL_SOIL -> return R.drawable.logo_station_soil
////                    RGConstant.FuelBrandCode.OIL_ECONOMIC -> return R.drawable.logo_station_ok
////                    RGConstant.FuelBrandCode.OIL_NH -> return R.drawable.logo_station_nh
////                    RGConstant.FuelBrandCode.OIL_EX -> return R.drawable.logo_station_ex
////                    else -> return R.drawable.list_standard_station
////                }
////            }
//
//
//            fun from(model: AllHighWayTbtModel): HighwayModel{
//                return HighwayModel(
//                    title = model.mainText,
//                    remainDistance = model.remainDistance,
//                    //0	NONE
//                    //1	톨게이트 (TG)
//                    //2	휴게소 (SA)
//                    //3	IC
//                    //4	JC
//                    //5	회전지점 (TURN)
//                    iconType = when(model.tbtType){
//                        2 -> IconType.fromSa(model.oilCompany, model.gasCompany)
//                        3 -> IconType.EXIT
//                        else -> IconType.EXIT
//                    }
//                )
//            }
//        }
//    }
//
//    private fun sendIcon(model: HighwayInfoConverterUiModel){
//        if(model.first == null){
//            HudManager.getInstance().getHudEvent().notifictionIconHide()
//            return
//        }
//
//        val firstIcon = if(model.last == null){
//            //first 기반
//            model.first.iconType.toIcon()
//        }else{
//            //last 기반
//            model.last.iconType.toIcon()
//        }
//
//        val secondIcon = if(model.last == null){
//            HudNotificationIconType.HIDE
//        }else{
//            //first 기반
//            model.first.iconType.toIcon()
//        }
//
//        HudManager.getInstance().getHudEvent().notifictionIcon(firstIcon, secondIcon)
//    }
//
//    private fun sendContent(
//        model: HighwayInfoConverterUiModel,
//    ) {
//        if(model.first == null){
//            //donothing
//            HudManager.getInstance().getHudEvent().notifictionMsgHide()
//        }else if(model.last == null){
//            HudManager.getInstance().getHudEvent().notifictionMsg(model.first.title,model.first.remainDistance)
//        }else{
//            HudManager.getInstance().getHudEvent().notifictionMsg(model.last.title,model.last.remainDistance, model.first.title,model.first.remainDistance)
//        }
//    }
//
//    private var old :HighwayInfoConverterUiModel? = null
//
//
//    override fun convert(
//        bleDataOld: BleData,
//        bleData: BleData,
//        failStatus: Set<BleCommandType>,
//    ){
//        if(!checkCanSendByRemoteConfigUseCase("HighwayInfo")){
//            return
//        }
//
//
//        val curr = HighwayInfoConverterUiModel.from(bleData)
//        val _old = old
//        if(_old == null){
//            sendIcon(curr)
//            sendContent(curr)
//            old = curr
//            return
//        }
//
//
//        Timber.d("curr : $curr, old : $_old")
//
//        if(!curr.eqIcon(_old)){
//            sendIcon(curr)
//        }
//        if(!curr.eqContent(_old)){
//            sendContent(curr)
//        }
//        old = curr
//    }
//
//}
