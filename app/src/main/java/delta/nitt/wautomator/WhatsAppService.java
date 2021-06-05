package delta.nitt.wautomator;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class WhatsAppService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(getRootInActiveWindow()==null){
            return;
        }

        AccessibilityNodeInfoCompat rootNodeInfo = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        List<AccessibilityNodeInfoCompat> messageList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        if(messageList == null)
            return;
        AccessibilityNodeInfoCompat messageField = messageList.get(0);
        if(messageField == null || messageField.getText().length() == 0 || messageField.getText().toString().endsWith("   "))
            return;
        List<AccessibilityNodeInfoCompat> sendMessageList = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if(sendMessageList == null || sendMessageList.isEmpty())
            return;

        AccessibilityNodeInfoCompat sendMessage = sendMessageList.get(0);
        if(!sendMessage.isVisibleToUser()){
            return;
        }

        sendMessage.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        try{
            Thread.sleep(500);
            performGlobalAction(GLOBAL_ACTION_BACK);
            Thread.sleep(500);
            performGlobalAction(GLOBAL_ACTION_BACK);
        }catch (InterruptedException e){

        }
    }

    @Override
    public void onInterrupt() {

    }

    void handleActionWhatsApp(String message,String count,String[] phone_no, Activity activity){

        try{
            PackageManager packageManager = activity.getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone="+ phone_no[0] +"&text=" + URLEncoder.encode(message, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }else {
                Log.e("No WHATSAPP","no");
            }
        } catch(Exception e) {
            Log.e("ERROR WHATSAPP",e.toString());

        }

    }

    void sendBroadcastMessage(String message){
        Intent localIntent = new Intent("my.own.broadcast");
        localIntent.putExtra("result",message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}
