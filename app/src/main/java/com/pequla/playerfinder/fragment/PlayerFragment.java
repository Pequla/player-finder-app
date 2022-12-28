package com.pequla.playerfinder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pequla.playerfinder.DetailsActivity;
import com.pequla.playerfinder.R;
import com.pequla.playerfinder.adapter.DataAdapter;
import com.pequla.playerfinder.model.DataModel;
import com.pequla.playerfinder.model.DataPageModel;
import com.pequla.playerfinder.service.DialogCallback;
import com.pequla.playerfinder.service.RestService;

public class PlayerFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static int currentPage = 0;

    private Button mBtnPrevious;
    private TextView mCurrentPage;
    private Button mBtnNext;
    private ListView mListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnPrevious = view.findViewById(R.id.btn_previous);
        mCurrentPage = view.findViewById(R.id.current_page);
        mBtnNext = view.findViewById(R.id.btn_next);
        mListView = view.findViewById(R.id.player_list);

        // Retrieve data
        loadData();
    }

    private void loadData() {
        RestService service = RestService.getInstance();
        service.getDataPaged(currentPage, 6, new DialogCallback(getActivity(), response -> {
            String json = response.body().string();
            final DataPageModel model = service.getMapper().readValue(json, DataPageModel.class);
            requireActivity().runOnUiThread(() -> {
                mBtnPrevious.setEnabled(!model.isFirst());
                mBtnPrevious.setOnClickListener((v) -> {
                    currentPage = currentPage - 1;
                    loadData();
                });
                mCurrentPage.setText(String.valueOf(currentPage));
                mBtnNext.setEnabled(!model.isLast());
                mBtnNext.setOnClickListener((v) -> {
                    currentPage = currentPage + 1;
                    loadData();
                });

                // Create data adapter to form a list
                DataAdapter adapter = new DataAdapter(requireActivity(), R.layout.player_list_row, model.getContent());
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(this);
            });
        }));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Open player details
        DataModel model = (DataModel) adapterView.getItemAtPosition(i);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.PLAYER_DATA_ID_KEY, String.valueOf(model.getId()));
        startActivity(intent);
    }
}