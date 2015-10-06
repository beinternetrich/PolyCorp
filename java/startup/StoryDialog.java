package startup;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mmtechworks.polygam101.R;

public class StoryDialog extends DialogFragment {
    TextView storyBox;
    public StoryDialog() {}
    // Empty constructor required for DialogFragment

    @Override
    public void onStart() {
        super.onStart();
        Log.v("LOG_SDialog", "01 onStart");
        // safety check
        if (getDialog() == null) {return;}
        Log.v("LOG_SDialog", "02 getDialog not NULL");

        // set animation usage on show/hide dialog
        Log.v("LOG_SDialog", "03 getting Dialog");
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
        Log.v("LOG_SDialog", "04 got Dialog");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.v("LOG_SDialog", "05 getting view story");
//        View view = inflater.inflate(R.layout.activity_storydialog, container);
//        TextView storyBox = (TextView) view.findViewById(R.id.storyBox);
//        //storyBox.setMovementMethod(LinkMovementMethod.getInstance());
//        storyBox.setText(Html.fromHtml("<b>Hello Gamer</b> - For more info on this game visit the PolyCorp website.<p><b>This text is bold</b> <em>This text is emphasized</em> <code>This is computer output</code> This is<sub> subscript</sub> and <sup>superscript</sup></p><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut non consequat arcu, sed malesuada massa. Quisque ligula lectus, iaculis eget finibus vestibulum, hendrerit sed massa. Nam ac risus congue, lobortis arcu eget, ornare elit. Integer elementum, ex eget dapibus rutrum, eros purus vestibulum nibh, nec faucibus nisi neque sed elit. Proin id posuere diam. Donec porta metus at nibh bibendum iaculis. Aliquam non felis ut purus interdum tempus maximus quis dolor. Nunc at metus nec tortor scelerisque facilisis id ac felis. Nullam tempus ipsum felis, sit amet porttitor neque sagittis ac.<br /><br />Nulla rutrum, est at tempor vehicula, lacus lacus elementum neque, sed aliquet sem risus fringilla purus. Duis bibendum libero eu mattis rutrum. Nunc sed dolor lobortis, placerat urna sit amet, aliquet elit. In eget ipsum euismod libero convallis vulputate a eu quam. Sed odio ex, efficitur nec iaculis vitae, aliquam nec dolor. Maecenas sollicitudin tincidunt semper. Curabitur justo odio, ultrices ut mi vitae, interdum convallis metus. Etiam velit leo, imperdiet et egestas sit amet, pharetra sed urna. Curabitur varius sapien mauris, quis consectetur odio viverra vitae. Fusce vel neque mollis, facilisis libero ut, iaculis mauris.</p>"));
//        //builder.setView(layout);
//        //AlertDialog alert = builder.show();
//        Log.v("LOG_SDialog", "STORY LOADED");
//        return view;

        LinearLayout linLayout=new LinearLayout(getActivity());
        Log.v("LOG_SDialog", "06 getting view story");
        //View view=new View(getActivity());
        Log.v("LOG_SDialog", "07 getting view story");
        //TextView storyBox = (TextView) view.findViewById(R.id.storyBox);
        storyBox = new TextView(getActivity());
        Log.v("LOG_SDialog", "08 getting view story");
//        //storyBox.setMovementMethod(LinkMovementMethod.getInstance());
        Log.v("LOG_SDialog", "09 getting view story");
        storyBox.setText(Html.fromHtml("<div style='font-size=large'><b>Hello Gamer</b> - For more info on this game visit the PolyCorp website.<p><b>This text is bold</b> <em>This text is emphasized</em> <code>This is computer output</code> This is<sub> subscript</sub> and <sup>superscript</sup></p><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut non consequat arcu, sed malesuada massa. Quisque ligula lectus, iaculis eget finibus vestibulum, hendrerit sed massa. Nam ac risus congue, lobortis arcu eget, ornare elit. Integer elementum, ex eget dapibus rutrum, eros purus vestibulum nibh, nec faucibus nisi neque sed elit. Proin id posuere diam. Donec porta metus at nibh bibendum iaculis. Aliquam non felis ut purus interdum tempus maximus quis dolor. Nunc at metus nec tortor scelerisque facilisis id ac felis. Nullam tempus ipsum felis, sit amet porttitor neque sagittis ac.<br /><br />Nulla rutrum, est at tempor vehicula, lacus lacus elementum neque, sed aliquet sem risus fringilla purus. Duis bibendum libero eu mattis rutrum. Nunc sed dolor lobortis, placerat urna sit amet, aliquet elit. In eget ipsum euismod libero convallis vulputate a eu quam. Sed odio ex, efficitur nec iaculis vitae, aliquam nec dolor. Maecenas sollicitudin tincidunt semper. Curabitur justo odio, ultrices ut mi vitae, interdum convallis metus. Etiam velit leo, imperdiet et egestas sit amet, pharetra sed urna. Curabitur varius sapien mauris, quis consectetur odio viverra vitae. Fusce vel neque mollis, facilisis libero ut, iaculis mauris.</p></div>"));
        Log.v("LOG_SDialog", "10 getting view story");
        linLayout.addView(storyBox);
        Log.v("LOG_SDialog", "11 getting view story");
        Log.v("LOG_SDialog", "STORY LOADED");
        return linLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v("LOG_OSDlg", "SDlg-Calling super.SaveInstance...");
        super.onSaveInstanceState(savedInstanceState);
        Log.v("LOG_OSDlg", "SDlg-Called super.SaveInstance...");
        // Save UI state changes to the savedInstanceState. Bundle passed to onCreate if process killed/restarted.
        Log.v("LOG_OSDlg", "SDlgSaved Instance Saved. " + storyBox.getText());
    }
    //@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        storyBox.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState. Bundle passed to onCreate.
        Log.v("LOG_OSDlg", "SDlg-Called super.onRestoreI ok."+storyBox.getText());
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }

}