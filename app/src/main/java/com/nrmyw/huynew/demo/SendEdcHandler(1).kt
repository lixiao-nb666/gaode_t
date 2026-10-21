//package com.infratek.t850.repository
//
//import android.annotation.SuppressLint
//import android.graphics.Bitmap
//import android.graphics.Matrix
//import com.infratek.core.util.isSimilarImage
//import com.infratek.core.util.EdcUtils
//import com.infratek.t850.model.BleCommandType
//import com.infratek.t850.model.BleData
//import com.infratek.t850.repository.EDCManagerImpl.DRIVEMODE
//import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType
//import com.nrmyw.hud_manager_lib.HudManager
//import com.nrmyw.hud_manager_lib.type.HudDevice
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.SupervisorJob
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.withContext
//import timber.log.Timber
//import javax.inject.Inject
//import javax.inject.Singleton
//
//
//@SuppressLint("MissingPermission")
//@Singleton
//class SendEdcHandler @Inject constructor(
//    private val qaHelper: QaHelper,
//    private val edcManager: EdcManager,
//    private val bleManager: BleManager,
//    private val roadNameConverter: RoadNameConverter,
//    private val tbtConv: TbtInfoConverter,
//    private val destinationDistanceInfoConverter: DestinationDistanceInfoConverter,
//    private val naviProgressConverter: NaviProgressConverter,
//    //
//    private val sectionInfoConverter: SectionInfoConverter,
//    private val sdiInfoConverter: SdiInfoConverter,
//    private val rearCameraConverter: RearCameraConverter,
//    private val speedLimitTwoConverter: SpeedLimitTwoConverter,
//    private val speedCurrentConverter: SpeedCurrentConverter,
//    private val overSpeedConverter: OverSpeedConverter,
//    private val tbtBlinkConverter: TbtBlinkConverter,
//    private val tbtColorConverter: TbtColorConverter,
//    private val laneInfoConverter: LaneInfoConverter,
//    private val gpsConverter: GpsConverter,
//    private val timeInfoConverter: TimeInfoConverter,
//    private val laneTurnInfoConverter: LaneTurnInfoConverter,
//    private val highwayInfoConverter: HighwayInfoConverter,
//) {
//    val coroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
//
//    init {
//
//        edcManager.edcDataFlow.onEach {
//            send()
//        }.launchIn(coroutineScope)
//
//        edcManager.turnOffFlow.onEach {
//            turnOff()
//        }.launchIn(coroutineScope)
////        qaHelper.configFlow.onEach { config ->
////            HudManager.getInstance().getHudSetConfig().apply {
////                setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS.also { it.qualityV = config.progressQuality })
////                BleSendBitmapQualityType.ULTRA_HIGH.qualityV = config.complexLoadQuality
////                BleSendBitmapQualityType.ULTRA_HIGH.zoomScaling = 0.8f
////                setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH)
////                Timber.d("config 적용 progressQuality ${config.progressQuality}, complexLoadQuality ${config.complexLoadQuality}")
////            }
////        }.launchIn(coroutineScope)
//
//        bleManager.hudDevice.onEach {
//            when(it){
//                HudDevice.T800,
//                HudDevice.T3000,
//                HudDevice.T3000_1,
//                HudDevice.H3000,
//                HudDevice.H3000_1->{
//
//                }
//                HudDevice.T700,
//                HudDevice.T700_1,
//                HudDevice.T700_2->{
//
//                }
//                HudDevice.T850,
//                HudDevice.T850_1,
//                HudDevice.T850_2,
//                HudDevice.T900,
//                HudDevice.T900_1,
//                HudDevice.T900_2->{
////                    HudManager.getInstance().getHudSetConfig().imageMaxH = 225
////                    HudManager.getInstance().getHudSetConfig().imageMaxW = 200
////
////                    HudManager.getInstance().getHudSetConfig().apply {
////                        BleSendBitmapQualityType.ULTRA_HIGH.qualityV = 66
////                        BleSendBitmapQualityType.ULTRA_HIGH.zoomScaling = 0.8f
////                        setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH)
////                    }
//                }
//                HudDevice.H5000,
//                HudDevice.H5000_1,
//                HudDevice.H5000_2,
//                HudDevice.HUD_T,
//                HudDevice.HUT_T -> {
//
//                }
//                null -> {
//
//                }
//
//                HudDevice.HUYNEW_AR_DEBUG,
//                HudDevice.HUYNEW_AR -> {
//
//                }
//            }
//        }.launchIn(coroutineScope)
//    }
//
//    private var sendTick = 0
//
//    private var tbtIdx = 0
//
//
//    private var mNameToggleCnt = 0
//
//
//    fun heartBeat(){
//        tbtIdx = 0
//    }
//    private val mMtu = 512
//    var bleData : BleData = edcManager.bleData
//    private var bleDataOld : BleData = BleData()
//
//    private suspend fun turnOff(){
//        bleData.reset()
//        roadNameConverter.convertTurnOff()
//        gpsConverter.convertTurnOff()
//        overSpeedConverter.convertTurnOff()
//        HudManager.getInstance().getHudEvent().hideImage()
//        naviProgressConverter.hide()
//        sectionInfoConverter.convertTurnOff()
//        timeInfoConverter.convertTurnOff()
//        destinationDistanceInfoConverter.convertTurnOff()
//        speedCurrentConverter.convertTurnOff()
//        send()
//        Timber.d( "turnOffAll")
//    }
//
//
//
//    private val converters = listOf(
//        tbtConv,
//        sectionInfoConverter,
//        sdiInfoConverter,
//        rearCameraConverter,
//        speedLimitTwoConverter,
//        overSpeedConverter,
//        tbtBlinkConverter,
//        tbtColorConverter,
//        laneInfoConverter,
//        gpsConverter,
//        timeInfoConverter,
//        laneTurnInfoConverter,
//        speedCurrentConverter,
//    )
//
//
//    suspend fun send(){
//        if(!checkCanSend()){
//            return
//        }
//
//        sendInner()
//
//        bleManager.failStatus.clear()
//        bleDataOld.copy(bleData)
//
//        bleData.reset()
//    }
//
//    private fun checkCanSend(): Boolean{
//        if (bleManager.currentHud.value == null) {
//            bleDataOld.reset() // BT연결 끊겼을때 if문으로인해 저장된값이 초기화되지 않는 현상이 있어 연결 끊어졌을때 초기화코드 추가
//            Timber.d("BLE Status Disconnected ============== ")
//            return false
//        }
//
//        if (bleManager.currentHud.value?.isValidSerialNumber() != true) {
//            Timber.d("Serial Number is not set ${bleManager.currentHud.value?.serialNumber}")
//            return false
//        }
//        if(!edcManager.canSend){
//            Timber.d("canSend is false")
//            return false
//        }
//
//        return true
//    }
//
//    private suspend fun sendInner(){
//        Timber.d("Send Inner")
//        if(bleDataOld.tbtType != bleData.tbtType && bleData.tbtType != 0) {
//            Timber.d("Send TBT names make toggle 0 ${bleData.tbtType} ${bleDataOld.tbtType}")
//            mNameToggleCnt = 0
//        }
//        if(bleData.tbtType2 != bleDataOld.tbtType2 && bleData.tbtType2 != 0) {
//            Timber.d("Send TBT names2 make toggle 0 ${bleData.tbtType2} ${bleDataOld.tbtType2}")
//            mNameToggleCnt = 0
//        }
//
//        converters.forEach { converter ->
//            converter.convert(bleDataOld, bleData, bleManager.failStatus)
//        }
//        destinationDistanceInfoConverter.convert(bleDataOld, bleData, bleManager.failStatus)
//
//
////        tbtConv.convert(bleDataOld, bleData, bleManager.failStatus)
//
//
//        // 방면명칭, 목적지까지 거리, 시간
//        if ((edcManager.driveMode == DRIVEMODE.SIMUL) || (edcManager.driveMode == DRIVEMODE.ROUTE)) {
//            //방면명칭 무조건보내도록
//
//            val tbtTexts = EdcUtils.collectTBTTexts(
//                bleData.tbtCrossName,
//                bleData.tbtNearDirName,
//                bleData.tbtMidDirName,
//                bleData.tbtFarDirName,
//                bleData.tbtType,
//                bleData.reamainViaPoint[0],
//                bleData.reamainViaPoint[1],
//                bleData.destinationName,
//                bleData.tbtMainText,
//            )
//            val nTextCnt = tbtTexts.count { it != null }
//
//            if (nTextCnt <= tbtIdx) {
//                tbtIdx = 0
//            }
//            Timber.d("Tbt Names is ${tbtTexts.contentToString()}[$tbtIdx], ${mNameToggleCnt}")
//            // 방면정보
//            if (tbtTexts[tbtIdx] != null) {
//                //방면명칭 무조건보내도록
//                if (bleDataOld.tbtText == null
//                    || bleDataOld.tbtText != tbtTexts[tbtIdx]
//                    || bleManager.failStatus.contains(BleCommandType.SEND_TBT_NAME)) {
//                    HudManager.getInstance().getHudEvent().sendNextLaneName(tbtTexts[tbtIdx]) //TODO 복잡교차로 없으면 두줄로?
//                }
//                bleData.tbtText = tbtTexts[tbtIdx]
//            }
//
//            mNameToggleCnt++
//            if (mNameToggleCnt % 5 == 0) tbtIdx++
//
//        }
//        highwayInfoConverter.convert(bleDataOld, bleData, bleManager.failStatus)
//
//        roadNameConverter.convert(bleDataOld, bleData, bleManager.failStatus)
//
//        sendImageInner()
//    }
//
//
//    private var lastHideTick = -1
//
//    private suspend fun sendImageInner() {
//        sendTick += 1
//        if(bleData.imgCorss != null){
//            Timber.d("IMAGE bitmap old:${bleDataOld.imgCrossSize} new:${bleData.imgCrossSize}")
//        }
//        if (!isSimilarImage(
//                bleDataOld.imgCrossSize,
//                bleData.imgCrossSize
//            )
//        ) {
//            if (bleData.imgCrossSize == 0L) {
//                Timber.d("hud_log_SEND_IMAGE_hide IMAGE SEND FALSE show current is 0 /curr:${bleData.imgCrossSize}/prev:${bleDataOld.imgCrossSize}")
//                bleManager.hideImage()
//                lastHideTick = sendTick
//            } else {
//                sendCrossImage(bleData.imgCorss)
////                if (bleDataOld.imgCrossSize != 0L) {
////                    Timber.d("hud_log_SEND_IMAGE_hide IMAGE SEND FALSE show prev is not 0, curr is not 0/curr:${bleData.imgCrossSize}/prev:${bleDataOld.imgCrossSize}")
////                    bleManager.hideImage()
////                }
////                if(bleData.imgCrossSize != 0L){
////                    sendCrossImage(bleData.imgCorss)
////                }
//            }
//        }
//
//        hideRetryIfImgageNotExist()
//
//        delay(100)
//        naviProgressConverter.tryCreate(bleData)
//    }
//
//    /**
//     * 직전에 이미지 hide하였고, 현재도 이미지 없으면 hide 한번더 전송
//     */
//    private suspend fun hideRetryIfImgageNotExist(){
//        if(bleData.imgCrossSize == 0L && sendTick-1 == lastHideTick){
//            bleManager.hideImage()
//        }
//    }
//
//
//    private fun createAdjustedBitMap(
//        bitmap: Bitmap,
//        targetWidthPx : Float,
//        targetHeightPx: Float,
//    ): Bitmap {
//        return bitmap
//        // SDK 수정됨 2026_07_08
////        val width = bitmap.width
////        val height = bitmap.height
////
////        val scaleX = targetWidthPx / width
////        val scaleY = targetHeightPx / height
////
////        val matrix = Matrix()
////        matrix.postScale(scaleX, scaleY)
////
////        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false)
//    }
//
//
//    private suspend fun sendCrossImage(bitmapOrigin: Bitmap) = withContext(coroutineScope.coroutineContext) {
//        Timber.d("IMAGE SEND")
//        // Create and start the thread
//        val bitmap = createAdjustedBitMap(
//            bitmap = bitmapOrigin,
//            targetWidthPx = HudManager.getInstance().getHudSetConfig().imageMaxW.toFloat(),
//            targetHeightPx = HudManager.getInstance().getHudSetConfig().imageMaxH.toFloat()
//        )
////            delay(50.milliseconds) // 다른 명령어 전송하고 보내도록 50ms 대기
//        Timber.d("hud_log_SEND_IMAGE_send ${bitmap.width} ${bitmap.height}")
//        bleManager.sendComplexRoadImage(bitmap)
//
//
//        bleManager.failStatus.add(BleCommandType.SEND_SPEED)
//        bleManager.failStatus.add(BleCommandType.SEND_SDI)
//        bleManager.failStatus.add(BleCommandType.SEND_REAR_CAMERA)
//
//    }
//}