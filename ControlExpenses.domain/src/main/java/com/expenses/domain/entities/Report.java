package com.expenses.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by Andres on 17/11/2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Report {

    private Double totalAmount;
    private Double dailyAverage;
    private List<Expense> expenses;

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getDailyAverage() {
        return dailyAverage;
    }

    public void setDailyAverage(Double dailyAverage) {
        this.dailyAverage = dailyAverage;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Report rhs = (Report) obj;
        return new EqualsBuilder()
                .append(this.totalAmount, rhs.totalAmount)
                .append(this.dailyAverage, rhs.dailyAverage)
                .append(this.expenses, rhs.expenses)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(totalAmount)
                .append(dailyAverage)
                .append(expenses)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("totalAmount", totalAmount)
                .append("dailyAverage", dailyAverage)
                .append("expenses", expenses)
                .toString();
    }
}