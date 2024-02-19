package com.encore.views;

import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ViewsService {
    private final PostRepository postRepository;
    public ViewsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    /*
    * @most view API
    * Month, Week, Day
    * except 0
    * */
    public List<ViewsDto> DailyMostTop10Viewd() {
        LocalDate now = LocalDate.now();
        LocalDateTime today = now.atStartOfDay();
        List<Post> mostViewPostsDaily = postRepository.findFirst10ByViewsCreatedAtAfterOrderByViewsCreatedAtDesc(today);
        List<ViewsDto> DailyMostTop10Viewd = calculateAndSetRank(mostViewPostsDaily);
        return DailyMostTop10Viewd;
    }

    public List<ViewsDto> WeekMostTop10Viewd() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        List<Post> mostViewPostWeek = postRepository.findFirst10ByViewsCreatedAtAfterOrderByViewsCreatedAtDesc(oneWeekAgo);
        List<ViewsDto> WeekMostTop10Viewd = calculateAndSetRank(mostViewPostWeek);
        return WeekMostTop10Viewd;
    }


    public List<ViewsDto> MonthMostTop10Viewd() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusYears(1);
        List<Post> mostViewedPostsMonthly = postRepository.findFirst10ByViewsCreatedAtAfterOrderByViewsCreatedAtDesc(oneMonthAgo);
        List<ViewsDto> MonthMostTop10Viewd = calculateAndSetRank(mostViewedPostsMonthly);
        return MonthMostTop10Viewd;
    }

    private List<ViewsDto> calculateAndSetRank(List<Post> mostViewedPosts) {

        List<ViewsDto> rankedViewsDtoList = new ArrayList<>();

        AtomicInteger rank = new AtomicInteger(1);
        return mostViewedPosts.stream()
                .map(post -> ViewsDto.ToViewsDto(post, rank.getAndIncrement()))
                .collect(Collectors.toList());
    }
}
