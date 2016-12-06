package inputcells;
/**
 * @author 刘世杰
 * 添加图片的的基础控件
 */

import com.example.helloworld.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureInputCellFragment extends BaseInputCellFragment {

	//先定义空视图
	private static final int REQUESTCODE_ALBUM = 1;
	private static final int REQUESTCODE_CAMERA = 2;
	ImageView imageView;
	TextView labelText;
	TextView hintText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inputcell_picture, container);
		
		//然后在此处指向layout中的控件
		imageView = (ImageView) view.findViewById(R.id.image);
		labelText = (TextView) view.findViewById(R.id.label);
		hintText = (TextView) view.findViewById(R.id.hint);

		imageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				onImagevViewClick();
			}
		});
		return view;
	}
			//实现点击事件
	void onImagevViewClick() {
		String[] items = { "拍照", "相册" };
		new AlertDialog.Builder(getActivity()).setTitle(labelText.getText()).setItems(items, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					takePhoto();
					break;

				case 1:
					pickFromAlbum();
					break;

				default:
					break;
				}
			}
		}).setNegativeButton("取消", null).show();

	}

	//下面为上面所用到的方法的定义
	void pickFrmCamera() {
		Intent itnt = new Intent(Intent.ACTION_GET_CONTENT);
		itnt.setType("image/*");
		startActivityForResult(itnt, REQUESTCODE_ALBUM);
	}

	void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, REQUESTCODE_CAMERA);
	}

	void pickFromAlbum() {
		Intent itnt = new Intent(Intent.ACTION_GET_CONTENT);
		itnt.setType("image/*");
		startActivityForResult(itnt, REQUESTCODE_ALBUM);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_CANCELED)
			return;

		if (requestCode == REQUESTCODE_CAMERA) {
			Bitmap bmp = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(bmp);
		} else if (requestCode == REQUESTCODE_ALBUM) {
			try {
				Bitmap bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
				imageView.setImageBitmap(bmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setLabelText(String labelText) {
		this.labelText.setText(labelText);
	}

	public void setHintText(String hintText) {
		this.hintText.setHint(hintText);
	}
}
