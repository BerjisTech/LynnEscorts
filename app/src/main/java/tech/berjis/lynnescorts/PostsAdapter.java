package tech.berjis.lynnescorts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vanniktech.emoji.EmojiTextView;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Posts> listData;
    private Context mContext;

    PostsAdapter(Context mContext, List<Posts> listData) {
        this.mContext = mContext;
        this.listData = listData;
    }

    @NonNull
    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Posts ld = listData.get(position);
        if (ld.getText().equals("")) {
            holder.postText.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        EmojiTextView userName, postText;
        TextView postTime;
        ImageView likes, comments, postImage;
        View mView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            postText = itemView.findViewById(R.id.postText);
            postTime = itemView.findViewById(R.id.postTime);
            likes = itemView.findViewById(R.id.likes);
            comments = itemView.findViewById(R.id.comments);
            postImage = itemView.findViewById(R.id.postImage);
            mView = itemView;
        }
    }
}
