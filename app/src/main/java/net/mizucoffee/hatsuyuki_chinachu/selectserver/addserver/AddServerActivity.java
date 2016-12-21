package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.mizucoffee.hatsuyuki_chinachu.R;
import net.mizucoffee.hatsuyuki_chinachu.model.ServerConnection;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.SelectServerPresenter;
import net.mizucoffee.hatsuyuki_chinachu.selectserver.SelectServerPresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddServerActivity extends AppCompatActivity implements AddServerView{

    private AddServerPresenter mPresenter;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    @BindView(R.id.nameEt)
    public EditText nameEt;
    @BindView(R.id.hostEt)
    public EditText hostEt;
    @BindView(R.id.portEt)
    public EditText portEt;
    @BindView(R.id.userNameEt)
    public EditText userNameEt;
    @BindView(R.id.passWordEt)
    public EditText passWordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);

        mPresenter = new AddServerPresenterImpl(this);

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hostEt.getText().toString().equals("")) {hostEt.setError("入力してください"); return;}

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

                sc.setUsername(userNameEt.getText().toString());
                sc.setPassword(passWordEt.getText().toString());

                mPresenter.save(sc);

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
        mPresenter.onDestroy();
    }

    @Override
    public void finishActivity(){
        finish();
    }
}