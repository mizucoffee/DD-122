package net.mizucoffee.hatsuyuki_chinachu.selectserver.addserver;

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
    private FloatingActionButton fab;

    @BindView(R.id.nameEt)
    private EditText nameEt;
    @BindView(R.id.hostEt)
    private EditText hostEt;
    @BindView(R.id.portEt)
    private EditText portEt;
    @BindView(R.id.userNameEt)
    private EditText userNameEt;
    @BindView(R.id.passWordEt)
    private EditText passWordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_server);

        mPresenter = new AddServerPresenterImpl(this);

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //エラー処理追加
                ServerConnection sc = new ServerConnection();

                sc.setName(nameEt.getText().toString());
                sc.setHost(hostEt.getText().toString());
                sc.setPort(portEt.getText().toString());
                sc.setUsername(userNameEt.getText().toString());
                sc.setPassword(passWordEt.getText().toString());

                mPresenter.save(sc);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}