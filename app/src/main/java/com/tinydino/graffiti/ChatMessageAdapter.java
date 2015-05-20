package com.tinydino.graffiti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.drew.graffiti.R;

import java.util.List;

/**
 * Created by jetters on 5/17/15.
 */


public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {

    private List<ChatMessage> items;

    public ChatMessageAdapter(Context context, int textViewResourceId, List<ChatMessage> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.list_item_style, null);
        }

        ChatMessage message = items.get(position);

        ImageView image = (ImageView) v.findViewById(R.id.chat_pic);
        if (message.Image != null) {
            image.setImageBitmap(message.Image);
            image.setVisibility(View.VISIBLE);
        }
        else {
            image.setImageDrawable(null);
            image.setVisibility(View.GONE);
        }

        TextView userText = (TextView) v.findViewById(R.id.chat_user);
        userText.setText(message.UserName + "\n[" + message.UserLocation + "]");

        TextView messageText = (TextView) v.findViewById(R.id.chat_message);
        messageText.setText(message.Message);

        return v;
    }
}
