package pl.mdcode.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pl.mdcode.domain.enumeration.CAMPAING_ACTIVITY_STATUS;

import pl.mdcode.domain.enumeration.ACTIVITY_TYPE;

/**
 * A CampaignActivity.
 */
@Entity
@Table(name = "campaign_activity")
public class CampaignActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "campaign_activity", nullable = false)
    private CAMPAING_ACTIVITY_STATUS campaignActivity;

    @NotNull
    @Size(max = 300)
    @Column(name = "text", length = 300, nullable = false)
    private String text;

    @NotNull
    @Size(max = 500)
    @Column(name = "link_to_activity", length = 500, nullable = false)
    private String linkToActivity;

    @NotNull
    @Size(max = 100)
    @Column(name = "nick_in_activity", length = 100, nullable = false)
    private String nickINActivity;

    @NotNull
    @Column(name = "number_of_links_to_campaign_pages", nullable = false)
    private Integer numberOfLinksToCampaignPages;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ACTIVITY_TYPE activityType;

    @OneToOne
    @JoinColumn(unique = true)
    private AgentRate agentRate;

    @ManyToOne(optional = false)
    @NotNull
    private Campaign campaign;

    @ManyToMany
    @JoinTable(name = "campaign_activity_agent",
               joinColumns = @JoinColumn(name="campaign_activities_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="agents_id", referencedColumnName="id"))
    private Set<Agent> agents = new HashSet<>();

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

    public CampaignActivity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public CampaignActivity url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public CampaignActivity creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public CAMPAING_ACTIVITY_STATUS getCampaignActivity() {
        return campaignActivity;
    }

    public CampaignActivity campaignActivity(CAMPAING_ACTIVITY_STATUS campaignActivity) {
        this.campaignActivity = campaignActivity;
        return this;
    }

    public void setCampaignActivity(CAMPAING_ACTIVITY_STATUS campaignActivity) {
        this.campaignActivity = campaignActivity;
    }

    public String getText() {
        return text;
    }

    public CampaignActivity text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLinkToActivity() {
        return linkToActivity;
    }

    public CampaignActivity linkToActivity(String linkToActivity) {
        this.linkToActivity = linkToActivity;
        return this;
    }

    public void setLinkToActivity(String linkToActivity) {
        this.linkToActivity = linkToActivity;
    }

    public String getNickINActivity() {
        return nickINActivity;
    }

    public CampaignActivity nickINActivity(String nickINActivity) {
        this.nickINActivity = nickINActivity;
        return this;
    }

    public void setNickINActivity(String nickINActivity) {
        this.nickINActivity = nickINActivity;
    }

    public Integer getNumberOfLinksToCampaignPages() {
        return numberOfLinksToCampaignPages;
    }

    public CampaignActivity numberOfLinksToCampaignPages(Integer numberOfLinksToCampaignPages) {
        this.numberOfLinksToCampaignPages = numberOfLinksToCampaignPages;
        return this;
    }

    public void setNumberOfLinksToCampaignPages(Integer numberOfLinksToCampaignPages) {
        this.numberOfLinksToCampaignPages = numberOfLinksToCampaignPages;
    }

    public ACTIVITY_TYPE getActivityType() {
        return activityType;
    }

    public CampaignActivity activityType(ACTIVITY_TYPE activityType) {
        this.activityType = activityType;
        return this;
    }

    public void setActivityType(ACTIVITY_TYPE activityType) {
        this.activityType = activityType;
    }

    public AgentRate getAgentRate() {
        return agentRate;
    }

    public CampaignActivity agentRate(AgentRate agentRate) {
        this.agentRate = agentRate;
        return this;
    }

    public void setAgentRate(AgentRate agentRate) {
        this.agentRate = agentRate;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public CampaignActivity campaign(Campaign campaign) {
        this.campaign = campaign;
        return this;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Set<Agent> getAgents() {
        return agents;
    }

    public CampaignActivity agents(Set<Agent> agents) {
        this.agents = agents;
        return this;
    }

    public CampaignActivity addAgent(Agent agent) {
        this.agents.add(agent);
        agent.getCampaignActivities().add(this);
        return this;
    }

    public CampaignActivity removeAgent(Agent agent) {
        this.agents.remove(agent);
        agent.getCampaignActivities().remove(this);
        return this;
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
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
        CampaignActivity campaignActivity = (CampaignActivity) o;
        if (campaignActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campaignActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CampaignActivity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", campaignActivity='" + getCampaignActivity() + "'" +
            ", text='" + getText() + "'" +
            ", linkToActivity='" + getLinkToActivity() + "'" +
            ", nickINActivity='" + getNickINActivity() + "'" +
            ", numberOfLinksToCampaignPages='" + getNumberOfLinksToCampaignPages() + "'" +
            ", activityType='" + getActivityType() + "'" +
            "}";
    }
}
