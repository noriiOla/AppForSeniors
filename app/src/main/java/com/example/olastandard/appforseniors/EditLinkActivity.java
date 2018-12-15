package com.example.olastandard.appforseniors;

        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.util.Patterns;
        import android.view.View;
        import android.webkit.URLUtil;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.olastandard.appforseniors.PushDIalog.PushDialogButtonsOkInterface;
        import com.example.olastandard.appforseniors.PushDIalog.PushDialogManager;

        import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.FileOutputStream;
        import java.io.FileWriter;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.PrintWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.net.UnknownHostException;
        import java.util.ArrayList;

        import butterknife.ButterKnife;
        import butterknife.OnClick;

public class EditLinkActivity extends MainActivity {


    EditText urlTextEdit;
    EditText addressTextEdit;

    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_edit_link);
        initToolbar();
        ButterKnife.bind(this);

        _toolbarSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSave(v);
            }
        });

        String title = (String) getIntent().getSerializableExtra("title");
        String adres = (String) getIntent().getSerializableExtra("adres");

        urlTextEdit=(EditText) findViewById(R.id.nazwaLinku_edit);
        addressTextEdit=(EditText) findViewById(R.id.urlL_edit);
        urlTextEdit.setText(title);
        addressTextEdit.setText(adres);
    }




    private void initToolbar() {
        showBackButton();
        showRightButton();

        changeTitleForRightButton("Zapisz");
        setTitle(getResources().getString(R.string.web));
    }




    public void buttonSave (View view)
    {


        EditText urlTextEdit=(EditText) findViewById(R.id.nazwaLinku_edit);
        EditText addressTextEdit=(EditText) findViewById(R.id.urlL_edit);
        if(urlTextEdit.getText().toString().equals("") ||addressTextEdit.getText().toString().equals("")){
            //Toast.makeText(getApplicationContext(), "Nie podano nazwy lub adresu", Toast.LENGTH_LONG).show();
            new PushDialogManager().showDialogWithOkButton(EditLinkActivity.this, "Nie podano nazwy lub adresu", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    return;
                }
            });

            return;
            //    return;
        }
        String saveText= urlTextEdit.getText().toString()+","+addressTextEdit.getText().toString()+"\n";



        //Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        Save (saveText,urlTextEdit.getText().toString(),addressTextEdit.getText().toString());
        //Toast.makeText(getApplicationContext(), "Zapisano ", Toast.LENGTH_LONG).show();
        /*urlTextEdit.setText("");
        addressTextEdit.setText("");*/

    }

    public  void Save( String data,String nazwa,String link) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean result;

       /* if (!(result = (activeNetworkInfo != null && activeNetworkInfo.isConnected()))) {
            Toast.makeText(getApplicationContext(), "Brak dostepu do neta ", Toast.LENGTH_LONG).show();
            return;
        }*/




        nazwa=nazwa.trim();
        link=link.trim();

        String title = (String) getIntent().getSerializableExtra("title");
        String adres = (String) getIntent().getSerializableExtra("adres");


        ArrayList<String> al=new ArrayList<String>();
        ArrayList<String> al2=new ArrayList<String>();
        if(nazwa.equals(title)&& link.equals(adres))
        {
            new PushDialogManager().showDialogWithOkButton(EditLinkActivity.this, "Nie zmieniono niczego", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    finish();
                   // return;
                }
            });
            return;
        }else{

            try {
                FileInputStream fis = this.getApplicationContext().openFileInput("savedFile8");
                InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String data1;
                String data2;
                while((data1=bufferedReader.readLine( )) != null)
                {

                    data2=data1.split(",")[0];
                    // Toast.makeText(getApplicationContext(), data+" "+name, Toast.LENGTH_LONG).show();
                    if (data2.equals(title)){
                        // Toast.makeText(getApplicationContext(), "Nazwa linku wystapila", Toast.LENGTH_LONG).show();

                    }
                    else{
                        al.add(data1);
                        al2.add(data2);
                    }


                }
            } catch (FileNotFoundException e) {
                Log.d("EXCEPTION", "File not found");
            } catch (UnsupportedEncodingException e) {
                Log.d("EXCEPTION", e.getMessage());
            } catch (IOException e) {
                Log.d("EXCEPTION", e.getMessage());
            }






            /*Boolean bol=  getApplicationContext().deleteFile("savedFile8");
            String result1="";*/
       /* Collections.reverse(arrayList);
        Collections.reverse(arrayListListView);*/
         /*   for(String line : al)
            {result1+=line+"\n";}
            // Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
            try {
                outputStream = this.getApplicationContext().openFileOutput("savedFile8", MODE_PRIVATE);

                outputStream.write(result1.getBytes());



                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
*/

        }



        if (al2.contains(nazwa)) {

            // Toast.makeText(getApplicationContext(), "istnieje juz nazwa podac inna", Toast.LENGTH_LONG).show();
            new PushDialogManager().showDialogWithOkButton(EditLinkActivity.this, "Podana nazwa została wybrana wczesniej", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    finish();
                    return;

                }
            });

            return;
        }
        if (!link.startsWith("http://") && !link.startsWith("https://"))
        {link = "http://" + link;}
        // Toast.makeText(getApplicationContext(), nazwa+ " " +link, Toast.LENGTH_LONG).show();

        boolean o=Patterns.WEB_URL.matcher(link).matches();
        if(o){}else{
            //Toast.makeText(getApplicationContext(), "niedzialajacy link" , Toast.LENGTH_LONG).show();

            new PushDialogManager().showDialogWithOkButton(EditLinkActivity.this, "Niepoprawny format adresu", new PushDialogButtonsOkInterface() {
                @Override
                public void onOkButtonTap() {
                    return;
                }
            });

            return;
        }

        // if (nazwa.matches("((http)[s]?(://).*)")) {
           /* try {

                 URL url = new URL(link);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                int responseCode = huc.getResponseCode();
                if (responseCode != 200) {
                    Toast.makeText(getApplicationContext(), "niedzialajacy link"+responseCode , Toast.LENGTH_LONG).show();
                    return;
                }
                System.out.println("The supplied URL is GOOD!");
            }
            catch (UnknownHostException | MalformedURLException ex) {
                Toast.makeText(getApplicationContext(), "niedzialajacy link 1"+link, Toast.LENGTH_LONG).show();
            return;
            }
            catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "niedzialajacy link 2"+link, Toast.LENGTH_LONG).show();
                System.out.println(ex.getMessage());
                return;
            }catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "niedzialajacy link 2"+link, Toast.LENGTH_LONG).show();
                System.out.println(ex.getMessage());
                return;
            }*/

        //  }



String result1=nazwa+","+link+"\n";
        Boolean bol=  getApplicationContext().deleteFile("savedFile8");
          //  String result1="";*/
       /* Collections.reverse(arrayList);
        Collections.reverse(arrayListListView);*/
            for(String line : al)
            {result1+=line+"\n";}

            // Toast.makeText(getApplicationContext(),result ,Toast.LENGTH_LONG).show();
            try {
                outputStream = this.getApplicationContext().openFileOutput("savedFile8", MODE_PRIVATE);

                outputStream.write(result1.getBytes());



                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        //read();
//       file.mkdirs();
//
//        FileOutputStream fos = null;
//        try
//        {
//            fos = new FileOutputStream(file);
//
//        }
//        catch (FileNotFoundException e) {e.printStackTrace();}
//        try
//        {
//            try
//            {
//
//                    fos.write(data.getBytes());
//
//                        fos.write("\n".getBytes());
//
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }
//        finally
//        {
//            try
//            {
//                fos.close();
//            }
//            catch (IOException e) {e.printStackTrace();}
//        }
        finish();
    }

    private boolean read(String name) {

        // TextView urlTextEdit=(TextView) findViewById(R.id.textView);
        try {
            FileInputStream fis = this.getApplicationContext().openFileInput("savedFile8");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String data;
            while((data=bufferedReader.readLine( )) != null)
            {

                data=data.split(",")[0];
                // Toast.makeText(getApplicationContext(), data+" "+name, Toast.LENGTH_LONG).show();
                if (data.equals(name)){
                    // Toast.makeText(getApplicationContext(), "Nazwa linku wystapila", Toast.LENGTH_LONG).show();
                    return false;
                }


            }
        } catch (FileNotFoundException e) {
            Log.d("EXCEPTION", "File not found");
        } catch (UnsupportedEncodingException e) {
            Log.d("EXCEPTION", e.getMessage());
        } catch (IOException e) {
            Log.d("EXCEPTION", e.getMessage());
        }
        return true;
    }





}
