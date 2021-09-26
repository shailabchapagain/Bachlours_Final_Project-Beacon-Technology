package com.example.ipbeacons_mobile_app.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipbeacons_mobile_app.R;
import com.example.ipbeacons_mobile_app.ui.Info_Page.InfopageActivity;

import org.w3c.dom.Text;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private List<Info> infoList;
    Context context;

    public InfoAdapter(List<Info> infoList, Context context) {
        this.infoList = infoList;
        this.context=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_info, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
              final  Info inf= infoList.get(position);
              holder.body.setText(infoList.get(position).getInformationText());
              holder.body.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(context, InfopageActivity.class);
                      intent.putExtra("information",inf.getInformationText());
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      context.startActivity(intent);

                  }
              });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

      TextView body;

       public ViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView .findViewById(R.id.information);
        }

    }
}
