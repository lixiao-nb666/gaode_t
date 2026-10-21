package com.newbee.downloader;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;
import com.newbee.downloader.bean.NewBeeFileDownLoaderBean;
import com.newbee.downloader.bean.NewBeeFileDownLoaderRetrunBean;
import com.newbee.downloader.bean.ResultNewBeeFileDownLoaderBean;
import com.newbee.downloader.event.retrun.NewBeeFileDownLoaderRetrunEventSubscriptionSubject;
import com.newbee.downloader.event.send.NewBeeFileDownLoaderEventObserver;
import com.newbee.downloader.event.send.NewBeeFileDownLoaderEventSubscriptionSubject;
import com.newbee.downloader.util.DowaLoadTaskToRetrunBeanUtil;

import java.util.List;

public class NewBeeFileDownLoaderManager {
    //    pending	等待，已经进入下载队列	数据库中的soFarBytes与totalBytes
//    started	结束了pending，并且开始当前任务的Runnable	-
//    connected	已经连接上	ETag, 是否断点续传, soFarBytes, totalBytes
//    progress	下载进度回调	soFarBytes
//    blockComplete	在完成前同步调用该方法，此时已经下载完成	-
//    retry	重试之前把将要重试是第几次回调回来	之所以重试遇到Throwable, 将要重试是第几次, soFarBytes
//    completed	完成整个下载过程	-
//    paused	暂停下载	soFarBytes
//    error	下载出现错误	抛出的Throwable
//    warn	在下载队列中(正在等待/正在下载)已经存在相同下载连接与相同存储路径的任务	-


        private final String tag="NewBeeFileDownLoaderManager>>>";

        private static NewBeeFileDownLoaderManager newBeeFileDownLoaderManager;
        private FileDownloadListener fileDownloadListener=new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            NewBeeFileDownLoaderRetrunBean  newBeeFileDownLoaderRetrunBean= DowaLoadTaskToRetrunBeanUtil.getRetrunBean(task);
            Log.i(tag,"pending---"+newBeeFileDownLoaderRetrunBean.toString());
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            int a=soFarBytes/1024;
            int b=totalBytes/1024;
            int progress=(a*100)/b;
            NewBeeFileDownLoaderRetrunBean  newBeeFileDownLoaderRetrunBean= DowaLoadTaskToRetrunBeanUtil.getRetrunBean(task);
            NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().progress(newBeeFileDownLoaderRetrunBean,progress,soFarBytes,totalBytes);
            Log.i(tag,"progress---"+newBeeFileDownLoaderRetrunBean.getFileName()+"--"+progress+"---"+soFarBytes+"/"+totalBytes);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            NewBeeFileDownLoaderRetrunBean  newBeeFileDownLoaderRetrunBean= DowaLoadTaskToRetrunBeanUtil.getRetrunBean(task);
            Log.i(tag,"completed---"+newBeeFileDownLoaderRetrunBean.toString());
            NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().completed(newBeeFileDownLoaderRetrunBean);
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            NewBeeFileDownLoaderRetrunBean  newBeeFileDownLoaderRetrunBean= DowaLoadTaskToRetrunBeanUtil.getRetrunBean(task);
            Log.i(tag,"paused---"+newBeeFileDownLoaderRetrunBean.toString());
            NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().statu();
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            NewBeeFileDownLoaderRetrunBean  newBeeFileDownLoaderRetrunBean= DowaLoadTaskToRetrunBeanUtil.getRetrunBean(task);
            Log.i(tag,"error---"+newBeeFileDownLoaderRetrunBean.toString());
            NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().err(newBeeFileDownLoaderRetrunBean,e);
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            NewBeeFileDownLoaderRetrunBean  newBeeFileDownLoaderRetrunBean= DowaLoadTaskToRetrunBeanUtil.getRetrunBean(task);
            Log.i(tag,"warn---"+newBeeFileDownLoaderRetrunBean.toString());
            NewBeeFileDownLoaderRetrunEventSubscriptionSubject.getInstance().statu();
        }
    };

        private NewBeeFileDownLoaderEventObserver newBeeFileDownLoaderEventObserver=new NewBeeFileDownLoaderEventObserver() {
            @Override
            public void start(NewBeeFileDownLoaderBean newBeeFileDownLoaderBean) {
                startDownLoader(newBeeFileDownLoaderBean);
            }

            @Override
            public void start(ResultNewBeeFileDownLoaderBean resultNewBeeFileDownLoaderBean) {
                startDownLoader(resultNewBeeFileDownLoaderBean);
            }

            @Override
            public void pause(int taskId) {
                pauseDownLoader(taskId);
            }
        };


        private NewBeeFileDownLoaderManager(){}


        public static NewBeeFileDownLoaderManager getInstance(){
            if(null==newBeeFileDownLoaderManager){
                synchronized (NewBeeFileDownLoaderManager.class){
                    if(null==newBeeFileDownLoaderManager){
                        newBeeFileDownLoaderManager=new NewBeeFileDownLoaderManager();
                    }
                }
            }
            return newBeeFileDownLoaderManager;
        }

        public void init(Context context){
            FileDownloader.init(context.getApplicationContext());
            FileDownloader.getImpl().bindService();
            FileDownloader.getImpl().setMaxNetworkThreadCount(12);
            NewBeeFileDownLoaderEventSubscriptionSubject.getInstance().attach(newBeeFileDownLoaderEventObserver);
        }




        public void close(){
            NewBeeFileDownLoaderEventSubscriptionSubject.getInstance().detach(newBeeFileDownLoaderEventObserver);
            FileDownloader.getImpl().unBindService();
        }

        private void startDownLoader(NewBeeFileDownLoaderBean downLoaderBean){
            if(null==downLoaderBean|| TextUtils.isEmpty(downLoaderBean.getFileUrl())||TextUtils.isEmpty(downLoaderBean.getFilePath())||TextUtils.isEmpty(downLoaderBean.getTag())){
                return;
            }

            FileDownloader.getImpl().create(downLoaderBean.getFileUrl()).setAutoRetryTimes(5).setTag(downLoaderBean.getTag()).setPath(downLoaderBean.getFilePath()).setListener(fileDownloadListener).start();
        }


     private void startDownLoader(ResultNewBeeFileDownLoaderBean downLoaderBeanS){
        if(null==downLoaderBeanS||null==downLoaderBeanS.getMap()||downLoaderBeanS.getMap().size()==0){
            return;
        }
        for(NewBeeFileDownLoaderBean downLoaderBean:downLoaderBeanS.getMap().values()){
            startDownLoader(downLoaderBean);
        }
        }

      private void pauseDownLoader(int id){
            FileDownloader.getImpl().pause(id);
      }

}
