package com.roshan.project2_roshan_sreekanth.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roshan.project2_roshan_sreekanth.R;
import com.roshan.project2_roshan_sreekanth.models.ProductModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    View root;
    private RecyclerView myRecyclerView;
    private HashMap<String, List<ProductModel>> products;
    private ArrayList<ProductModel> productsList;
    public static PlaceholderFragment newInstance(int index)
    {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        products = new HashMap<String, List<ProductModel>>();

        ArrayList<ProductModel> mensWear = new ArrayList<>();
        ArrayList<ProductModel> womensWear = new ArrayList<>();
        ArrayList<ProductModel> kidsWear = new ArrayList<>();

        mensWear.add(new ProductModel("Blue Shirt Slim Fit", "MBSSF", 30));
        mensWear.add(new ProductModel("Casual Fit Red Cardigan", "MCFRC", 27));
        mensWear.add(new ProductModel("Turtleneck Sweater", "MLSTS", 21));
        mensWear.add(new ProductModel("Under Armour Blitzing Cap", "MUABC", 21));
        mensWear.add(new ProductModel("Sherpa Trucker Jacket", "MSTJ", 38));

        womensWear.add(new ProductModel("Crop Top Sleeve Shirt","WCTSS", 14));
        womensWear.add(new ProductModel("Casual Graphic Tee","WCGT", 12));
        womensWear.add(new ProductModel("V-Neck Button Down Top","WVBDT", 15));
        womensWear.add(new ProductModel("Short Sleeve Casual Tee","WSSCT", 15));
        womensWear.add(new ProductModel("Polyester Cuffed Shawl","WPCS", 25));

        kidsWear.add(new ProductModel("Casual Pocket Tee","KCPT", 13));
        kidsWear.add(new ProductModel("Long Sleeve Tee","KLST", 15));
        kidsWear.add(new ProductModel("Plaid Long Sleeve Hoodie","KPLSH", 20));
        kidsWear.add(new ProductModel("Long Sleeve Thermal Tee","KLSTT", 96));
        kidsWear.add(new ProductModel("White V-Neck Tee","KWVT", 10));

        products.put("0", mensWear);
        products.put("1", womensWear);
        products.put("2", kidsWear);

        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        productsList = (ArrayList<ProductModel>) products.get(Integer.toString(index - 1));
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_store, container, false);
        myRecyclerView = root.findViewById(R.id.productsRecyclerView);

        RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), productsList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerAdapter);

        return root;
    }
}