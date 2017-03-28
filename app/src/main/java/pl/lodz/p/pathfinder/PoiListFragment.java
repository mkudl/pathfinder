package pl.lodz.p.pathfinder;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.model.PointOfInterest;

//FIXME fix javadoc
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PoiListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PoiListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoiListFragment extends Fragment
{

    private List<PointOfInterest> myPlacesList;
    String listenerType;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "poiList";
    private static final String ARG_PARAM2 = "listenerType";


//    private OnFragmentInteractionListener mListener;


    /**
     * DO NOT USE
     * use the newInstance method instead, in order to pass data
     */
    public PoiListFragment()
    {
        // Required empty public constructor
    }

    //FIXME fix javadoc
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PoiListFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static PoiListFragment newInstance(String param1, String param2)
    public static PoiListFragment newInstance(List<PointOfInterest> poiList, String listenerType) //TODO? replace string with enum
    {
        PoiListFragment fragment = new PoiListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1,new ArrayList<Parcelable>(poiList));
        args.putString(ARG_PARAM2,listenerType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            myPlacesList = getArguments().getParcelableArrayList(ARG_PARAM1);
            listenerType = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.poi_menu_tab_my_fragment, container, false);

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.poi_my_fragment_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        if(myPlacesList==null) myPlacesList = new ArrayList<>();

        PoiCardRVAdapter adapter = new PoiCardRVAdapter(myPlacesList,this.createItemListener(listenerType));
        rv.setAdapter(adapter);


        return v;
    }




    //      TODO? Change to actual factory method?(polymorphism and shit)
    private RvItemClickListener<PointOfInterest> createItemListener(String type)
    {
        RvItemClickListener<PointOfInterest> result = null;
        switch (type)
        {
            case "MOVE_MAP":
                result = new PoiListOnClickUpdateMap();
                break;
            case "OPEN_EDIT":
                result = new PoiListOnClickOpenEditMode();
                break;
            default:
                throw new IllegalArgumentException("Unknown argument" + type);
        }

        return result;
    }




//    private View.OnClickListener createOnClickListener(PoiViewListenerType listenerType)
//    {
//
//    }





//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri)
//    {
//        if (mListener != null)
//        {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context)
//    {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach()
//    {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener
//    {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
