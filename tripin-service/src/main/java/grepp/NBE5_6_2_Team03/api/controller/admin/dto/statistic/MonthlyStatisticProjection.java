// grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic.MonthlyStatisticProjection.java
package grepp.NBE5_6_2_Team03.api.controller.admin.dto.statistic;

public interface MonthlyStatisticProjection {
    Integer getMonth(); // select month(...) as month
    Long getCount();   // select count(*) as count
}