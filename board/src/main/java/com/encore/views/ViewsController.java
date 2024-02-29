package com.encore.views;

import com.encore.common.support.ResponseCode;
import com.encore.common.support.SomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board/post")
public class ViewsController {
    private final ViewsService viewsService;

    public ViewsController(ViewsService viewsService) {
        this.viewsService = viewsService;
    }
    @GetMapping("today")
    public SomException getMostViewdTop10PostToday() {
        List<ViewsDto> postResDtos = viewsService.DailyMostTop10Viewd();
        return new SomException(ResponseCode.SUCCESS, postResDtos);
    }
    @GetMapping("week")
    public SomException getMostViewdTop10PostWeek() {
        List<ViewsDto> postResDtos = viewsService.WeekMostTop10Viewd();
        return new SomException(ResponseCode.SUCCESS, postResDtos);
    }
    @GetMapping("month")
    public SomException getMostViewdTop10PostMonth() {
        List<ViewsDto> postResDtos = viewsService.MonthMostTop10Viewd();
        return new SomException(ResponseCode.SUCCESS, postResDtos);
    }
}
