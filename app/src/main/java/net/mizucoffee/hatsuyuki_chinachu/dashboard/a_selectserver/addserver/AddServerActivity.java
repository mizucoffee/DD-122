package net.mizucoffee.hatsuyuki_chinachu.dashboard.a_selectserver.addserver;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.gson.Gson;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.dashboard.DashboardViewModel;
import net.mizucoffee.hatsuyuki_chinachu.databinding.ActivityAddServerBinding;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

import butterknife.BindView;

public class AddServerActivity extends AppCompatActivity implements AddServerView{

    private ActivityAddServerBinding mBinding;
    private AddServerViewModel mAddServerVM;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

//    @BindView(R.id.name_et)
//    public EditText nameEt;
//    @BindView(R.id.host_et)
//    public EditText hostEt;
//    @BindView(R.id.port_et)
//    public EditText portEt;
//    @BindView(R.id.username_et)
//    public EditText usernameEt;
//    @BindView(R.id.password_et)
//    public EditText passwordEt;
    int position;

    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddServerVM = new AddServerViewModel(this);

        setContentView(R.layout.activity_add_server);
        Shirayuki.initActivity(this);

        if (getIntent().getStringExtra("data") != null) {
            ServerConnection sc = new Gson().fromJson(getIntent().getStringExtra("data"),ServerConnection.class);
            nameEt.setText(sc.getName());
            hostEt.setText(sc.getHost());
            portEt.setText(sc.getPort());
            usernameEt.setText(sc.getUsername());
            passwordEt.setText(sc.getPassword());
            position = getIntent().getIntExtra("position",0);
            isEdit = true;
        }

        fab.setOnClickListener(view -> {

                if(hostEt.getText().toString().equals("")) {hostEt.setError("入力してください"); return;}
                if(Integer.parseInt(portEt.getText().toString()) < 1 || Integer.parseInt(portEt.getText().toString()) > 65535) {portEt.setError("ポート番号は1-65535で指定される必要があります"); return;}

                ServerConnection sc = new ServerConnection();

                if(nameEt.getText().toString().equals(""))
                    sc.setName("Server");
                else
                    sc.setName(nameEt.getText().toString());

                sc.setHost(hostEt.getText().toString());

                if(portEt.getText().toString().equals(""))
                    sc.setPort("10772");
                else
                    sc.setPort(portEt.getText().toString());

                sc.setUsername(usernameEt.getText().toString());
                sc.setPassword(passwordEt.getText().toString());

                if(isEdit)
                    mPresenter.editedServerconnection(sc,position);
                else {
                    sc.setId(System.currentTimeMillis());
                    mPresenter.addServerConnection(sc);
                }
        });
    }

    @Override
    public SharedPreferences getActivitySharedPreferences(String name ,int mode){
        return getSharedPreferences(name,mode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finishActivity(){
        finish();
    }
}