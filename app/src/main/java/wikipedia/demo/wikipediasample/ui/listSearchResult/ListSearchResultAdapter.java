package wikipedia.demo.wikipediasample.ui.listSearchResult;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wikipedia.demo.wikipediasample.R;
import wikipedia.demo.wikipediasample.models.Page;

public class ListSearchResultAdapter extends RecyclerView.Adapter<ListSearchResultAdapter.ViewHolder> {

    private List<Page> pages = new ArrayList<>();
    private Context mContext;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        void onClickItem(Page page);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_search_result_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Page page = pages.get(position);
        holder.titleTv.setText(page.getTitle());
        if (page.getTerms() != null && page.getTerms().getDescription() != null) {
            holder.descTv.setText(page.getTerms().getDescription().get(0));
        } else {
            holder.descTv.setText("N/A");
        }

        if (page.getThumbnail() != null && page.getThumbnail().getSource() != null) {
            Glide.with(holder.imageView)
                    .load(page.getThumbnail().getSource())
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.ic_question_mark);
        }
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_iv)
        ImageView imageView;
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.desc_tv)
        TextView descTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onClickItem(pages.get(getAdapterPosition()));
                }
            });
        }
    }

    public void addData(List<Page> pages) {
        if (pages != null) {
            this.pages.clear();
            this.pages.addAll(pages);
            notifyDataSetChanged();
        }
    }
}
