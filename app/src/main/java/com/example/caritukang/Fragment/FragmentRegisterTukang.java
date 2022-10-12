package com.example.caritukang.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caritukang.Model.User;
import com.example.caritukang.R;
import com.example.caritukang.Util.EncryptPassword;
import com.example.caritukang.Util.Util;
import com.example.caritukang.databinding.FragmentRegisterTukangBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegisterTukang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegisterTukang extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegisterTukang() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegisterTukang.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegisterTukang newInstance(String param1, String param2) {
        FragmentRegisterTukang fragment = new FragmentRegisterTukang();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentRegisterTukangBinding fragmentRegisterTukangBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       fragmentRegisterTukangBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_tukang,container, false);
       setInputTypeEditText();
       checkEmpty();
        mAuth= FirebaseAuth.getInstance();
        fragmentRegisterTukangBinding.idBtnDaftar.setOnClickListener(this);

       return fragmentRegisterTukangBinding.getRoot();
    }
    void checkEmpty(){
        fragmentRegisterTukangBinding.idEditTextRegisterEmail.addTextChangedListener(textWatcher);
        fragmentRegisterTukangBinding.idEditTextNamaLengkap.addTextChangedListener(textWatcher);
        fragmentRegisterTukangBinding.idEditTextRegisterNomorHandphone.addTextChangedListener(textWatcher);
        fragmentRegisterTukangBinding.idEditTextTanggalLahir.addTextChangedListener(textWatcher);
        fragmentRegisterTukangBinding.idEditTextRegisterPassword.addTextChangedListener(textWatcher);
        fragmentRegisterTukangBinding.idEditTextRegisterPasswordConfirmation.addTextChangedListener(textWatcher);
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String _dataEmail = fragmentRegisterTukangBinding.idEditTextRegisterEmail.getText().toString().trim();
            String _namaLengkap = fragmentRegisterTukangBinding.idEditTextNamaLengkap.getText().toString().trim();
            String _nomorHandphone = fragmentRegisterTukangBinding.idEditTextRegisterNomorHandphone.getText().toString().trim();
            String _tanggalLahir = fragmentRegisterTukangBinding.idEditTextTanggalLahir.getText().toString().trim();
            String _password = fragmentRegisterTukangBinding.idEditTextRegisterPassword.getText().toString().trim();
            String _passwordConfirmation = fragmentRegisterTukangBinding.idEditTextRegisterPasswordConfirmation.getText().toString().trim();
            Log.d("_password = " , _password);
            Log.d("_pw = ",_passwordConfirmation);
            //check apakah data email sudah sesuai format
            if(!Util.patternMatches(_dataEmail, Util.regexPattern)){
                fragmentRegisterTukangBinding.idEditTextRegisterEmail.setError("Mohon Isikan Alamat Email Anda");
            }

            //  String patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
            if(_password.length() <= 6){
                fragmentRegisterTukangBinding.idEditTextRegisterPassword.setError("Password Harus Lebih Dari 6 Karakter");
            }
            if(_passwordConfirmation.length() <= 6){
                fragmentRegisterTukangBinding.idEditTextRegisterPasswordConfirmation.setError("Password Harus Lebih Dari 6 Karakter");
            }

            /*
            bug cara ngecek password
            if(_password == _passwordConfirmation){
                Toast.makeText(getActivity().getApplicationContext(), "Password Sama ",Toast.LENGTH_SHORT).show();
            } else{
                fragmentRegisterBinding.idEditTextRegisterPasswordConfirmation.setError("Password Tidak Sama !");
                Toast.makeText(getActivity().getApplicationContext(), "Password Tidak Sama ",Toast.LENGTH_SHORT).show();
            }

             */

            /*else if(_password.length() > 6 && !Util.patternMatches(_password, patternPassword)){
                fragmentRegisterBinding.idEditTextRegisterPassword.setError("Password Setidaknya Mengandung 1 spesial karakter , 1 digit angka dan 1 huruf besar dan kecil ");

            }
             */
            //saat semua data kosong maka tombol daftar tidak aktif
            fragmentRegisterTukangBinding.idBtnDaftar.setEnabled(
                    !_dataEmail.isEmpty() &&
                            !_namaLengkap.isEmpty()
                            && !_nomorHandphone.isEmpty()
                            && !_tanggalLahir.isEmpty()
                            && !_password.isEmpty()
                            && !_passwordConfirmation.isEmpty()
                            && _dataEmail.length() >= 4
                            && _namaLengkap.length() >= 4
                            && _nomorHandphone.length() >= 7
                            && _password.length() > 5
                            && _passwordConfirmation.length() > 5
            );
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    final Calendar myCalendar = Calendar.getInstance();
    FirebaseAuth mAuth;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_back_register_account_user:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.id_base_frame_layout, new FragmentLogin()).commit();
                break;
            case R.id.id_edit_text_tanggal_lahir:
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, day);
                        updateTanggalLahir();
                    }
                };
                new DatePickerDialog(getActivity(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.id_btn_daftar:
                DatabaseReference databaseReference = getFirebaseDatabase();
                try {
                    databaseReference.child("users").orderByChild("dataEmail").equalTo(getUserRegisterData().getDataEmail()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(getActivity().getApplicationContext(), "DATA SUDAH ADA !", Toast.LENGTH_SHORT).show();
                            } else{
                                try {
                                    mAuth.createUserWithEmailAndPassword(getUserRegisterData().getDataEmail(), getUserRegisterData().getPasswordUser()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            Toast.makeText(getActivity().getApplicationContext(), "Register Berhasil", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                } catch (InvalidAlgorithmParameterException e) {
                                    e.printStackTrace();
                                } catch (NoSuchPaddingException e) {
                                    e.printStackTrace();
                                } catch (IllegalBlockSizeException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (BadPaddingException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    databaseReference.child("users").child(Util.generatedIdUser()).setValue(getUserRegisterData());
                                } catch (InvalidAlgorithmParameterException e) {
                                    e.printStackTrace();
                                } catch (NoSuchPaddingException e) {
                                    e.printStackTrace();
                                } catch (IllegalBlockSizeException e) {
                                    e.printStackTrace();
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (BadPaddingException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    EncryptPassword encryptPassword;
    private User getUserRegisterData() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String _dataEmail = fragmentRegisterTukangBinding.idEditTextRegisterEmail.getText().toString().trim();
        String _namaLengkap = fragmentRegisterTukangBinding.idEditTextNamaLengkap.getText().toString().trim();
        String _nomorHandphone = fragmentRegisterTukangBinding.idEditTextRegisterNomorHandphone.getText().toString().trim();
        String _tanggalLahir = fragmentRegisterTukangBinding.idEditTextTanggalLahir.getText().toString().trim();
        String _password = fragmentRegisterTukangBinding.idEditTextRegisterPassword.getText().toString().trim();
        String _passwordConfirmation = fragmentRegisterTukangBinding.idEditTextRegisterPasswordConfirmation.getText().toString().trim();
        String _alamat = fragmentRegisterTukangBinding.idEditTextAlamatLengkap.getText().toString().trim();
        User user = new User();
        user.setNameUser(_namaLengkap);
        user.setNomorHpUser(_nomorHandphone);
        encryptPassword = new EncryptPassword();
        user.setPasswordUser(encryptPassword.encryptPassword(_password));
        user.setRoleUser("TUKANG");
        user.setSaldoUser(0);
        user.setAlamatUser(_alamat);
        user.setTanggalLahir(_tanggalLahir);
        user.setDataEmail(_dataEmail);
        user.setAlamatUser(_alamat);
        user.setLocationGeoXYZUser(_alamat);
        return user;
    }
    public DatabaseReference getFirebaseDatabase(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://cari-tukang-1fe9d-default-rtdb.asia-southeast1.firebasedatabase.app");
        return firebaseDatabase.getReference();

    }
    private void updateTanggalLahir(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        fragmentRegisterTukangBinding.idTextInputLayoutTanggalLahir.getEditText().setText(dateFormat.format(myCalendar.getTime()), TextView.BufferType.EDITABLE);
    }
    void setInputTypeEditText(){
        fragmentRegisterTukangBinding.idEditTextRegisterEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        fragmentRegisterTukangBinding.idEditTextNamaLengkap.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        fragmentRegisterTukangBinding.idEditTextRegisterNomorHandphone.setInputType(InputType.TYPE_CLASS_PHONE);
        fragmentRegisterTukangBinding.idEditTextTanggalLahir.setInputType(InputType.TYPE_CLASS_DATETIME);
        //set input all
        fragmentRegisterTukangBinding.idEditTextRegisterEmail.setText("test_data@gmail.com");
        fragmentRegisterTukangBinding.idEditTextNamaLengkap.setText("test_datagmail.com");
        fragmentRegisterTukangBinding.idEditTextRegisterNomorHandphone.setText("1234567891");
        fragmentRegisterTukangBinding.idEditTextTanggalLahir.setText("12/10/2022");
        fragmentRegisterTukangBinding.idEditTextRegisterPassword.setText("123412312321");
        fragmentRegisterTukangBinding.idEditTextRegisterPasswordConfirmation.setText("123412312321");

    }
}