package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradepredictionsystem.R;
import com.example.gradepredictionsystem.TeacherFragment;

import java.util.ArrayList;
import java.util.List;

import model.Functions;

import static android.content.ContentValues.TAG;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.Viewholder> implements Filterable {
    private List<Functions> exampleList;
    private List<Functions> exampleListFull;
    public Context context;

    public TeacherAdapter(List<Functions> exampleList,Context context) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
        this.context=context;
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        public Viewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView1 = itemView.findViewById(R.id.text_view1);
            textView2 = itemView.findViewById(R.id.text_view2);


        }
    }


    @Override
    public TeacherAdapter.Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder( TeacherAdapter.Viewholder holder, final int position) {
        Functions currentItem = exampleList.get(position);
        holder.imageView.setImageResource(R.drawable.ic_person_pin_circle_b_24dp);
            holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getCMSID());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick1: "+exampleList.get(position).getName());
//                if (exampleList.get(position).getText1()!=null){
//                sendinfo(exampleList.get(position).getText1(),exampleList.get(position).getText2());}
                if(exampleList!=null) {
                    Log.d(TAG, "onClick1: "+exampleList.get(position).getCMSID());
                    ((TeacherFragment) context).setStudentTab( exampleList.get(position).getName(), exampleList.get(position).getCMSID());
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return exampleList.size();
    }
    @Override
    public Filter getFilter() {
        return filterd;
    }
    private Filter filterd = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Functions> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Functions item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
