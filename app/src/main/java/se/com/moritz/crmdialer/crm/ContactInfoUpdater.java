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
    private static boolean updated = false;

    public static void updateListView(ListView contactListView,ListView accountListView,ListView caseListView, Context context){
        listAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1, contactInfo);
        contactListView.setAdapter(listAdapter);

        listAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1, accountInfo);
        accountListView.setAdapter(listAdapter);

        listAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1, caseInfo);
        caseListView.setAdapter(listAdapter);

        updated = true;
    }

    public static ArrayList<String> getContactInfo() {
        return contactInfo;
    }

    public static void addContactInfo(String s) {
        if(!contactInfo.contains(s)){
            addInfo(contactInfo,s);
        }
    }

    public static void addAccountInfo(String s){
        if(!accountInfo.contains(s)) {
            addInfo(accountInfo, s);
        }
    }

    public static void addCaseInfo(String s){
        if(!caseInfo.contains(s)) {
            addInfo(caseInfo, s);
        }
    }

    public static boolean ContactInfoUpdated(){
        return updated;
    }

    private static void addInfo(ArrayList<String> infoAL, String s){
        infoAL.add(s);
    }

    public static void clearContactInfo(){
        contactInfo.clear();
        accountInfo.clear();
        caseInfo.clear();
        updated = false;
    }
}
