package com.zyx.rpc.venue;

import com.zyx.entity.venue.Venue;
import com.zyx.param.venue.VenueParam;

import java.util.List;

/**
 * Created by MrDeng on 2016/11/10.
 */
public interface VenueFacade {

    public void uploadVenue(Venue venue);

    public List<Venue> getVenues(VenueParam param);

}
