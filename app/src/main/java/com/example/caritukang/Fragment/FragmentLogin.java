package com.example.caritukang.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.caritukang.MainActivity;
import com.example.caritukang.Model.User;
import com.example.caritukang.R;
import com.example.caritukang.Util.EncryptPassword;
import com.example.caritukang.Util.Util;
import com.example.caritukang.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLogin() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentLoginBinding fragmentLoginBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        fragmentLoginBinding.idBtnLogin.setOnClickListener(this);
        return fragmentLoginBinding.getRoot();
    }
    private EncryptPassword mInstanceEncrypt = new EncryptPassword();
    private FirebaseAuth firebaseAuth;
    @Override
    public void onClick(View view) {


        switch (view.getId()){
            case R.id.id_btn_login:
                firebaseAuth = FirebaseAuth.getInstance();
                try {
                    firebaseAuth.signInWithEmailAndPassword(fragmentLoginBinding.idEditTextEmail.getText().toString(),  mInstanceEncrypt.encryptPassword(fragmentLoginBinding.idEditTextPassword.getText().toString()))
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity().getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
                                    } else  {
                                        fragmentLoginBinding.idEditTextEmail.setError("Harap Cek Kembali Email Anda");
                                        fragmentLoginBinding.idEditTextPassword.setError("Harap Cek Kembali Password Anda");
                                        Toast.makeText(getActivity().getApplicationContext(),"Login Gagal",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }

                /*
                   Query query = Util.getFirebaseDatabase().child("users").orderByChild("dataEmail");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot user : snapshot.getChildren()) {
                                User user1 = user.getValue(User.class);
                                System.out.println(user1.getDataEmail() + " NAME");
                                try {
                                    String decPassword = mInstanceEncrypt.decryptPassword(user1.getPasswordUser());
                                    if (decPassword.equals(fragmentLoginBinding.idEditTextPassword.getText().toString().trim())
                                    && fragmentLoginBinding.idEditTextEmail.getText().toString().equals(user1.getDataEmail())) {
                                        Toast.makeText(getContext().getApplicationContext(), "Login Success !",Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity().getApplicationContext(), "Password is wrong", Toast.LENGTH_LONG).show();
                                    }
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (NoSuchPaddingException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (IllegalBlockSizeException e) {
                                    e.printStackTrace();
                                } catch (BadPaddingException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                 */

                Util.getFirebaseDatabase().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataChildern : snapshot.getChildren()){
                            User user = dataChildern.getValue(User.class);
                            Toast.makeText(getActivity().getApplicationContext(), "Data = " + user.getDataEmail(),Toast.LENGTH_SHORT).show();
                            System.out.print(user.getNameUser());

                        }


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

        }
    }
}