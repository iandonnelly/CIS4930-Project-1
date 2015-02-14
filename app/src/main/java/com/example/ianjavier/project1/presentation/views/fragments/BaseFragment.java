package com.example.ianjavier.project1.presentation.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ianjavier.project1.App;
import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.presentation.model.Message;
import com.example.ianjavier.project1.presentation.views.TabListener;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public abstract class BaseFragment extends Fragment implements Observer {
    private MessageAdapter mAdapter;
    private ListView mListView;
    private Handler mHandler;

    protected TabListener mListener;

    protected abstract int getLayoutResource();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        mListView = (ListView) view.findViewById(R.id.list);
        mListener.onCreateTabView();
        return view;
    }

    public void setMessageLog(List<Message> messageLog) {
        mAdapter = new MessageAdapter(getActivity(), R.layout.message_log_row_layout, messageLog);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void update(Observable observable, Object data) {
        mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
    }

    private class MessageAdapter extends ArrayAdapter<Message> {
        private Activity mContext;
        private List<Message> mMessageLog;

        public MessageAdapter (Activity context, int resource, List<Message> messageLog) {
            super(context, resource, messageLog);
            mContext = context;
            mMessageLog = messageLog;
        }

        private class ViewHolder {
            TextView message;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;

            // If the row has not been instantiated, inflate the view and setup the view holder
            if (rowView == null) {
                LayoutInflater inflater = mContext.getLayoutInflater();
                rowView = inflater.inflate(R.layout.message_log_row_layout, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.message = (TextView) rowView.findViewById(R.id.text1);
                rowView.setTag(viewHolder);
            }

            // If there is a message at the requested location
            if (mMessageLog.get(position) != null) {
                // Set the text
                ViewHolder holder = (ViewHolder) rowView.getTag();
                holder.message.setText(mMessageLog.get(position).getMessage());

                // Set the text color
                TextView textView = (TextView) rowView.findViewById(R.id.text1);
                switch (mMessageLog.get(position).getMessageType()) {
                    case STATUS:
                        textView.setTextColor(App.getContext().getResources().getColor(R.color.message_status));
                        break;
                    case CURRENT_USER:
                        textView.setTextColor(App.getContext().getResources().getColor(R.color.message_current_user));
                        break;
                    case OTHER_USER:
                        textView.setTextColor(App.getContext().getResources().getColor(R.color.message_other_user));
                        break;
                }
            }

            return rowView;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TabListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
