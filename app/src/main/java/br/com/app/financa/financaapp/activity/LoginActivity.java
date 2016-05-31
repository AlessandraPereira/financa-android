package br.com.app.financa.financaapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.app.financa.financaapp.R;
import br.com.app.financa.financaapp.banco.Banco;
import br.com.app.financa.financaapp.bean.Usuario;
import br.com.app.financa.financaapp.dao.UsuarioDao;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Inicia a tela de cadastro de usuário (SignupActivity)
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        Usuario usuario = new Usuario();
        usuario.setEmail(_emailText.getText().toString());
        usuario.setSenha(_passwordText.getText().toString());

        UsuarioDao usuarioDao = new UsuarioDao(getBaseContext());
        final Boolean logado = usuarioDao.logar(usuario);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (logado) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                /* Finalizamos a tela de login por conta do usuário ter cadastrado um novo usuário
                 * Ou seja, ele já logou */
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Banco.USUARIO_LOGADO = true;
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login falhou", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("entre com um e-mail válido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() > 10) {
            _passwordText.setError("entre 1 a 10 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}