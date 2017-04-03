package pl.lodz.p.pathfinder.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PoiSearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PoiSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoiSearchFragment extends Fragment
{


    Button againButton;
    Button actionButton;

    TextView placeName;

    LinearLayout infoLayout;
    LinearLayout actionLayout;



    PointOfInterest currentSelection;



    final int PLACE_PICKER_REQUEST = 9420;


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public PoiSearchFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PoiSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PoiSearchFragment newInstance(String param1, String param2) //FIXME remove parameters
    {
        PoiSearchFragment fragment = new PoiSearchFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null)
//        {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_poi_search, container, false);
        againButton = (Button) v.findViewById(R.id.poi_picker_button_pickagain);
        actionButton = (Button) v.findViewById(R.id.poi_picker_action_button);
        infoLayout = (LinearLayout) v.findViewById(R.id.poi_picker_info_layout);
        actionLayout = (LinearLayout) v.findViewById(R.id.poi_picker_action_layout);
        placeName = (TextView) v.findViewById(R.id.poi_search_selected_name);

        hideUI();

        againButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callPicker();
            }
        });

        //TODO
//        actionButton.setOnClickListener();

//        callPicker();
        return v;
    }


    private void callPicker()
    {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try
        {
            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e)
        {
            e.printStackTrace();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
//                Place place = PlacePicker.getPlace(this,data);
                //TODO remove toast
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(getActivity(), toastMsg, Toast.LENGTH_LONG).show();

                placeName.setText(place.getName());
                currentSelection = new PointOfInterest(place.getName().toString(),"placeholder" /*FIXME*/,place.getLatLng(),place.getId());

                showUI();
            }
        }
    }


    private void hideUI()
    {
        infoLayout.setVisibility(View.INVISIBLE);
        actionLayout.setVisibility(View.GONE);
        actionButton.setVisibility(View.GONE);
    }

    private void showUI()
    {
        infoLayout.setVisibility(View.VISIBLE);
        actionLayout.setVisibility(View.VISIBLE);
        actionButton.setVisibility(View.VISIBLE);
    }



//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//    }

}
