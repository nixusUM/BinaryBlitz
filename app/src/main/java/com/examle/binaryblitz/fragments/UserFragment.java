package com.examle.binaryblitz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.examle.binaryblitz.MainActivity;
import com.examle.binaryblitz.R;
import com.examle.binaryblitz.api.SaveUserQuery;
import com.examle.binaryblitz.model.User;
import com.examle.binaryblitz.mvp.UserPresenter;
import com.examle.binaryblitz.mvp.UserView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends Fragment implements UserView, Validator.ValidationListener  {

    @Order(1)
    @NotEmpty(sequence = 1, messageResId = R.string.field_is_required)
    @BindView(R.id.txt_first_name)
    EditText txtFirstName;
    @Order(2)
    @NotEmpty(sequence = 1, messageResId = R.string.field_is_required)
    @BindView(R.id.txt_last_name)
    EditText txtLastName;
    @Order(3)
    @NotEmpty(sequence = 1, messageResId = R.string.field_is_required)
    @Email(sequence = 2, messageResId = R.string.invalid_email)
    @BindView(R.id.txt_email)
    EditText txtEmail;
    @BindView(R.id.txt_avatar)
    EditText txtAvatar;

    private Validator validator;
    private UserPresenter presenter;
    private String userId;
    private boolean isEditUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserPresenter(this);
        validator = new Validator(this);
        validator.setValidationMode(Validator.Mode.IMMEDIATE);
        validator.setValidationListener(this);
        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            isEditUser = getArguments().getBoolean("editUser");
        }
    }

    public static UserFragment newInstance(String userId, boolean editUser) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putBoolean("editUser", editUser);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.showUser(userId);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setUpBackNavigation(isEditUser  ? "Edit user" : "Create user");
    }

    @Override
    public void showUser(User user) {
        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtEmail.setText(user.getEmail());
        txtAvatar.setText(user.getAvatarUrl());
    }

    @OnClick(R.id.btn_save)
    public void saveUser() {
        validator.validate();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        ((MainActivity) getActivity()).showDialog();
    }

    @Override
    public void hideProgressDialog() {
        ((MainActivity) getActivity()).hideDialog();
    }

    @Override
    public void onValidationSucceeded() {
        if (isEditUser) {
            presenter.editUser(new SaveUserQuery(fillUser()));
        } else {
            presenter.saveUser(new SaveUserQuery(fillUser()));
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                showToast(message);
            }
        }
    }

    private User fillUser() {
        return new User(
                txtFirstName.getText().toString(),
                txtLastName.getText().toString(),
                txtEmail.getText().toString(),
                txtAvatar.getText().toString());
    }
}
