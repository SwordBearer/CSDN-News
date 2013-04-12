package xmu.swordbearer.csdn.common.util;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class UIUtils {
	public static void showToast(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static void showErrorDialog(Context context, String title,
			String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (title != null)
			builder.setTitle(title);
		builder.setMessage(text);
		builder.setNegativeButton(R.string.ok, null);
		builder.show();
	}
}
