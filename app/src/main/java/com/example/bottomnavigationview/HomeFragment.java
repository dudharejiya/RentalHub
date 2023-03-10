package com.example.bottomnavigationview;
import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView productRecyclerView,cateogryRecycleView;
    product_adapter product_adapter;
    categoryAdapter categoryAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cateogryRecycleView = view.findViewById(R.id.categoryRecycleHome_ID);
        productRecyclerView = view.findViewById(R.id.product_recycle_id);


        productRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        FirebaseRecyclerOptions<Product_Model> options = new FirebaseRecyclerOptions.Builder<Product_Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Product_Model.class)
                .build();
        product_adapter = new product_adapter(options);
        productRecyclerView.setAdapter(product_adapter);

        cateogryRecycleView.setLayoutManager(new GridLayoutManager(view.getContext(), 12));
        FirebaseRecyclerOptions<categoryModel> options2 = new FirebaseRecyclerOptions.Builder<categoryModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Master_Category"), categoryModel.class)
                .build();
        categoryAdapter = new categoryAdapter(options2);
        cateogryRecycleView.setAdapter(categoryAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        product_adapter.startListening();
        categoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        product_adapter.stopListening();
        categoryAdapter.stopListening();
    }


}