package se.com.moritz.crmdialer.crm;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ContactInfoUpdater {

    private static ArrayList<String> contactInfo = new ArrayList<>();
    private static ArrayList<String> accountInfo = new ArrayList<>();
    private static ArrayList<String> caseInfo = new ArrayList<>();
    private static ArrayAdapter<String> listAdapter;
    private static String TAG = "ContactInfoUpdater";

    public static void updateListView(ListView listView, Context context){
        listAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1, contactInfo);
        listView.setAdapter(listAdapter);
    }

    public static ArrayList<String> getContactInfo() {
        return contactInfo;
    }

   /* public static void addContactInfo(String s) {
        contactInfo.add(s);
        Log.i(TAG, "ContactInfo updated");
    }*/

    public static void addContactInfo(String s) {
        addInfo(contactInfo,s);
        addInfo(accountInfo,s);
        addInfo(caseInfo,s);
    }

    public static void addInfo(ArrayList<String> infoAL , String s){
        infoAL.add(s);
        Log.i(TAG, "Info updated");
    }

    public static void clearContactInfo(){
        contactInfo.clear();
    }
}
