package com.jackson.ccc.viewpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.jackson.ccc.gridview.Data;
import com.jackson.ccc.http.TackPicture;
import com.jackson.ccc.prasehtml.DetailActivity;
import com.jackson.ccc.test.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LXP on 17-3-12.
 */

@EFragment(R.layout.schoolcircle)
public class Fragement2 extends Fragment {
    @ViewById(R.id.schoolIB)
    protected ImageButton schoolIB;
    private ListView scList;
    private Context context;
    private List<Data> mData= new LinkedList<Data>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.schoolcircle, container, false);
        this.scList = (ListView) view.findViewById(R.id.talkAbout);
        initadapter(1);
        this.schoolIB=(ImageButton)view.findViewById(R.id.schoolIB);

        schoolIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetail = new Intent(getActivity(), TackPicture.class);
                getActivity().startActivity(toDetail);
            }
        });
        return view;
    }

        public void initadapter(int pos){
            ArrayAdapter<CharSequence> arrayadapter = ArrayAdapter.createFromResource(
                    getActivity(), R.array.school, android.R.layout.simple_list_item_activated_1);
            scList.setAdapter(arrayadapter);
        }

    @Click(R.id.schoolIB)
        void showDialog() {

        final EditText inputServer = new EditText(getActivity());
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.growup)).setIcon(
                R.mipmap.school).setView(inputServer).setNegativeButton(
                getString(R.string.ok), null);
        builder.setMessage("请输入您要发布的动态：");

        builder.show();
    }

}