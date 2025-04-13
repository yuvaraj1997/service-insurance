package com.boltech.service_insurance.repository.custom;

import com.boltech.service_insurance.constant.UserPolicyConstants;
import com.boltech.service_insurance.dto.dashboard.DailyCount;
import com.boltech.service_insurance.model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

import static com.boltech.service_insurance.util.DateUtil.nowZoneDateTime;

@Repository
public class CustomUserPolicyRepositoryImpl implements CustomUserPolicyRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<DailyCount> getUserPolicyCount(int numberOfDays) {
        ZonedDateTime now = nowZoneDateTime();
        Instant sevenDaysAgo = now.minusDays(6).toInstant(); // includes today

        MatchOperation matchLast7Days = Aggregation.match(
                Criteria.where("createdAt").gte(sevenDaysAgo)
        );

        ProjectionOperation projectToDate = Aggregation.project()
                .andExpression("dateToString('%Y-%m-%d', createdAt)").as("date");

        GroupOperation groupByDate = Aggregation.group("date").count().as("policiesIssued");

        SortOperation sortByDate = Aggregation.sort(Sort.Direction.ASC, "_id");

        ProjectionOperation renameIdToDate = Aggregation.project()
                .and("_id").as("date")
                .and("policiesIssued").as("policiesIssued")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(
                matchLast7Days,
                projectToDate,
                groupByDate,
                sortByDate,
                renameIdToDate
        );
        return mongoTemplate.aggregate(aggregation, "user_policies", DailyCount.class).getMappedResults();
    }
}
