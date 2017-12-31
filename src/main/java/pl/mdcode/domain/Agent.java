package pl.mdcode.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import pl.mdcode.domain.enumeration.LEGAL_PERSONALITY;

import pl.mdcode.domain.enumeration.AGENT_STATUS;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "short_description", length = 100, nullable = false)
    private String shortDescription;

    @NotNull
    @Size(max = 1000)
    @Column(name = "full_description", length = 1000, nullable = false)
    private String fullDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "legal_personality")
    private LEGAL_PERSONALITY legalPersonality;

    @Enumerated(EnumType.STRING)
    @Column(name = "agent_status")
    private AGENT_STATUS agentStatus;

    @NotNull
    @Column(name = "preferred_rate", precision=10, scale=2, nullable = false)
    private BigDecimal preferredRate;

    @ManyToMany(mappedBy = "agents")
    @JsonIgnore
    private Set<CampaignActivity> campaignActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Agent shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public Agent fullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
        return this;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public LEGAL_PERSONALITY getLegalPersonality() {
        return legalPersonality;
    }

    public Agent legalPersonality(LEGAL_PERSONALITY legalPersonality) {
        this.legalPersonality = legalPersonality;
        return this;
    }

    public void setLegalPersonality(LEGAL_PERSONALITY legalPersonality) {
        this.legalPersonality = legalPersonality;
    }

    public AGENT_STATUS getAgentStatus() {
        return agentStatus;
    }

    public Agent agentStatus(AGENT_STATUS agentStatus) {
        this.agentStatus = agentStatus;
        return this;
    }

    public void setAgentStatus(AGENT_STATUS agentStatus) {
        this.agentStatus = agentStatus;
    }

    public BigDecimal getPreferredRate() {
        return preferredRate;
    }

    public Agent preferredRate(BigDecimal preferredRate) {
        this.preferredRate = preferredRate;
        return this;
    }

    public void setPreferredRate(BigDecimal preferredRate) {
        this.preferredRate = preferredRate;
    }

    public Set<CampaignActivity> getCampaignActivities() {
        return campaignActivities;
    }

    public Agent campaignActivities(Set<CampaignActivity> campaignActivities) {
        this.campaignActivities = campaignActivities;
        return this;
    }

    public Agent addCampaignActivity(CampaignActivity campaignActivity) {
        this.campaignActivities.add(campaignActivity);
        campaignActivity.getAgents().add(this);
        return this;
    }

    public Agent removeCampaignActivity(CampaignActivity campaignActivity) {
        this.campaignActivities.remove(campaignActivity);
        campaignActivity.getAgents().remove(this);
        return this;
    }

    public void setCampaignActivities(Set<CampaignActivity> campaignActivities) {
        this.campaignActivities = campaignActivities;
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
        Agent agent = (Agent) o;
        if (agent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", shortDescription='" + getShortDescription() + "'" +
            ", fullDescription='" + getFullDescription() + "'" +
            ", legalPersonality='" + getLegalPersonality() + "'" +
            ", agentStatus='" + getAgentStatus() + "'" +
            ", preferredRate='" + getPreferredRate() + "'" +
            "}";
    }
}
