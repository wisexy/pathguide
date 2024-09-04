package com.indi.wisexy.pathguide.util;

import com.alibaba.fastjson2.JSON;
import com.indi.wisexy.pathguide.constant.gaode.GaodeApiConstant;
import com.indi.wisexy.pathguide.model.RequestLocation;
import com.indi.wisexy.pathguide.model.gaode.GaodePath;
import com.indi.wisexy.pathguide.model.gaode.GaodeResponse;
import com.indi.wisexy.pathguide.model.gaode.GaodeRoute;
import com.indi.wisexy.pathguide.model.graph.GuideEdge;
import com.indi.wisexy.pathguide.model.graph.GuideLocation;
import com.indi.wisexy.pathguide.request.HttpRequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
public class GaodeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(GaodeUtils.class);

    public static GaodeResponse requestGaode(String fromLocation, String toLocation) {
        RequestLocation requestLocation = new RequestLocation(GaodeApiConstant.GAODE_MAP_USER_KEY, fromLocation, toLocation);
        LOGGER.debug("param = {}", requestLocation.getRequestParam());
        String result = HttpRequestHelper.syncPost(GaodeApiConstant.GAODE_MAP_API_URL + requestLocation.getRequestParam(), StringUtils.SPACE);
        GaodeResponse gaodeResponse = JSON.parseObject(result, GaodeResponse.class);
        System.out.println(gaodeResponse.toString());
        System.out.println(gaodeResponse.getRoute().toString());
        return gaodeResponse;
    }

    public static GuideLocation parseGuideLocation(String gaodeLocation) {
        if (StringUtils.isBlank(gaodeLocation)) {
            return null;
        }
        String[] location = gaodeLocation.split(",");
        if (StringUtils.isAllBlank(location) || location.length != 2) {
            return null;
        }

        return new GuideLocation(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
    }

    public static TreeSet<GuideEdge> parseGuideEdge(GaodeResponse gaodeResponse) {
        if (gaodeResponse.getStatus() == GaodeApiConstant.RESPONSE_STATUS_FAILED) {
            return null;
        }
        GaodeRoute route = gaodeResponse.getRoute();
        List<GaodePath> paths = route.getPaths();
        if (paths == null || paths.isEmpty()) {
            return null;
        }

        return paths.stream().map(path -> GuideEdge.builder()
                .distance(path.getDistance())
                .duration(path.getDuration())
                .from(parseGuideLocation(route.getOrigin()))
                .to(parseGuideLocation(route.getDestination())).build()).collect(Collectors.toCollection(TreeSet::new));
    }

    public static GuideEdge getFastGuideEdge(GaodeResponse gaodeResponse) {
        TreeSet<GuideEdge> set = parseGuideEdge(gaodeResponse);
        if (!CollectionUtils.isEmpty(set)) {
            return set.first();
        } else {
            return null;
        }
    }

    //https://restapi.amap.com/v3/direction/driving?key=85d57347961b70dd1104fce4897135de&origin=116.481028,39.989643&destination=116.434446,39.90816
    public static void main(String[] args) {
        GaodeResponse gaodeResponse = GaodeUtils.requestGaode("120.0328,30.239643", "120.554446,27.97816");
        System.out.println(getFastGuideEdge(gaodeResponse));
    }

}
