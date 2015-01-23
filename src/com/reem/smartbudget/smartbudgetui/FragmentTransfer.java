package com.reem.smartbudget.smartbudgetui;

import com.reem.smartbudget.R;
import com.reem.smartbudget.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTransfer extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_transfer, container,
				false);

		return rootView;
	}

}
