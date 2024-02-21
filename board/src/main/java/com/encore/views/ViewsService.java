package com.encore.views;

import com.encore.post.domain.Post;
import com.encore.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ViewsService {
    private final PostRepository postRepository;
    private final ViewsRepository viewsRepository;

    public ViewsService(PostRepository postRepository, ViewsRepository viewsRepository) {
        this.postRepository = postRepository;
        this.viewsRepository = viewsRepository;
    }


    /*
     * @most view API
     * Month, Week, Day
     * except 0
     * */
    public List<ViewsDto> DailyMostTop10Viewd() {
        LocalDate now = LocalDate.now();
        LocalDateTime today = now.atStartOfDay();

        List<Long> DailyMostTop10ViewPostIdList = viewsRepository.findTop10PostIdsByCreatedAtAfterOrderByViewsCountDesc(today);
        List<Post> DailyTop10MostViewdPosts = postRepository.findAllById(DailyMostTop10ViewPostIdList);



        DailyTop10MostViewdPosts.sort(Comparator.comparing(post -> DailyMostTop10ViewPostIdList.indexOf(post.getId())));

        List<ViewsDto> DailyTop10MostViewdPostsDto = calculateAndSetRank(DailyTop10MostViewdPosts);
        return DailyTop10MostViewdPostsDto;
    }


    public List<ViewsDto> WeekMostTop10Viewd() {

        List<Object[]> top10PostIdsOrderByWeeklyViewsDesc = viewsRepository.findTop10PostIdsOrderByWeeklyViewsDesc(7);
        for (Object[] i : top10PostIdsOrderByWeeklyViewsDesc) {
            System.out.println(i[0] + ":" + i[1]);
        }
        List<Long> WeekMostTop10ViewPostIdList = getPostIdList(top10PostIdsOrderByWeeklyViewsDesc);
        List<Post> WeekTop10MostViewdPosts = postRepository.findAllById(WeekMostTop10ViewPostIdList);
        WeekTop10MostViewdPosts.sort(Comparator.comparing(post -> WeekMostTop10ViewPostIdList.indexOf(post.getId())));

        List<ViewsDto> WeekTop10MostViewdPostsDto = calculateAndSetRank(WeekTop10MostViewdPosts);
        return WeekTop10MostViewdPostsDto;
    }


    public List<ViewsDto> MonthMostTop10Viewd() {

        List<Object[]> top10PostIdsOrderByWeeklyViewsDesc = viewsRepository.findTop10PostIdsOrderByWeeklyViewsDesc(30);
        List<Long> MonthMostTop10ViewPostIdList = getPostIdList(top10PostIdsOrderByWeeklyViewsDesc);
        List<Post> MostViewedPostsMonthlyPosts = postRepository.findAllById(MonthMostTop10ViewPostIdList);

        MostViewedPostsMonthlyPosts.sort(Comparator.comparing(post -> MonthMostTop10ViewPostIdList.indexOf(post.getId())));

        List<ViewsDto> MostViewedPostsMonthlyPostsDto = calculateAndSetRank(MostViewedPostsMonthlyPosts);
        return MostViewedPostsMonthlyPostsDto;
    }

    private static List<Long> getPostIdList(List<Object[]> top10PostIdsOrderByWeeklyViewsDesc) {
        List<Long> MostTop10ViewPostIdList = top10PostIdsOrderByWeeklyViewsDesc.stream()
                .map(array -> ((BigInteger) array[0]).longValue())
                .collect(Collectors.toList());
        return MostTop10ViewPostIdList;
    }

    private List<ViewsDto> calculateAndSetRank(List<Post> mostViewedPosts) {

        List<ViewsDto> rankedViewsDtoList = new ArrayList<>();

        AtomicInteger rank = new AtomicInteger(1);
        return mostViewedPosts.stream()
                .map(post -> ViewsDto.ToViewsDto(post, rank.getAndIncrement()))
                .collect(Collectors.toList());
    }
}
