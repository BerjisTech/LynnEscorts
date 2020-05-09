package tech.berjis.lynnescorts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ViewHolder> {

    private List<ImageList> listData;
    private LayoutInflater mInflater;
    private String view_type;

    ImagePagerAdapter(Context context, List<ImageList> data, String view_type) {
        this.mInflater = LayoutInflater.from(context);
        this.listData = data;
        this.view_type = view_type;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.new_post_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageList ld = listData.get(position);
        Picasso.get().load(ld.getImage()).into(holder.image);
        if (view_type.equals("new_post")) {
            holder.delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, delete;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            delete = itemView.findViewById(R.id.delete);
        }
    }

}
