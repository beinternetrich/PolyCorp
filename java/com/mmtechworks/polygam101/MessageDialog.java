package com.mmtechworks.polygam101;

//MessageDialog used in conjunction with Recycler-Swipe/FlexibleViews
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import util.Utils;

public class MessageDialog extends DialogFragment {
	
	public static final String TAG = MessageDialog.class.getSimpleName();
	public static final String ARG_ICON = "icon";
	public static final String ARG_TITLE = "title";
	public static final String ARG_MESSAGE = "message";

	public MessageDialog() {}
	
	public static MessageDialog newInstance(int icon, String title, String message) {
		return newInstance(icon, title, message, null);
	}
	
	public static MessageDialog newInstance(int icon, String title, String message, Fragment fragment) {
		MessageDialog confirmDialog = new MessageDialog();
		Bundle args = new Bundle();
		args.putInt(ARG_ICON, icon);
		args.putString(ARG_TITLE, title);
		args.putString(ARG_MESSAGE, message);
		confirmDialog.setArguments(args);
		if (fragment != null) confirmDialog.setTargetFragment(fragment, 0);
		return confirmDialog;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public AlertDialog onCreateDialog(Bundle savedInstanceState) {
		View dialogView = LayoutInflater.from(getActivity())
				.inflate(R.layout.dialog_message, null);
		
		TextView messageView = (TextView) dialogView.findViewById(R.id.message);
		messageView.setMovementMethod(LinkMovementMethod.getInstance());
		messageView.setText(Html.fromHtml(getArguments().getString(ARG_MESSAGE)));
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());//), R.style.AppTheme_AlertDialog);
		builder.setTitle(getArguments().getString(ARG_TITLE))
				//.setIconAttribute(android.R.attr.alertDialogIcon)
				.setIcon(getArguments().getInt(ARG_ICON))
				.setView(dialogView)
				.setPositiveButton(R.string.OK, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		
		final AlertDialog dialog = builder.create();
		dialog.setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Utils.hideSoftInput(getActivity());
			}
		});
		
		return dialog;
	}
	
}