package pl.lodz.p.pathfinder.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

import pl.lodz.p.pathfinder.PoiUtils;
import pl.lodz.p.pathfinder.R;
import pl.lodz.p.pathfinder.model.PointOfInterest;

public abstract class PoiListFragment extends Fragment implements PhotoDownloadCallback

{

    private List<PointOfInterest> myPlacesList;


    private RVAdapterRemovable adapter;
    private RVAdapterPhotoUpdateable adapterPhotoUpdateable;


//TODO truncate long poi descriptions



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_PARAM1 = "poiList";
    static final String ARG_PARAM2 = "listenerType";




    /**
     * DO NOT USE
     * use the newInstance method instead, in order to pass data
     */
    public PoiListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            myPlacesList = getArguments().getParcelableArrayList(ARG_PARAM1);
//            listenerType = getArguments().getString(ARG_PARAM2);
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


        RecyclerView.Adapter adapter = createRVAdapter(myPlacesList);
        this.adapter = (RVAdapterRemovable) adapter;
        this.adapterPhotoUpdateable = (RVAdapterPhotoUpdateable) adapter;
        rv.setAdapter(adapter);


        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .build();
        googleApiClient.connect();

        int i=0;
        for(PointOfInterest p : myPlacesList)
        {
            PoiUtils.getPhotosForPoi(googleApiClient,p,i,this);
            i++;
        }

        return v;
    }

    @Override
    public void onDestroyView()
    {
        adapter = null;
        adapterPhotoUpdateable = null;
        super.onDestroyView();
    }

    RecyclerView.Adapter createRVAdapter(List<PointOfInterest> dataset)
    {
        return new PoiCardRVAdapter(dataset,this.createItemListener(), this.createPhotoList(dataset));
    }

    protected List<Bitmap> createPhotoList(List<PointOfInterest> dataset)
    {
        List<Bitmap> result = new ArrayList<>(dataset.size());

        Bitmap placeholder = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.placeholder);
        for (int i = 0; i < dataset.size(); i++)
        {
            result.add(placeholder);
        }

        return result;
    }


    @Override
    public void photoDownloaded(Bitmap bitmap, int position)
    {
        updatePhoto(position,bitmap);
    }


    public void updatePhoto(int position, Bitmap bitmap)
    {
        if(adapterPhotoUpdateable != null){
            adapterPhotoUpdateable.updatePhoto(bitmap,position);
        }
    }



    public void removeAt(int position)
    {
        if(adapter!= null){
            adapter.removeAt(position);
        }
    }



    abstract RvItemClickListener<PointOfInterest> createItemListener();
}
