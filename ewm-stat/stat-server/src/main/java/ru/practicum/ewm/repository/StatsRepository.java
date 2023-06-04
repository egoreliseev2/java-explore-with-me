package ru.practicum.ewm.repository;

import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Long> {
    @Query("select new ru.practicum.ewm.model.ViewStats(hit.app, hit.uri, count(distinct hit.ip)) " +
            "from EndpointHit hit " +
            "where hit.timestamp >= :start " +
            "and hit.timestamp <= :end " +
            "and hit.uri IN (:uris) " +
            "group by hit.app, hit.uri " +
            "order by count(distinct hit.ip) desc")
    List<ViewStats> getStatsUnique(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end,
                                   @Param("uris") List<String> uris);

    @Query("select new ru.practicum.ewm.model.ViewStats(hit.app, hit.uri, count(hit.ip)) " +
            "from EndpointHit hit " +
            "where hit.timestamp >= :start " +
            "and hit.timestamp <= :end " +
            "and hit.uri IN (:uris) " +
            "group by hit.app, hit.uri " +
            "order by count(hit.ip) desc")
    List<ViewStats> getStatsNotUnique(@Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end,
                                      @Param("uris") List<String> uris);

    @Query("select new ru.practicum.ewm.model.ViewStats(h.app,h.uri, count(h.ip)) " +
            "from EndpointHit as h " +
            "WHERE h.timestamp > ?1 " +
            "AND h.timestamp<?2 " +
            "Group by h.app, h.uri " +
            "order by count(h.ip) desc")
    List<ViewStats> getHits(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.model.ViewStats(h.app,h.uri, count(distinct  h.ip)) " +
            "from EndpointHit as h " +
            "WHERE h.timestamp > ?1 " +
            "AND h.timestamp<?2 " +
            "Group by h.app, h.uri " +
            "order by count(distinct  h.ip) desc")
    List<ViewStats> getHitsUniqueIp(LocalDateTime start, LocalDateTime end);
}
