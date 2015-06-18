package com.kenny.openimgur.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kenny.openimgur.R;
import com.kenny.openimgur.activities.ConvoThreadActivity;
import com.kenny.openimgur.adapters.ConvoAdapter;
import com.kenny.openimgur.api.ApiClient;
import com.kenny.openimgur.api.Endpoints;
import com.kenny.openimgur.api.ImgurBusEvent;
import com.kenny.openimgur.classes.ImgurConvo;
import com.kenny.openimgur.classes.ImgurHandler;
import com.kenny.openimgur.ui.MultiStateView;
import com.kenny.openimgur.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.util.ThrowableFailureEvent;

/**
 * Created by kcampagna on 12/24/14.
 */
public class ProfileMessagesFragment extends BaseFragment implements View.OnClickListener, View.OnLongClickListener {
    private static final String KEY_ITEMS = "items";

    @InjectView(R.id.multiView)
    MultiStateView mMultiStatView;

    @InjectView(R.id.commentList)
    RecyclerView mListView;

    private ConvoAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (user == null) {
            throw new IllegalStateException("User is not logged in");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_ITEMS)) {
            List<ImgurConvo> items = savedInstanceState.getParcelableArrayList(KEY_ITEMS);
            mAdapter = new ConvoAdapter(getActivity(), items, this, this);
            mListView.setAdapter(mAdapter);
            mMultiStatView.setViewState(MultiStateView.ViewState.CONTENT);
        }
    }

    @Override
    public void onClick(View v) {
        ImgurConvo convo = mAdapter.getItem(mListView.getLayoutManager().getPosition(v));
        startActivityForResult(ConvoThreadActivity.createIntent(getActivity(), convo), ConvoThreadActivity.REQUEST_CODE);
    }

    @Override
    public boolean onLongClick(View v) {
        final ImgurConvo convo = mAdapter.getItem(mListView.getLayoutManager().getPosition(v));
        new AlertDialog.Builder(getActivity(), theme.getAlertDialogTheme())
                .setTitle(R.string.convo_delete)
                .setMessage(R.string.convo_delete_message)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteConvo(convo);
                    }
                }).show();
        return true;
    }

    private void deleteConvo(ImgurConvo convo) {
        String url = String.format(Endpoints.DELETE_CONVO.getUrl(), convo.getId());
        // We don't care about the response
        new ApiClient(url, ApiClient.HttpRequest.DELETE).doWork(ImgurBusEvent.EventType.CONVO_DELETE, convo.getId(), null);
        mAdapter.removeItem(convo.getId());

        if (mAdapter.isEmpty()) {
            mMultiStatView.setViewState(MultiStateView.ViewState.EMPTY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (mAdapter == null || mAdapter.isEmpty()) {
            new ApiClient(Endpoints.ACCOUNT_CONVOS.getUrl(), ApiClient.HttpRequest.GET).doWork(ImgurBusEvent.EventType.ACCOUNT_CONVOS, null, null);
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void onEventAsync(@NonNull ImgurBusEvent event) {
        if (event.eventType == ImgurBusEvent.EventType.ACCOUNT_CONVOS) {
            try {
                int status = event.json.getInt(ApiClient.KEY_STATUS);

                if (status == ApiClient.STATUS_OK) {
                    if (event.json.get(ApiClient.KEY_DATA) instanceof Boolean) {
                        mHandler.sendEmptyMessage(ImgurHandler.MESSAGE_EMPTY_RESULT);
                        return;
                    }

                    JSONArray data = event.json.getJSONArray(ApiClient.KEY_DATA);
                    List<ImgurConvo> convos = new ArrayList<>(data.length());

                    for (int i = 0; i < data.length(); i++) {
                        convos.add(new ImgurConvo(data.getJSONObject(i)));
                    }

                    if (convos.isEmpty()) {
                        mHandler.sendEmptyMessage(ImgurHandler.MESSAGE_EMPTY_RESULT);
                    } else {
                        mHandler.sendMessage(ImgurHandler.MESSAGE_ACTION_COMPLETE, convos);
                    }

                } else {
                    mHandler.sendMessage(ImgurHandler.MESSAGE_ACTION_FAILED, ApiClient.getErrorCodeStringResource(status));
                }

            } catch (JSONException ex) {
                LogUtil.e(TAG, "Error parsing profile comments", ex);
                mHandler.sendMessage(ImgurHandler.MESSAGE_ACTION_FAILED, ApiClient.getErrorCodeStringResource(ApiClient.STATUS_JSON_EXCEPTION));
            }
        }
    }

    public void onEventMainThread(ThrowableFailureEvent event) {
        Throwable e = event.getThrowable();

        if (e instanceof IOException) {
            mHandler.sendMessage(ImgurHandler.MESSAGE_ACTION_FAILED, ApiClient.getErrorCodeStringResource(ApiClient.STATUS_IO_EXCEPTION));
        } else if (e instanceof JSONException) {
            mHandler.sendMessage(ImgurHandler.MESSAGE_ACTION_FAILED, ApiClient.getErrorCodeStringResource(ApiClient.STATUS_JSON_EXCEPTION));
        } else {
            mHandler.sendMessage(ImgurHandler.MESSAGE_ACTION_FAILED, ApiClient.getErrorCodeStringResource(ApiClient.STATUS_INTERNAL_ERROR));
        }

        LogUtil.e(TAG, "Error received from Event Bus", e);

    }

    private ImgurHandler mHandler = new ImgurHandler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_ACTION_COMPLETE:
                    List<ImgurConvo> convos = (ArrayList<ImgurConvo>) msg.obj;
                    mAdapter = new ConvoAdapter(getActivity(), convos, ProfileMessagesFragment.this, ProfileMessagesFragment.this);
                    mListView.setAdapter(mAdapter);
                    mMultiStatView.setViewState(MultiStateView.ViewState.CONTENT);
                    break;

                case MESSAGE_ACTION_FAILED:
                    mMultiStatView.setErrorText(R.id.errorMessage, (Integer) msg.obj);
                    mMultiStatView.setViewState(MultiStateView.ViewState.ERROR);
                    break;

                case MESSAGE_EMPTY_RESULT:
                    mMultiStatView.setEmptyText(R.id.emptyMessage, getString(R.string.profile_no_convos));
                    mMultiStatView.setViewState(MultiStateView.ViewState.EMPTY);
                    break;
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null && !mAdapter.isEmpty()) {
            outState.putParcelableArrayList(KEY_ITEMS, mAdapter.retainItems());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConvoThreadActivity.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ImgurConvo convo = data.getParcelableExtra(ConvoThreadActivity.KEY_BLOCKED_CONVO);
                    if (convo != null && mAdapter != null) deleteConvo(convo);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
