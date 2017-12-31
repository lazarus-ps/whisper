package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pl.mdcode.domain.enumeration.CAMPAING_STATUS;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "campaign_activity", nullable = false)
    private String campaignActivity;

    @NotNull
    @Column(name = "list_of_posts_for_activity", nullable = false)
    private Integer listOfPostsForActivity;

    @NotNull
    @Column(name = "execution_status", nullable = false)
    private Integer executionStatus;

    @NotNull
    @Column(name = "budget", nullable = false)
    private Double budget;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "campaign_status", nullable = false)
    private CAMPAING_STATUS campaignStatus;

    @NotNull
    @Column(name = "parent_campaign", nullable = false)
    private Integer parentCampaign;

    @ManyToOne
    private PrincipleSubscription principleSubscription;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Campaign name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Campaign description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Campaign startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Campaign endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCampaignActivity() {
        return campaignActivity;
    }

    public Campaign campaignActivity(String campaignActivity) {
        this.campaignActivity = campaignActivity;
        return this;
    }

    public void setCampaignActivity(String campaignActivity) {
        this.campaignActivity = campaignActivity;
    }

    public Integer getListOfPostsForActivity() {
        return listOfPostsForActivity;
    }

    public Campaign listOfPostsForActivity(Integer listOfPostsForActivity) {
        this.listOfPostsForActivity = listOfPostsForActivity;
        return this;
    }

    public void setListOfPostsForActivity(Integer listOfPostsForActivity) {
        this.listOfPostsForActivity = listOfPostsForActivity;
    }

    public Integer getExecutionStatus() {
        return executionStatus;
    }

    public Campaign executionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
        return this;
    }

    public void setExecutionStatus(Integer executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Double getBudget() {
        return budget;
    }

    public Campaign budget(Double budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public CAMPAING_STATUS getCampaignStatus() {
        return campaignStatus;
    }

    public Campaign campaignStatus(CAMPAING_STATUS campaignStatus) {
        this.campaignStatus = campaignStatus;
        return this;
    }

    public void setCampaignStatus(CAMPAING_STATUS campaignStatus) {
        this.campaignStatus = campaignStatus;
    }

    public Integer getParentCampaign() {
        return parentCampaign;
    }

    public Campaign parentCampaign(Integer parentCampaign) {
        this.parentCampaign = parentCampaign;
        return this;
    }

    public void setParentCampaign(Integer parentCampaign) {
        this.parentCampaign = parentCampaign;
    }

    public PrincipleSubscription getPrincipleSubscription() {
        return principleSubscription;
    }

    public Campaign principleSubscription(PrincipleSubscription principleSubscription) {
        this.principleSubscription = principleSubscription;
        return this;
    }

    public void setPrincipleSubscription(PrincipleSubscription principleSubscription) {
        this.principleSubscription = principleSubscription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Campaign campaign = (Campaign) o;
        if (campaign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", campaignActivity='" + getCampaignActivity() + "'" +
            ", listOfPostsForActivity='" + getListOfPostsForActivity() + "'" +
            ", executionStatus='" + getExecutionStatus() + "'" +
            ", budget='" + getBudget() + "'" +
            ", campaignStatus='" + getCampaignStatus() + "'" +
            ", parentCampaign='" + getParentCampaign() + "'" +
            "}";
    }
}
