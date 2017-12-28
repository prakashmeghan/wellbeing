package com.conceptappsworld.wellbeing.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conceptappsworld.wellbeing.R;
import com.conceptappsworld.wellbeing.model.Category;

import java.util.ArrayList;

/**
 * Created by prakash.meghani on 12/28/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CateoryAdapterViewHolder> {

    private ArrayList<Category> alCategory;
    private Context context;

    private final CategoryAdapterOnClickHandler mClickHandler;

    public interface CategoryAdapterOnClickHandler {
        void onClick(Category category);
    }

    public CategoryAdapter(Context _context, CategoryAdapterOnClickHandler clickHandler) {
        this.context = _context;
        mClickHandler = clickHandler;
    }

    @Override
    public CateoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_category;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new CateoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CateoryAdapterViewHolder holder, int position) {
        String categoryName = alCategory.get(position).getCategoryName();
        holder.tvCategory.setText(categoryName);
    }

    @Override
    public int getItemCount() {
        if (null == alCategory) return 0;
        return alCategory.size();
    }

    public void setCategoryData(ArrayList<Category> categoryData) {
        alCategory = categoryData;
        notifyDataSetChanged();
    }

    public class CateoryAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tvCategory;

        public CateoryAdapterViewHolder(View view) {
            super(view);
            tvCategory = view.findViewById(R.id.tv_category);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Category category = alCategory.get(adapterPosition);
            mClickHandler.onClick(category);
        }
    }
}
