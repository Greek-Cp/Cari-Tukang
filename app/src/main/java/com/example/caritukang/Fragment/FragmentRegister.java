package com.example.caritukang.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

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

import com.example.caritukang.R;
import com.example.caritukang.Util.Util;
import com.example.caritukang.databinding.FragmentRegisterBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
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

    FragmentRegisterBinding fragmentRegisterBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRegisterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        setInputTypeEditText();
        fragmentRegisterBinding.idBtnBackRegisterAccountUser.setOnClickListener(this);
        fragmentRegisterBinding.idEditTextTanggalLahir.setOnClickListener(this);
        checkEmpty();
        return fragmentRegisterBinding.getRoot();
    }
    void setInputTypeEditText(){
        fragmentRegisterBinding.idEditTextRegisterEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        fragmentRegisterBinding.idEditTextNamaLengkap.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        fragmentRegisterBinding.idEditTextRegisterNomorHandphone.setInputType(InputType.TYPE_CLASS_PHONE);
        fragmentRegisterBinding.idEditTextTanggalLahir.setInputType(InputType.TYPE_CLASS_DATETIME);
    }
    void checkEmpty(){
        fragmentRegisterBinding.idEditTextRegisterEmail.addTextChangedListener(textWatcher);
        fragmentRegisterBinding.idEditTextNamaLengkap.addTextChangedListener(textWatcher);
        fragmentRegisterBinding.idEditTextRegisterNomorHandphone.addTextChangedListener(textWatcher);
        fragmentRegisterBinding.idEditTextTanggalLahir.addTextChangedListener(textWatcher);
        fragmentRegisterBinding.idEditTextRegisterPassword.addTextChangedListener(textWatcher);
        fragmentRegisterBinding.idEditTextRegisterPasswordConfirmation.addTextChangedListener(textWatcher);
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String _dataEmail = fragmentRegisterBinding.idEditTextRegisterEmail.getText().toString().trim();
            String _namaLengkap = fragmentRegisterBinding.idEditTextNamaLengkap.getText().toString().trim();
            String _nomorHandphone = fragmentRegisterBinding.idEditTextRegisterNomorHandphone.getText().toString().trim();
            String _tanggalLahir = fragmentRegisterBinding.idEditTextTanggalLahir.getText().toString().trim();
            String _password = fragmentRegisterBinding.idEditTextRegisterPassword.getText().toString().trim();
            String _passwordConfirmation = fragmentRegisterBinding.idEditTextRegisterPasswordConfirmation.getText().toString().trim();
            Log.d("_password = " , _password);
            Log.d("_pw = ",_passwordConfirmation);
            //check apakah data email sudah sesuai format
            if(!Util.patternMatches(_dataEmail, Util.regexPattern)){
                fragmentRegisterBinding.idEditTextRegisterEmail.setError("Mohon Isikan Alamat Email Anda");
            }

          //  String patternPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
            if(_password.length() <= 6){
                fragmentRegisterBinding.idEditTextRegisterPassword.setError("Password Harus Lebih Dari 6 Karakter");
            }
            if(_passwordConfirmation.length() <= 6){
                fragmentRegisterBinding.idEditTextRegisterPasswordConfirmation.setError("Password Harus Lebih Dari 6 Karakter");
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
            fragmentRegisterBinding.idBtnDaftar.setEnabled(
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
                break;
        }
    }


    private void updateTanggalLahir(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        fragmentRegisterBinding.idTextInputLayoutTanggalLahir.getEditText().setText(dateFormat.format(myCalendar.getTime()), TextView.BufferType.EDITABLE);
    }
}