package com.zyx.rpc.venue;

import com.zyx.entity.venue.Venue;
import com.zyx.param.venue.FindVenueParam;
import com.zyx.param.venue.VenueParam;

import java.util.List;
import java.util.Map;

/**
 * Created by MrDeng on 2016/11/10.
 */
public interface VenueFacade {

    public void uploadVenue(Venue venue);

    public List<Venue> getVenues(VenueParam param);

    /**
     * 场馆查询
     * @param param
     * @return
     */
    Map<String,Object> findVenue(FindVenueParam param);

    /**
     * 场馆详细信息查询
     *
     * @param id
     * @return
     */
    Map<String,Object> findVenueById(Integer id);

}
