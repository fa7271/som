package com.encore.views;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import com.encore.post.domain.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board/post")
public class ViewsController {
    private final ViewsService viewsService;

    public ViewsController(ViewsService viewsService) {
        this.viewsService = viewsService;
    }

    @GetMapping("/allrank")
    public SomException getMostViewedPostsAllPeriods() {

        Map<String, List<ViewsDto>> allPeriodsData = new HashMap<>();
        allPeriodsData.put("daily", viewsService.DailyMostViewd());
        allPeriodsData.put("weekly", viewsService.WeekMostViewd());
        allPeriodsData.put("monthly", viewsService.MontMostViewd());

        return new SomException(ResponseCode.SUCCESS, allPeriodsData);
//        return ResponseEntity.ok(allPeriodsData);
    }
}
