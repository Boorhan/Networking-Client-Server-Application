package com.example.clientside;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Button saveBtn,sendFile;

    boolean senFile=false;
    String textInsideTheSelectedFile;

    ArrayList<String> cnctClients = new ArrayList<>();

    Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,nice,house,you,teacher,ok,space, cross, signAudio;
    TextView textView;
    Spinner spUsers;
    ArrayAdapter<String> spUsersAdapter;

    List<String> userList;

    static int SocketServerPORT;

    LinearLayout loginPanel, chatPanel;

    EditText editTextUserName, editTextAddress,portNum;
    Button buttonConnect,signBtn;
    TextView chatMsg;

    EditText editTextSay;
    Button buttonSend, speechText;
    Button buttonDisconnect;

    ConstraintLayout signConstraint;

    String msgLog = "",str="";
    String TAG="custom";

    ChatClientThread chatClientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //spUsers = (Spinner) findViewById(R.id.spusers);
        /*userList = new ArrayList<String>();
        spUsersAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, userList);
        spUsersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUsers.setAdapter(spUsersAdapter);*/

        signAudio = (Button) findViewById(R.id.signToAudioButton);
        saveBtn = (Button)findViewById(R.id.saveMsg);
        sendFile = (Button)findViewById(R.id.sendFile);

        cross = (Button) findViewById(R.id.crossButton);
        a=(Button)findViewById(R.id.buttonA);
        b=(Button)findViewById(R.id.buttonB);
        c=(Button)findViewById(R.id.buttonC);
        d=(Button)findViewById(R.id.buttonD);
        e=(Button)findViewById(R.id.buttonE);
        f=(Button)findViewById(R.id.buttonF);
        g=(Button)findViewById(R.id.buttonG);
        h=(Button)findViewById(R.id.buttonH);
        i=(Button)findViewById(R.id.buttonI);
        j=(Button)findViewById(R.id.buttonJ);
        k=(Button)findViewById(R.id.buttonK);
        l=(Button)findViewById(R.id.buttonL);
        m=(Button)findViewById(R.id.buttonM);
        n=(Button)findViewById(R.id.buttonN);
        o=(Button)findViewById(R.id.buttonO);
        p=(Button)findViewById(R.id.buttonP);
        q=(Button)findViewById(R.id.buttonQ);
        r=(Button)findViewById(R.id.buttonR);
        s=(Button)findViewById(R.id.buttonS);
        t=(Button)findViewById(R.id.buttonT);
        u=(Button)findViewById(R.id.buttonU);
        v=(Button)findViewById(R.id.buttonV);
        w=(Button)findViewById(R.id.buttonW);
        x=(Button)findViewById(R.id.buttonX);
        y=(Button)findViewById(R.id.buttonY);
        z=(Button)findViewById(R.id.buttonZ);
        nice=(Button)findViewById(R.id.Nice);
        teacher=(Button)findViewById(R.id.teacher);
        you=(Button)findViewById(R.id.You);
        house=(Button)findViewById(R.id.House);
        ok=(Button)findViewById(R.id.OK);
        space=(Button)findViewById(R.id.Space);
        textView=(TextView)findViewById(R.id.textView);

        loginPanel = (LinearLayout) findViewById(R.id.loginPanel);
        chatPanel = (LinearLayout)findViewById(R.id.chatpanel);
        speechText = (Button)findViewById(R.id.speechText);

        signConstraint =(ConstraintLayout)findViewById(R.id.SignPanel);

        editTextUserName = (EditText) findViewById(R.id.username);
        editTextAddress = (EditText) findViewById(R.id.address);
        portNum = (EditText) findViewById(R.id.portNumber);

        //SocketServerPORT=
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonDisconnect = (Button) findViewById(R.id.disconnect);

        signBtn=(Button)findViewById(R.id.signText);

        chatMsg = (TextView) findViewById(R.id.chatmsg);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonDisconnect.setOnClickListener(buttonDisconnectOnClickListener);

        editTextSay = (EditText)findViewById(R.id.say);
        buttonSend = (Button)findViewById(R.id.send);

        buttonSend.setOnClickListener(buttonSendOnClickListener);

        getIpAddress();

        Intent iin= getIntent();
        Bundle Bb = iin.getExtras();

        if(Bb!=null)
        {
            String j =(String) Bb.get("SignValue");
            editTextSay.setText(j);
            loginPanel.setVisibility(View.GONE);
            chatPanel.setVisibility(View.VISIBLE);
        }

        sendFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setType("text/plain").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a TXT file"), 123);
                senFile=true;
            }
        });

        saveBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String fileName="ChatServerMsg",data=msgLog;
                writeToFile(fileName,data);

            }
        });

        a.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "j";
                textView.setText(str);
            }
        });
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "i";
                textView.setText(str);
            }
        });
        c.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "h";
                textView.setText(str);
            }
        });
        d.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "g";
                textView.setText(str);
            }
        });
        e.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "f";
                textView.setText(str);
            }
        });
        f.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "e";
                textView.setText(str);
            }
        });
        g.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "d";
                textView.setText(str);
            }
        });
        h.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "c";
                textView.setText(str);
            }
        });
        i.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "b";
                textView.setText(str);
            }
        });
        j.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "a";
                textView.setText(str);
            }
        });
        k.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "s";
                textView.setText(str);
            }
        });
        l.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "r";
                textView.setText(str);
            }
        });
        m.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "q";
                textView.setText(str);
            }
        });
        n.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "p";
                textView.setText(str);
            }
        });
        o.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "o";
                textView.setText(str);
            }
        });
        p.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "n";
                textView.setText(str);
            }
        });
        q.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "m";
                textView.setText(str);
            }
        });
        r.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "l";
                textView.setText(str);
            }
        });
        s.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "k";
                textView.setText(str);
            }
        });
        t.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "z";
                textView.setText(str);
            }
        });
        u.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "y";
                textView.setText(str);
            }
        });
        v.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "x";
                textView.setText(str);
            }
        });
        w.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "w";
                textView.setText(str);
            }
        });
        x.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "v";
                textView.setText(str);
            }
        });
        y.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "u";
                textView.setText(str);
            }
        });
        z.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "t";
                textView.setText(str);
            }
        });
        nice.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ " nice";
                textView.setText(str);
            }
        });
        teacher.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ "teacher";
                textView.setText(str);
            }
        });
        ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ " okay";
                textView.setText(str);
            }
        });
        house.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ " house";
                textView.setText(str);
            }
        });
        you.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ " you";
                textView.setText(str);
            }
        });
        space.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                str= str+ " ";
                textView.setText(str);
            }
        });
        cross.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (str != null && str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                }
                textView.setText(str);
            }
        });

        signAudio.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String signMSG= textView.getText().toString();
                editTextSay.setText(signMSG);
                signConstraint.setVisibility(View.GONE);
                chatPanel.setVisibility(View.VISIBLE);
                str="";
                textView.setText("");
            }
        });
        verifyStoragePermissions();
    }

    private String uriToString(Uri uri){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)));
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append("\n"+line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }


    public void fileSend(String ss){

        //ss="##_MSG_From_File_##\n"+ss;

        chatClientThread.sendMsg(ss + "\n");
    }


    public void verifyStoragePermissions() {
        // Check if we have write permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }
    }

    private void writeToFile(String fileName, String data) {
        Long time= System.currentTimeMillis();
        String timeMill = " "+time.toString();
        File defaultDir = Environment.getExternalStorageDirectory();
        File file = new File(defaultDir, fileName+timeMill+".txt");
        FileOutputStream stream;
        try {
            stream = new FileOutputStream(file, false);
            stream.write(data.getBytes());
            stream.close();
            //showToast("file saved in: "+file.getPath());
            Toast.makeText(this, "file saved in: "+file.getPath(), Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d(TAG, e.toString());
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {

                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip +=inetAddress.getHostAddress();
                    }

                }

            }
            editTextAddress.setText(ip);

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode==RESULT_OK) {
            Uri uri = data.getData();
            textInsideTheSelectedFile = uriToString(uri);
            //Now do your stuff. Must attach a code at the beginning of the string before sending this string.
            //writeToFile("Borhan",textInsideTheSelectedFile);
            Toast.makeText(this, textInsideTheSelectedFile, Toast.LENGTH_SHORT).show();
            textInsideTheSelectedFile=textInsideTheSelectedFile+"\n";
            if (senFile) {
                 fileSend(textInsideTheSelectedFile);
                 senFile=false;
            }


        }

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editTextSay.setText(result.get(0));
                }
                break;
        }
    }

    OnClickListener buttonDisconnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if(chatClientThread==null){
                return;
            }
            chatClientThread.disconnect();
        }

    };

    OnClickListener buttonSendOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (editTextSay.getText().toString().equals("")) {
                return;
            }

            if(chatClientThread==null){
                return;
            }

            chatClientThread.sendMsg(editTextSay.getText().toString() + "\n");
        }

    };



    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String textUserName = editTextUserName.getText().toString();
            if (textUserName.equals("")) {
                Toast.makeText(MainActivity.this, "Enter User Name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String textAddress = editTextAddress.getText().toString();
            if (textAddress.equals("")) {
                Toast.makeText(MainActivity.this, "Enter Addresse",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String portText= portNum.getText().toString();
            if (textAddress.equals("")) {
                Toast.makeText(MainActivity.this, "Enter Port Number",
                        Toast.LENGTH_LONG).show();
                return;
            }
            else{
                SocketServerPORT=Integer.valueOf(portText);
            }

            msgLog = "";
            chatMsg.setText(msgLog);
            loginPanel.setVisibility(View.GONE);
            chatPanel.setVisibility(View.VISIBLE);
            // .setVisibility(View.VISIBLE);

            chatClientThread = new ChatClientThread(textUserName, textAddress, SocketServerPORT);
            chatClientThread.start();
        }

    };

    public void getSign(View view){

       /* Intent intent=new Intent(MainActivity.this, SignActivity.class);
        startActivity(intent);*/
       chatPanel.setVisibility(View.GONE);

        signConstraint.setVisibility(View.VISIBLE);

    }

    private class ChatClientThread extends Thread {

        String name;
        String dstAddress;
        int dstPort;

        String msgToSend = "";
        boolean goOut = false;


        ChatClientThread(String name, String address, int port) {
            this.name = name;
            dstAddress = address;
            dstPort = port;
        }

        @Override
        public void run() {
            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream.writeUTF(name);

                /*String s= name +" "+dstAddress;
                userList.add(s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spUsersAdapter.notifyDataSetChanged();
                    }
                });*/

                dataOutputStream.flush();

                while (!goOut) {
                    if (dataInputStream.available() > 0) {
                        msgLog += dataInputStream.readUTF();

                        //writeToFile("BORHAN",msgLog);

                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                chatMsg.setText(msgLog);
                            }
                        });
                    }
                    if(!msgToSend.equals("")){
                        if (senFile) {
                            msgToSend = textInsideTheSelectedFile;
                            Toast.makeText(MainActivity.this, "Heree", Toast.LENGTH_SHORT).show();
                        }
                        dataOutputStream.writeUTF(msgToSend);
                        dataOutputStream.flush();
                        msgToSend = "";
                    }
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
                final String eString = e.toString();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
                final String eString = e.toString();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        loginPanel.setVisibility(View.VISIBLE);
                        chatPanel.setVisibility(View.GONE);
                    }

                });
            }

        }

        private void sendMsg(String msg){
            msgToSend = msg;
        }

        private void disconnect(){
            goOut = true;
        }
    }

}