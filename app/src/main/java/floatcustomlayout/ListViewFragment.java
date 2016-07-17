package floatcustomlayout;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.n930044.myapplication.R;


public class ListViewFragment extends Fragment {

	private View mContentView;
	private ListView mListView;
	private List<String> mList = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContentView = LayoutInflater.from(getActivity()).inflate(R.layout.float_layout_inner_listview, null);
		mListView = (ListView) mContentView.findViewById(R.id.float_layout_inner_view);
		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return mContentView;
	}

	private void initData() {
		for (int i = 0; i < 100; i++) {
			mList.add("ListView_Item" + i);
		}
		mListView.setAdapter(new Myadapter());
	}

	public static Fragment getInstain() {
		Fragment fragment = new ListViewFragment();
		return fragment;
	}

	class Myadapter extends BaseAdapter{

		private LayoutInflater mInflater;

		Myadapter(){

			this.mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Log.d("ktr:", "getView:" + position);

			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listitem, null);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView.findViewById(R.id.textView);
				convertView.setTag(holder); //绑定ViewHolder对象
			}
			else {
				holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
			}

				holder.textView.setText("position: " + position);

			return convertView;
		}

		/*存放控件 的ViewHolder*/
		public final class ViewHolder {
			public TextView textView;
		}
	}
}
