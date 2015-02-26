package com.expenses.domain.entities;

import com.expenses.domain.adapter.deserialize.JsonDeserializerDate;
import com.expenses.domain.adapter.serialize.JsonSerializeDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Andres on 13/11/2014.
 */

@Entity
@Table(name = "expense")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "description")
	private String description;

    @JsonSerialize(using = JsonSerializeDate.class)
    @JsonDeserialize(using = JsonDeserializerDate.class)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name = "comment")
	private String comment;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @PrimaryKeyJoinColumn
    @JsonBackReference("user-expense")
    private User user;

    public Expense() {
		super();
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        Expense rhs = (Expense) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.description, rhs.description)
                .append(this.date, rhs.date)
                .append(this.comment, rhs.comment)
                .append(this.amount, rhs.amount)
                .append(this.user, rhs.user)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(description)
                .append(date)
                .append(comment)
                .append(amount)
                .append(user)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("description", description)
                .append("date", date)
                .append("comment", comment)
                .append("amount", amount)
                .append("user", user)
                .toString();
    }
}
