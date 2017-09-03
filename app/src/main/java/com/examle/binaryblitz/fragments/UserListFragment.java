package com.examle.binaryblitz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.examle.binaryblitz.MainActivity;
import com.examle.binaryblitz.R;
import com.examle.binaryblitz.adapter.UserAdapter;
import com.examle.binaryblitz.model.User;
import com.examle.binaryblitz.mvp.UserListPresenter;
import com.examle.binaryblitz.mvp.UserListView;
import com.examle.binaryblitz.utils.ItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListFragment extends Fragment implements UserListView {

    @BindView(R.id.list_products)
    RecyclerView listProducts;

    private UserListPresenter presenter;
    private UserAdapter adapter;
    private List<User> userList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UserListPresenter(this);
        adapter = new UserAdapter(initItemClick());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getUserList();
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listProducts.setLayoutManager(layoutManager);
        listProducts.setAdapter(adapter);
    }

    private ItemClickListener initItemClick() {
      return new ItemClickListener() {
          @Override
          public void onItemClick(View view, int position) {
              ((MainActivity) getActivity()).visibleFab(false);
              ((MainActivity) getActivity())
                      .showFragment(UserFragment.newInstance(userList.get(position).getId(), true));
          }
      };
    }

    @Override
    public void onUserLoaded(List<User> users) {
        userList = users;
        adapter.setUsersList(users);
    }

    @Override
    public void showToast() {
        Toast.makeText(getContext(), "Проверьте интернет соеденение", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        ((MainActivity) getActivity()).showDialog();
    }

    @Override
    public void hideProgressDialog() {
        ((MainActivity) getActivity()).hideDialog();
    }
}
